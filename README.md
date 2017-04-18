# MaterialDialog
This tiny Android library provides Material-Design dialogs.


## How to use
### 1. Import
Add JitPack to your repositories:
```
   repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
```

and add the library to your dependencies:
```
    compile 'com.github.pepperonas:materialdialog:0.3.3'
```


### 2. Showing a dialog
```java
new MaterialDialog.Builder(this)
                .title("MaterialDialog")
                .message("This is a simple MaterialDialog.")
                .positiveText("OK")
                .neutralText("NOT NOW")
                .negativeText("CANCEL")
                .positiveColor(R.color.green_700)
                .neutralColor(R.color.yellow_700)
                .negativeColor(R.color.pink_700)
                .showListener(new MaterialDialog.ShowListener() {
                    @Override
                    public void onShow(AlertDialog d) {
                        super.onShow(d);
                        Toast.makeText(MainActivity.this, "onShow", Toast.LENGTH_SHORT).show();
                    }
                })
                .dismissListener(new MaterialDialog.DismissListener() {
                    @Override
                    public void onDismiss() {
                        super.onDismiss();
                        Toast.makeText(MainActivity.this, "onDismiss", Toast.LENGTH_SHORT).show();
                    }
                })
                .buttonCallback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNeutral(MaterialDialog dialog) {
                        super.onNeutral(dialog);
                        Toast.makeText(MainActivity.this, "Not now", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
```                


## ProGuard
```
    -keep class com.pepperonas.materialdialog.** { *; }
    -dontwarn com.pepperonas.materialdialog.**
```


## Screenshots

##### Overview
![SC011](screenshots/sc_tbl001.png?raw=true "Dialog Set Marker")
##### Simple Dialog
![SC012](screenshots/sc_tbl002.png?raw=true "Dialog Set Marker")
##### List Dialog
![SC001](screenshots/sc_phn001.png?raw=true "Dialog Set Marker")
##### List Dialog - Single Selection
![SC010](screenshots/sc_phn010.png?raw=true "Dialog Set Marker")
##### List Dialog - Multiple Selection
![SC002](screenshots/sc_phn002.png?raw=true "Dialog Set Marker")
##### Licence Dialog
![SC003](screenshots/sc_phn003.png?raw=true "Dialog Set Marker")
##### Changelog Dialog
![SC015](screenshots/sc_tbl005.png?raw=true "Dialog Set Marker")
##### Directory Chooser Dialog
![SC013](screenshots/sc_tbl003.png?raw=true "Dialog Set Marker")
##### File Chooser Dialog
![SC016](screenshots/sc_tbl006.png?raw=true "Dialog Set Marker")
##### Fullscreen Dialog
![SC005](screenshots/sc_phn005.png?raw=true "Dialog Set Marker")
##### Dimmed Dialog
![SC006](screenshots/sc_phn006.png?raw=true "Dialog Set Marker")
##### Custom Font Dialog
![SC007](screenshots/sc_phn007.png?raw=true "Dialog Set Marker")
##### Custom View Dialog
![SC008](screenshots/sc_phn008.png?raw=true "Dialog Set Marker")
##### Scaled Dialog
![SC004](screenshots/sc_phn004.png?raw=true "Dialog Set Marker")
##### Delayed Button Dialog
![SC009](screenshots/sc_phn009.png?raw=true "Dialog Set Marker")


## Showcase
Try the sample application. It's on Google Play :)
https://play.google.com/store/apps/details?id=com.pepperonas.materialdialog.showcase


## Contact

* Martin Pfeffer - https://celox.io - <martin.pfeffer@celox.io>


## License
    Copyright 2017 Martin Pfeffer

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


