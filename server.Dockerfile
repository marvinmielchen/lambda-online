FROM maven:3-eclipse-temurin-17 as build
WORKDIR /workspace/lambo
COPY lambo/pom.xml ./pom.xml
RUN mvn dependency:resolve
COPY lambo .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
ENV SPRING_CONFIG_LOCATION=classpath:/application.yml
ENV JAVA_OPTS="-Xms512m -Xmx1024m"
COPY --from=build /workspace/lambo/target/lambo-0.0.1-SNAPSHOT.jar /workspace/lambo/server.jar
EXPOSE 8080/tcp
WORKDIR /workspace/lambo
ENTRYPOINT java $JAVA_OPTS -jar server.jar --spring.config.location=$SPRING_CONFIG_LOCATION