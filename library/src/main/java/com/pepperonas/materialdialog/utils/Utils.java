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

package com.pepperonas.materialdialog.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class Utils {

    public static int dp2px(Context context, int dip) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics
                ()));
    }


    public static String[] sortStrings(String[] list) {
        Collections.sort(Arrays.asList(list), new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
        return list;
    }


    public static File[] sortFiles(File[] list) {
        Collections.sort(Arrays.asList(list), new Comparator<File>() {
            @Override
            public int compare(File s1, File s2) {
                return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });
        return list;
    }


    public static List<String> sortStrings(List<String> list) {
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
        return list;
    }


    public static List<File> sortFiles(List<File> list) {
        Collections.sort(list, new Comparator<File>() {
            @Override
            public int compare(File s1, File s2) {
                return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });
        return list;
    }


    public static Typeface resolveTypeface(Context context, String fontName) {
        return Typeface.createFromAsset(context.getAssets(), String.format("fonts/%s", fontName));
    }

}
