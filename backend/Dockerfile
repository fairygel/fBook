FROM gradle:jdk21-alpine AS build

COPY build.gradle /app/
COPY settings.gradle /app/

COPY src /app/src

WORKDIR /app

RUN gradle clean build -x test

FROM openjdk:21-jdk

COPY --from=build /app/build/libs/fBook-0.0.1-SNAPSHOT.jar fBook.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "fBook.jar"]