package shopping_app.util;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

@Component
public class MoneyUtil { //Format dạng số từ 1000 -> 1.000

    private final DecimalFormat df;

    public MoneyUtil() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        this.df = new DecimalFormat("#,###", symbols);
    }

    public String format(Long value) {
        if (value == null) return null;
        return df.format(value);
    }
}
