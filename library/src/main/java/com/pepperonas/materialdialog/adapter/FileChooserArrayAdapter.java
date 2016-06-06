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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pepperonas.materialdialog.MaterialDialog;
import com.pepperonas.materialdialog.R;

import java.io.File;
import java.util.List;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class FileChooserArrayAdapter extends ArrayAdapter<File> {

    private MaterialDialog mMaterialDialog;
    private boolean mDismissOnSelection = false;

    private LayoutInflater mInflater;
    private List<File> mFiles;

    private MaterialDialog.ItemClickListener mItemClickListener;
    private MaterialDialog.ItemLongClickListener mItemLongClickListener;


    public FileChooserArrayAdapter(@NonNull MaterialDialog materialDialog, @NonNull Context context, @NonNull List<File>
            strings, @NonNull File startPath, @Nullable MaterialDialog.ItemClickListener itemClickListener, @Nullable
                                           MaterialDialog.ItemLongClickListener itemLongClickListener, boolean
                                           dismissOnSelection) {
        super(context, R.layout.custom_list_item, strings);

        mMaterialDialog = materialDialog;
        mDismissOnSelection = dismissOnSelection;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFiles = strings;
        mItemClickListener = itemClickListener;
        mItemLongClickListener = itemLongClickListener;
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View row = convertView;
        final ViewHolder viewHolder;

        if (row == null) {
            row = mInflater.inflate(R.layout.custom_list_item, parent, false);

            final LinearLayout linearLayout = (LinearLayout) row.findViewById(R.id.ll_simple_list_item);
            final TextView tv = (TextView) row.findViewById(R.id.tv_simple_list_item);

            viewHolder = new ViewHolder();
            viewHolder.linearLayout = linearLayout;
            viewHolder.tv = tv;
            row.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) row.getTag();
        }

        final View finalRow = row;
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mItemClickListener != null) {
                    mItemClickListener.onClick(finalRow, position, finalRow.getId());
                    if (mDismissOnSelection) {
                        mMaterialDialog.dismiss();
                    }
                }
                if (mItemLongClickListener != null) {
                    mItemLongClickListener.onLongClick(finalRow, position, finalRow.getId());
                    if (mDismissOnSelection) {
                        mMaterialDialog.dismiss();
                    }
                }

            }
        });


        viewHolder.tv.setText(mFiles.get(position).getName());
        viewHolder.tv.setTag(position);

        return row;
    }


    static class ViewHolder {

        LinearLayout linearLayout;
        TextView tv;

    }

}
