package org.google.pageObjects;

import net.thucydides.core.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.google.extention.eCommBase;
import org.openqa.selenium.devtools.v85.emulation.Emulation;
import org.openqa.selenium.devtools.v85.log.Log;
import org.openqa.selenium.devtools.v85.performance.Performance;
import org.openqa.selenium.devtools.v85.performance.model.Metric;
import java.util.List;
import java.util.Optional;

@DefaultUrl("page:home.url")
public class HomePage extends eCommBase {

    @FindBy(css = "li#nav-contact > a")
    public WebElementFacade contactOption;

    @FindBy(css = "li#nav-login a")
    public WebElementFacade loginOption;

    @FindBy(css = "li#nav-user > a")
    public WebElementFacade loggedInUser;

    public void navigateTo() {

        getDevTools().createSession();
        getDevTools().send(Log.enable());
        getDevTools().addListener(Log.entryAdded(),
                logEntry -> {
                    System.out.println("log: " + logEntry.getText());
                    System.out.println("level: " + logEntry.getLevel());
                });
        getDevTools().send(Emulation.setGeolocationOverride(Optional.of(52.5043),
                Optional.of(13.4501),
                Optional.of(1)));

        getDevTools().send(Performance.enable(Optional.empty()));
        List<Metric> metricList = getDevTools().send(Performance.getMetrics());

        for (Metric m : metricList) {
            System.out.println(m.getName() + " = " + m.getValue());
        }
        System.out.println("========================================================");
    }

}
