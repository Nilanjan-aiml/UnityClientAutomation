@Unity
Feature: Unity Application Automation

  @demo1
  Scenario Outline: Verify that Workview Applications are listed correctly in the Client
    Given I set the data for "<ScenarioName>" from "<JsonFileName>" JSON File
    Given I launch Unity Application
    And I login with the Credentials
    Then I verify that Unity Client opens sucessfully
    And I verify that Workview Applications are listed correctly in the Client

    Examples: 
      | ScenarioName | JsonFileName       | URL1 | Search Query |
      | scenario1    | DemoTestData1.json | url1 | credentials  |

  @demo2
  Scenario Outline: Verify that user is able select and open any Workview Application
    Given I set the data for "<ScenarioName>" from "<JsonFileName>" JSON File
    Given I launch Unity Application
    And I login with the Credentials
    Then I verify that Unity Client opens sucessfully
    And I verify that Workview Applications are listed correctly in the Client
		Then I verify that user is able select and open any Workview Application
		
    Examples: 
      | ScenarioName | JsonFileName       | URL2 | Search Query |
      | scenario2    | DemoTestData2.json | url2 | searchText   |
