package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {
    private DataGenerator() {}

    private static final List<String> CITIES = List.of(
            "Москва", "Санкт-Петербург", "Новосибирск",
            "Екатеринбург", "Казань", "Нижний Новгород",
            "Челябинск", "Самара", "Омск", "Ростов-на-Дону"
    );

    public static String generateDate(int daysToAdd) {
        return LocalDate.now()
                .plusDays(daysToAdd)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        return CITIES.get(ThreadLocalRandom.current().nextInt(CITIES.size()));
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {}

        public static UserInfo generateUser(String locale) {
            return new UserInfo(
                    generateCity(),
                    generateName(locale),
                    generatePhone(locale)
            );
        }
    }

    public static class UserInfo {
        private final String city;
        private final String name;
        private final String phone;

        public UserInfo(String city, String name, String phone) {
            this.city = city;
            this.name = name;
            this.phone = phone;
        }

        public String getCity() {
            return city;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }
    }
}