package utilities;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomGenerator {

    public String getRandomEmail(){
        return (RandomStringUtils.randomAlphanumeric(8)+"@"+
                RandomStringUtils.randomAlphabetic(5)+".com").toLowerCase();
    }

    public String getRandomPassword(){
        List<String> password = new ArrayList<>(List.of(
                RandomStringUtils.random(2, 65, 90, true, false),
                RandomStringUtils.random(2, 97, 122, true, false),
                RandomStringUtils.randomNumeric(2),
                RandomStringUtils.randomAlphanumeric(2)
        ));
        Collections.shuffle(password);
        return String.join("", password);

    }


    public String getRandomMobileNumber(){
        return RandomStringUtils.randomNumeric(10);
    }

    public String getRandomUserName(){
        String[] names = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank"};
        Random random = new Random();
        return names[random.nextInt(names.length)]+RandomStringUtils.randomAlphabetic(5);
    }

    public String getRandomFullName(){
        String[] firstName = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank"};
        String[] lastName = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank"};
        Random random = new Random();
        return firstName[random.nextInt(firstName.length)]+RandomStringUtils.randomAlphabetic(2)
                +lastName[random.nextInt(lastName.length)];
    }
}
