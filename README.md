# 카카오페이의 사전과제

## 개발 프레임워크
 - IDE : STS-3.9.4.RELEASE
 - Springboot-2.1.3.RELEASE
 - Gradle-5.2.1
 - Java-1.8
 
## 문제해결 전략
 - 요구사항에 부합하기 위해 API 명세 설계
 - 지역명 검색 및 키워드 검색구현을 위한 DB 설계
 	- 지역명은 시도,시군구,읍면동,상세주소 필드로 관리하여 지역명 기반 검색시 속도를 높이도록 함
 	- 프로그램에 연결된 테마나 프로그램에 연결된 지역에 대한 관계 테이블을 두어 프로그램, 지역, 테마에 대한 메타 정보는 고유하게 관리하도록 하고 관계 정보만 변경하여 프로그램의 연관정보를 변경할 수 있도록 함
 	- ID는 프로그램의 코드로 관리함. 이는 추후 DB성능상 Number형의 Auto_Increment하는 형태의 ID로 관리하고 프로그램 코드를 별도로 관리하는 방향으로 수정 필요
 		- 이는 프로그램 ID를 시퀀스 테이블로 관리하고 있는 부분과 같이 고민해야할 문제임 
 - 개발API의 단위 테스트 수행
 	- 클라이언트에 제공하는 API의 유효성 확인을 위해 RestTemplate을 활용한 Controller 테스트 수행
 - 선택 문제를 해결하기 위해 가중치 로직 도입
 - 개발API의 단위 테스트 수행
 - JWT 도입
 - 전체API의 단위 테스트 수행
 
## 빌드 및 실행 방법
 - 빌드 방법
	- STS 활용
		- STS에서 프로젝트를 Import
		- Gradle 라이브러리 동기화
		- Project build
 - 실행 방법
	- STS 활용
		- Run As Spring Boot App으로 실행
	- Jar 파일 활용
		- /build/libs/config/application.properties 에 DB 정보 설정
		- /build/libs/kakaopay-1.0.0.jar 를 직접 수행
			- ex) java -jar kakaopay-1.0.0.jar
			
## API 명세
	POST /init/database
		- 기능 : DB 초기화
		
	POST /data
		- 기능 : 프로그램 추가
	
	PUT /data
		- 기능 : 프로그램 수정
		
	GET /data?regionCode=reg0038
		- 기능 : 프로그램 정보 조회
		
	GET /program/list?region=평창군
		- 기능 : 지역명에 포함되는 프로그램 목록 조회
		
	GET /program/count/region?keyword=세계문화유산
		- 기능 : 프로그램 상세 설명에 포함되는 지역과 수 조회
		
	GET /program/count/word?keyword=문화
		- 기능 : 프로그램 상세 정보에서 단어의 출현빈도수 조회