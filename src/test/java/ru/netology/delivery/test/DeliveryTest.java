package ru.netology.delivery.test;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.delivery.data.DataGenerator.*;

class DeliveryTest {

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Пользователь может запланировать и перенести встречу")
    void shouldScheduleAndRescheduleMeeting() {
        // Подготовка тестовых данных
        var validUser = DataGenerator.Registration.generateUser("ru");
        var firstMeetingDay = 4; // дней до первой встречи
        var secondMeetingDay = 7; // дней до второй встречи

        var firstDate = DataGenerator.generateDate(firstMeetingDay);
        var secondDate = DataGenerator.generateDate(secondMeetingDay);

        // Заполняем форму для первой встречи
        enterCity(validUser.getCity());
        enterDate(firstDate);
        enterName(validUser.getName());
        enterPhone(validUser.getPhone());
        agreeWithTerms();
        clickScheduleButton();

        // Проверяем подтверждение первой встречи
        checkSuccessNotification("Встреча успешно запланирована на " + firstDate);

        // Меняем дату на вторую
        enterDate(secondDate);
        clickScheduleButton();

        // Подтверждаем перенос встречи
        checkRescheduleConfirmation();
        clickRescheduleButton();

        // Проверяем подтверждение второй встречи
        checkSuccessNotification("Встреча успешно запланирована на " + secondDate);
    }

    // Вспомогательные методы для лучшей читаемости
    private void enterCity(String city) {
        $("[data-test-id='city'] input").setValue(city);
    }

    private void enterDate(String date) {
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
    }

    private void enterName(String name) {
        $("[data-test-id='name'] input").setValue(name);
    }

    private void enterPhone(String phone) {
        $("[data-test-id='phone'] input").setValue(phone);
    }

    private void agreeWithTerms() {
        $("[data-test-id='agreement']").click();
    }

    private void clickScheduleButton() {
        $(byText("Запланировать")).click();
    }

    private void clickRescheduleButton() {
        $(byText("Перепланировать")).click();
    }

    private void checkSuccessNotification(String expectedText) {
        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text(expectedText));
    }

    private void checkRescheduleConfirmation() {
        $("[data-test-id='replan-notification']")
                .shouldBe(visible)
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
    }
}