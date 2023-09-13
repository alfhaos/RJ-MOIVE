리엑트와 JPA을 활용한 영화 예약 시스템 입니다!

[사용 기술]
- Frontend
    - React, axios, react-router
- Backend
    - RestApi, quartz, JPA, DATA JPA, QUERY DSL, JUnit 5
- Frame work
    - Spring boot
- DataBase
    - H2, Oracle
 
[기간]
프로젝트 진행 기간 (2023.7 ~ 2023.8)

[ERD]
![image](https://github.com/alfhaos/RJ-MOIVE/assets/87509332/333c38ea-7680-4790-820a-9540bd860b4f)


[구현 기능]

[후기]
- 영화 목록 조회 부터 예약, 결제 , 예약 확인등의 기능을 TDD방법을 이용하여 단위 테스트를 진행하며 개발을 진행하였고 이를 통해 테스트의 중요성을 경험하였습니다.
- 영화 예약시 좌석 및 예약 시간 선택에 따라 페이지 전체를 렌더링 하는 것이 아닌 변경된 부분만 다시 로딩하기 때문에 랜더링을 효율적으로 사용할수 있었습니다.
또한 이전까지는 SSR인 jsp와 thymeleaf만을 가지고 개발을 진행했기에 CSR 방식의 개발을 경험해볼수 있었고 React 활용법을 익혔습니다.
- 실제 서비스 환경에서 발생할수 있는 문제인 동시성 제어에 대한 테스트를 진행해볼수있었으며 이를 통해 동시성 문제에 대한 개념과 해결법을 익힐수 있었습니다.
