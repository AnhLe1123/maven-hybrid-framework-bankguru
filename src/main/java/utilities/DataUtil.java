package utilities;

import com.github.javafaker.Faker;

public class DataUtil {
    private Faker faker;
    public static DataUtil getData() {
        return new DataUtil();
    }

    public DataUtil() {
        faker = new Faker();
    }

    public String getFirstName() {
        return faker.name().firstName().replaceAll("[^A-Za-z0-9]","");
    }

    public String getLastName() {
        return faker.name().lastName().replaceAll("[^A-Za-z0-9]","");
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getEmailAddress() {
        String randEmail = faker.internet().emailAddress();
        String emailAddress;
        if (randEmail.length() > 30) {
            emailAddress = randEmail.substring(randEmail.length() - 30);
        } else {
            emailAddress = randEmail;
        }
        return emailAddress;
    }

    public String getPassword() {
        return faker.internet().password();
    }

    public String getCompanyName() {
        return faker.company().name();
    }

    public String getCityName() {
        return faker.address().city();
    }

    public String getStateName() {
        return faker.address().state();
    }

    public String getAddress() {
        return faker.address().streetAddress();
    }

    public String getZipCode() {
        return faker.address().zipCode();
    }

    public String getPhoneNumber() {
        return faker.numerify("0#########");
    }

    public String getFaxNumber() {
        return faker.numerify("0#########");
    }

    public String getPinNumber() {
        return faker.numerify("######");
    }
}
