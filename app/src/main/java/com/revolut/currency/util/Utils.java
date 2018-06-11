package com.revolut.currency.util;

import android.content.Context;

import com.revolut.currency.model.Currency;
import com.revolut.currency.model.RateList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {

    public static int getCountryLogoResId(Context context, String currencyName) {
        return context.getResources().getIdentifier("flag_" + currencyName, "drawable", context.getPackageName());
    }

    public static List<Currency> fromServerToLocal(RateList fromServer, float amount) {
        List<Currency> list = new ArrayList<>();
        list.add(new Currency(fromServer.base.toUpperCase(), amount));
        for (Map.Entry<String, Float> current : fromServer.rates.entrySet()) {
            list.add(new Currency(current.getKey(), current.getValue()));
        }
        return list;
    }

    public static List<Currency> updateCurrencyListLocally(List<Currency> currencyList, int position, Float amount) {
        List<Currency> list = new ArrayList<>();
        list.add(new Currency(currencyList.get(position).name, 1.0F));
        for (int iStart = 1; iStart < currencyList.size(); iStart++) {
            if (position != iStart) {
                Currency currency = currencyList.get(iStart);
                list.add(new Currency(currency.name, currency.value / amount));
            } else {
                list.add(new Currency(currencyList.get(0).name, 1/currencyList.get(position).value));
            }
        }
        return list;
    }
}
