#docker build -t weather . && docker run weather
ARG VERSION=11.0.1

FROM openjdk:${VERSION}-jdk as BUILD

COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon build

FROM openjdk:${VERSION}-jre

COPY --from=BUILD /build/libs/weather-0.0.1-SNAPSHOT.jar /bin/runner/run.jar
WORKDIR /bin/runner

CMD ["java","-jar","run.jar"]



