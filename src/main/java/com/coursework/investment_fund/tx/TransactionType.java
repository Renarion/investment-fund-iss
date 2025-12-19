package com.coursework.investment_fund.tx;

public enum TransactionType {

    DEPOSIT("Пополнение"),
    WITHDRAW("Вывод");

    private final String label;

    TransactionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}