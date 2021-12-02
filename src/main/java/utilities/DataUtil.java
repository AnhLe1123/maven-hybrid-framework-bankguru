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
        return faker.name().firstName();
    }

    public String getLastName() {
        return faker.name().lastName();
    }

    public String getFullName() {
        return faker.name().fullName();
    }

    public String getEmailAddress() {
        return faker.internet().emailAddress();
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
