package pl.droidsonroids.jspoon;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import pl.droidsonroids.jspoon.annotation.Selector;
import pl.droidsonroids.jspoon.rule.CustomLocaleRule;

public class BigDecimalFormatTest {

    private final static Locale CUSTOM_DEFAULT_LOCALE = Locale.US;
    private Jspoon jspoon;

    @Rule
    public CustomLocaleRule customLocaleRule = new CustomLocaleRule(CUSTOM_DEFAULT_LOCALE);

    @Before
    public void setUp() {
        jspoon = Jspoon.create();
    }

    private static class Money {

        @Selector(value = "#amount", format = "0,000.00") BigDecimal amount;
    }

    @Test
    public void amount() throws ParseException {
        Money money = createObjectFromHtml(Money.class);
        DecimalFormat format = new DecimalFormat("0,000.00");
        format.setParseBigDecimal(true);
        BigDecimal expected = (BigDecimal) format.parse("50,000.00");
        assertEquals(money.amount, expected);
    }

    private <T> T createObjectFromHtml(Class<T> className) {
        HtmlAdapter<T> htmlAdapter = jspoon.adapter(className);
        return htmlAdapter.fromHtml("<div>"
            + "<span id='amount'>50,000.00</span>"
            + "</div>");
    }
}
