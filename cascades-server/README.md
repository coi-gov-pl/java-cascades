# CASCADES

Provides a easy way to manage remote databases for development and test purposes.

## Starting the application:

##### Mode: Poroduction/Hibernate 
If you run applications without starting parameters, then the application will run in production mode where you need to connect to an external database. 
The production and hibernate profiles then work. Operation based on pluggable databse and information/auxiliary

```sh
  _       _   _                                     _          
 | |__   (_) | |__     ___   _ __   _ __     __ _  | |_    ___ 
 | '_ \  | | | '_ \   / _ \ | '__| | '_ \   / _` | | __|  / _ \
 | | | | | | | |_) | |  __/ | |    | | | | | (_| | | |_  |  __/
 |_| |_| |_| |_.__/   \___| |_|    |_| |_|  \__,_|  \__|  \___|
                                                              
 ```
and

```sh
                             _                  _     _                 
  _ __    _ __    ___     __| |  _   _    ___  | |_  (_)   ___    _ __  
 | '_ \  | '__|  / _ \   / _` | | | | |  / __| | __| | |  / _ \  | '_ \ 
 | |_) | | |    | (_) | | (_| | | |_| | | (__  | |_  | | | (_) | | | | |
 | .__/  |_|     \___/   \__,_|  \__,_|  \___|  \__| |_|  \___/  |_| |_|
 |_|                                                                    
 ```
 
 ##### Mode: Hibernate/Development 
 If we want to work in an environment that only connects to the information base, we set development and hibernate profiles.
Set hibernate/development environment with command line arg `--spring.profiles.active=hibernate, development` or set environment variable with export `spring_profiles_active=hibernate, development`

 ```sh
  _       _   _                                     _          
 | |__   (_) | |__     ___   _ __   _ __     __ _  | |_    ___ 
 | '_ \  | | | '_ \   / _ \ | '__| | '_ \   / _` | | __|  / _ \
 | | | | | | | |_) | |  __/ | |    | | | | | (_| | | |_  |  __/
 |_| |_| |_| |_.__/   \___| |_|    |_| |_|  \__,_|  \__|  \___|
````
and
```sh
      _                         _                                              _   
   __| |   ___  __   __   ___  | |   ___    _ __    _ __ ___     ___   _ __   | |_ 
  / _` |  / _ \ \ \ / /  / _ \ | |  / _ \  | '_ \  | '_ ` _ \   / _ \ | '_ \  | __|
 | (_| | |  __/  \ V /  |  __/ | | | (_) | | |_) | | | | | | | |  __/ | | | | | |_ 
  \__,_|  \___|   \_/    \___| |_|  \___/  | .__/  |_| |_| |_|  \___| |_| |_|  \__|
                                           |_|                                                                                               
 ```
 
 ##### Mode: Development 
 
If we want to work in an environment that does not connect to any database, then we set the development profile. All information will come from Stub.

Set development environment with command line arg `--spring.profiles.active=development` or set environment variable with `export spring_profiles_active=development`

```sh
      _                         _                                              _   
   __| |   ___  __   __   ___  | |   ___    _ __    _ __ ___     ___   _ __   | |_ 
  / _` |  / _ \ \ \ / /  / _ \ | |  / _ \  | '_ \  | '_ ` _ \   / _ \ | '_ \  | __|
 | (_| | |  __/  \ V /  |  __/ | | | (_) | | |_) | | | | | | | |  __/ | | | | | |_ 
  \__,_|  \___|   \_/    \___| |_|  \___/  | .__/  |_| |_| |_|  \___| |_| |_|  \__|
                                           |_|                                                                                               
 ```

## Example usage PostgresSQL:

* Run PostgreSQL server in docker
    ```sh
    $ docker run --name cascades-postgres -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres:10.4
    ```

* Build project with profile `-Ppostgresql`
It adds PostgreSQL driver.

* Run application with profile 
    ```sh
    $ mvn spring-boot:run --Dspring.profiles.activeproduction,hibernate,postgresqldocker
    ```

## Creating a template
    
We need two files:
- `template.json `
- `deploy.sql`

We create an archive containing files with a `.zip` extension.

#### Files:

##### 1. Template.json
JSON with parameter
Example:
```sh
{
	"name": "exampletemplatename",  - Template name can not contain uppercase letters in PostgresSQL
	"isDefault": "false",
	"serverId": "exampleServerId", - serverId of database settings on the server
	"status": "created",
	"version": "1.0.3",
	"deployScript": "deploy.sql"
}
```
##### 2. Deploy.sql
SQL script with data and settings.
Example PostgresSQL:

```sh
CREATE TABLE COMPANY(
   ID INT PRIMARY KEY     NOT NULL,
   NAME           TEXT    NOT NULL,
   AGE            INT     NOT NULL,
   ADDRESS        CHAR(50),
   SALARY         REAL
);
```
### Loading Template on server
- Open in the browser:  
    ```sh
    localhost:8085
    ```
- Select file
- Press upload

`Remember! You can not add a new template with an existing name.`

Response:

```sh
target	
id	"Nj8cwEYBRjsmMBKdJZtKZTwh" - id for which we can create a new database from the provided template
name	"exampletemplatename"
status	"CREATED"
serverId	"hdx234rd" - serverId of database settings on the server
version	"1.0.3"
default	false
violations	[]
```






## Manually initializing a database instance from the template: 

To create a database, we give the TemplateId and the name of the new instance "instanceName". 
All parameters are optional:
- `templateId`
- `instanceName`

All mediatype: 
```sh
application/json
```
All you need to do is refer to a specific endpoint with the POST method.

Endpoint:
```sh
localhost:8085/databases
```
Example:

```sh
$ curl -d '{"templateId": "KlqmuU7bq2zMbNpAbGd0ZTBu", "instanceName": "exampleName"}' -H "Content-Type: application/json" -X POST "http://localhost:8085/databases/"
```

Response:
```sh
{
"target":
    {
    "databaseId":
          {
              "id":"8ol39tu0uemf"
          },
         "databaseName":"qweICVvY",
         "networkBind":
          {
              "host":"172.17.0.3","port":5432
          },
         "credentials":
          {
              "username":"Pcf3e7ac",
              "password":"%8<\u001B%\"&\u0010!$D*\u0007-C\u001F\n\u0018#D)\u0014@("
          }
    },
    "violations":[]
}
```

It is also possible to create a database without any input parameters. The database will be created from the default Template.

Example:
```sh
$ curl -d '{}' -H "Content-Type: application/json" -X POST "http://localhost:8085/databases/"
```

We get data on which we can connect to the new database. We also get a database id which will be useful for removing the given instance.

## Manually removing a database instance: 
The method removes database instances and changes the status on the secondary database to deleted.
To delete the database, we give the database id. "databaseId was returned when creating a new database"

Parameter:
- `id`

All mediatype: 
```sh
application/json
```
All you need to do is refer to a specific endpoint with the DELETE method.

Endpoint:
```sh
localhost:8085/databases/{id}
```

Example:

```sh
$ curl -d '{}' -H "Content-Type: application/json" -X DELETE "http://localhost:8085/databases/yourDatabseId"
```
Response:
```sh
{
    "violations": [],
    "successful": true
}
```

