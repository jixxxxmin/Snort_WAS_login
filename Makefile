# 변수 선언
REPO:=/home/user/github/Snort_WAS_login
SERVLET_JAR:=/opt/tomcat9/lib/servlet-api.jar

HELLO_SRC:=$(REPO)/hello/src/HelloServlet.java
HELLO_CLASS:=$(REPO)/hello/WEB-INF/classes

LOGIN_SRC:=$(REPO)/login/src/LoginServlet.java
LOGIN_AUTH_SRC:=$(REPO)/login/src/LoginAuthServlet.java
LOGIN_CLASS:=$(REPO)/login/WEB-INF/classes

ADMIN_SRC:=$(REPO)/admin/src/CreateSubmenuServlet.java
ADMIN_CLASS:=$(REPO)/admin/WEB-INF/classes


# define command
.PHONY: pull make_folders hello login loginauth admin restart set build clean


# git pull
pull:
	git pull

# 폴더 생성
make_folders:
	mkdir -p $(HELLO_CLASS)
	mkdir -p $(LOGIN_CLASS)
	mkdir -p $(ADMIN_CLASS)

# compile
hello:
	javac -classpath "$(SERVLET_JAR)" -d $(HELLO_CLASS) $(HELLO_SRC)
login:
	javac -classpath "$(SERVLET_JAR)" -d $(LOGIN_CLASS) $(LOGIN_SRC)
loginauth:
	javac -classpath "$(SERVLET_JAR)" -d $(LOGIN_CLASS) $(LOGIN_AUTH_SRC)
admin:
	javac -classpath "$(SERVLET_JAR)" -d $(ADMIN_CLASS) $(ADMIN_SRC)

# systemctl
restart:
	systemctl stop tomcat && systemctl start tomcat


# commands
set: make_folders
build: hello login loginauth admin restart


# compile 파일 초기화
clean:
	rm -f $(HELLO_CLASS)/HelloServlet.class
	rm -f $(LOGIN_CLASS)/LoginServlet.class
	rm -f $(LOGIN_CLASS)/LoginAuthServlet.class
	rm -f $(ADMIN_CLASS)/CreateSubmenuServlet.class
