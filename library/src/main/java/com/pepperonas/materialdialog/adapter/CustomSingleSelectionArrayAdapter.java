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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pepperonas.materialdialog.MaterialDialog;
import com.pepperonas.materialdialog.R;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class CustomSingleSelectionArrayAdapter extends ArrayAdapter<String> {

    private static final String TAG = "SingleSelectionAdapter";

    private LayoutInflater mInflater;
    private CharSequence[] mStrings;

    private int mSelectedPosition = -1;

    private MaterialDialog.ItemClickListener mItemClickListener;
    private MaterialDialog.ItemLongClickListener mItemLongClickListener;

    private Typeface mTypeface;


    public CustomSingleSelectionArrayAdapter(Context context, String[] strings, int selectedPosition, MaterialDialog
            .ItemClickListener itemClickListener, MaterialDialog.ItemLongClickListener itemLongClickListener, Typeface typeface) {
        super(context, R.layout.custom_list_item_single_selection, strings);

        init(context, strings, selectedPosition, itemClickListener, typeface);

    }


    private void init(Context context, String[] strings, int selectedPosition, MaterialDialog.ItemClickListener
            itemClickListener, Typeface typeface) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mStrings = strings;
        mSelectedPosition = selectedPosition;
        mItemClickListener = itemClickListener;
        mTypeface = typeface;
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View row = convertView;
        final ViewHolder viewHolder;

        if (row == null) {
            row = mInflater.inflate(R.layout.custom_list_item_single_selection, parent, false);

            final LinearLayout linearLayout = (LinearLayout) row.findViewById(R.id.ll_simple_list_item_single_selection);
            final RadioButton rbtn = (RadioButton) row.findViewById(R.id.rb_simple_list_item_single_selection);
            final TextView tv = (TextView) row.findViewById(R.id.tv_simple_list_item_single_selection);

            if (mTypeface != null) {
                rbtn.setTypeface(mTypeface);
                tv.setTypeface(mTypeface);
            }

            viewHolder = new ViewHolder();
            viewHolder.linearLayout = linearLayout;
            viewHolder.rbtn = rbtn;
            viewHolder.tv = tv;
            row.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) row.getTag();
        }

        final View finalRow = row;
        viewHolder.rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedPosition = (Integer) v.getTag();
                notifyDataSetChanged();

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
                RadioButton tmpRbtn = (RadioButton) v.findViewById(R.id.rb_simple_list_item_single_selection);
                mSelectedPosition = (Integer) tmpRbtn.getTag();
                notifyDataSetChanged();

                if (mItemClickListener != null) {
                    mItemClickListener.onClick(finalRow, position, finalRow.getId());
                }
                if (mItemLongClickListener != null) {
                    mItemLongClickListener.onLongClick(finalRow, position, finalRow.getId());
                }
            }
        });

        viewHolder.rbtn.setChecked(position == mSelectedPosition);

        viewHolder.tv.setText(mStrings[position]);
        viewHolder.rbtn.setTag(position);
        viewHolder.tv.setTag(position);

        return row;
    }


    static class ViewHolder {

        LinearLayout linearLayout;
        RadioButton rbtn;
        TextView tv;
    }

}
