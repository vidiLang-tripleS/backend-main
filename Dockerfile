FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} vidilang.jar
ENTRYPOINT ["java","-jar","/vidilang.jar"]