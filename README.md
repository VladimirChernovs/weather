# the-weather-project

### Tech stack
* Kotlin
* Spring 5, Spring Boot 2, WebFlux
* Etc (Gradle, Swagger, Docker)

## Install

Clone this repo

## Docker image

coming soon ...


## Build

```shell
./gradlew build
```

## Run

```shell
./gradlew run
```

# Test Task (Java-Kotlin)

- Overview
- Requirements
    - Development process
    - Build tools
    - Functional requirements
        - Bonus
    - Non-functional requirements

# Overview

The test task will cover the following areas:

1. REST API design and implementation
2. Work with remote services
3. Work with async tasks.
4. Error handling
5. Work with databases using ORM
6. Work with git

# Requirements

## Development process

1. Use public github repository. https://github.com/
2. Work with the repo as you usually work in production (feature branches, commits, etc). Do not do just one commit with all your work. We
want to see how your solution was evolving over the time.
3. Write your code as you write production code (tests, javadoc, naming, codestyle, etc)
4. Provide clear instructions how to build the code, how to run, what configuration options are available.

##Build tools

1. Use any of the commonly used build tools — Apache Maven, Gradle, Apache Ant.

## Functional requirements

1. Implement a Java web-service, which will provide REST-style API to get weather information.
2. To get actual weather information — use https://openweathermap.org/
    2. Do async requests with timeout and error handling.
3. Service should provide the following endpoints:
    3. Get city weather
        3. If city is registered — get information cached by background update (see below)
        3. If city is not registered — do an actual call to the external API.
        3. External response should be cached for 5 minutes and on a consequent request, cached information should be returned.
    3. Register city
        3. User should be able to register city. City information should be saved into a database (use h2 or sqlite)
        3. To work with database use ORM
    3. Get last weather for all registered cities
    3. Unregister city
        3. Delete city information from DB with all cached information
    3. Endpoints should return JSON/XML depending on headers sent by Users.
4. Background update:
    4.  Implement background updating of weather for a registered cities.
    4.  Weather information should be stored in a database.
##  Bonus
    1. Docker image
#   Non-functional requirements
1. Use Java 8+ or Kotlin
2. Write Javadoc
3. Write tests (unit, integration, mocks)
4. Use frameworks: Spring, ORM (Hibernate/EclipseLink or via standard JPA

