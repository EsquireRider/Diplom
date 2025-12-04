package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    private SelenideElement buyButton = $$("button").find(text("Купить"));
    private SelenideElement creditButton = $$("button").find(text("Купить в кредит"));

    public MainPage() {
        buyButton.shouldBe(visible);
        creditButton.shouldBe(visible);
    }

    public PaymentPage payByDebitCard() {
        buyButton.click();
        return new PaymentPage();
    }

    public CreditPage payByCredit() {
        creditButton.click();
        return new CreditPage();
    }
}
