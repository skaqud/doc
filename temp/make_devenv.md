# 개요 

다음과 같은 환경에서 linux(CentOS) 기반으로 WEB-WAS-DB를 구성하고 Sample application deploy 하여 테스트합니다.

세부 테스트환경

- OS : CentOS 7(vagrant box:centos/7)
- WEB : nginx(yum repository 버전)
- WAS : apache-tomcat 7.x,(openjdk 1.8)
- DB - mariadb
- WebApp : spring-petclinic(https://github.com/spring-projects/spring-petclinic) 혹은 spring-webapp-template(https://github.com/making/spring-webapp-template)



# 실행하기

우선은 vagrant + virtualbox로 linux vm을 로컬에서 시작

해당 vm에서 다음과 같이 패키지 관리자로 최신 OS 로 업데이트

	yum update

이상 없이 업데이트 될 경우 기본적인 준비가 된 것으로 봅니다.

### 0) 계정 생성

	#계정 생성
	adduser testu

	#비번 설정
	passwd testu 

### 1) DB 설치

참고 : http://firstboos.tistory.com/entry/CentOS-7-에서-mariadb-설치

위 블로그 내용을 참고해서 기본 패키지관리자를 통해 설치

yum install mariadb

블로그 내용대로 characterset 설정을 한 뒤 서비스를 시작

service mariadb start

root 비번을 적당히 설정한 뒤 DB상태를 확인합니다.


### 2) WAS 설치

우선 tomcat에서 사용할 jdk를 설치합니다.

	yum search openjdk | grep jdk

목록을 확인하여 java 1.7 jdk를 설치합니다.

	yum install java-1.7.0-openjdk.x86_64

다음과 같이 설치를 확인합니다.

	[root@localhost ~]# java -version
	java version "1.7.0_101"
	OpenJDK Runtime Environment (rhel-2.6.6.1.el7_2-x86_64 u101-b00)
	OpenJDK 64-Bit Server VM (build 24.95-b01, mixed mode)

tomcat을 다운로드 합니다. 다운로드 주소는 이곳에서 확인, tomcat의 경우 binary를 다운로드 받은 뒤 특정 디렉토리에 설치합니다. webapps 들이 deploy되어야 하기에 일반적으로 관리될 수 있는 디렉토리에 위치시키는 게 좋을 것 같습니다.

	mkdir app
	cd app
	wget http://apache.mirror.cdnetworks.com/tomcat/tomcat-8/v8.0.35/bin/apache-tomcat-8.0.35.tar.gz

압축을 해제한 뒤 tomcat을 시작하여 정상적으로 기동되는 지 확인합니다.
     
    tar zxvf apache-tomcat-8.0.35.tar.gz
    cd apache-tomcat-8.0.35/bin
    ./startup.sh
    [testu@localhost bin]$ ./startup.sh
	Using CATALINA_BASE:   /home/testu/app/apache-tomcat-8.0.35
	Using CATALINA_HOME:   /home/testu/app/apache-tomcat-8.0.35
	Using CATALINA_TMPDIR: /home/testu/app/apache-tomcat-8.0.35/temp
	Using JRE_HOME:        /
	Using CLASSPATH:       /home/testu/app/apache-tomcat-8.0.35/bin/bootstrap.jar:/home/testu/app/apache-tomcat-8.0.35/bin/tomcat-juli.jar
	Tomcat started.

curl명령 혹은 브라우저를 통해 실행을 확인합니다.


### 3) WEB 설치

아래와 같이 repository를 추가하여 nginx 저장소를 등록한 뒤

	vi /etc/yum.repos.d/nginx.repo

	[nginx]
	name=nginx repo
	baseurl=http://nginx.org/packages/centos/7/$basearch/
	gpgcheck=0
	enabled=1

nginx를 설치합니다.

	yum install nginx

이후 서비스를 시작하고,

	systemctl start nginx

	[root@localhost ~]# ps -ef | grep nginx
	root      2178     1  0 10:33 ?        00:00:00 nginx: master process /usr/sbin/nginx -c /etcnginx/nginx.conf
	nginx     2180  2178  0 10:33 ?        00:00:00 nginx: worker process

curl명령 혹은 브라우저를 통해 실행을 확인합니다.



### 4) WAR 준비 및 테스트

다운로드 페이지(https://eclipse.org/downloads/)에서 

Java EE Developers verison을 다운로드 한 뒤 원하는 곳에 압축을 풀고 실행합니다.




### 5) Deploy 및 통합테스트


