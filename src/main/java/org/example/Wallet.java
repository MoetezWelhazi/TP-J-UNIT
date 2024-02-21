package org.example;

import java.sql.SQLOutput;

public class Wallet {
    private String walletRef;
    private double amount;
    private Currency currency;

    public String getWalletRef() {
        return walletRef;
    }

    public Wallet(String walletRef, double amount, Currency currency) {
        this.walletRef = walletRef;
        this.amount = amount;
        this.currency = currency;
    }

    public Wallet() {
        amount = 0;
        currency = Currency.TND;
        walletRef= "Moe";
    }

    public void addAmount(double amount, Currency currency, ExchangeRate exchangeRateMap) {
    //code
        this.amount += amount * exchangeRateMap.getExchangeRate(currency.name(), this.currency.name());
    }
    public double getAmount(Currency targetCurrency, ExchangeRate exchangeRateMap) {
    //code
        System.out.println(targetCurrency.name());
        System.out.println(this.currency.name());
        return this.amount * exchangeRateMap.getExchangeRate(this.currency.name(), targetCurrency.name());
    }
}
