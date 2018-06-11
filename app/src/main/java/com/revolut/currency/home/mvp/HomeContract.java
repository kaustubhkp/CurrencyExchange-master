package com.revolut.currency.home.mvp;

import android.support.annotation.NonNull;

import com.revolut.currency.model.Currency;
import java.util.List;


public interface HomeContract {

    interface View {
        void updateList(@NonNull List<Currency> currencyList, Float amount);
        void showError(Throwable throwable);
        void showProgress(boolean shouldShow);
        void updateAmount(Float amount);
    }

    interface Presenter {
        void getCurrencyList(String base, Float amount);
        void start();
        void stop();
    }
}
