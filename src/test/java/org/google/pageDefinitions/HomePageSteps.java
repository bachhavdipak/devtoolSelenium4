package org.google.pageDefinitions;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import org.google.pageObjects.HomePage;

public class HomePageSteps extends ScenarioSteps {

    private HomePage homePage = new HomePage();

    @Step
    public void navigateToPage() {
        homePage.open();
        homePage.navigateTo();

    }
}
