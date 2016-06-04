#개요

Spring 기반의 J2EE 서비스의 구성에 대해 설명하고 실제 그 Sample Application을 동작시켜 내용을 확인합니다.

* Sample Application

- spring-petclinic(https://github.com/spring-projects/spring-petclinic)
- spring-webapp-template(https://github.com/making/spring-webapp-template)


#프로젝트의 구성

###파일 및 디렉토리의 구성

* 참고 - http://addio3305.tistory.com/39 의 프로젝트의 구조 그림 참고

-/src/main/java
-/src/main/resources
-/src/test/java
-/src/test/resources
-/src/main/webapps
-/target

###web.xml(/src/main/webapps)

웹 어플리케이션의 설정을 가지고 있는 파일입니다. 웹 어플리케이션 구동의 시작점이라고 생각하면 될 것 같습니다.
dispacher서블릿 설정과 서블릿 설정파일(관련 뷰 설정, 에러페이지 설정, 템플릿 설정 등)
이 들어있습니다.

    웹 어플리케이션 이름 및 description
    view관련 설정파일, listener 설정
    Dispatcher 서블릿 설정 
    이외 filter 설정 등

###spring config file(/src/main/resources)

프로젝트에서 사용하는 모든 스프링 설정 파일들이 들어있으며, 용도에 따라 -datasource, transaction, security 등을 붙여 씁니다. spring 버전이 향상됨에 따라 될 수 있으면 설정에 들어가는 내용을 줄이고, 소스에 annotation 형태로 들어가는 형태로 많이 사용합니다.

참고 : http://www.nextree.co.kr/p5864/


###controller,service,model(/src/main/java)

일반적으로 controller - Service(ServiceImpl) - Model(DAO-model) 형태로 구성하여 사용합니다.

    Controller 
    Service(ServiceImpl)
    Model 

###빌드 도구 maven(/pom.xml)

프로젝트의 라이브러리 목록을 관리하고 프로젝트를 빌드하며 테스트하는 설정을 가지고 있습니다. 이전 빌드툴 도구인 Ant에 이어 가장 많이 사용되며 현재는 Gradle과 함께 쓰이고 있습니다. 기본적으로 프로젝트의 / 경로 바로 하위의 pom.xml 파일에 기록되며, 다음과 같은 내용들을 가지고 있습니다.

    프로젝트 정보 설정 - name, version
    Repository 설정 - snapshot, release
    관련 라이브러리 dependency
    빌드 플러그인 도구 설정
    Code test,inspection 도구 설정


#그밖에

이론적인 배경이나 프로세스, 원리에 대한 부분은 전자정부 프레임워크의 http://www.egovframe.go.kr/wiki/doku.php?id=egovframework:실행환경가이드 를 참고
다만, 현재 Spring 버전에 비해 버전 업 속도가 느린편이며, 신기술이 빠르게 반영되지 않는 단점이 있음.

#참고 

      http://addio3305.tistory.com/41
      http://addio3305.tistory.com/43 
