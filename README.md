### **SMS Parser**
[ ![Download](https://api.bintray.com/packages/romandanylyk/maven/pageindicatorview/images/download.svg) ](https://bintray.com/romandanylyk/maven/pageindicatorview/_latestVersion)[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-PageIndicatorView-green.svg?style=true)](https://android-arsenal.com/details/1/4555)  
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)


`SMS Parser` is helper library to receive sms by phone number or letter address.

![](https://raw.githubusercontent.com/romandanylyk/PageIndicatorView/master/assets/preview_anim_drop.gif)

### **Integration**
To add `smsparser` to your project, first make sure in root `build.gradle` you have specified the following repository:
```groovy
    repositories {
        jcenter()
    }
```
>***Note***: by creating new project in Android Studio it will have `jcenter` repository specified by default, so you will not need to add it manually.

Once you make sure you have `jcenter` repository in your project, all you need to do is to add the following line in `dependencies` section of your project `build.gradle`.
 
See latest library version [ ![Download](https://api.bintray.com/packages/romandanylyk/maven/pageindicatorview/images/download.svg) ](https://bintray.com/romandanylyk/maven/pageindicatorview/_latestVersion)
```groovy
implementation 'com.romandanylyk:pageindicatorview:X.X.X'
```

Keep in mind, that `Sms Parser` has min [API level 23](https://developer.android.com/about/dashboards/index.html).

### **Usage Sample**
Usage of `Sms Parser` is quite simple.

1. Add permissions to your `AndroidManifest.xml`
```xml
    <uses-permission android:name="android.permission.READ_SMS" />
    <uses-permission android:name="android.permission.RECEIVE_SMS" />
```
2. Request permissions for your project
```kotlin
        private fun checkSmsPermissions() {
        val readSmsPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_SMS
        )
        val receiveSmsPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECEIVE_SMS
        )
        if (readSmsPermissionGranted != PackageManager.PERMISSION_GRANTED ||
            receiveSmsPermissionGranted != PackageManager.PERMISSION_GRANTED
        ) {
            requestSmsPermissions()
        } else {
            registerSmsReceiver()
        }
    }

    private fun requestSmsPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS
            ),
            SMS_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                registerSmsReceiver()
            } else {
                Toast.makeText(this, "Autocomplete disabled", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        const val SMS_PERMISSION_CODE = 101
    }
```

3. Registrate SmsReceiver
```kotlin

    private fun registerSmsReceiver() {
        val smsReceiver = SmsReceiver("phoneNumber or letteral address", "\\d{2}-\\d{2}") { message ->
            firstPinView.setText(message)
        }

        val intentFilter = IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        registerReceiver(smsReceiver, intentFilter)
    }
```

### **Release Note**
See release notes on [github releases](https://github.com/romandanylyk/PageIndicatorView/releases) or [Bintray release notes](https://bintray.com/romandanylyk/maven/pageindicatorview#release).

### **License**

    Copyright 2017 Roman Danylyk
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
