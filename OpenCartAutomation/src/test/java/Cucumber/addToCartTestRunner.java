package Cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features= "src/test/java/Cucumber/addToCart.feature",
glue="OpenCart/stepDefinitions",
monochrome=false,
plugin= {"html:target/addToCart-reports.html"})
public class addToCartTestRunner extends AbstractTestNGCucumberTests{

}
