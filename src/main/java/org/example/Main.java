package org.example;
public class Main {
    public static void main(String[] args) {
        ExchangeRate exchangeRate =  new ExchangeRate();
        Wallet wallet = new Wallet();
        wallet.addAmount(50,Currency.USD,exchangeRate);
        //System.out.printf(wallet.getWalletRef()+":"+wallet.getAmount(Currency.TND,exchangeRate));
        System.out.println(exchangeRate.getExchangeRate("TND", "USD"));
    }
}