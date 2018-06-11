package com.revolut.currency.home.listener;


public interface OnAmountChangedListener {
    void onAmountChanged(String currencyName, Float amount);
}
