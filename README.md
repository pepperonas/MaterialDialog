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
    compile 'com.github.pepperonas:materialdialog:0.2.8'
```


###2. Showing a dialog
```java
@OnMaterialDialogClick(clickRes = R.id.ClickYesNoParam,clickType = MaterialDialogClickTyp.positive)
    public void onYesClick(){
        Toast.makeText(this,"Yes was Clicked",Toast.LENGTH_SHORT).show();

    }
```                
###3. Change Text Color etc.
for example
```java
MaterialDialogHelper.DefaultValue.message="My spezial Message";
```
or by annotation 
```java
@OnMaterialDialogClick(clickRes = R.id.ClickYesNoParam,clickType = MaterialDialogClickTyp.positive, messageRes = R.string.spezialmsg)
```


##ProGuard
```
    -keep class com.pepperonas.materialdialog.** { *; }
    -dontwarn com.pepperonas.materialdialog.**
```


##Showcase
Try the sample application. It's on Google Play :)
https://play.google.com/store/apps/details?id=com.pepperonas.materialdialog.showcase

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


