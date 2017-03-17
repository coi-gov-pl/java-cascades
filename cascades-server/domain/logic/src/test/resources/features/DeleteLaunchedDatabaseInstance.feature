# language: en

Business Need: User can delete his launched database instances

    Any authorized user can delete database instances launched by himself.
    User must pass a database ID which will specify which database
    instance should be deleted. System should return information about
    success or list of errors that caused a failure.

Background:

    Given authenticated user is "michael"
    And user is authorized to delete a database instance
    And proper database ID are "ora12e34", "ora23r45", "pos45y67" and "pos34t56"
    And database ID "pos45y67" has already been deleted
    But only "ora12e34", "pos45y67" and "ora23r45" database IDs belongs to user "michael"

Scenario: User fails to delete a launched database instance without passing database ID

    When user doesn't pass database ID
    Then system should return error "Proper database ID is required"
    And launched database hasn't been deleted

Scenario: User fails to delete launched database instance when passing invalid database ID

    When user pass database ID of "ora98r76"
    Then system should return error "Database ID ora98r76 doesn't exists"
    And launched database hasn't been deleted

Scenario: User fails to delete launched database, because authenticated user is invalid

    Given user "michael" is unavailable for whatever reason
    When user pass valid database ID
    Then system should return error "Given user - michael is not available"
    And launched database hasn't been deleted
    And potential security breach has been logged

Scenario: User fails to delete launched database, because given database ID is valid, but wasn't launched by logged user

    When user pass a valid database ID of "pos34t56"
    Then system should return error "Given database ID pos34t56 doesn't belong to you!"
    And launched database instance hasn't been deleted
    And potential security breach has been logged

Scenario: User deletes launched database instance

    When user pass database ID of "ora12e34"
    Then launched database instance has been deleted
    And status of database instance has been changed to "DELETED"

Scenario: User fails to delete launched database instance, because given database ID is valid, but it has already been deleted.

    When user pass database ID of "pos45y67"
    Then system should return error "Database ID pos45y67 doesn't exists"
    And launched database hasn't been deleted
