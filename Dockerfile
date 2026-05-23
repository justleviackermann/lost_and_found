FROM eclipse-temurin:25-jre-alpine
ADD target/lostandfound.jar lostandfound.jar
ENTRYPOINT ["java","-jar","/lostandfound.jar"]