Serenity BDD Framework : Author : Dipak Bachhav

Custom Feature :
1. Included Java log to print the output messages which eliminate `system.out.println` practice`
3. Capable to view both Serenity Report and Cucumber summary report
4. Lots of Helpers are included in the Framework to fast track the scripting and avoid duplication
5. Multi browser support is already available in the framework to run test through multiple browser (chrome/firefox)
6. Framework also capable to read the Test Data from json file apart from the feature file
7. Framework also capable to read the Test error messages from json file apart from the feature file
8. In addition to the standard HTML reports, Framework provides additional reports for more specific usages

# Run UI tests

Run tests with gradle

`./gradlew test`


## Cross browser testing

Options set via `serenity.properties`

- `webdriver.managed.driver` - chrome or firefox

#To produce report use below :
- `$gradlew clean test aggregate`
Test Reports Path : Serenity Report - target/site/serenity/index.html


#To produce emailable reports use below
- `$gradle reports`
