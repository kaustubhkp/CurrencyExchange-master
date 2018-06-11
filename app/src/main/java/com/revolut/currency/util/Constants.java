package com.revolut.currency.util;

import android.annotation.SuppressLint;

public class Constants {
    public static final long CONNECTION_TIMEOUT = 60;
    public static final long READ_TIMEOUT = 60;

    @SuppressLint("Typos")
    public static final String BASE_URL = "https://revolut.duckdns.org";
    public static final String GET_LATEST_EXCHANGE = "/latest";

    public static final String DEFAULT_BASE = "EUR";
}

