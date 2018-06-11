package com.revolut.currency.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.revolut.currency.R;
import com.revolut.currency.home.listener.OnAmountChangedListener;
import com.revolut.currency.model.Currency;
import com.revolut.currency.util.LogUtil;
import com.revolut.currency.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrencyExchangeAdapter extends RecyclerView.Adapter<CurrencyExchangeAdapter.CurrencyViewHolder> {

    private static final String TAG = CurrencyExchangeAdapter.class.getName();
    private final Context mContext;
    private final List<Currency> currencyList;
    private OnAmountChangedListener mAmountChangedListener = null;
    private String mCurrencyName;
    private Float enteredAmount = 1.0F;

    public CurrencyExchangeAdapter(Context context, OnAmountChangedListener listener) {
        LogUtil.d(TAG, "CurrencyExchangeAdapter");
        mContext = context;
        mAmountChangedListener = listener;
        currencyList = new ArrayList<>();
    }

    public void setCurrencyList(@NonNull List<Currency>  rateLists, float amount) {
        LogUtil.d(TAG, "setCurrencyList with amount :: " + amount);
        enteredAmount = amount;
        currencyList.clear();
        currencyList.addAll(rateLists);
        notifyItemRangeChanged(1, currencyList.size());
    }
    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency,
                parent, false);
        return new CurrencyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        holder.bind(currencyList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }

    public void updateAmount(float amount) {
        LogUtil.d(TAG, "updateAmount:: " + amount);
        enteredAmount = amount;
        notifyItemRangeChanged(1, currencyList.size());
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_country_logo)
        ImageView mImageViewCountryLogo;
        @BindView(R.id.text_view_currency_name)
        TextView mTextViewCurrencyName;
        @BindView(R.id.edit_text_currency_amount)
        EditText mEditTextCurrencyAmount;

        CurrencyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(Currency currency, int position) {
            mTextViewCurrencyName.setText(currency.name);
            if (position == 0) {
                mCurrencyName = currency.name;
                mEditTextCurrencyAmount.setText((String.format(Locale.US, "%.2f", currency.value )));
            } else {
                mEditTextCurrencyAmount.setText((String.format(Locale.US, "%.2f", currency.value * enteredAmount)));
            }

            mImageViewCountryLogo.setImageResource(Utils.getCountryLogoResId(mContext, currency.name.toLowerCase()));

            mEditTextCurrencyAmount.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus && position > 0 && !mCurrencyName.equalsIgnoreCase(currency.name)) {
                    LogUtil.d(TAG, "OnFocusChange for position :: " + position);

                    mCurrencyName = currency.name;
                    enteredAmount = currency.value * enteredAmount;

                    List<Currency> updateList = Utils.updateCurrencyListLocally(currencyList, position, enteredAmount);

                    currencyList.clear();
                    currencyList.addAll(updateList);

                    notifyItemMoved(position, 0);

                    mAmountChangedListener.onAmountChanged(mCurrencyName, enteredAmount);
                }
            });

            mEditTextCurrencyAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (mEditTextCurrencyAmount.isFocused()) {
                        try {
                            enteredAmount = Float.valueOf(s.toString());
                        } catch (NumberFormatException ex) {
                            enteredAmount = 0.0F;
                            LogUtil.printStackTrace(ex);
                        }
                        LogUtil.d(TAG, "onTextChanged with name :: " + currency + " amount ::" + enteredAmount);
                        mAmountChangedListener.onAmountChanged(mCurrencyName, enteredAmount);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
    }
}
