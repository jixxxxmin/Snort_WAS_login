# 변수 선언
REPO:=/home/user/github/Snort_WAS_login
SERVLET_JAR:=/opt/tomcat9/lib/servlet-api.jar
GSON_ADMIN_JAR:=$(REPO)/admin/WEB-INF/lib

HELLO_SRC:=$(REPO)/hello/src/HelloServlet.java
HELLO_CLASS:=$(REPO)/hello/WEB-INF/classes

LOGIN_SRC:=$(REPO)/login/src/LoginServlet.java
LOGIN_AUTH_SRC:=$(REPO)/login/src/LoginAuthServlet.java
LOGIN_CLASS:=$(REPO)/login/WEB-INF/classes

ADMIN_MCREATE_SRC:=$(REPO)/admin/src/CreateSubmenuServlet.java
ADMIN_MGET_SRC:=$(REPO)/admin/src/GetAdminMenuServlet.java
ADMIN_CLASS:=$(REPO)/admin/WEB-INF/classes


# define command
.PHONY: pull make_folders delete down hello login loginauth mcreate mget restart set build clean


# git pull
pull:
	git pull

# 폴더 생성
make_folders:
	mkdir -p $(HELLO_CLASS)
	mkdir -p $(LOGIN_CLASS)
	mkdir -p $(GSON_ADMIN_JAR)
	mkdir -p $(ADMIN_CLASS)

# 필요 파일 download
delete:
	rm -f $(GSON_ADMIN_JAR)/gson-2.10.1.jar
down:
	curl -o $(GSON_ADMIN_JAR)/gson-2.10.1.jar https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar

# compile
hello:
	javac -classpath "$(SERVLET_JAR)" -d $(HELLO_CLASS) $(HELLO_SRC)
login:
	javac -classpath "$(SERVLET_JAR)" -d $(LOGIN_CLASS) $(LOGIN_SRC)
loginauth:
	javac -classpath "$(SERVLET_JAR)" -d $(LOGIN_CLASS) $(LOGIN_AUTH_SRC)
mcreate:
	javac -classpath "$(SERVLET_JAR)" -d $(ADMIN_CLASS) $(ADMIN_MCREATE_SRC)
mget:
	javac -classpath "$(SERVLET_JAR):$(GSON_ADMIN_JAR)/gson-2.10.1.ja" -d $(ADMIN_CLASS) $(ADMIN_MGET_SRC)

# systemctl
restart:
	systemctl stop tomcat && systemctl start tomcat


# commands
set: make_folders delete down
build: hello login loginauth mcreate mget restart


# compile 파일 초기화
clean:
	rm -f $(HELLO_CLASS)/HelloServlet.class
	rm -f $(LOGIN_CLASS)/LoginServlet.class
	rm -f $(LOGIN_CLASS)/LoginAuthServlet.class
	rm -f $(ADMIN_CLASS)/CreateSubmenuServlet.class
	rm -f $(ADMIN_CLASS)/GetAdminMenuServlet.class
