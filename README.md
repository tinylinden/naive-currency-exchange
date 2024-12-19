# NCE - Naive Currency Exchange

[![NCE Banner](banner.jpg)](https://unsplash.com/photos/SAYzxuS1O3M)  
<sub>Photo by [Json Leung](https://unsplash.com/@ninjason) on [Unsplash](https://unsplash.com/)</sub>

[![stability: experimental](https://masterminds.github.io/stability/experimental.svg)](https://masterminds.github.io/stability/experimental.html)

## Build and Run

There are some prerequisite to play with the project:
* [Docker](https://docs.docker.com/get-docker/) should be installed - as integration
  tests are using [Testcontainers](https://www.testcontainers.org/) to avoid all those
  in-memory/embedded/mocked solutions, and check if integrations really works.
* [Docker Compose](https://docs.docker.com/compose/install/) should be installed - as
  project is using PostgreSQL for persistence, and starting provided `copose.yaml`
  is painless way to run it locally.

You can build the service and run all the tests with `./gradlew clean check`.

If you want to start the application locally, then all you have to do is start
all dependencies with:
```
compose up --detach
```
And after that start the service with:
```
./gradlew bootRun
``` 
With profile `default` all HTTP calls are directed to Mock-Server (running in container). 
If you want to enable integration with real exchange rate provider, just enable profile `live`
while starting application:
```
./gradlew bootRun --args='--spring.profiles.active=live'
```

After application is up and running you can explore the API and play with it using:

* Swagger UI available at http://localhost:8082
* Mock-Server available at http://localhost:8081/mockserver/dashboard
