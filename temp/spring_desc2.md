# ORM이란 #

ORM(Object Relational Mapping)이란 RDB 테이블을 객체지향적으로 변환하기 위한 기술입니다. RDB 자체는 프로그래밍적인 개념이 고려되지 않았으며, 도메인에 대한 내용들을 저장하는 형태로 데이터와 Relation을 가지고 있습니다. 이를 자바를 통해 좀 더 쉽게 접근하기 위한 방법이 ORM이며  Spring Application상에서 IoC, MVC등과 같이 사용되는 기술로 일반적으로 MyBatis(iBatis)와 Hibernate 등이 많이 사용됩니다.


## MyBatis ##

개발자가 지정한 SQL, 저장 프로시저 및 몇가지 고급 매핑을 지원하는 퍼시스턴트 프레임워크, ORM이라고 본격적으로 보기에는 무리가 있다는 의견도 있다. JDBC로 처리하는 코드와 파라미터의 설정 및 결과 매핑을 대신해주어 DB연결부분을 독립적으로 사용할 수 있도록 해 준다. 

공식 홈 - http://www.mybatis.org/mybatis-3/ko/

참고 

    시작하기 - http://www.mybatis.org/mybatis-3/ko/getting-started.html
    ORM의 장점과 단점 - http://layered.tistory.com/entry/ORM%EC%9D%80-%EC%95%88%ED%8B%B0%ED%8C%A8%ED%84%B4%EC%9D%B4%EB%8B%A4-ORM-is-an-antipattern
    example1 - http://addio3305.tistory.com/62
    example2 - http://jjeong.tistory.com/606


## Hibernate ##
위에서 설명한 JPA구현체 중 하나, MyBatis의 경우 쿼리가 특정 위치에 들어있어, 쿼리 자체를 수정하거나 변경해야 할 필요가 있으나, Hibernate의 경우 그런 작업들이 모두 자바 코드로 이루어짐. 다만, 여러가지 이유(기술 숙성도, 개발자 차이 등)에 따라 Hibernate보다 MyBatis 등이 더 선호되기도 합니다.

참고
    
    http://jjeong.tistory.com/614



** JPA(Java Persistence API)

"JPA는 여러 ORM 전문가가 참여한 EJB 3.0 스펙 작업에서 기존 EJB ORM이던 Entity Bean을 JPA라고 바꾸고 JavaSE, JavaEE를 위한 영속성(persistence) 관리와 ORM을 위한 표준 기술입니다. JPA는 ORM 표준 기술로 Hibernate, OpenJPA, EclipseLink, TopLink Essentials과 같은 구현체가 있고 이에 표준 인터페이스가 바로 JPA입니다."(From http://blog.woniper.net/255)

** Connection Pool

WAS에서 DB로 연결을 생성할 때, 요청이 들어와서 생성하기보다는 미리 생성된 Pool안에서 꺼내와서 성능을 개선하고, 자원을 효율적으로 사용하기 위한 아키텍처

- Connection Pool의 종류

DBCP, c3p0, Proxool, BoneCP, TOMCAT JDBC CONNECTION POOL
위와 같이 다양한 종류의 Connection Pool들이 사용되나 일반적으로 WAS 벤더 혹은 오픈소스 쪽에서 제공하는 Pool을 쓰는 게 가장 좋음.

참고 

    * http://blog.chakannom.com/2015/12/connection-pool.html
    * http://d2.naver.com/helloworld/5102792


# Spring에서 Session을 처리하는 방법 #

## 1. Spring AOP를 이용한 Interceptor처리 ##

전체 프로그램을 수정할 필요 없이 특정 시점(Aspect) 에 해당 요청의 세션유무를 판단하여 세션이 있을 경우 있을때의 처리를, 없을 경우 로그인 요청 페이지를 보여주는 식으로 구성, Spring에서는 Interceptor라는 요소를 통해 DispatcherServlet으로부터 Controller 로 가는 요청을 확인하고 처리합니다.

참고

    http://addio3305.tistory.com/43
    http://blog.acronym.co.kr/590


** AOP란? 관점지향 프로그래밍(Aspect Oriented Programming)

"관점 지향 프로그래밍(Aspect-Oriented Programming, AOP)은 컴퓨팅에서 메인 프로그램의 비즈니스 로직으로부터 2차적 또는 보조 기능들을 고립시키는 프로그램 패러다임이다. 이것은 횡단관심사의 분리를 허용하고, 관점 지향 소프트웨어 개발의 기초를 형성하여 모듈화를 증가시키려 한다. 관점 지향 소프트웨어 개발이 모든 엔지니어링 분야에 관련되는 반면에, 관점 지향 프로그래밍은 소스코드 레벨에서 관심사들의 모듈화를 지원하는 프로그래밍 기술과 툴들을 포함한다."(From 위키백과)

Spring에서 AOP란 공통적으로 발생하는 인증, 권한, 로깅, 트랜잭션 처리 등에 대한 반복적으로 들어갈 수 있는 부분들을 그때그때 처리하지 말고, 공통기능으로 개발한 뒤 삽입할 수 있도록 하는 개념을 말합니다.

## 2. Spring Security 구성방법 ##

Spring Project에서 만들어 놓은 인증과 인가를 전담하는 라이브러리, 혹은 프레임워크. 사용자와 권한 등 Domain 관련 정보들이 고정되어야 하는 위험 때문에 기존시스템을 변경하는 형태로 접근하기 힘드나, 별도로 AOP를 사용하여 구성하는 방법 보다는 좀 더 제어하기 쉽고 안전하다고 생각됩니다. 

참고

    http://www.javaproject.kr/Document/View/1761/[Spring-강좌]-8회---spring-security-이용한-로그인-인증-권한-처리-%3E

