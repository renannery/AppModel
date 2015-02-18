package com.origamitecnologia.appmodel.control;

import android.support.annotation.NonNull;

import com.rengwuxian.materialedittext.validation.METValidator;

public class EditTextValidator extends METValidator {

    public EditTextValidator(@NonNull String errorMessage) {
        super(errorMessage);
    }

    @Override
    public boolean isValid(@NonNull CharSequence charSequence, boolean b) {
        return charSequence.length() > 0;
    }
}
