#MaterialDialog
This tiny Android library provides Material-Design dialogs.


##How to use
###1. Import
Add JitPack to your repositories:

```
   repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
```

and add the library to your dependencies:

```
   compile 'com.github.pepperonas:materialdialog:0.0.1'
```



###2. Showing a dialog

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


##Showcase
Coming soon...

##Developed By

* Martin Pfeffer - http://www.pepperonas.com - <pepperonas@gmail.com>


##License

    Copyright 2016 Martin Pfeffer

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


