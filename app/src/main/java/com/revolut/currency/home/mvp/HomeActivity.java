package com.revolut.currency.home.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.revolut.currency.R;
import com.revolut.currency.app.RevolutApplication;
import com.revolut.currency.home.adapter.CurrencyExchangeAdapter;
import com.revolut.currency.home.dagger.DaggerHomeComponent;
import com.revolut.currency.home.dagger.HomeModule;
import com.revolut.currency.model.Currency;
import com.revolut.currency.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeContract.View{

    private static final String TAG = HomeActivity.class.getName();
    @BindView(R.id.recycle_view_currency_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_view)
    ProgressBar progressBar;

    @Inject
    HomeContract.Presenter presenter;

    private CurrencyExchangeAdapter mCurrencyExchangeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.d(TAG, "onCreate");
        DaggerHomeComponent.builder()
                .appComponent(((RevolutApplication)getApplication()).getAppComponent())
                .homeModule(new HomeModule(this))
                .build().inject(this);
        ButterKnife.bind(this);

        mCurrencyExchangeAdapter = new CurrencyExchangeAdapter(getApplicationContext(), (currencyName, amount)
                -> presenter.getCurrencyList(currencyName, amount));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mCurrencyExchangeAdapter);
        mRecyclerView.getItemAnimator().setChangeDuration(0);
    }


    @Override
    public void updateList(@NonNull List<Currency> currencyList, Float amount) {
        LogUtil.d(TAG, "updateCurrencyListLocally with amount::" + amount);
        mCurrencyExchangeAdapter.setCurrencyList(currencyList, amount);
    }

    @Override
    public void showError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(boolean shouldShow) {
        LogUtil.d(TAG, "showProgress::" + shouldShow);
        progressBar.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void updateAmount(Float amount) {
        LogUtil.d(TAG, "updateAmount::" + amount);
        mCurrencyExchangeAdapter.updateAmount(amount);
    }


    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(TAG, "onStart");
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG, "onStop");
        presenter.stop();
    }
}
