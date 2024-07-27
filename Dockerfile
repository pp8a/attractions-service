FROM tomcat:10.0
COPY target/attractions-service-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/attractions-service.war