package com.revolut.currency.app;

import android.annotation.SuppressLint;
import android.app.Application;

import com.revolut.currency.dagger.AppComponent;
import com.revolut.currency.dagger.AppModule;
import com.revolut.currency.dagger.DaggerAppComponent;

@SuppressLint("Typos")
public class RevolutApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                            .appModule(new AppModule(this))
                            .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
