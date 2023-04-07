FROM ubuntu:jammy

# Update packages and install necessary tools
RUN apt-get update && apt-get install -y curl gnupg2

# Install Java 17
RUN curl -fsSL https://apt.corretto.aws/corretto.key | gpg --dearmor | tee /usr/share/keyrings/corretto-archive-keyring.gpg >/dev/null \
    && echo "deb [arch=amd64 signed-by=/usr/share/keyrings/corretto-archive-keyring.gpg] https://apt.corretto.aws stable main" > /etc/apt/sources.list.d/corretto.list \
    && apt-get update && apt-get install -y openjdk-17-jdk-headless

# Install git
RUN apt-get install -y git

# Install Maven
RUN apt-get install -y maven

# Install MySQL
RUN apt-get install -y mysql-server && \
    service mysql start && \
    mysql -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'PassW00rd!'"

# Install Apache Tomcat
ENV TOMCAT_MAJOR=8 \
    TOMCAT_VERSION=8.5.87 \
    CATALINA_HOME=/opt/tomcat
RUN groupadd tomcat && \
    useradd -s /bin/false -g tomcat -d $CATALINA_HOME tomcat && \
    mkdir -p $CATALINA_HOME && \
    curl -fsSL https://downloads.apache.org/tomcat/tomcat-$TOMCAT_MAJOR/v$TOMCAT_VERSION/bin/apache-tomcat-$TOMCAT_VERSION.tar.gz | tar -xzC $CATALINA_HOME --strip-components=1 && \
    chown -R tomcat:tomcat $CATALINA_HOME && \
    chmod +x $CATALINA_HOME/bin/*.sh

# Set environment variables
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 \
    PATH=$PATH:$JAVA_HOME/bin:$CATALINA_HOME/bin

# Clone repo
RUN git clone https://github.com/Dedg/testsAutomation.git

# Load db schema
RUN service mysql start && mysql -u root -pPassW00rd! < testsAutomation/db_structure.sql

WORKDIR testsAutomation/blog

# Build
RUN mvn package

# Copy war to Toncat webapps
RUN rm -rf $CATALINA_HOME/webapps/ROOT
RUN cp target/blog-1.0-SNAPSHOT.war $CATALINA_HOME/webapps/ROOT.war

# Expose port 8080 for Tomcat
EXPOSE 8080

CMD ["bash"]
# Start MySQL and Tomcat server
CMD service mysql start && ${CATALINA_HOME}/bin/startup.sh && sleep infinity