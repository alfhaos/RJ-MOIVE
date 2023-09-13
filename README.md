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

[Entity 연관관계]
![image](https://github.com/alfhaos/RJ-MOIVE/assets/87509332/e8d4199a-c937-4639-8a8e-0c49415147b9)

[구현 기능]
1. 영화 목록 조회
   
![1](https://github.com/alfhaos/RJ-MOIVE/assets/87509332/12bbd72c-089d-4e6c-ada2-911e7849a20b)

2. 예약 진행 화면
![2](https://github.com/alfhaos/RJ-MOIVE/assets/87509332/fd179755-2932-4461-b7f4-7aa07c44c21c)

   
3. 결제 및 예약 진행
![3](https://github.com/alfhaos/RJ-MOIVE/assets/87509332/df5fa88e-4aee-4f3d-bcda-9514ba3972c8)

4. 예약 목록 조회
![4](https://github.com/alfhaos/RJ-MOIVE/assets/87509332/2f137ac3-0545-4826-940c-c0ec78bed81e)

5. 예약 취소
![5](https://github.com/alfhaos/RJ-MOIVE/assets/87509332/0933ab46-45dd-4a2d-9eb8-f738ffcdfeda)

[동시성 제어 테스트]
![image](https://github.com/alfhaos/RJ-MOIVE/assets/87509332/71fd12f5-eff9-4b8a-a413-4b5df55d3758)

[후기]
- 영화 목록 조회 부터 예약, 결제 , 예약 확인등의 기능을 TDD방법을 이용하여 단위 테스트를 진행하며 개발을 진행하였고 이를 통해 테스트의 중요성을 경험하였습니다.
- 영화 예약시 좌석 및 예약 시간 선택에 따라 페이지 전체를 렌더링 하는 것이 아닌 변경된 부분만 다시 로딩하기 때문에 랜더링을 효율적으로 사용할수 있었습니다.
또한 이전까지는 SSR인 jsp와 thymeleaf만을 가지고 개발을 진행했기에 CSR 방식의 개발을 경험해볼수 있었고 React 활용법을 익혔습니다.
- 실제 서비스 환경에서 발생할수 있는 문제인 동시성 제어에 대한 테스트를 진행해볼수있었으며 이를 통해 동시성 문제에 대한 개념과 해결법을 익힐수 있었습니다.
