@Googlelogin @selenium
Feature: Basiclogin to google


@Googleurl
Scenario: BasicDataCapture_reportHeader_Scenarios
Given Invoke Browser
When Enter Google
When Enter in search bar
Then Verify the resultant page title
Then Click on the image tab
Then Clear Search Tab '3'


	
