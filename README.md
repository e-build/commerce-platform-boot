# 🎲 프로젝트 개요
* 쿠팡, 오늘의 집, 무신사와 같은 온라인 이커머스 플랫폼들은 대규모 트래픽을 어떻게 처리하고 있을지
* 사용자 인증, 상품, 주문과 같은 도메인에서 발생하는 대용량 데이터를 어떻게 다루고 있을지
* 이러한 궁금증들을 기반으로 직접 이커머스 플랫폼 서버를 구현해보는 프로젝트를 진행하게 되었습니다.

# 🎲 서비스 주요 기능 
* E-commerce Platform 프로젝트에 대한 기능은 설계 시 정의한 유스케이스와 API 문서를 통해 참고할 수 있습니다.
  * [유스케이스](https://github.com/e-build/commerce-platform-boot/wiki/3.-Usecase)
  * [API 문서](https://github.com/e-build/commerce-platform-boot/wiki/4.-API-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4-%EC%84%A4%EA%B3%84)

# 🎲 사용 기술
* Gradle
* Java 11
* Spring boot, Spring Security (OAuth2, JWT)
* JPA, Querydsl
* Docker
* Jenkins

# 🎲 프로젝트 목표
1. 유지보수 용이한 코드 작성
   * 객체 지향 설계 5원칙과 객체지향 특성을 고려한 설계 고민
   * [리팩토링 정량적 원칙](https://github.com/e-build/commerce-platform-boot/wiki/5.-Techical-Issue) 에 따른 코드 작성
   * [Java](https://newwisdom.tistory.com/96), 
   [Git Commit 컨벤션](https://overcome-the-limits.tistory.com/entry/%ED%98%91%EC%97%85-%ED%98%91%EC%97%85%EC%9D%84-%EC%9C%84%ED%95%9C-%EA%B8%B0%EB%B3%B8%EC%A0%81%EC%9D%B8-git-%EC%BB%A4%EB%B0%8B%EC%BB%A8%EB%B2%A4%EC%85%98-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0) 과 
   [Git Flow](https://hellowoori.tistory.com/56) 에 따른 개발 진행
    
2. 대용량 트래픽을 고려한 아키텍처
   * [시스템 구성](https://github.com/e-build/commerce-platform-boot/wiki/2.-Architecture#%EC%8B%9C%EC%8A%A4%ED%85%9C-%EA%B5%AC%EC%84%B1)
   * [ERD](https://github.com/e-build/commerce-platform-boot/wiki/2.-Architecture#erd)

3. 테스트 코드 작성
   * 레이어 아키텍처별 테스트 코드 작성
     * 안정적인 리팩토링
     * 안정적인 통합 및 배포

4. 문서화
   * 프론트엔드-백엔드 팀이 협업하는 환경에서 요청과 응답에 대해 잘 정리된 API 문서를 고려하여 다음과 같이 문서화를 진행했습니다.
   * 프로젝트 구축 단계에서는 아직 유스케이스와 기능에 대한 정의가 충분히 이루어지지 않았다는 점을 고려하여, 
     * 협의를 통해 기능별로 합의가 도출이 완료된 직후 각각 기능별 API 인터페이스를 작성하며 협업한다고 가정했습니다.
     * 병렬로 진행되는 협업 상황에서 서로 공유하며 작업할 수 있도록 사전에 정의된 [API 문서](https://github.com/e-build/commerce-platform-boot/wiki/4.-API-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4-%EC%84%A4%EA%B3%84)
   * 프로젝트 구축 이후 유지보수 단계에서는 자동화된 API 문서 업데이트를 위하여 Spring RestDocs를 적용했습니다. ***[미완성]***

5. CI/CD를 구축을 통한 개발 효율성 향상
   * 협업하는 환경에서 각자의 코드를 통합, 배포하는 과정에도 많은 리소스가 소요됩니다. 
   * 이러한 리소스를 최소화하기 위한 방법으로 젠킨스를 활용하여 CI/CD 시스템을 구축했습니다.
   * 반복되는 업무에 대한 비용을 최소화하고 비즈니스 로직을 개발하는데 집중하도록 노력했습니다.

6. 성능 테스트를 통한 성능 개선
   * 실제 서비스 환경에서 트래픽이 몰리는 경우 예상치 못한 문제들이 발생할 수 있기 때문에 성능 테스트 역시 반드시 병행되어야 합니다. 성능 테스트를 통해 병목 지점을 개선하고 컴퓨팅 자원을 더 효율적을 활용할 수 있는 방안들을 고민하여 성능을 향상시키기 위해 노력합니다. 
   * 사용한 툴은 다음과 같습니다.
     * JMeter - [성능 테스트 시나리오]() 별로 높은 트래픽을 발생시킵니다. ***[미완성]***
     * Pinpoint - 성능을 모니터링하여 개선점을 찾아냅니다. ***[미완성]***