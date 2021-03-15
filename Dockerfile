FROM tomcat:9.0.44-jdk8

WORKDIR /tmp

RUN apt-get update
RUN apt-get install -y maven

WORKDIR /tmp/homeassignment
COPY pom.xml ./pom.xml
COPY src ./src

RUN mvn clean package -DskipTests=true

RUN mv target/*.war /usr/local/tomcat/webapps/ROOT.war