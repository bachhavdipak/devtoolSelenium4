package org.google.extention;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.devtools.v85.emulation.Emulation;
import org.openqa.selenium.devtools.v85.log.Log;
import org.openqa.selenium.devtools.v85.performance.Performance;
import org.openqa.selenium.devtools.v85.performance.model.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class BasePageObject extends PageObject implements PageAssertions {

    private final Logger logger = LoggerFactory.getLogger(BasePageObject.class);


}

