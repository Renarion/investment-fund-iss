package com.coursework.investment_fund.web;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component("vf")
public class ViewFormat {

    private static final DateTimeFormatter RU_DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String date(LocalDate d) {
        return d == null ? "" : d.format(RU_DATE);
    }

    public String money(BigDecimal v) {
        if (v == null) return "";
        DecimalFormatSymbols s = new DecimalFormatSymbols(new Locale("ru", "RU"));
        s.setGroupingSeparator(' ');
        s.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", s);
        return df.format(v) + " ₽";
    }

    public String txType(String t) {
        if (t == null) return "";
        return switch (t) {
            case "DEPOSIT" -> "Пополнение";
            case "WITHDRAW" -> "Вывод";
            default -> t;
        };
    }

    public String phone(String raw) {
    if (raw == null) return "";
    String digits = raw.replaceAll("\\D", "");

    // Если ввели 8XXXXXXXXXX -> сделаем 7XXXXXXXXXX
    if (digits.length() == 11 && digits.startsWith("8")) {
        digits = "7" + digits.substring(1);
    }

    // Если ввели 977XXXXXXX -> сделаем 7 + ...
    if (digits.length() == 10) {
        digits = "7" + digits;
    }

    if (digits.length() != 11 || !digits.startsWith("7")) {
        return raw; // не ломаем странные форматы
    }

    String c = digits.substring(1, 4);
    String a = digits.substring(4, 7);
    String b = digits.substring(7, 9);
    String d = digits.substring(9, 11);

    return "+7(" + c + ")" + a + "-" + b + "-" + d;
    }
}