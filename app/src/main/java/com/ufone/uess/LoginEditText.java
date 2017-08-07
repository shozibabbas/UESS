package com.ufone.uess;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by sayyed.shozib on 8/7/2017.
 */

public class LoginEditText extends android.support.v7.widget.AppCompatEditText {
    public LoginEditText(Context context) {
        super(context);
    }

    public LoginEditText(Context context, AttributeSet attribute_set) {
        super(context, attribute_set);
    }

    public LoginEditText(Context context, AttributeSet attribute_set, int def_style_attribute) {
        super(context, attribute_set, def_style_attribute);
    }

    @Override
    public boolean onKeyPreIme(int key_code, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
            this.clearFocus();

        return super.onKeyPreIme(key_code, event);
    }
}
