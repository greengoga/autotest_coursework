package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.SQLHelper;
import ru.netology.page.PaymentPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class tourPurchaseTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:8080/");
        SQLHelper.clearDatabase();
    }

    @Test
    void shouldSuccessfullyPayWithValidCard() {
        PaymentPage paymentPage = new PaymentPage();

        paymentPage.clickBuy();
        paymentPage.enterCardNumber("1111 2222 3333 4444");
        paymentPage.enterMonth("12");
        paymentPage.enterYear("26");
        paymentPage.enterCardHolder("IVAN");
        paymentPage.enterCVC("123");
        paymentPage.clickContinue();
        paymentPage.successNotification().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком"));

        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    void shouldFailWithCardNumberTooShort() {
        PaymentPage paymentPage = new PaymentPage();

        paymentPage.clickBuy();
        paymentPage.enterCardNumber("1111 2222 3333 444");
        paymentPage.enterMonth("12");
        paymentPage.enterYear("26");
        paymentPage.enterCardHolder("IVAN");
        paymentPage.enterCVC("123");
        paymentPage.clickContinue();
        paymentPage.getCardNumberError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldFailIfCardTooOld() {
        PaymentPage paymentPage = new PaymentPage();

        paymentPage.clickBuy();
        paymentPage.enterCardNumber("1111 2222 3333 4444");
        paymentPage.enterMonth("12");
        paymentPage.enterYear("22");
        paymentPage.enterCardHolder("IVAN");
        paymentPage.enterCVC("123");
        paymentPage.clickContinue();
        paymentPage.getYearError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Истёк срок действия карты"));
    }

    @Test
    void shouldFailIfCardExpiryTooFar() {
        PaymentPage paymentPage = new PaymentPage();

        paymentPage.clickBuy();
        paymentPage.enterCardNumber("1111 2222 3333 4444");
        paymentPage.enterMonth("12");
        paymentPage.enterYear("31");
        paymentPage.enterCardHolder("IVAN");
        paymentPage.enterCVC("123");
        paymentPage.clickContinue();
        paymentPage.getYearError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверно указан срок действия карты"));
    }

    @Test
    void shouldFailIfCardMonthOutOfBounds() {
        PaymentPage paymentPage = new PaymentPage();

        paymentPage.clickBuy();
        paymentPage.enterCardNumber("1111 2222 3333 4444");
        paymentPage.enterMonth("13");
        paymentPage.enterYear("30");
        paymentPage.enterCardHolder("IVAN");
        paymentPage.enterCVC("123");
        paymentPage.clickContinue();
        paymentPage.getMonthError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверно указан срок действия карты"));
    }

    @Test
    void shouldFailIfCVCTooShort() {
        PaymentPage paymentPage = new PaymentPage();

        paymentPage.clickBuy();
        paymentPage.enterCardNumber("1111 2222 3333 4444");
        paymentPage.enterMonth("12");
        paymentPage.enterYear("30");
        paymentPage.enterCardHolder("IVAN");
        paymentPage.enterCVC("12");
        paymentPage.clickContinue();
        paymentPage.getCVCError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldFailWhenPayingWithWrongCard() {
        PaymentPage paymentPage = new PaymentPage();

        paymentPage.clickBuy();
        paymentPage.enterCardNumber("1111 2222 3333 4440");
        paymentPage.enterMonth("12");
        paymentPage.enterYear("30");
        paymentPage.enterCardHolder("IVAN");
        paymentPage.enterCVC("123");
        paymentPage.clickContinue();
        paymentPage.failureNotification().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
    }

    @Test
    void shouldFailIfFieldsEmpty() {
        PaymentPage paymentPage = new PaymentPage();

        paymentPage.clickBuy();
        paymentPage.clickContinue();
        paymentPage.getCardNumberError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверный формат"));
        paymentPage.getMonthError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверный формат"));
        paymentPage.getYearError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверный формат"));
        paymentPage.getCardHolderError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
        paymentPage.getCVCError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверный формат"));
    }

    //BUGZZZZ (all the following test should not succeed)

    @Test
    void shouldFailIfOwnerNameContainsNumbersOrSymbols() {
        PaymentPage paymentPage = new PaymentPage();

        paymentPage.clickBuy();
        paymentPage.enterCardNumber("1111 2222 3333 4444");
        paymentPage.enterMonth("12");
        paymentPage.enterYear("26");
        paymentPage.enterCardHolder("@!01");
        paymentPage.enterCVC("123");
        paymentPage.clickContinue();
        paymentPage.getCardHolderError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldFailWhenPayingWithDeclinedCard() {
        PaymentPage paymentPage = new PaymentPage();

        paymentPage.clickBuy();
        paymentPage.enterCardNumber("5555 6666 7777 8888");
        paymentPage.enterMonth("12");
        paymentPage.enterYear("30");
        paymentPage.enterCardHolder("IVAN");
        paymentPage.enterCVC("123");
        paymentPage.clickContinue();
        paymentPage.failureNotification().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
    }

    @Test
    void shouldFailIfMonthIsZero() {
        PaymentPage paymentPage = new PaymentPage();

        paymentPage.clickBuy();
        paymentPage.enterCardNumber("1111 2222 3333 4444");
        paymentPage.enterMonth("00");
        paymentPage.enterYear("26");
        paymentPage.enterCardHolder("IVAN");
        paymentPage.enterCVC("123");
        paymentPage.clickContinue();
        paymentPage.getCardHolderError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldFailIfOwnerNameTooLong() {
        PaymentPage paymentPage = new PaymentPage();

        paymentPage.clickBuy();
        paymentPage.enterCardNumber("1111 2222 3333 4444");
        paymentPage.enterMonth("12");
        paymentPage.enterYear("26");
        paymentPage.enterCardHolder("qwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnm");
        paymentPage.enterCVC("123");
        paymentPage.clickContinue();
        paymentPage.getCardHolderError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldOnlyReturnDeclinedNotification() {
        PaymentPage paymentPage = new PaymentPage();

        paymentPage.clickBuy();
        paymentPage.enterCardNumber("4536 9875 3648 9674");
        paymentPage.enterMonth("12");
        paymentPage.enterYear("26");
        paymentPage.enterCardHolder("IVAN");
        paymentPage.enterCVC("123");
        paymentPage.clickContinue();
        paymentPage.failureNotification()
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
        paymentPage.successNotification()
                .shouldNot(Condition.exist);
    }

    @Test
    void shouldFailIfEveryFieldNotValidated() {
        PaymentPage paymentPage = new PaymentPage();

        paymentPage.clickBuy();
        paymentPage.enterCardNumber("0000 0000 0000 0000");
        paymentPage.enterMonth("00");
        paymentPage.enterYear("00");
        paymentPage.enterCardHolder("000");
        paymentPage.enterCVC("000");
        paymentPage.clickContinue();
        paymentPage.getCardNumberError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверный формат"));
        paymentPage.getMonthError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверный формат"));
        paymentPage.getYearError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверный формат"));
        paymentPage.getCardHolderError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
        paymentPage.getCVCError().shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверный формат"));
    }
}