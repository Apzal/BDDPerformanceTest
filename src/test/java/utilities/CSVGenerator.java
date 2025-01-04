package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static utilities.Constants.TEST_DATA_FILE_PATH;

public class CSVGenerator {


    static RandomGenerator randomGenerator = new RandomGenerator();
    static Logger logger = LogManager.getLogger(CSVGenerator.class);

    public static void generateCSV(String fileName1,String fileName2, int numEntries, String iteration) {
        fileName1 = TEST_DATA_FILE_PATH + fileName1+iteration+".csv";
        fileName2 = TEST_DATA_FILE_PATH + fileName2+iteration+".csv";
        try (BufferedWriter writer1 = new BufferedWriter(new FileWriter(fileName1));
             BufferedWriter writer2 = new BufferedWriter(new FileWriter(fileName2));) {

            for (int i = 0; i < numEntries; i++) {
                String fullName = randomGenerator.getRandomFullName();
                String userName = randomGenerator.getRandomUserName();
                String email = randomGenerator.getRandomEmail();
                String password = randomGenerator.getRandomPassword();
                String phone = randomGenerator.getRandomMobileNumber();

                writer1.append(fullName).append(",")
                        .append(userName).append(",")
                        .append(email).append(",")
                        .append(password).append(",")
                        .append(phone).append("\n");

                writer2.append(userName).append(",")
                        .append(email).append(",")
                        .append(password).append(",")
                        .append(phone).append("\n");
            }
            logger.info("Successfully created {} and {} csv file",fileName1,fileName2);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public static void generateCSV1(String fileName, int numEntries) {
        fileName = TEST_DATA_FILE_PATH + fileName;
        try (FileWriter writer = new FileWriter(fileName, false)) {

            for (int i = 0; i < numEntries; i++) {
                String fullName = randomGenerator.getRandomFullName();
                String userName = randomGenerator.getRandomUserName();
                String email = randomGenerator.getRandomEmail();
                String password = randomGenerator.getRandomPassword();
                String phone = randomGenerator.getRandomMobileNumber();

                writer.append(fullName).append(",")
                        .append(userName).append(",")
                        .append(email).append(",")
                        .append(password).append(",")
                        .append(phone).append("\n");
            }
            logger.info("Successfully created {} csv file",fileName);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
