package org.google.stepDefinitions;


import io.cucumber.java.en.Given;
import net.thucydides.core.steps.ScenarioSteps;
import org.google.manager.Properties;
import org.google.pageDefinitions.HomePageSteps;
import net.thucydides.core.annotations.Steps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StepDefinitions extends ScenarioSteps {



    private Logger logger = LoggerFactory.getLogger(StepDefinitions.class);

    @Steps
    public HomePageSteps homePageSteps;


    @Given("^the Guest user is on the google page$")
    public void theGuestUserIsOnContactPage() {
//        logger.debug("the Guest user is on the google page ::", Properties.getBaseUrl());
        homePageSteps.navigateToPage();

    }
}





