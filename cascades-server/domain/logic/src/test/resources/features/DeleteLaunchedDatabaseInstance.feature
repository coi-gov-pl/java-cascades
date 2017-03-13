# language: en

Business Need: User can delete his launched database instance

    Any authorized user can delete launched by himself database instances.
    System after proper delete of the database should return all connection related
    information. User must pass a database ID which will
    specify which database instance should be deleted.

Background:

    Given authenticated user is "michael"
    And user is authorized to delete a database instance
    And proper database ID are "ora12e34", "ora23r45" and "pos34t56"
    But only "ora12e34" and "ora23r45" belongs to user

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
    Then system should return error "Given id of database: pos34t56 doesn't belong to logged user: michael"
    And launched database instance hasn't been deleted

Scenario: User deletes launched database instance

    When user pass database ID of "ora12e34"
    Then launched database instance has been deleted
    And status of database instance has been changed to "DELETED"
    And system should return info "Database has been deleted."
