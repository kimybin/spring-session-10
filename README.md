## 1. 프로젝트 소개 
- 멋쟁이사자처럼 14기 백엔드 세션 10주차 과제 
- Spring Boot 기반 재고관리 REST API (UI 없음)
- 이후 Docker/Docker Compose로 컨테이너화하여 배포 환경 구성

## 2. 기술 스택 
- Language: Java 17
- Framework: Spring Boot 3.5.15, Spring Data JPA
- DB: MySQL 8.0
- Infra: Docker, Docker Compose

## 3. 실행 방법 

1. 클론 및 환경변수 설정
```angular2html
git clone <repo-url>
cd spring-session-10
cp .env.example .env
```
- .env 파일을 열어 MYSQL_ROOT_PASSWORD에 원하는 비밀번호를 채워주세요.
2. 컨테이너 실행
```
docker-compose up --build
```
- app(Spring Boot)과 db(MySQL) 두 컨테이너가 함께 뜹니다. 
- db가 healthcheck를 통과한 뒤에만 app이 시작되도록 구성하여, 
- DB 초기화 타이밍 문제를 방지했습니다.

3. 확인
- 서버는 http://localhost:8080 에서 응답합니다.

## 4. API 예시 
1. 재고 등록
```angular2html
POST /inventories
Content-Type: application/json
```
2. Request
```angular2html
{
  "name": "테스트상품",
  "foodType": "WET",
  "expirationDate": "2026-06-12",
  "quantity": 10,
  "unit": "G",
  "nutritionImageURL": "http://example.com/image.jpg",
  "isSnack": false
}
```

3. Response 201 Created
```angular2html
{
  "id": 1,
  "name": "테스트상품",
  "foodType": "WET",
  "expirationDate": "2026-06-12",
  "quantity": 10,
  "unit": "G",
  "nutritionImageURL": null,
  "isSnack": false
}
```

4. 터미널 환경에서 확인하는 법 

- POST 요청 예시
```
curl -X POST http://localhost:8080/inventories \
  -H "Content-Type: application/json" \
  -d '{"name":"테스트상품","quantity":10,"expirationDate":"2026-06-12","foodType":"WET","unit":"G","nutritionImageUrl":"http://example.com/image.jpg","isSnack":false}'
  ```
- POST 응답 예시 
```angular2html
{"id":1,"name":"테스트상품","foodType":"WET","expirationDate":"2026-06-12","quantity":10,"unit":"G","nutritionImageURL":null,"isSnack":false} 
```

## 5. 트러블슈팅 
1. docker-compose.yml의 volume 이름 오타
    - services.db.volumes에서 참조한 volume 이름과, 최하단 volumes: 섹션에 선언한 이름이 일치하지 않아 invalid compose project 에러 발생. 
    - compose는 두 위치의 이름이 정확히 같아야 volume을 인식함을 깨달음 

2. DB 연결 타이밍 이슈 (Connection refused)
   - depends_on만 설정했을 때, db 컨테이너가 시작되자마자 app 컨테이너가 연결을 시도해 Communications link failure로 애플리케이션이 죽는 문제 발생.
   - depends_on은 컨테이너 시작 순서만 보장할 뿐, MySQL이 실제로 연결을 받을 수 있는 상태인지는 보장하지 않음
   - db 서비스에 healthcheck를 추가하고 app이 service_healthy 조건을 기다리도록 설정
```angular2html
db:
    healthcheck:
        test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
        interval: 5s
        timeout: 5s
        retries: 10

app:
    depends_on:
        db:
            condition: service_healthy
```

3. 비밀번호 관리
- application.properties에 DB 비밀번호가 평문으로 있던 것을 발견하고, .gitignore에 추가한 뒤 실제 값이 빠진 application.properties.example을 별도로 커밋
- 컨테이너 환경에서는 docker-compose.yml의 environment 값이 application.properties보다 우선 적용되므로, 실제 값은 .env 파일(gitignore 처리)로만 관리
