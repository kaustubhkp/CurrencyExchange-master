package com.revolut.currency.home.dagger;


import com.revolut.currency.dagger.AppComponent;
import com.revolut.currency.home.mvp.HomeActivity;

import dagger.Component;

@HomeScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
}
