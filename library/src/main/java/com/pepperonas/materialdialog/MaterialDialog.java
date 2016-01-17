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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class MaterialDialog extends AlertDialog {

    private static final String TAG = "MaterialDialog";


    public MaterialDialog(final Builder builder) {
        super(builder.context);

        invoke(builder);

    }


    public MaterialDialog(final Builder builder, int style) {
        super(builder.context, style);

        invoke(builder);

    }


    private void invoke(final Builder builder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (builder.dimPercent != -1) {
                float value = (float) builder.dimPercent / 100f;
                if (value < 0f) value = 0f;
                if (value > 1f) value = 1f;
                getWindow().setDimAmount(value);
            }
        }

        if (builder.customView != null) {
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);

            this.setView(
                    builder.customView,
                    builder.viewSpacingLeft, builder.viewSpacingTop,
                    builder.viewSpacingRight, builder.viewSpacingBottom);
        }


        /**
         * list
         * */
        if (builder.items != null && builder.items.length > 0) {
            if (builder.customView != null) {
                Log.w(TAG, "ListView will override the custom view.");
            }
            final ListView lv = new ListView(builder.context);
            lv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

           /* if (!builder.multiChoice && !builder.blankListing) {
                final SingleChoiceArrayAdapter scaa = new SingleChoiceArrayAdapter(
                        builder.context, builder.items);
                lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
                lv.setDivider(null);
                lv.setAdapter(scaa);*/


            if (!builder.multiChoice && !builder.blankListing) {
                final ArrayAdapter<String> aa = new ArrayAdapter<>(builder.context, android.R.layout.simple_list_item_single_choice, builder.items);
                lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
                lv.setDivider(null);
                lv.setAdapter(aa);

            } else if (!builder.multiChoice) {
                final ArrayAdapter<String> aa = new ArrayAdapter<>(
                        builder.context, android.R.layout.simple_list_item_1, builder.items);
                lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
                lv.setDivider(null);
                lv.setAdapter(aa);

            } else {
                final ArrayAdapter<String> aa = new ArrayAdapter<>(
                        builder.context, android.R.layout.simple_list_item_multiple_choice, builder.items);
                lv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                lv.setDivider(null);
                lv.setAdapter(aa);
            }


            this.setView(lv);

            if (builder.preSelectedIndices != null) {
                if (!builder.multiChoice && builder.preSelectedIndices.length > 1) {
                    Log.w(TAG, "Can't select multiple items in single selection. Will only select \"" +
                               builder.items[builder.preSelectedIndices[builder.preSelectedIndices.length - 1]] + "\".");
                }

                if (builder.preSelectedIndices.length <= builder.items.length) {
                    if (builder.preSelectedIndices.length > 0) {
                        for (int i = 0; i < builder.preSelectedIndices.length; i++) {
                            lv.setItemChecked(builder.preSelectedIndices[i], true);
                        }
                    }
                } else Log.w(TAG, "More items to check than exists. Will nothing select.");
            }

            if (builder.itemClickListener != null) {
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        builder.itemClickListener.onClick(view, position, id);
                        lv.setItemChecked(position, true);
                        if (builder.dismissOnSelection) {
                            dismiss();
                        }
                    }
                });
            }
            if (builder.itemLongClickListener != null)
                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        builder.itemLongClickListener.onLongClick(view, position, id);
                        if (builder.itemLongClickable) {
                            lv.setItemChecked(position, true);
                        }
                        if (builder.dismissOnSelection) {
                            dismiss();
                        }
                        return builder.itemLongClickable;
                    }
                });
            if (builder.itemSelectedListener != null) {
                lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        builder.itemSelectedListener.onSelected(view, position, id);
                        lv.setItemChecked(position, true);
                        if (builder.dismissOnSelection) {
                            dismiss();
                        }
                    }


                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        builder.itemSelectedListener.onNothingSelected(parent);
                    }
                });
            }

        } // end list


        if (builder.title != null) {
            this.setTitle(builder.title);
        }

        if (builder.message != null) {
            this.setMessage(builder.message);
        }

        if (builder.icon != -1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.setIcon(builder.context.getDrawable(builder.icon));
            } else this.setIcon(builder.context.getResources().getDrawable(builder.icon));
        }


        this.setCancelable(builder.cancelable);
        this.setCanceledOnTouchOutside(builder.canceledOnTouchOutside);

        if (builder.positiveText != null) {
            this.setButton(BUTTON_POSITIVE, builder.positiveText, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (builder.buttonCallback != null) {
                        builder.buttonCallback.onAny(MaterialDialog.this);
                        builder.buttonCallback.onPositive(MaterialDialog.this);
                    }
                }
            });
        }

        if (builder.neutralText != null) {
            this.setButton(BUTTON_NEUTRAL, builder.neutralText, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (builder.buttonCallback != null) {
                        builder.buttonCallback.onAny(MaterialDialog.this);
                        builder.buttonCallback.onNeutral(MaterialDialog.this);
                    }
                }
            });
        }

        if (builder.negativeText != null) {
            this.setButton(BUTTON_NEGATIVE, builder.negativeText, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (builder.buttonCallback != null) {
                        builder.buttonCallback.onAny(MaterialDialog.this);
                        builder.buttonCallback.onNegative(MaterialDialog.this);
                    }
                }
            });
        }

        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                AlertDialog dialog = (AlertDialog) d;

                if (builder.showListener != null) {
                    builder.showListener.onShow(dialog);
                }

                // button color
                if (builder.positiveText != null && builder.positiveColor != -1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        dialog.getButton(BUTTON_POSITIVE).setTextColor(builder.context.getColor(builder.positiveColor));
                    } else dialog.getButton(BUTTON_POSITIVE).setTextColor(builder.context.getResources().getColor(builder.positiveColor));

                    if (builder.positiveDelayed && builder.finishedText != null) {
                        DialogButtonCountDown countDown = new DialogButtonCountDown(builder.millisInFuture, builder.countDownInterval, builder.finishedText, dialog.getButton(BUTTON_POSITIVE));
                        countDown.start();
                    }
                }
                if (builder.neutralText != null && builder.neutralColor != -1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        dialog.getButton(BUTTON_NEUTRAL).setTextColor(builder.context.getColor(builder.neutralColor));
                    } else dialog.getButton(BUTTON_NEUTRAL).setTextColor(builder.context.getResources().getColor(builder.neutralColor));
                }
                if (builder.negativeText != null && builder.negativeColor != -1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        dialog.getButton(BUTTON_NEGATIVE).setTextColor(builder.context.getColor(builder.negativeColor));
                    } else dialog.getButton(BUTTON_NEGATIVE).setTextColor(builder.context.getResources().getColor(builder.negativeColor));
                }

                // dialog height
                Log.i(TAG, "MaterialDialog " + getWindow().getDecorView().getHeight());
            }

        });


        /**
         * license dialog
         * */
        if (builder.licenseDialog) {
            if (builder.libNames.length == builder.libDevelopers.length
                && builder.libDevelopers.length == builder.libLicenses.length
                && builder.libNames.length != 0) {

                LinearLayout llLibContainer = (LinearLayout) builder.customView.findViewById(R.id.ll_dialog_license_container);

                for (int i = 0; i < builder.libNames.length; i++) {
                    LayoutInflater inflater = LayoutInflater.from(builder.context);
                    LinearLayout llLicenseInfo = (LinearLayout) inflater.inflate(R.layout.item_license, null);

                    ((TextView) llLicenseInfo.findViewById(R.id.tv_lib_name)).setText(builder.libNames[i]);
                    ((TextView) llLicenseInfo.findViewById(R.id.tv_lib_developer)).setText(builder.libDevelopers[i]);
                    ((TextView) llLicenseInfo.findViewById(R.id.tv_lib_licence)).setText(builder.libLicenses[i]);
                    llLibContainer.addView(llLicenseInfo);
                }
            } else Log.e(TAG, "Invalid license information. Check the size of the arrays.");
        } // end license dialog


        /**
         * changelog dialog
         * */
        if (builder.changelogDialog) {
            if (builder.clVersionNames.length == builder.clDates.length
                && builder.clDates.length == builder.clReleaseInfos.length
                && builder.clVersionNames.length != 0) {

                LinearLayout llClContainer = (LinearLayout) builder.customView.findViewById(R.id.ll_dialog_changelog_container);

                for (int i = 0; i < builder.clVersionNames.length; i++) {
                    LayoutInflater inflater = LayoutInflater.from(builder.context);
                    LinearLayout llChangelog = (LinearLayout) inflater.inflate(R.layout.item_changelog, null);

                    ((TextView) llChangelog.findViewById(R.id.tv_cl_version)).setText(builder.clVersionNames[i]);
                    ((TextView) llChangelog.findViewById(R.id.tv_cl_date)).setText(builder.clDates[i]);

                    ReleaseInfo ri = builder.clReleaseInfos[i];
                    for (int j = 0; j < ri.getReleaseInfo().size(); j++) {
                        LayoutInflater _inflater = LayoutInflater.from(builder.context);
                        LinearLayout llReleaseInfo = (LinearLayout) _inflater.inflate(R.layout.item_changelog_release_info, null);
                        ((TextView) llReleaseInfo.findViewById(R.id.tv_release_info)).setText(ri.getReleaseInfo().get(j));
                        if (builder.clBullet != null) {
                            // ensure to set custom bullet
                            ((TextView) llReleaseInfo.findViewById(R.id.tv_release_info_bullet)).setText(builder.clBullet);
                        } else llReleaseInfo.findViewById(R.id.tv_release_info_bullet).setVisibility(View.GONE);

                        ((LinearLayout) llChangelog.findViewById(R.id.ll_dialog_changelog_release_info_container)).addView(llReleaseInfo);
                    }

                    llClContainer.addView(llChangelog);
                }
            } else Log.e(TAG, "Invalid changelog information. Check the size of the arrays.");
        } // end changelog dialog


        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (builder.dismissListener != null) {
                    builder.dismissListener.onDismiss();
                }
            }
        });
    }


    public static class Builder {

        /**
         * Base
         */
        private final Context context;
        private int style = -1;
        private CharSequence title;
        private CharSequence message;
        private int dimPercent = -1;
        private CharSequence positiveText;
        private CharSequence neutralText;
        private CharSequence negativeText;
        private int positiveColor = -1;
        private int neutralColor = -1;
        private int negativeColor = -1;
        private ButtonCallback buttonCallback;
        private ShowListener showListener;
        private DismissListener dismissListener;
        private int icon = -1;
        private boolean canceledOnTouchOutside = true;
        private boolean cancelable = true;
        private View customView;
        int viewSpacingLeft;
        int viewSpacingTop;
        int viewSpacingRight;
        int viewSpacingBottom;

        /**
         * List
         */
        // false will show RadioButton / CheckBox
        private boolean blankListing;
        private boolean multiChoice;
        private boolean dismissOnSelection = false;
        private String[] items;
        private int[] preSelectedIndices;
        private ItemClickListener itemClickListener;
        private ItemLongClickListener itemLongClickListener;
        private ItemSelectedListener itemSelectedListener;
        private boolean itemLongClickable = false;

        // delayed positive
        private boolean positiveDelayed;
        private long millisInFuture;
        private long countDownInterval;
        private String finishedText;

        /**
         * License
         */
        private boolean licenseDialog = false;
        private String[] libNames;
        private String[] libDevelopers;
        private String[] libLicenses;

        /**
         * Changelog
         */
        private boolean changelogDialog = false;
        private String[] clVersionNames;
        private String[] clDates;
        private ReleaseInfo[] clReleaseInfos;
        private String clBullet;


        public Builder(Context context) {this.context = context;}


        public Builder(Context context, int style) {
            this.context = context;
            this.style = style;
        }


        public Builder title(@NonNull CharSequence title) {
            this.title = title;
            return this;
        }


        public Builder title(@StringRes int title) {
            this.title = context.getString(title);
            return this;
        }


        public Builder message(@NonNull CharSequence message) {
            this.message = message;
            return this;
        }


        public Builder message(@StringRes int message) {
            this.message = context.getString(message);
            return this;
        }


        public Builder dim(int percent) {
            this.dimPercent = percent;
            return this;
        }


        public Builder positiveText(@NonNull CharSequence positiveText) {
            this.positiveText = positiveText;
            return this;
        }


        public Builder positiveText(@StringRes int positiveText) {
            this.positiveText = context.getString(positiveText);
            return this;
        }


        public Builder neutralText(@NonNull CharSequence neutralText) {
            this.neutralText = neutralText;
            return this;
        }


        public Builder neutralText(@StringRes int neutralText) {
            this.neutralText = context.getString(neutralText);
            return this;
        }


        public Builder negativeText(@NonNull CharSequence negativeText) {
            this.negativeText = negativeText;
            return this;
        }


        public Builder negativeText(@StringRes int negativeText) {
            this.negativeText = context.getString(negativeText);
            return this;
        }


        public Builder positiveColor(@ColorRes int colorId) {
            this.positiveColor = colorId;
            return this;
        }


        public Builder neutralColor(@ColorRes int colorId) {
            this.neutralColor = colorId;
            return this;
        }


        public Builder negativeColor(@ColorRes int colorId) {
            this.negativeColor = colorId;
            return this;
        }


        public Builder icon(@DrawableRes int drawableId) {
            this.icon = drawableId;
            return this;
        }


        public Builder buttonCallback(@NonNull ButtonCallback buttonCallback) {
            this.buttonCallback = buttonCallback;
            return this;
        }


        public Builder showListener(@NonNull ShowListener showListener) {
            this.showListener = showListener;
            return this;
        }


        public Builder dismissListener(@NonNull DismissListener dismissListener) {
            this.dismissListener = dismissListener;
            return this;
        }


        public Builder canceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }


        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }


        public Builder customView(@NonNull View customView) {
            this.customView = customView;
            return this;
        }


        public Builder customView(@LayoutRes int layoutId) {
            LayoutInflater inflater = LayoutInflater.from(context);
            customView = inflater.inflate(layoutId, null);
            return this;
        }


        public Builder viewSpacing(int viewSpacingLeft, int viewSpacingTop, int viewSpacingRight, int viewSpacingBottom) {
            this.viewSpacingLeft = viewSpacingLeft;
            this.viewSpacingTop = viewSpacingTop;
            this.viewSpacingRight = viewSpacingRight;
            this.viewSpacingBottom = viewSpacingBottom;
            return this;
        }


        // list
        public Builder listItems(@NonNull String... items) {
            this.blankListing = true;
            this.multiChoice = false;
            this.dismissOnSelection = true;
            this.items = items;
            return this;
        }


        public Builder listItemsSingleChoice(boolean dismissOnSelection, @NonNull String... items) {
            this.blankListing = false;
            this.multiChoice = false;
            this.dismissOnSelection = dismissOnSelection;
            this.items = items;
            return this;
        }


        public Builder listItemsMultiChoice(@NonNull String... items) {
            this.blankListing = false;
            this.multiChoice = true;
            this.items = items;
            return this;
        }


        public Builder selection(int... preSelected) {
            this.preSelectedIndices = preSelected;
            return this;
        }


        public Builder itemLongClickable(boolean itemLongClickable) {
            this.itemLongClickable = itemLongClickable;
            return this;
        }


        public Builder itemClickListener(@NonNull ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
            return this;
        }


        public Builder itemLongClickListener(@NonNull ItemLongClickListener itemLongClickListener) {
            this.itemLongClickable = true;
            this.itemLongClickListener = itemLongClickListener;
            return this;
        }


        public Builder itemSelectedListener(@NonNull ItemSelectedListener itemSelectedListener) {
            this.itemSelectedListener = itemSelectedListener;
            return this;
        }


        public Builder positiveDelayed(long millisInFuture, long countDownInterval, @NonNull String finishedText) {
            this.positiveDelayed = true;
            this.millisInFuture = millisInFuture;
            this.countDownInterval = countDownInterval;
            this.finishedText = finishedText;
            return this;
        }


        public Builder licenseDialog(@NonNull String[] libraryNames, @NonNull String[] libraryDevelopers, @NonNull String[] libraryLicenses) {
            LayoutInflater inflater = LayoutInflater.from(context);
            customView = inflater.inflate(R.layout.dialog_license, null);
            this.licenseDialog = true;
            this.libNames = libraryNames;
            this.libDevelopers = libraryDevelopers;
            this.libLicenses = libraryLicenses;
            return this;
        }


        public Builder licenseDialog(@NonNull List<LicenseInfo> licenseInfos) {
            LayoutInflater inflater = LayoutInflater.from(context);
            customView = inflater.inflate(R.layout.dialog_license, null);
            this.licenseDialog = true;

            this.libNames = new String[licenseInfos.size()];
            this.libDevelopers = new String[licenseInfos.size()];
            this.libLicenses = new String[licenseInfos.size()];

            int i = 0;
            for (LicenseInfo li : licenseInfos) {
                libNames[i] = li.getName();
                libDevelopers[i] = li.getDeveloper();
                libLicenses[i] = li.getLicenseText();
                i++;
            }

            return this;
        }


        public Builder changelogDialog(@NonNull String[] versionNames, @NonNull String[] dates, @NonNull ReleaseInfo[] releaseInfos) {
            return changelogDialog(versionNames, dates, releaseInfos, "");
        }


        public Builder changelogDialog(@NonNull String[] versionNames, @NonNull String[] dates, @NonNull ReleaseInfo[] releaseInfos, @Nullable String bullet) {
            LayoutInflater inflater = LayoutInflater.from(context);
            customView = inflater.inflate(R.layout.dialog_changelog, null);
            this.changelogDialog = true;
            this.clVersionNames = versionNames;
            this.clDates = dates;
            this.clReleaseInfos = releaseInfos;

            if (bullet != null) {
                this.clBullet = bullet;
            }

            return this;
        }


        public Builder changelogDialog(@NonNull List<Changelog> changelogs) {
            return changelogDialog(changelogs, "");
        }


        public Builder changelogDialog(@NonNull List<Changelog> changelogs, @Nullable String bullet) {
            LayoutInflater inflater = LayoutInflater.from(context);
            customView = inflater.inflate(R.layout.dialog_changelog, null);
            this.changelogDialog = true;

            this.clVersionNames = new String[changelogs.size()];
            this.clDates = new String[changelogs.size()];
            this.clReleaseInfos = new ReleaseInfo[changelogs.size()];

            if (bullet != null) {
                this.clBullet = bullet;
            }

            int i = 0;
            for (Changelog cl : changelogs) {
                clVersionNames[i] = cl.getVersionName();
                clDates[i] = cl.getDate();
                clReleaseInfos[i] = cl.getReleaseInfo();
                i++;
            }

            return this;
        }


        public MaterialDialog build() {
            if (style == -1) return new MaterialDialog(this);
            else return new MaterialDialog(this, style);
        }


        public void show() {
            this.build().show();
        }
    }


    public abstract static class ButtonCallback {

        public void onAny(MaterialDialog dialog) {
            Log.d(TAG, "onAny " + "");
        }


        public void onPositive(MaterialDialog dialog) {
            Log.d(TAG, "onPositive " + "");
        }


        public void onNeutral(MaterialDialog dialog) {
            Log.d(TAG, "onNeutral " + "");
        }


        public void onNegative(MaterialDialog dialog) {
            Log.d(TAG, "onNegative " + "");
        }


        public ButtonCallback() {
        }


        protected final Object clone() throws CloneNotSupportedException {
            return super.clone();
        }


        public final boolean equals(Object o) {
            return super.equals(o);
        }


        protected final void finalize() throws Throwable {
            super.finalize();
        }


        public final int hashCode() {
            return super.hashCode();
        }


        public final String toString() {
            return super.toString();
        }
    }


    public abstract static class DismissListener {

        public DismissListener() {
        }


        public void onDismiss() {
            Log.d(TAG, "onDismiss " + "");
        }


        protected final Object clone() throws CloneNotSupportedException {
            return super.clone();
        }


        public final boolean equals(Object o) {
            return super.equals(o);
        }


        protected final void finalize() throws Throwable {
            super.finalize();
        }


        public final int hashCode() {
            return super.hashCode();
        }


        public final String toString() {
            return super.toString();
        }
    }


    public abstract static class ShowListener {

        public ShowListener() {
        }


        public void onShow(AlertDialog dialog) {
            Log.d(TAG, "onShow " + "");
        }


        protected final Object clone() throws CloneNotSupportedException {
            return super.clone();
        }


        public final boolean equals(Object o) {
            return super.equals(o);
        }


        protected final void finalize() throws Throwable {
            super.finalize();
        }


        public final int hashCode() {
            return super.hashCode();
        }


        public final String toString() {
            return super.toString();
        }
    }


    public abstract static class ItemClickListener {

        public ItemClickListener() {
        }


        public void onClick(View v, int position, long id) {
            Log.d(TAG, "onClick " + position);
        }


        protected final Object clone() throws CloneNotSupportedException {
            return super.clone();
        }


        public final boolean equals(Object o) {
            return super.equals(o);
        }


        protected final void finalize() throws Throwable {
            super.finalize();
        }


        public final int hashCode() {
            return super.hashCode();
        }


        public final String toString() {
            return super.toString();
        }
    }


    public abstract static class ItemLongClickListener {

        public ItemLongClickListener() {
        }


        public void onLongClick(View view, int position, long id) {
            Log.d(TAG, "onLongClick " + position);
        }


        protected final Object clone() throws CloneNotSupportedException {
            return super.clone();
        }


        public final boolean equals(Object o) {
            return super.equals(o);
        }


        protected final void finalize() throws Throwable {
            super.finalize();
        }


        public final int hashCode() {
            return super.hashCode();
        }


        public final String toString() {
            return super.toString();
        }
    }


    public abstract static class ItemSelectedListener {

        public ItemSelectedListener() {
        }


        public void onSelected(View view, int position, long id) {
            Log.d(TAG, "onSelected " + position);
        }


        public void onNothingSelected(AdapterView<?> parent) {

        }


        protected final Object clone() throws CloneNotSupportedException {
            return super.clone();
        }


        public final boolean equals(Object o) {
            return super.equals(o);
        }


        protected final void finalize() throws Throwable {
            super.finalize();
        }


        public final int hashCode() {
            return super.hashCode();
        }


        public final String toString() {
            return super.toString();
        }
    }


    public static class Version {

        public static String getVersionName() {
            return BuildConfig.VERSION_NAME;
        }


        public static String getVersionInfo() {
            return "materialdialog-" + BuildConfig.VERSION_NAME;
        }


        /**
         * @return The license text.
         */
        public static String getLicense() {
            return "Copyright (c) 2016 Martin Pfeffer\n" +
                   " \n" +
                   "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                   "you may not use this file except in compliance with the License.\n" +
                   "You may obtain a copy of the License at\n" +
                   " \n" +
                   "     http://www.apache.org/licenses/LICENSE-2.0\n" +
                   " \n" +
                   "Unless required by applicable law or agreed to in writing, software\n" +
                   "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                   "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                   "See the License for the specific language governing permissions and\n" +
                   "limitations under the License.";
        }

    }

}
