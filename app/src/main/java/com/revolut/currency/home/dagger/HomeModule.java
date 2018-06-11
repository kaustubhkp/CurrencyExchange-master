package com.revolut.currency.home.dagger;

import com.revolut.currency.home.mvp.HomeActivity;
import com.revolut.currency.home.mvp.HomeContract;
import com.revolut.currency.home.mvp.HomePresenter;
import com.revolut.currency.net.CurrencyService;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    private final HomeContract.View homeView;

    public HomeModule(HomeActivity homeView) {
        this.homeView = homeView;
    }

    @Provides
    @HomeScope
    public HomeContract.Presenter provideHomePresenter(CurrencyService currencyService) {
        return new HomePresenter(currencyService, homeView);
    }
}
