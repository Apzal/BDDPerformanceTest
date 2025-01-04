package hooks;

import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.JmeterEngine;

import java.io.File;
import java.io.IOException;

import static utilities.Constants.RESULT_OUTPUT_DIR;

public class Hooks {

    static Logger logger = LogManager.getLogger(Hooks.class);


    @BeforeAll
    public static void beforeAll(){
        logger.info("---------*************Test Execution Started**********************--------------");

        try {
            FileUtils.deleteDirectory(new File(RESULT_OUTPUT_DIR));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            System.exit(1);
        }
    }

    @Before
    public void before(Scenario scenario){
        logger.info("Execution started for scenario:{}", scenario.getName());
    }


    @After
    public void after(Scenario scenario) {
        logger.info("Execution completed for scenario:{},Status:{}", scenario.getName(), scenario.getStatus().toString());
    }

    @AfterAll
    public static void afterAll(){
        new JmeterEngine().generateReport();
        logger.info("Test Execution Finished");
        logger.info("---------***********************************--------------");
    }


}
