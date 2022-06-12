package com.example.advancedprojectandroidclient.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * A TextWatcher that will show the error message if the text is empty.
 */
public class LoginScreenTextWatcher implements TextWatcher {

    private final TextView errorTv;

    public LoginScreenTextWatcher(TextView errorTv) {
        this.errorTv = errorTv;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().isEmpty()) {
            errorTv.setVisibility(TextView.GONE);
        } else {
            errorTv.setVisibility(TextView.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
