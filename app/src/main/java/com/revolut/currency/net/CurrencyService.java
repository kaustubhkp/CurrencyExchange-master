package com.revolut.currency.net;


import com.revolut.currency.util.Constants;
import com.revolut.currency.model.RateList;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencyService {

    @GET(Constants.GET_LATEST_EXCHANGE)
    Single<RateList> getLatestExchangeRates(@Query("base") String base);
}
