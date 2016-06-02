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
public class LicenseInfo {

    @NonNull
    private String name;

    @NonNull
    private String developer;

    @NonNull
    private String licenseText;


    /**
     * Instantiates a new License info.
     *
     * @param name        the name
     * @param developer   the developer
     * @param licenseText the license text
     */
    public LicenseInfo(@NonNull String name, @NonNull String developer, @NonNull String licenseText) {
        this.name = name;
        this.developer = developer;
        this.licenseText = licenseText;
    }


    /**
     * Gets name.
     *
     * @return the name
     */
    @NonNull
    public String getName() {
        return name;
    }


    /**
     * Gets developer.
     *
     * @return the developer
     */
    @NonNull
    public String getDeveloper() {
        return developer;
    }


    /**
     * Gets license text.
     *
     * @return the license text
     */
    @NonNull
    public String getLicenseText() {
        return licenseText;
    }

}
