package test;

import com.codeborne.selenide.Selenide;
import data.CardInfo;
import data.DataHelper;
import db.DBHelper;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.MainPage;
import page.PaymentPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {
    @BeforeEach
    void setUp() {
        Selenide.open("http://localhost:8080/");
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }

    @Test
    @DisplayName("Успешная оплата дебетовой картой")
    void PayWithApprovedCard() {
        MainPage mainPage = new MainPage();
        PaymentPage paymentPage = mainPage.payByDebitCard();
        CardInfo approvedCard = DataHelper.getApprovedCard();

        paymentPage.fillPaymentForm(approvedCard);
        paymentPage.clickContinue();
        paymentPage.checkSuccessNotification();
    }

    @Test
    @Story("Негативные сценарии")
    @DisplayName("Отказ в оплате дебетовой картой")
    void paymentWithDeclinedCard() {
        MainPage mainPage = new MainPage();
        PaymentPage paymentPage = mainPage.payByDebitCard();
        CardInfo declinedCard = DataHelper.getDeclinedCard();

        paymentPage.fillPaymentForm(declinedCard);
        paymentPage.clickContinue();
        paymentPage.checkErrorNotification();

        DBHelper.PaymentEntity payment = DBHelper.getLatestPayment();
        assertEquals("DECLINED", payment.getStatus());
    }


}
