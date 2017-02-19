/*
 * Copyright (c) 2017 Martin Pfeffer
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

package com.pepperonas.materialdialog.model;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class SelectionModel {

    private int position;
    private boolean isChecked;


    public SelectionModel(int position, boolean isChecked) {
        this.position = position;
        this.isChecked = isChecked;
    }


    public int getPosition() {
        return position;
    }


    public SelectionModel setPosition(int position) {
        this.position = position;
        return this;
    }


    public boolean isChecked() {
        return isChecked;
    }


    public SelectionModel setChecked(boolean checked) {
        isChecked = checked;
        return this;
    }
}
