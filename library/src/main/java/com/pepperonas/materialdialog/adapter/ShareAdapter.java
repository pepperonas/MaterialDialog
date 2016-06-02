package com.pepperonas.materialdialog.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pepperonas.materialdialog.R;
import com.pepperonas.materialdialog.utils.Utils;

import java.util.List;


/**
 * @author Martin Pfeffer (pepperonas)
 */
public class ShareAdapter extends BaseAdapter {

    private Object[] items;
    private LayoutInflater mInflater;
    private Context mCtx;


    public ShareAdapter(@NonNull Context context) {
        this.mInflater = LayoutInflater.from(context);

        Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        List activities = context.getPackageManager().queryIntentActivities(sendIntent, 0);
        this.items = activities.toArray();
        this.mCtx = context;
    }


    public int getCount() {
        return items.length;
    }


    public Object getItem(int position) {
        return items[position];
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_list_item_share_app, null);

            holder = new ViewHolder();

            holder.logo = (ImageView) convertView.findViewById(R.id.iv_simple_list_item_share_app);
            holder.name = (TextView) convertView.findViewById(R.id.tv_simple_list_item_share_app);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(((ResolveInfo) items[position]).activityInfo
                .applicationInfo.loadLabel(mCtx.getPackageManager()).toString());

        holder.logo.setImageDrawable(((ResolveInfo) items[position]).activityInfo
                .applicationInfo.loadIcon(mCtx.getPackageManager()));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(
                Utils.dp2px(mCtx, 16),
                Utils.dp2px(mCtx, 4),
                Utils.dp2px(mCtx, 4),
                Utils.dp2px(mCtx, 4));

        holder.logo.setLayoutParams(layoutParams);

        return convertView;
    }


    static class ViewHolder {

        TextView name;
        ImageView logo;
    }

}