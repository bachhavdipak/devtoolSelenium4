package org.google.extention;

import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class eCommBase extends BasePageObject  {

    private final Logger logger = LoggerFactory.getLogger(eCommBase.class);

    //I'd like to get rid of this method- ideally produce something that is reliable and generic (which this isn't)
    public void selectOptionFromDropdown(WebElementFacade element, String text) {

        final String id = element.getAttribute("id").replaceAll("-button", "-menu");
        clickOn(element);
        clickOn($(By.xpath(String.format("//ul[@id='%s']//li//a[text()='%s']", id, text))));


    }

    public String getElementText(WebElementFacade element) {

        String text = null;

        for (int i = 0; i <= 2; i++) {
            try {
                text = element.waitUntilVisible().getText().replaceAll("[^\\d_]", "").trim();
                break;
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                logger.info(e.getMessage());
            }
        }
        return text;
    }
}

