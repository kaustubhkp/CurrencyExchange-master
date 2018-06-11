package com.revolut.currency.home.mvp;


import android.text.TextUtils;

import com.revolut.currency.model.Currency;
import com.revolut.currency.net.CurrencyService;
import com.revolut.currency.util.Constants;
import com.revolut.currency.util.LogUtil;
import com.revolut.currency.util.Utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter implements HomeContract.Presenter {
    private static final String TAG = HomePresenter.class.getName();
    private final CurrencyService mCurrencyService;
    private final CompositeDisposable compositeDisposable;
    private final HomeContract.View view;
    private boolean isLoaded = false;
    private boolean viewStopped = false;
    private String currentBase;
    private float enteredAmount;

    public HomePresenter(CurrencyService currencyService, HomeContract.View view) {
        LogUtil.d(TAG, "HomePresenter");
        this.mCurrencyService = currencyService;
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getCurrencyList(String base, Float amount) {
        LogUtil.d(TAG, "getCurrencyList with base::" + base + " and amount:: " + amount);
        enteredAmount = amount;
        if (!TextUtils.isEmpty(currentBase) && currentBase.equalsIgnoreCase(base)) {
            LogUtil.d(TAG, "currency base is not changed");
            view.updateAmount(amount);
        } else {
            currentBase = base;
            compositeDisposable.clear();
            compositeDisposable.add(mCurrencyService.getLatestExchangeRates(currentBase)
                    .doOnSubscribe(__ -> {
                        if (!isLoaded) {
                            view.showProgress(true);
                        }
                    })
                    .delay(1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .repeatUntil(() -> viewStopped)
                    .map(fromServer -> Utils.fromServerToLocal(fromServer, enteredAmount))
                    .doOnTerminate(() -> view.showProgress(false))
                    .subscribe(this::handleResult, view::showError));
        }
    }

    private void handleResult(List<Currency> currencyList) {
        LogUtil.d(TAG, "handleResult");
        view.updateList(currencyList, enteredAmount);
        if (!isLoaded) {
            view.showProgress(false);
        }
        isLoaded = true;
    }

    @Override
    public void start() {
        LogUtil.d(TAG, "start");
        viewStopped = false;
        getCurrencyList(Constants.DEFAULT_BASE, 1.0F);
    }

    @Override
    public void stop() {
        LogUtil.d(TAG, "stop");
        viewStopped = true;
        compositeDisposable.clear();
    }
}
