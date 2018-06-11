
package com.revolut.currency.util;

import android.annotation.SuppressLint;
import android.util.Log;

import com.revolut.currency.BuildConfig;

public class LogUtil {
    private static final boolean DEBUG = BuildConfig.DEBUG;
    @SuppressLint("Typos")
    private static final String TAG = "Revolut";

    /**
     * Log debug.
     *
     * @param tag - The tag to use for this logging event.
     * @param msg - The message to print out for this logging event.
     */
    public static void d(String tag, String msg) {
        if (DEBUG && msg != null) {
            Log.d(TAG, "[" + tag + "] " + msg);
        }
    }

    /**
     * Print the stack trace.
     *
     * @param e - The exception to use for this stack trace.
     */
    public static void printStackTrace(Exception e) {
        if (DEBUG && e != null) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

}
