# 변수 선언
REPO:=/home/user/github/Snort_WAS_login
SERVLET_JAR:=/opt/tomcat9/lib/servlet-api.jar

HELLO_SRC:=$(REPO)/hello/src/HelloServlet.java
HELLO_CLASS:=$(REPO)/hello/WEB-INF/classes

LOGIN_SRC:=$(REPO)/login/src/LoginServlet.java
LOGIN_AUTH_SRC:=$(REPO)/login/src/LoginAuthServlet.java
LOGIN_CLASS:=$(REPO)/login/WEB-INF/classes


# define command
.PHONY: pull jhello jlogin jloginauth restart hello login clean all


# git pull
pull:
	git pull

# compile
jhello:
	javac -classpath "$(SERVLET_JAR)" -d $(HELLO_CLASS) $(HELLO_SRC)
jlogin:
	javac -classpath "$(SERVLET_JAR)" -d $(LOGIN_CLASS) $(LOGIN_SRC)
jloginauth:
	javac -classpath "$(SERVLET_JAR)" -d $(LOGIN_CLASS) $(LOGIN_AUTH_SRC)
	
# systemctl
restart:
	systemctl stop tomcat && systemctl start tomcat


# commands
hello: pull jhello restart
login: pull jlogin jloginauth restart
all: pull jhello jlogin jloginauth restart


# compile 파일 초기화
clean:
	rm -f $(HELLO_CLASS)/HelloServlet.class
	rm -f $(LOGIN_CLASS)/LoginServlet.class
	rm -f $(LOGIN_CLASS)/LoginAuthServlet.class
