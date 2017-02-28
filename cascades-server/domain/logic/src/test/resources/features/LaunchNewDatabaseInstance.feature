# language: en

Business Need: User can launch a new database instance

    Any authorized user can launch new database instances,
    up to the centrally specified limit or personal one,
    which one comes first. System after proper launch of
    the database should return all connection related
    information. User must pass a template ID which will
    specify a base template database on remote shared
    database server and can pass instance name.

Background:

    Given authenticated user is "jackie"
    And user is authorized to launch a database instance
    And global limit is set to 100
    And personal limit of "jackie" is set to 1
    And proper template ID are "6e4b78z", "f4ab6a58" and "eaba275"
    And user pass a proper database type

Scenario: User fails to launch a new database instance without passing template ID

    When user doesn't pass template ID
    Then system should return error "Proper template ID is required"
    And new database instance hasn't been launched

Scenario: User fails to launch a new database instance when passing invalid template ID

    When user pass template ID of "w34e566"
    Then system should return error "Template ID w34e566 doesn't exists"
    And new database instance hasn't been launched

Scenario: User launches a new database instance without specifying instance name

    When user doesn't pass instance name
    And user pass template ID of "f4ab6a58"
    Then new database instance has been launched
    And operation took less then 1 minute
    And system returns filled credentials
    And system returns filled network bind
    And system returns filled database ID

Scenario: User launches a new database instance with specifying instance name

    When user pass instance name of "my-fun-db"
    And user pass valid template ID
    Then new database instance has been launched
    And system returns filled database ID that contains "my-fun-db"

Scenario: User fails to launch to many databases because of personal limit

    When user pass valid template ID
    Then new database instance has been launched
    When user pass valid template ID
    Then system should return error "Personal limit of 1 launched database instances has been reached"
    And new database instance hasn't been launched

Scenario: User fails to launch to many databases because of global limit

    Given other users already launched 99 database instances
    When user pass valid template ID
    Then new database instance has been launched

    When user pass valid template ID
    Then system should return error "Global limit of 100 launched database instances has been reached"
    And new database instance hasn't been launched

Scenario: User fails to launch database, because authenticated user is invalid

    Given user "jackie" is unavailable for whatever reason
    When user pass valid template ID
    Then system should return error "Given user - jackie is not available"
    And new database instance hasn't been launched
    And potential security breach has been logged

Scenario: User fails to launch database, because given database type is unavailable

    Given user pass a invalid database type of "invalidDB"
    When user pass valid template ID
    Then system should return error "Invalid database type given - invalidDB"
    And new database instance hasn't been launched
