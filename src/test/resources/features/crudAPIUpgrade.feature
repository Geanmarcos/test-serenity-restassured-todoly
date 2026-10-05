@TestCrudClean
Feature: crudAPIUpgrade

  Background:
    Given Carlos is an user on todo.ly

  Scenario Outline: Como usuario quiero hacer el CRUD de un proyecto y item por API
    # create project
    When Carlos sends a POST request to "/api/projects.json" with body
      """
      {
        "Content": "<cContentProject>",
        "Icon": <cIconProject>
      }
      """
    Then the response code is <statusCode>
    And the attribute string "Content" is "<cContentProject>"
    And the attribute int "Icon" is "<cIconProject>"
    And the response matches the schema "<pathSchemaProject>"
    And Carlos saves the value of "Id" as "projectId"

    # update project
    When Carlos sends a PUT request to "/api/projects/{projectId}.json" with body
      """
      {
        "Content": "<uContentProject>",
        "Icon": <uIconProject>
      }
      """
    Then the response code is <statusCode>
    And the attribute string "Content" is "<uContentProject>"
    And the attribute int "Icon" is "<uIconProject>"
    And the response matches the schema "<pathSchemaProject>"

    # read project
    When Carlos sends a GET request to "/api/projects/{projectId}.json"
    Then the response code is <statusCode>
    And the attribute string "Content" is "<uContentProject>"
    And the attribute int "Icon" is "<uIconProject>"
    And the response matches the schema "<pathSchemaProject>"

    # create item
    When Carlos sends a POST request to "/api/items.json" with body
      """
      {
        "Content": "<cContentItem>",
        "ProjectId": {projectId}
      }
      """
    Then the response code is <statusCode>
    And the attribute string "Content" is "<cContentItem>"
    And the attribute boolean "Checked" is "<checkedItem>"
    And the attribute int "ProjectId" is "{projectId}"
    And the response matches the schema "<pathSchemaItem>"
    And Carlos saves the value of "Id" as "itemId"

    # update item
    When Carlos sends a PUT request to "/api/items/{itemId}.json" with body
      """
      {
        "Content": "<uContentItem>",
        "ProjectId": {projectId},
        "Checked": <uCheckedItem>
      }
      """
    Then the response code is <statusCode>
    And the attribute string "Content" is "<uContentItem>"
    And the attribute boolean "Checked" is "<uCheckedItem>"
    And the attribute int "ProjectId" is "{projectId}"
    And the response matches the schema "<pathSchemaItem>"

    # search item
    When Carlos sends a GET request to "/api/items/{itemId}.json"
    Then the response code is <statusCode>
    And the attribute string "Content" is "<uContentItem>"
    And the attribute boolean "Checked" is "<uCheckedItem>"
    And the attribute int "ProjectId" is "{projectId}"
    And the response matches the schema "<pathSchemaItem>"

    # delete item
    When Carlos sends a DELETE request to "/api/items/{itemId}.json"
    Then the response code is <statusCode>
    And the attribute string "Content" is "<uContentItem>"
    And the attribute boolean "Checked" is "<uCheckedItem>"
    And the attribute int "ProjectId" is "{projectId}"
    And the attribute boolean "Deleted" is "<deleteItem>"
    And the response matches the schema "<pathSchemaItem>"

    # delete project
    When Carlos sends a DELETE request to "/api/projects/{projectId}.json"
    Then the response code is <statusCode>
    And the attribute string "Content" is "<uContentProject>"
    And the attribute int "Icon" is "<uIconProject>"
    And the attribute boolean "Deleted" is "<deleteProject>"
    And the response matches the schema "<pathSchemaProject>"

    Examples:
      | cContentProject | cIconProject | statusCode | pathSchemaProject                | uContentProject | uIconProject | cContentItem | checkedItem | pathSchemaItem                | uContentItem | uCheckedItem | deleteItem | deleteProject |
      | GRUPO 3         | 1            | 200        | schemas/createProjectSchema.json | U GRUPO 3       | 2            | TALLER 03    | false       | schemas/createItemSchema.json | U TALLER 3   | true         | true       | true          |
      | TEST 3          | 1            | 200        | schemas/createProjectSchema.json | U TEST 3        | 2            | TALLER 03    | false       | schemas/createItemSchema.json | U TALLER 3   | true         | true       | true          |