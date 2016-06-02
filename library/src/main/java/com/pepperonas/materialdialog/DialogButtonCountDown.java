/*
 * Copyright (c) 2016 Martin Pfeffer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pepperonas.materialdialog;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class DialogButtonCountDown extends CountDownTimer {

    private String mFinishedText;
    private Button mButton;


    public DialogButtonCountDown(long millisInFuture, long countDownInterval, String finishedText, Button button) {
        super(millisInFuture, countDownInterval);
        this.mButton = button;
        this.mFinishedText = finishedText;
        mButton.setEnabled(false);
        mButton.setClickable(false);
    }


    @Override
    public void onFinish() {
        mButton.setText(mFinishedText);
        mButton.setEnabled(true);
        mButton.setClickable(true);
    }


    @Override
    public void onTick(long millisUntilFinished) {
        int remaining = (int) (millisUntilFinished / 1000);
        mButton.setText(String.valueOf(remaining));
    }
}
