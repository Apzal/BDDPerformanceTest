package utilities;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.report.config.ConfigurationException;
import org.apache.jmeter.report.dashboard.GenerationException;
import org.apache.jmeter.report.dashboard.ReportGenerator;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

import static org.apache.jmeter.JMeter.JMETER_REPORT_OUTPUT_DIR_PROPERTY;
import static utilities.Constants.*;

public class JmeterEngine {

    static Logger logger = LogManager.getLogger(JmeterEngine.class);
    private HashTree testPlanTree;
    private StandardJMeterEngine jmeter;
    private ResultCollector resultCollector;

    public void executeScript(String jmxFileName) {
       initializeProperties();
        loadTree(jmxFileName);
        configureLogSummariser();
        configureTestPlan();
        runScript();
    }

    private void initializeProperties(){
        String jmeterHomePath = JMETER_HOME_PATH;
        jmeter = new StandardJMeterEngine();
        JMeterUtils.loadJMeterProperties(jmeterHomePath+"\\bin\\jmeter.properties");
        JMeterUtils.setJMeterHome(jmeterHomePath);
        JMeterUtils.initLocale();
        try {
            SaveService.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTree(String jmxFileName){
        try {
            testPlanTree = SaveService.loadTree(new File(JMX_DIR_PATH+jmxFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void configureLogSummariser(){
        Summariser summer = null;
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
        if (!summariserName.isEmpty()) {
            summer = new Summariser(summariserName);
        }
        resultCollector = new ResultCollector(summer);
        resultCollector.setFilename(JTL_FILE_PATH);
    }

    private void configureTestPlan(){
        testPlanTree.add(testPlanTree.getArray()[0], resultCollector);
        jmeter.configure(testPlanTree);
    }

    private void runScript(){
        logger.info("-------------------------Running Scripts---------------------------------");
        jmeter.run();
        logger.info("-------------------------Completed Test Script Execution ---------------------------------");
    }

    public void generateReport(){
        logger.info("-------------------------Generating Report ---------------------------------");
        JMeterUtils.setProperty(JMETER_REPORT_OUTPUT_DIR_PROPERTY, RESULT_OUTPUT_DIR);
        ReportGenerator generator;
        try {
            generator = new ReportGenerator(JTL_FILE_PATH, null);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        try {
            generator.generate();
        } catch (GenerationException e) {
            throw new RuntimeException(e);
        }
        logger.info("Report Created, Path -> {}",RESULT_OUTPUT_DIR);
    }
}