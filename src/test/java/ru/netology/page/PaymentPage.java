package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {

    private SelenideElement paymentButton = $("button");
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement cardHolderField = $$("span.input__top").findBy(Condition.text("Владелец"))
            .sibling(0).$("input");
    private SelenideElement cvcField = $("[placeholder='999']");
    private SelenideElement continueButton = $$("button.button_view_extra")
            .findBy(Condition.text("Продолжить"));

    public SelenideElement getCardNumberError() {
        return $(".input_invalid .input__sub");
    }

    public SelenideElement getMonthError() {
        return $$("span.input__top").findBy(Condition.text("Месяц"))
                .parent().$(".input__sub");
    }

    public SelenideElement getYearError() {
        return $$("span.input__top").findBy(Condition.text("Год"))
                .parent().$(".input__sub");
    }

    public SelenideElement getCardHolderError() {
        return $$("span.input__top").findBy(Condition.text("Владелец"))
                .parent().$(".input__sub");
    }

    public SelenideElement getCVCError() {
        return $$("span.input__top").findBy(Condition.text("CVC/CVV"))
                .parent().$(".input__sub");
    }

    public void enterCardNumber(String number) {
        cardNumberField.setValue(number);
    }

    public void clickBuy() {
        paymentButton.click();
    }

    public void enterMonth(String month) {
        monthField.setValue(month);
    }

    public void enterYear(String year) {
        yearField.setValue(year);
    }

    public void enterCardHolder(String name) {
        cardHolderField.setValue(name);
    }

    public void enterCVC(String cvc) {
        cvcField.setValue(cvc);
    }

    public void clickContinue() {
        continueButton.click();
    }

    public SelenideElement successNotification() {
        return $(".notification_status_ok");
    }

    public SelenideElement failureNotification() {
        return $(".notification_status_error");
    }
}