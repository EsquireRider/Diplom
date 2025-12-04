package data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {}

    private static Faker faker = new Faker(new Locale("ru"));

    public static CardInfo getApprovedCard() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo("4444 4444 4444 4442", getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
    }

    public static String getValidMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getValidYear() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getValidOwner() {
        return faker.name().fullName();
    }

    public static String getValidCvc() {
        return faker.number().digits(3);
    }
}
