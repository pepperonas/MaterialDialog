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
import android.graphics.drawable.Drawable;
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
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pepperonas.materialdialog.adapter.CustomArrayAdapter;
import com.pepperonas.materialdialog.adapter.CustomMultipleSelectionArrayAdapter;
import com.pepperonas.materialdialog.adapter.CustomSingleSelectionArrayAdapter;
import com.pepperonas.materialdialog.data.Changelog;
import com.pepperonas.materialdialog.data.LicenseInfo;
import com.pepperonas.materialdialog.data.ReleaseInfo;
import com.pepperonas.materialdialog.utils.Utils;

import java.util.List;

/**
 * The type Material dialog.
 *
 * @author Martin Pfeffer (pepperonas)
 */
public class MaterialDialog extends AlertDialog {

    private static final String TAG = "MaterialDialog";


    /**
     * Instantiates a new Material dialog.
     *
     * @param builder the builder
     */
    public MaterialDialog(final Builder builder) {
        super(builder.context);

        invoke(builder);

    }


    /**
     * Instantiates a new Material dialog.
     *
     * @param builder the builder
     * @param style   the style
     */
    public MaterialDialog(final Builder builder, int style) {
        super(builder.context, style);

        invoke(builder);

    }


    private void invoke(final Builder builder) {
        boolean isListDialog = false;

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

            if (builder.viewSpacingLeft == -1 && builder.viewSpacingTop == -1 && builder.viewSpacingRight == -1 && builder
                    .viewSpacingBottom == -1) {
                builder.viewSpacingLeft = Utils.dp2px(builder.context, 8);
                builder.viewSpacingTop = Utils.dp2px(builder.context, 0);
                builder.viewSpacingRight = Utils.dp2px(builder.context, 12);
                builder.viewSpacingBottom = Utils.dp2px(builder.context, 0);
            }

            this.setView(
                    builder.customView,
                    builder.viewSpacingLeft, builder.viewSpacingTop,
                    builder.viewSpacingRight, builder.viewSpacingBottom);
        }

        /**
         * list
         * */
        if (builder.items != null && builder.items.length > 0) {
            isListDialog = true;

            if (builder.customView != null) {
                Log.w(TAG, "ListView will override the custom view.");
            }

            LayoutInflater layoutInflater = (LayoutInflater) builder.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout llListDialog = (LinearLayout) layoutInflater.inflate(R.layout.dialog_list, null);
            this.setView(
                    llListDialog,
                    builder.viewSpacingLeft, builder.viewSpacingTop,
                    builder.viewSpacingRight, builder.viewSpacingBottom);

            final ListView lv = (ListView) llListDialog.findViewById(R.id.list_dialog_listview);
            final TextView tv = (TextView) llListDialog.findViewById(R.id.list_dialog_tv_message);
            if (builder.message != null) {
                // ensure to set message
                tv.setText(builder.message);
            } else {
                // remove message and set space on top
                llListDialog.removeView(tv);
                lv.setPadding(0, Utils.dp2px(builder.context, 16), 0, 0);
            }
            if (builder.title == null) {
                lv.setPadding(0, Utils.dp2px(builder.context, 8), 0, Utils.dp2px(builder.context, 8));
            }


            if (!builder.multiChoice && !builder.blankListing) {
                // single selection (RadioButton)
                int preselectedIndex = 0;
                if (builder.preSelectedIndices.length > 0) {
                    preselectedIndex = builder.preSelectedIndices[0];
                    if (preselectedIndex >= builder.items.length) {
                        Log.w(TAG, "Selected item greater than item count. Will select first item.");
                        preselectedIndex = 0;
                    }
                }
                if (builder.preSelectedIndices.length != 1) {
                    Log.w(TAG, "Can't select multiple items in single selection. Will only select \"" +
                            builder.items[builder.preSelectedIndices[builder.preSelectedIndices.length - 1]] + "\".");
                }

                final CustomSingleSelectionArrayAdapter cssaa = new CustomSingleSelectionArrayAdapter(
                        builder.context,
                        builder.items,
                        preselectedIndex,
                        builder.itemClickListener,
                        builder.itemLongClickListener);

                lv.setDivider(null);
                lv.setAdapter(cssaa);
            } // end single selection (RadioButton)

            else if (!builder.multiChoice) {
                // single selection (blank)
                final CustomArrayAdapter caa = new CustomArrayAdapter(
                        this,
                        builder.context,
                        builder.items,
                        builder.itemClickListener,
                        builder.itemLongClickListener,
                        builder.dismissOnSelection);

                lv.setDivider(null);
                lv.setAdapter(caa);

            } // end single selection (blank)

            else {
                // multi-choice (CheckBox)
                final CustomMultipleSelectionArrayAdapter cmsaa = new CustomMultipleSelectionArrayAdapter(
                        builder.context,
                        builder.items,
                        builder.preSelectedIndices,
                        builder.itemClickListener,
                        builder.itemLongClickListener);

                lv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                lv.setDivider(null);
                lv.setAdapter(cmsaa);
            } // end multi-choice (CheckBox)

            if (builder.adapter != null) {
                // set base adapter
                lv.setDivider(null);
                lv.setAdapter(builder.adapter);
            } // end base adapter

        } // end list


        if (builder.title != null) {
            this.setTitle(builder.title);
        }

        if (builder.message != null) {
            if (!isListDialog) {
                this.setMessage(builder.message);
            }
        }

        if (builder.icon != -1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.setIcon(builder.context.getDrawable(builder.icon));
            } else this.setIcon(builder.context.getResources().getDrawable(builder.icon));
        }
        if (builder.drawable != null) {
            this.setIcon(builder.drawable);
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
                    } else
                        dialog.getButton(BUTTON_POSITIVE).setTextColor(builder.context.getResources().getColor(builder
                                .positiveColor));

                    if (builder.positiveDelayed && builder.finishedText != null) {
                        DialogButtonCountDown countDown = new DialogButtonCountDown(builder.millisInFuture, builder
                                .countDownInterval, builder.finishedText, dialog.getButton(BUTTON_POSITIVE));
                        countDown.start();
                    }
                }
                if (builder.neutralText != null && builder.neutralColor != -1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        dialog.getButton(BUTTON_NEUTRAL).setTextColor(builder.context.getColor(builder.neutralColor));
                    } else
                        dialog.getButton(BUTTON_NEUTRAL).setTextColor(builder.context.getResources().getColor(builder
                                .neutralColor));
                }
                if (builder.negativeText != null && builder.negativeColor != -1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        dialog.getButton(BUTTON_NEGATIVE).setTextColor(builder.context.getColor(builder.negativeColor));
                    } else
                        dialog.getButton(BUTTON_NEGATIVE).setTextColor(builder.context.getResources().getColor(builder
                                .negativeColor));
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
                        } else
                            llReleaseInfo.findViewById(R.id.tv_release_info_bullet).setVisibility(View.GONE);

                        ((LinearLayout) llChangelog.findViewById(R.id.ll_dialog_changelog_release_info_container)).addView
                                (llReleaseInfo);
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


    /**
     * The type Builder.
     */
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
        private Drawable drawable;
        private boolean canceledOnTouchOutside = true;
        private boolean cancelable = true;
        private View customView;
        /**
         * The View spacing left.
         */
        int viewSpacingLeft = -1;
        /**
         * The View spacing top.
         */
        int viewSpacingTop = -1;
        /**
         * The View spacing right.
         */
        int viewSpacingRight = -1;
        /**
         * The View spacing bottom.
         */
        int viewSpacingBottom = -1;

        /**
         * List
         */
        // false will show RadioButton / CheckBox
        private boolean blankListing;
        private boolean multiChoice;
        private boolean dismissOnSelection = false;
        private String[] items;
        private BaseAdapter adapter;
        private Integer[] preSelectedIndices;
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


        /**
         * Instantiates a new Builder.
         *
         * @param context the context
         */
        public Builder(Context context) {
            this.context = context;
        }


        /**
         * Instantiates a new Builder.
         *
         * @param context the context
         * @param style   the style
         */
        public Builder(Context context, int style) {
            this.context = context;
            this.style = style;
        }


        /**
         * Title builder.
         *
         * @param title the title
         * @return the builder
         */
        public Builder title(@NonNull CharSequence title) {
            this.title = title;
            return this;
        }


        /**
         * Title builder.
         *
         * @param title the title
         * @return the builder
         */
        public Builder title(@StringRes int title) {
            this.title = context.getString(title);
            return this;
        }


        /**
         * Message builder.
         *
         * @param message the message
         * @return the builder
         */
        public Builder message(@Nullable CharSequence message) {
            this.message = message;
            return this;
        }


        /**
         * Message builder.
         *
         * @param message the message
         * @return the builder
         */
        public Builder message(@StringRes int message) {
            this.message = context.getString(message);
            return this;
        }


        /**
         * Dim builder.
         *
         * @param percent the percent
         * @return the builder
         */
        public Builder dim(int percent) {
            this.dimPercent = percent;
            return this;
        }


        /**
         * Positive text builder.
         *
         * @param positiveText the positive text
         * @return the builder
         */
        public Builder positiveText(@NonNull CharSequence positiveText) {
            this.positiveText = positiveText;
            return this;
        }


        /**
         * Positive text builder.
         *
         * @param positiveText the positive text
         * @return the builder
         */
        public Builder positiveText(@StringRes int positiveText) {
            this.positiveText = context.getString(positiveText);
            return this;
        }


        /**
         * Neutral text builder.
         *
         * @param neutralText the neutral text
         * @return the builder
         */
        public Builder neutralText(@NonNull CharSequence neutralText) {
            this.neutralText = neutralText;
            return this;
        }


        /**
         * Neutral text builder.
         *
         * @param neutralText the neutral text
         * @return the builder
         */
        public Builder neutralText(@StringRes int neutralText) {
            this.neutralText = context.getString(neutralText);
            return this;
        }


        /**
         * Negative text builder.
         *
         * @param negativeText the negative text
         * @return the builder
         */
        public Builder negativeText(@NonNull CharSequence negativeText) {
            this.negativeText = negativeText;
            return this;
        }


        /**
         * Negative text builder.
         *
         * @param negativeText the negative text
         * @return the builder
         */
        public Builder negativeText(@StringRes int negativeText) {
            this.negativeText = context.getString(negativeText);
            return this;
        }


        /**
         * Positive color builder.
         *
         * @param colorId the color id
         * @return the builder
         */
        public Builder positiveColor(@ColorRes int colorId) {
            this.positiveColor = colorId;
            return this;
        }


        /**
         * Neutral color builder.
         *
         * @param colorId the color id
         * @return the builder
         */
        public Builder neutralColor(@ColorRes int colorId) {
            this.neutralColor = colorId;
            return this;
        }


        /**
         * Negative color builder.
         *
         * @param colorId the color id
         * @return the builder
         */
        public Builder negativeColor(@ColorRes int colorId) {
            this.negativeColor = colorId;
            return this;
        }


        /**
         * Icon builder.
         *
         * @param drawableId the drawable id
         * @return the builder
         */
        public Builder icon(@DrawableRes int drawableId) {
            this.icon = drawableId;
            return this;
        }


        /**
         * Icon builder.
         *
         * @param drawable the drawable id
         * @return the builder
         */
        public Builder icon(Drawable drawable) {
            this.drawable = drawable;
            return this;
        }


        /**
         * Button callback builder.
         *
         * @param buttonCallback the button callback
         * @return the builder
         */
        public Builder buttonCallback(@NonNull ButtonCallback buttonCallback) {
            this.buttonCallback = buttonCallback;
            return this;
        }


        /**
         * Show listener builder.
         *
         * @param showListener the show listener
         * @return the builder
         */
        public Builder showListener(@NonNull ShowListener showListener) {
            this.showListener = showListener;
            return this;
        }


        /**
         * Dismiss listener builder.
         *
         * @param dismissListener the dismiss listener
         * @return the builder
         */
        public Builder dismissListener(@NonNull DismissListener dismissListener) {
            this.dismissListener = dismissListener;
            return this;
        }


        /**
         * Canceled on touch outside builder.
         *
         * @param canceledOnTouchOutside the canceled on touch outside
         * @return the builder
         */
        public Builder canceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }


        /**
         * Cancelable builder.
         *
         * @param cancelable the cancelable
         * @return the builder
         */
        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }


        /**
         * Custom view builder.
         *
         * @param customView the custom view
         * @return the builder
         */
        public Builder customView(@NonNull View customView) {
            this.customView = customView;
            return this;
        }


        /**
         * Custom view builder.
         *
         * @param layoutId the layout id
         * @return the builder
         */
        public Builder customView(@LayoutRes int layoutId) {
            LayoutInflater inflater = LayoutInflater.from(context);
            customView = inflater.inflate(layoutId, null);
            return this;
        }


        /**
         * View spacing dp builder.
         *
         * @param viewSpacingLeftDp   the view spacing left dp
         * @param viewSpacingTopDp    the view spacing top dp
         * @param viewSpacingRightDp  the view spacing right dp
         * @param viewSpacingBottomDp the view spacing bottom dp
         * @return the builder
         */
        public Builder viewSpacingDp(int viewSpacingLeftDp, int viewSpacingTopDp, int viewSpacingRightDp, int
                viewSpacingBottomDp) {
            this.viewSpacingLeft = Utils.dp2px(context, viewSpacingLeftDp);
            this.viewSpacingTop = Utils.dp2px(context, viewSpacingTopDp);
            this.viewSpacingRight = Utils.dp2px(context, viewSpacingRightDp);
            this.viewSpacingBottom = Utils.dp2px(context, viewSpacingBottomDp);
            return this;
        }


        /**
         * List items builder.
         *
         * @param dismissOnSelection the dismiss on selection
         * @param adapter            the adapter
         * @return the builder
         */
        // list
        public Builder adapter(boolean dismissOnSelection, BaseAdapter adapter) {
            this.blankListing = true;
            this.dismissOnSelection = dismissOnSelection;
            this.adapter = adapter;
            return this;
        }


        /**
         * List items builder.
         *
         * @param dismissOnSelection the dismiss on selection
         * @param items              the items
         * @return the builder
         */
        // list
        public Builder listItems(boolean dismissOnSelection, @NonNull String... items) {
            this.blankListing = true;
            this.dismissOnSelection = dismissOnSelection;
            this.multiChoice = false;
            this.items = items;
            return this;
        }


        /**
         * List items single selection builder.
         *
         * @param dismissOnSelection the dismiss on selection
         * @param items              the items
         * @return the builder
         */
        public Builder listItemsSingleSelection(boolean dismissOnSelection, @NonNull String... items) {
            this.blankListing = false;
            this.dismissOnSelection = dismissOnSelection;
            this.multiChoice = false;
            this.items = items;
            return this;
        }


        /**
         * List items multi choice builder.
         *
         * @param items the items
         * @return the builder
         */
        public Builder listItemsMultiChoice(@NonNull String... items) {
            this.blankListing = false;
            this.multiChoice = true;
            this.items = items;
            return this;
        }


        /**
         * Selection builder.
         *
         * @param preSelected the pre selected
         * @return the builder
         */
        public Builder selection(Integer... preSelected) {
            this.preSelectedIndices = preSelected;
            return this;
        }


        /**
         * Item long clickable builder.
         *
         * @param itemLongClickable the item long clickable
         * @return the builder
         */
        public Builder itemLongClickable(boolean itemLongClickable) {
            this.itemLongClickable = itemLongClickable;
            return this;
        }


        /**
         * Item click listener builder.
         *
         * @param itemClickListener the item click listener
         * @return the builder
         */
        public Builder itemClickListener(@NonNull ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
            return this;
        }


        /**
         * Item long click listener builder.
         *
         * @param itemLongClickListener the item long click listener
         * @return the builder
         */
        public Builder itemLongClickListener(@NonNull ItemLongClickListener itemLongClickListener) {
            this.itemLongClickable = true;
            this.itemLongClickListener = itemLongClickListener;
            return this;
        }


        /**
         * Item selected listener builder.
         *
         * @param itemSelectedListener the item selected listener
         * @return the builder
         */
        public Builder itemSelectedListener(@NonNull ItemSelectedListener itemSelectedListener) {
            this.itemSelectedListener = itemSelectedListener;
            return this;
        }


        /**
         * Positive delayed builder.
         *
         * @param millisInFuture    the millis in future
         * @param countDownInterval the count down interval
         * @param finishedText      the finished text
         * @return the builder
         */
        public Builder positiveDelayed(long millisInFuture, long countDownInterval, @NonNull String finishedText) {
            this.positiveDelayed = true;
            this.millisInFuture = millisInFuture;
            this.countDownInterval = countDownInterval;
            this.finishedText = finishedText;
            return this;
        }


        /**
         * License dialog builder.
         *
         * @param libraryNames      the library names
         * @param libraryDevelopers the library developers
         * @param libraryLicenses   the library licenses
         * @return the builder
         */
        public Builder licenseDialog(@NonNull String[] libraryNames, @NonNull String[] libraryDevelopers, @NonNull String[]
                libraryLicenses) {
            LayoutInflater inflater = LayoutInflater.from(context);
            customView = inflater.inflate(R.layout.dialog_license, null);
            this.licenseDialog = true;
            this.libNames = libraryNames;
            this.libDevelopers = libraryDevelopers;
            this.libLicenses = libraryLicenses;

            setViewSpacing(0);

            return this;
        }


        /**
         * License dialog builder.
         *
         * @param licenseInfos the license infos
         * @return the builder
         */
        public Builder licenseDialog(@NonNull List<LicenseInfo> licenseInfos) {
            LayoutInflater inflater = LayoutInflater.from(context);
            customView = inflater.inflate(R.layout.dialog_license, null);
            this.licenseDialog = true;

            this.libNames = new String[licenseInfos.size()];
            this.libDevelopers = new String[licenseInfos.size()];
            this.libLicenses = new String[licenseInfos.size()];

            setViewSpacing(0);

            int i = 0;
            for (LicenseInfo li : licenseInfos) {
                libNames[i] = li.getName();
                libDevelopers[i] = li.getDeveloper();
                libLicenses[i] = li.getLicenseText();
                i++;
            }

            return this;
        }


        /**
         * Changelog dialog builder.
         *
         * @param versionNames the version names
         * @param dates        the dates
         * @param releaseInfos the release infos
         * @return the builder
         */
        public Builder changelogDialog(@NonNull String[] versionNames, @NonNull String[] dates, @NonNull ReleaseInfo[]
                releaseInfos) {
            return changelogDialog(versionNames, dates, releaseInfos, "");
        }


        /**
         * Changelog dialog builder.
         *
         * @param versionNames the version names
         * @param dates        the dates
         * @param releaseInfos the release infos
         * @param bullet       the bullet
         * @return the builder
         */
        public Builder changelogDialog(@NonNull String[] versionNames, @NonNull String[] dates, @NonNull ReleaseInfo[]
                releaseInfos, @Nullable String bullet) {
            LayoutInflater inflater = LayoutInflater.from(context);
            customView = inflater.inflate(R.layout.dialog_changelog, null);
            this.changelogDialog = true;
            this.clVersionNames = versionNames;
            this.clDates = dates;
            this.clReleaseInfos = releaseInfos;

            setViewSpacing(0);

            if (bullet != null) {
                this.clBullet = bullet;
            }

            return this;
        }


        /**
         * Changelog dialog builder.
         *
         * @param changelogs the changelogs
         * @return the builder
         */
        public Builder changelogDialog(@NonNull List<Changelog> changelogs) {
            return changelogDialog(changelogs, "");
        }


        /**
         * Changelog dialog builder.
         *
         * @param changelogs the changelogs
         * @param bullet     the bullet
         * @return the builder
         */
        public Builder changelogDialog(@NonNull List<Changelog> changelogs, @Nullable String bullet) {
            LayoutInflater inflater = LayoutInflater.from(context);
            customView = inflater.inflate(R.layout.dialog_changelog, null);
            this.changelogDialog = true;

            this.clVersionNames = new String[changelogs.size()];
            this.clDates = new String[changelogs.size()];
            this.clReleaseInfos = new ReleaseInfo[changelogs.size()];

            setViewSpacing(0);

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


        /**
         * Build material dialog.
         *
         * @return the material dialog
         */
        public MaterialDialog build() {
            if (style == -1) return new MaterialDialog(this);
            else return new MaterialDialog(this, style);
        }


        /**
         * Show.
         */
        public void show() {
            this.build().show();
        }


        private void setViewSpacing(int spacing) {
            this.viewSpacingLeft = spacing;
            this.viewSpacingTop = spacing;
            this.viewSpacingRight = spacing;
            this.viewSpacingBottom = spacing;
        }
    }


    /**
     * The type Button callback.
     */
    public abstract static class ButtonCallback {

        /**
         * On any.
         *
         * @param dialog the dialog
         */
        public void onAny(MaterialDialog dialog) {
            Log.d(TAG, "onAny " + "");
        }


        /**
         * On positive.
         *
         * @param dialog the dialog
         */
        public void onPositive(MaterialDialog dialog) {
            Log.d(TAG, "onPositive " + "");
        }


        /**
         * On neutral.
         *
         * @param dialog the dialog
         */
        public void onNeutral(MaterialDialog dialog) {
            Log.d(TAG, "onNeutral " + "");
        }


        /**
         * On negative.
         *
         * @param dialog the dialog
         */
        public void onNegative(MaterialDialog dialog) {
            Log.d(TAG, "onNegative " + "");
        }


        /**
         * Instantiates a new Button callback.
         */
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


    /**
     * The type Dismiss listener.
     */
    public abstract static class DismissListener {

        /**
         * Instantiates a new Dismiss listener.
         */
        public DismissListener() {
        }


        /**
         * On dismiss.
         */
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


    /**
     * The type Show listener.
     */
    public abstract static class ShowListener {

        /**
         * Instantiates a new Show listener.
         */
        public ShowListener() {
        }


        /**
         * On show.
         *
         * @param dialog the dialog
         */
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


    /**
     * The type Item click listener.
     */
    public abstract static class ItemClickListener {

        /**
         * Instantiates a new Item click listener.
         */
        public ItemClickListener() {
        }


        /**
         * On click.
         *
         * @param v        the v
         * @param position the position
         * @param id       the id
         */
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


    /**
     * The type Item long click listener.
     */
    public abstract static class ItemLongClickListener {

        /**
         * Instantiates a new Item long click listener.
         */
        public ItemLongClickListener() {
        }


        /**
         * On long click.
         *
         * @param view     the view
         * @param position the position
         * @param id       the id
         */
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


    /**
     * The type Item selected listener.
     */
    public abstract static class ItemSelectedListener {

        /**
         * Instantiates a new Item selected listener.
         */
        public ItemSelectedListener() {
        }


        /**
         * On selected.
         *
         * @param view     the view
         * @param position the position
         * @param id       the id
         */
        public void onSelected(View view, int position, long id) {
            Log.d(TAG, "onSelected " + position);
        }


        /**
         * On nothing selected.
         *
         * @param parent the parent
         */
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


    /**
     * The type Version.
     */
    public static class Version {


        /**
         * Gets version name.
         *
         * @return the version name
         */
        public static String getVersionName() {
            return BuildConfig.VERSION_NAME;
        }


        /**
         * Gets version info.
         *
         * @return the version info
         */
        public static String getVersionInfo() {
            return "materialdialog-" + BuildConfig.VERSION_NAME;
        }


        /**
         * Gets license.
         *
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
