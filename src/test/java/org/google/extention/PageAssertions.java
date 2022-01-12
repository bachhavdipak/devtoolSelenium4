package org.google.extention;

import net.serenitybdd.core.exceptions.NoSuchElementException;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.webdriver.exceptions.ElementShouldBeVisibleException;
import org.hamcrest.CoreMatchers;
import org.openqa.selenium.ElementNotVisibleException;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;


public interface PageAssertions {

    default void assertPageObjectsAreDisplayed(List<WebElementFacade> pageObjects) {

        for (WebElementFacade pageObject : pageObjects) {
            try {
                pageObject.waitUntilVisible();
            } catch (NoSuchElementException | ElementShouldBeVisibleException e) {
                throw new ElementNotVisibleException(pageObject + " is not visible or does not exist");
            }
        }
    }


    default void assertElementText(WebElementFacade element, String text) {
        try {
            assertEquals(text, element.getText());
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Could not find element " + element);
        }
    }


    default void assertElementContainsText(WebElementFacade element, String text) {
        try {
            assertThat(element.getText(), CoreMatchers.containsString(text));
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Could not find element " + element);
        }
    }


    default void assertPageObjectText(Map<WebElementFacade, String> pageObjects) {

        for (Map.Entry entry : pageObjects.entrySet()) {
            try {
                assertEquals(entry.getValue().toString(), ((WebElementFacade) entry.getKey()).getText());
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException("Could not find element " + entry.getKey());
            }
        }
    }
}
