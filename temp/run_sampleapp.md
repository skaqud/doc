#개요

Spring 기반의 J2EE 서비스의 구성에 대해 설명하고 실제 그 Sample Application을 동작시켜 내용을 확인합니다.

* Sample Application

- spring-petclinic(https://github.com/spring-projects/spring-petclinic)
- spring-webapp-template(https://github.com/making/spring-webapp-template)


#프로젝트의 구성

###파일 및 디렉토리의 구성

* 참고 - http://addio3305.tistory.com/39 의 프로젝트의 구조 그림 참고

    /src/main/java
    /src/main/resources
    /src/test/java
    /src/test/resources
    /src/main/webapps
    /target

일반적으로 위와 같이 고정된 경로를 사용하며, 프로젝트 구동시 해당 디렉토리가 없을 경우 오류가 발생하기도 합니다. 

###web.xml(/src/main/webapps)

웹 어플리케이션의 설정을 가지고 있는 파일입니다. 웹 어플리케이션 구동의 시작점이라고 생각하면 될 것 같습니다.
dispacher서블릿 설정과 서블릿 설정파일(관련 뷰 설정, 에러페이지 설정, 템플릿 설정 등)
이 들어있습니다.

    <!-- 웹 어플리케이션 이름 및 description -->
    <display-name>Spring PetClinic</display-name>
    <description>Spring PetClinic sample application</description>

    
    <!-- view관련 설정파일, listener 설정 -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>
            classpath*:META-INF/spring/applicationContext.xml
            classpath*:META-INF/spring/spring-security.xml
        </param-value>
    </context-param>

    Dispatcher 서블릿 설정 
    <servlet>
        <servlet-name>appServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath*:META-INF/spring/spring-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>appServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!-- 이외 filter 설정, error페이지 설정 등 -->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <error-page>
        <error-code>404</error-code>
        <location>/WEB-INF/views/notFoundError.jsp?exceptionCode=e.tm.9404</location>
    </error-page>


###spring config file(/src/main/resources)

프로젝트에서 사용하는 모든 스프링 설정 파일들이 들어있으며, 용도에 따라 -datasource, transaction, security 등을 붙여 씁니다. spring 버전이 향상됨에 따라 될 수 있으면 설정에 들어가는 내용을 줄이고, 소스에 annotation 형태로 들어가는 형태로 많이 사용합니다. 설정은 너무 많으면 복잡해지고, 너무 적을 경우 각각의 소스를 직접 보지 않으면 전체적인 형태를 파악하기 힘든, 각각 장 단점을 가지고 있습니다. 어플리케이션의 용도에 따라 해당 파일이 있을 수도, 없을 수도 있습니다.

참고 : http://www.nextree.co.kr/p5864/
    
    <!-- spring-mvc.xml -->
    <bean id="viewResolver"
        class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/" />
        <property name="suffix" value=".jsp" />
        <property name="order" value="2" />
    </bean>
    <bean
        class="org.springframework.web.servlet.view.tiles2.TilesViewResolver">
        <property name="order" value="1" />
    </bean>
    <bean class="org.springframework.web.servlet.view.tiles2.TilesConfigurer">
        <property name="definitions">
            <list>
                <value>/WEB-INF/tiles/tiles-definitions.xml</value>
            </list>
        </property>
    </bean>
    <!-- applicationContext.xml, projectName-env.xml-->
    <bean id="realDataSource" class="org.apache.commons.dbcp.BasicDataSource"
        destroy-method="close">
        <property name="driverClassName" value="${database.driverClassName}" />
        <property name="url" value="${database.url}" />
        <property name="username" value="${database.username}" />
        <property name="password" value="${database.password}" />
        <property name="testOnBorrow" value="true" />
        <property name="testOnReturn" value="true" />
        <property name="testWhileIdle" value="true" />
        <property name="timeBetweenEvictionRunsMillis" value="1800000" />
        <property name="numTestsPerEvictionRun" value="3" />
        <property name="minEvictableIdleTimeMillis" value="1800000" />
        <property name="defaultAutoCommit" value="false" />
    </bean>
    <bean id="dataSource" class="net.sf.log4jdbc.Log4jdbcProxyDataSource">
        <constructor-arg name="realDataSource" ref="realDataSource" />
    </bean>


###controller,service,model(/src/main/java)

일반적으로 controller - Service(ServiceImpl) - Model(DAO-model) 형태로 구성하여 사용합니다.

    //UserSearchController.java 
    @RequestMapping("list")
    public String list(@PageableDefaults Pageable pageable, Model model) {
        Page<User> page = userService.findAll(pageable);
        model.addAttribute("page", page);
        return "user/list";
    }
    //UserServiceImpl.java
    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        return page;
    }
    //UserRepository.java
    public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT x FROM User x WHERE x.name LIKE :name% ORDER BY x.id", countQuery = "SELECT COUNT(x) FROM User x WHERE x.name LIKE :name%")
    Page<User> findByNameLike(@Param("name") String name, Pageable page);
}


###빌드 도구 maven(/pom.xml)

프로젝트의 라이브러리 목록을 관리하고 프로젝트를 빌드하며 테스트하는 설정을 가지고 있습니다. 이전 빌드툴 도구인 Ant에 이어 가장 많이 사용되며 현재는 Gradle과 함께 쓰이고 있습니다. 기본적으로 프로젝트의 / 경로 바로 하위의 pom.xml 파일에 기록되며, 다음과 같은 내용들을 가지고 있습니다.

    <!-- 프로젝트 정보 설정 - name, version -->
    <modelVersion>4.0.0</modelVersion>
    <groupId>xxxxxx.yyyyyy.zzzzzz</groupId>
    <artifactId>projectName</artifactId>
    <packaging>war</packaging>
    <version>1.0.0-SNAPSHOT</version>

    <!-- Repository 설정 - snapshot, release -->
    <repositories>
        <repository>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
            <id>central</id>
            <name>Maven Central repository</name>
            <url>http://repo1.maven.org/maven2/</url>
        </repository>
        <repository>
            <id>amateras</id>
            <name>Project Amateras Maven2 Repository</name>
            <url>http://amateras.sourceforge.jp/mvn/</url>
        </repository>
    </repositories>
    
    <!-- 관련 라이브러리 dependency -->
    <dependency>
        <groupId>commons-io</groupId>
        <artifactId>commons-io</artifactId>
        <version>${commons-io.version}</version>
    </dependency>
    
    <!-- 빌드 플러그인 도구 설정 -->
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <configuration>
            <includes>
                <include>**/*Test.java</include>
            </includes>
        </configuration>
    </plugin>

    <!-- 그밖에 Code test,inspection 도구 설정 -->


#그밖에

이론적인 배경이나 프로세스, 원리에 대한 부분은 전자정부 프레임워크의 http://www.egovframe.go.kr/wiki/doku.php?id=egovframework:실행환경가이드 를 참고
다만, 현재 Spring 버전에 비해 버전 업 속도가 느린편이며, 신기술이 빠르게 반영되지 않는 단점이 있음.

#참고 

기본적인 Spring project에 관해 비교적 잘 설명된 블로그

    - http://addio3305.tistory.com/41
    - http://addio3305.tistory.com/43 

전자정부 표준 프레임워크 사이트의 실행환경 가이드

    - http://www.egovframe.go.kr/wiki/doku.php?id=egovframework:실행환경가이드