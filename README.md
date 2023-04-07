Thit is a project that will be used for 'Software testing automation' subject.

It uses springframework, servlet, MySQL, hibernate.

What is realised:
- User authentication: register/login/logout;
- Password recovery;
- Profile management;
- Posts management: adding/editing/deletion;
- Comments management: adding/deletion;

To run web applications, it is needed to install the following:
- [Java 17+](https://www.oracle.com/java/technologies/downloads/#jdk17-linux)
- [Maven](https://dlcdn.apache.org/maven/maven-3/3.9.1/binaries/apache-maven-3.9.1-bin.zip)
- [MySQL](https://dev.mysql.com/downloads/mysql/5.5.html?os=31&version=5.1)
- [Apache Tomcat](https://tomcat.apache.org/)

Steps to get it work:
- clone repo: git clone https://github.com/Dedg/testsAutomation.git
- configure MySQL server and load db_structure.sql;
- update credentials in blog/src/main/webapp/WEB-INF/db-conf.xml;
- go to blog dir: cd testsAutomation/blog;
- build: mvn package;
- prepare ROOT dir: delete the existing ROOT directory under $TOMCAT_HOME/webapps (if it exists);
- copy target/blog-1.0-SNAPSHOT.war to $TOMCAT_HOME/webapps/ROOT.war.
- adjust $TOMCAT_HOME/conf/server.xml with "<Context docBase="ROOT" path="/" reloadable="true"/>" inside Host if it is not set;
- start tomcat: $ bash $TOMCAT_HOME/bin/startup.sh
