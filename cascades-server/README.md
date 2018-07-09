# Example usage

* Run PostgreSQL server in docker
`docker run --name cascades-postgres -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres:10.4`

* Build project with profile `-Ppostgresql`
It adds PostgreSQL driver.

* Run application with profile `mvn spring-boot:run --Dspring.profiles.activeproduction,hibernate,postgresqldocker`
