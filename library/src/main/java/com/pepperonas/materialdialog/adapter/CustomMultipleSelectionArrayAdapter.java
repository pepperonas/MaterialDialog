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
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pepperonas.materialdialog.MaterialDialog;
import com.pepperonas.materialdialog.R;
import com.pepperonas.materialdialog.model.SelectionModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class CustomMultipleSelectionArrayAdapter extends ArrayAdapter<String> {

    private static final String TAG = "MultiSelectionAdapter";

    private LayoutInflater mInflater;
    private CharSequence[] mStrings;

    private List<SelectionModel> mSelectionList = new ArrayList<>();

    private MaterialDialog.ItemClickListener mItemClickListener;
    private MaterialDialog.ItemLongClickListener mItemLongClickListener;

    private Typeface mTypeface;


    public CustomMultipleSelectionArrayAdapter(Context context, String[] strings, Integer[] selectedPositions, MaterialDialog
            .ItemClickListener itemClickListener, MaterialDialog.ItemLongClickListener itemLongClickListener, Typeface typeface) {
        super(context, R.layout.custom_list_item_multiple_selection, strings);

        init(context, strings, selectedPositions, itemClickListener, typeface);

    }


    private void init(Context context, String[] strings, Integer[] selectedPositions, MaterialDialog.ItemClickListener
            itemClickListener, Typeface typeface) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mStrings = strings;
        List<Integer> selected = Arrays.asList(selectedPositions);
        for (int i = 0; i < strings.length; i++) {
            mSelectionList.add(new SelectionModel(i, selected.contains(i)));
        }

        for (SelectionModel sm : mSelectionList) {
            Log.i(TAG, "init " + sm.getPosition() + " == " + sm.isChecked());
        }
        mItemClickListener = itemClickListener;
        mTypeface = typeface;
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View row = convertView;
        final ViewHolder viewHolder;

        if (row == null) {
            row = mInflater.inflate(R.layout.custom_list_item_multiple_selection, parent, false);

            final LinearLayout linearLayout = (LinearLayout) row.findViewById(R.id.ll_simple_list_item_multiple_selection);
            final CheckBox cbx = (CheckBox) row.findViewById(R.id.cbx_simple_list_item_multiple_selection);
            final TextView tv = (TextView) row.findViewById(R.id.tv_simple_list_item_multiple_selection);

            if (mTypeface != null) {
                cbx.setTypeface(mTypeface);
                tv.setTypeface(mTypeface);
            }

            viewHolder = new ViewHolder();
            viewHolder.linearLayout = linearLayout;
            viewHolder.cbx = cbx;
            viewHolder.tv = tv;
            row.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) row.getTag();
        }

        final View finalRow = row;

        viewHolder.cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSelectionList.get(position).setChecked(isChecked);

                if (mItemClickListener != null) {
                    mItemClickListener.onClick(finalRow, position, finalRow.getId());
                }
                if (mItemLongClickListener != null) {
                    mItemLongClickListener.onLongClick(finalRow, position, finalRow.getId());
                }
            }
        });

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.cbx.setChecked(!mSelectionList.get(position).isChecked());
                notifyDataSetChanged();
            }
        });

        viewHolder.tv.setText(mStrings[position]);
        viewHolder.cbx.setTag(position);
        viewHolder.tv.setTag(position);
        viewHolder.cbx.setChecked(mSelectionList.get(position).isChecked());

        return row;
    }


    static class ViewHolder {

        LinearLayout linearLayout;
        CheckBox cbx;
        TextView tv;
    }

}
