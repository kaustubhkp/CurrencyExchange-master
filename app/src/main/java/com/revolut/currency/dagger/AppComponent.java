package com.revolut.currency.dagger;

import com.revolut.currency.net.CurrencyService;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    CurrencyService getCurrencyService();
}
