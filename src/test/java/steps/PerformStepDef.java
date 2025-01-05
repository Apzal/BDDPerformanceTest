package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import utilities.CSVGenerator;
import utilities.Constants;
import utilities.JmeterEngine;
import utilities.XMLHelper;


public class PerformStepDef {

    String jmxFileName = "";

    @Given("I set the thread with {string}-{string}-{string} for {string} file")
    public void iSetTheThreadWithForFile(String threadCount, String rampUpPeriod, String loopCount,
                                         String jmxFileName) {
        this.jmxFileName = jmxFileName;
        XMLHelper.updateBasicDetails(jmxFileName);
        XMLHelper.updateElementText(XMLHelper.XPATH.ThreadCount,jmxFileName,0, threadCount);
        XMLHelper.updateElementText(XMLHelper.XPATH.RampUpPeriod,jmxFileName,0, rampUpPeriod);
        XMLHelper.updateElementText(XMLHelper.XPATH.LoopCount,jmxFileName,0, loopCount);
    }

    @And("I create a {string} and {string} file for request body with {string} data in iteration {string}")
    public void iCreateAAndFileForRequestBodyWithDataInIteration(String fileName1, String fileName2, String dataRowCount, String iteration) {
        CSVGenerator.generateCSV(fileName1, fileName2, Integer.parseInt(dataRowCount), iteration);
        XMLHelper.updateElementAttribute(XMLHelper.XPATH.HTTPTestName,jmxFileName,0, "testname",
                "Iteration_"+iteration+"_"+jmxFileName.replaceAll(".jmx",""));
        XMLHelper.updateElementText(XMLHelper.XPATH.CSVFile,jmxFileName,0,
                Constants.TEST_DATA_FILE_PATH +fileName1+iteration+".csv");
    }

    @Then("I run the script")
    public void iRunTheScript() {
        new JmeterEngine().executeScript(jmxFileName);
    }

    @And("I use the {string} file for request body in iteration {string}")
    public void iUseTheFileForRequestBodyInIteration(String fileName, String iteration) {
        XMLHelper.updateElementText(XMLHelper.XPATH.CSVFile,jmxFileName,0,
                Constants.TEST_DATA_FILE_PATH +fileName+iteration+".csv");
        XMLHelper.updateElementAttribute(XMLHelper.XPATH.HTTPTestName,jmxFileName,0, "testname",
                "Iteration_"+iteration+"_"+jmxFileName.replaceAll(".jmx",""));
    }

    @And("I use the {string} file for request body for Iteration {string}")
    public void iUseTheFileForRequestBodyForIteration(String fileName, String iteration) {
        XMLHelper.updateElementText(XMLHelper.XPATH.CSVFile,jmxFileName,0,
                Constants.TEST_DATA_FILE_PATH +fileName+".csv");
        XMLHelper.updateElementAttribute(XMLHelper.XPATH.HTTPTestName,jmxFileName,0, "testname",
                "Iteration_"+iteration+"_"+jmxFileName.replaceAll(".jmx",""));
    }
}
