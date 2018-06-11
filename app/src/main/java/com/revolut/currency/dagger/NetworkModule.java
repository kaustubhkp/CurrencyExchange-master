package com.revolut.currency.dagger;

import com.revolut.currency.net.CurrencyService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.revolut.currency.util.Constants.BASE_URL;
import static com.revolut.currency.util.Constants.CONNECTION_TIMEOUT;
import static com.revolut.currency.util.Constants.READ_TIMEOUT;


@Module
class NetworkModule {

  @Provides
  @Singleton
  public OkHttpClient provideOkHttpClient() {
      return new OkHttpClient.Builder()
              .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
              .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
              .build();
  }

  @Provides
  @Singleton
  public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
  }

  @Provides
  @Singleton
  public CurrencyService provideCurrencyService(Retrofit retrofit) {
      return retrofit.create(CurrencyService.class);
  }
}
