@smoke
Feature: crudAPI

  Scenario Outline: Crear Proyecto por API
    Given Pepito is an user on todo.ly v1
    When he create a project with Content "<cContentProject>" & Icon "<cIconProject>"
    Then he should see the project "<cContentProject>"
    Examples:
      | cContentProject | cIconProject |
      | serenity         | 10           |