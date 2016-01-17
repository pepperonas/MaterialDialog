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

package com.pepperonas.materialdialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pepperonas.materialdialog.R;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class SingleChoiceArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;


    public SingleChoiceArrayAdapter(Context context, String[] values) {
        super(context, R.layout.list_item_single_choice, values);
        this.context = context;
        this.values = values;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_item_single_choice, parent, false);
        RadioButton radioButton = (RadioButton) row.findViewById(R.id.rb_item_single_choice);
        TextView textView = (TextView) row.findViewById(R.id.tv_item_single_choice);
        textView.setText(values[position]);



        return row;
    }
}