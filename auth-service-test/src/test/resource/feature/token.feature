Feature: User request authorization token

  Scenario: After passing good credentials users gets token response
    Given correct username "admin" and password "admin" with grant type "password"
    When user pass request to "/oauth/token"
    Then the response status should be 200
    And should contain refresh token


  Scenario: Logged in user can refresh token
    Given logged in user with token
    When user request "/oauth/token" with grant type "refresh_token"
    Then the response status should be 200
    And should return new token