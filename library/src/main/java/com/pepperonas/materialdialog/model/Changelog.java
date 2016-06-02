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

package com.pepperonas.materialdialog.model;

import android.support.annotation.NonNull;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class Changelog {

    @NonNull
    private String versionName;

    @NonNull
    private String date;

    @NonNull
    private ReleaseInfo releaseInfo;


    /**
     * Instantiates a new Changelog.
     *
     * @param versionName the version name
     * @param date        the date
     * @param releaseInfo the release info
     */
    public Changelog(@NonNull String versionName, @NonNull String date, @NonNull ReleaseInfo releaseInfo) {
        this.versionName = versionName;
        this.date = date;
        this.releaseInfo = releaseInfo;
    }


    /**
     * Gets version name.
     *
     * @return the version name
     */
    @NonNull
    public String getVersionName() {
        return versionName;
    }


    /**
     * Gets date.
     *
     * @return the date
     */
    @NonNull
    public String getDate() {
        return date;
    }


    /**
     * Gets release info.
     *
     * @return the release info
     */
    @NonNull
    public ReleaseInfo getReleaseInfo() {
        return releaseInfo;
    }
}
