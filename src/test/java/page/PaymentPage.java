package page;

import com.codeborne.selenide.SelenideElement;
import data.CardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement heading = $$("h3").findBy(text("Оплата по карте"));
    private SelenideElement cardNumberField = $("input[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("input[placeholder='08']");
    private SelenideElement yearField = $("input[placeholder='22']");
    private SelenideElement ownerField = $$(".input__inner").find(text("Владелец")).$(".input__control");
    private SelenideElement cvcField = $("input[placeholder='999']");
    private SelenideElement continueButton = $$("button").findBy(text("Продолжить"));

    private SelenideElement successNotification = $(".notification_status_ok");
    private SelenideElement errorNotification = $(".notification_status_error");

    public PaymentPage() {
        heading.shouldBe(visible);
    }

    public void fillPaymentForm(CardInfo card) {
        cardNumberField.val(card.getNumber());
        monthField.val(card.getMonth());
        yearField.val(card.getYear());
        ownerField.val(card.getOwner());
        cvcField.val(card.getCvc());
    }

    public void clickContinue() {
        continueButton.click();
    }

    public void checkSuccessNotification() {
        successNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void checkErrorNotification() {
        errorNotification.shouldBe(visible, Duration.ofSeconds(15));
    }
}
