package com.jarvanmo.marsboot.widget.text;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.jarvanmo.common.R;

/**
 * Created by mo on 17-6-21.
 * Copyright © 2017, cnyanglao, Co,. Ltd. All Rights Reserve
 */

public class NotEmptyTextInputLayout extends TextInputLayout {

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(s.toString())) {
                setError("");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public NotEmptyTextInputLayout(Context context) {
        this(context, null);
    }

    public NotEmptyTextInputLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NotEmptyTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        EditText editText = getEditText();
        if (editText != null) {
            editText.addTextChangedListener(textWatcher);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EditText editText = getEditText();
        if (editText != null) {
            editText.removeTextChangedListener(textWatcher);
        }
    }

    public boolean doCheckEmpty() {

        if (isEmpty()) {
            String errorMsg = getContext().getString(R.string.can_not_be_empty);
            setError(errorMsg);
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        EditText editText = getEditText();
        return editText != null && TextUtils.isEmpty(editText.getText().toString().trim());
    }
}
