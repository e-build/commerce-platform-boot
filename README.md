[comment]: <> (# 🎁 월 1000만명 이상 사용하고 있는 E-commerce 플랫폼들은 어떻게 만들어진 것일까요?)

[comment]: <> (- 대규모 트래픽을 어떻게 처리하고 있을까요?)

[comment]: <> (- 대용량 데이터를 어떻게 다루고 있을까요?)

[comment]: <> (- 이러한 궁금증들을 통해서 직접 당근마켓 서버를 구현해보는 프로젝트를 진행하게 되었습니다.)

[comment]: <> (# 🛒 단순히 E-commerce 의 기능 구현만을 목표로 하지 않았습니다.)

[comment]: <> (* 네이버 스마트 스토어, 지그재그, 오늘의 집과 같은 E-commere 플랫폼들은 어떻게 안정적으로 트래픽을 소화하면서 대용량의 데이터를 처리하고 있는지)

[comment]: <> (* 실시간 푸시 알람 서비스는 어떻게 구현하였는 지)

[comment]: <> (* 확장에 용이한 객체지향 설계는 어떻게 이루어졌는 지)

[comment]: <> (* 클린코드를 위한 유지보수성을 고려한 서비스를 만들기 위해서, 읽기 좋은 코드 객체지향적 설계를 위해 노력하였습니다.)

[comment]: <> (# 🌈 서비스 주요 기능)

[comment]: <> (E-commerce Platform 에는 크게 다음과 같은 기능들이 있습니다. 더 자세한 내용들은 이곳을 참고해 주세요.)

[comment]: <> (- 회원가입 및 로그인)

[comment]: <> (  아이디 중복 체크, 비밀번호 암호화, 회원정보 탈퇴 등 회원가입 및 로그인 관련된 기타 기능 추가)

[comment]: <> (- 등등)

[comment]: <> (# 🌈 사용 기술)

[comment]: <> (- gradle)

[comment]: <> (- Java 11)

[comment]: <> (- Spring boot)

[comment]: <> (- JPA)

[comment]: <> (- Docker)

[comment]: <> (# 🌈 프로젝트 목표)

[comment]: <> (1. 유지보수성)

[comment]: <> (2. 대용량 트래픽을 고려한 아키텍처)

[comment]: <> (3. 테스트 코드)

[comment]: <> (4. 문서화를 통해 협업 효율성을 높이자)

[comment]: <> (   * 프론트엔드-백엔드 팀이 협업하는 환경에서 요청과 응답 방식에 대해 잘 정리된 API 문서는 원활한 커뮤니케이션을 도와주는 좋은 자료가 됩니다.)

[comment]: <> (   뿐만 아니라 외부의 사용자가 우리가 개발한 API를 호출하기 위해서도 API 문서는 필수적입니다.)

[comment]: <> (   하지만 개발을 진행하며 API 문서를 함께 직접 작성하는 것은 비효율적이기 때문에 Spring RestDocs와 같은 툴을 활용하여)

[comment]: <> (   문서 작업을 자동화할 수 있는 방법을 고민합니다.)

[comment]: <> (5. CI/CD를 구축하여 개발 프로세스의 효율성을 높이자)

[comment]: <> (   - 다수의 개발자가 하나의 서비스를 개발해나가는 환경에서는 각자의 코드를 머지하고 충돌을 해결하고 테스트하고 빌드, 배포하는 과정에도 많은 리소스가 소요됩니다. 이러한 문제를 해결하기 위한 방법으로 CI/CD를 직접 구축하여 애자일한 개발 프로세스를 실현하기 위해 노력합니다.)

[comment]: <> (6. 성능 테스트를 통한 성능을 개선하자)

[comment]: <> (   - 실제 서비스 환경에서 트래픽이 몰리는 경우 예상치 못한 문제들이 발생할 수 있기 때문에 성능 테스트 역시 반드시 병행되어야 합니다. 성능 테스트를 통해 병목 지점을 개선하고 컴퓨팅 자원을 더 효율적을 활용할 수 있는 방안들을 고민하여 성능을 향상시키기 위해 노력합니다.)

[comment]: <> (   nGrinder와 Pinpoint와 같은 툴을 이용해 높은 트래픽을 발생시키고 성능을 모니터링하여 개선점을 찾아냅니다.)



[comment]: <> (- 대규모 트래픽을 어떻게 처리하고 있을까요?)

[comment]: <> (- 대용량 데이터를 어떻게 다루고 있을까요?)

[comment]: <> (- 이러한 궁금증들을 통해서 직접 당근마켓 서버를 구현해보는 프로젝트를 진행하게 되었습니다.)

[comment]: <> (# 🛒 단순히 E-commerce 의 기능 구현만을 목표로 하지 않았습니다.)

[comment]: <> (* 네이버 스마트 스토어, 지그재그, 오늘의 집과 같은 E-commere 플랫폼들은 어떻게 안정적으로 트래픽을 소화하면서 대용량의 데이터를 처리하고 있는지  )

[comment]: <> (* 실시간 푸시 알람 서비스는 어떻게 구현하였는 지)

[comment]: <> (* 확장에 용이한 객체지향 설계는 어떻게 이루어졌는 지 )

[comment]: <> (* 클린코드를 위한 유지보수성을 고려한 서비스를 만들기 위해서, 읽기 좋은 코드 객체지향적 설계를 위해 노력하였습니다.)

[comment]: <> (# 🌈 서비스 주요 기능)

[comment]: <> (E-commerce Platform 에는 크게 다음과 같은 기능들이 있습니다. 더 자세한 내용들은 이곳을 참고해 주세요.)

[comment]: <> (- 회원가입 및 로그인)

[comment]: <> (    아이디 중복 체크, 비밀번호 암호화, 회원정보 탈퇴 등 회원가입 및 로그인 관련된 기타 기능 추가)

[comment]: <> (- 등등)

[comment]: <> (# 🌈 사용 기술)

[comment]: <> (- gradle)

[comment]: <> (- Java 11)

[comment]: <> (- Spring boot )

[comment]: <> (- JPA)

[comment]: <> (- Docker)

[comment]: <> (# 🌈 프로젝트 목표)

[comment]: <> (1. 유지보수성)

[comment]: <> (2. 대용량 트래픽을 고려한 아키텍처)

[comment]: <> (3. 테스트 코드)

[comment]: <> (4. 문서화를 통해 협업 효율성을 높이자)

[comment]: <> (   * 프론트엔드-백엔드 팀이 협업하는 환경에서 요청과 응답 방식에 대해 잘 정리된 API 문서는 원활한 커뮤니케이션을 도와주는 좋은 자료가 됩니다. )

[comment]: <> (   뿐만 아니라 외부의 사용자가 우리가 개발한 API를 호출하기 위해서도 API 문서는 필수적입니다.)

[comment]: <> (   하지만 개발을 진행하며 API 문서를 함께 직접 작성하는 것은 비효율적이기 때문에 Spring RestDocs와 같은 툴을 활용하여 )

[comment]: <> (   문서 작업을 자동화할 수 있는 방법을 고민합니다.)

[comment]: <> (5. CI/CD를 구축하여 개발 프로세스의 효율성을 높이자 )

[comment]: <> (   - 다수의 개발자가 하나의 서비스를 개발해나가는 환경에서는 각자의 코드를 머지하고 충돌을 해결하고 테스트하고 빌드, 배포하는 과정에도 많은 리소스가 소요됩니다. 이러한 문제를 해결하기 위한 방법으로 CI/CD를 직접 구축하여 애자일한 개발 프로세스를 실현하기 위해 노력합니다.)

[comment]: <> (6. 성능 테스트를 통한 성능을 개선하자)

[comment]: <> (   - 실제 서비스 환경에서 트래픽이 몰리는 경우 예상치 못한 문제들이 발생할 수 있기 때문에 성능 테스트 역시 반드시 병행되어야 합니다. 성능 테스트를 통해 병목 지점을 개선하고 컴퓨팅 자원을 더 효율적을 활용할 수 있는 방안들을 고민하여 성능을 향상시키기 위해 노력합니다.)

[comment]: <> (   nGrinder와 Pinpoint와 같은 툴을 이용해 높은 트래픽을 발생시키고 성능을 모니터링하여 개선점을 찾아냅니다.)

     
