# OfficialFoldingTabBar.Android
[![License](http://img.shields.io/badge/license-MIT-green.svg?style=flat)]()
[![](https://jitpack.io/v/Yalantis/OfficialFoldingTabBar.Android.svg)](https://jitpack.io/#Yalantis/OfficialFoldingTabBar.Android)
[![Yalantis](https://raw.githubusercontent.com/Yalantis/PullToRefresh/develop/PullToRefreshDemo/Resources/badge_dark.png)](https://yalantis.com/?utm_source=github)

Folding Tab Bar and Tab Bar Menu

Inspired by [our project on Dribbble](https://dribbble.com/shots/2003376-Tab-Bar-Animation)

Read how we did it [on our blog](https://yalantis.com/blog/foldingtabbar-for-android/)

![Preview](https://d13yacurqjgara.cloudfront.net/users/495792/screenshots/2003376/tab_bar_animation_fin-02.gif)

##Requirements
- Android SDK 19+

##Usage

Add to your root build.gradle:
```Groovy
allprojects {
	repositories {
	...
	maven { url "https://jitpack.io" }
	}
}
```

Add the dependency:
```Groovy
dependencies {
	compile 'com.github.Yalantis:OfficialFoldingTabBar.Android:v0.9'
}
```
## How to use this library

* Create menu.xml for your menu
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/ftb_menu_nearby"
        android:icon="@drawable/ic_nearby_icon"
        android:title="Nearby"/>

    <item
        android:id="@+id/ftb_menu_new_chat"
        android:icon="@drawable/ic_new_chat_icon"
        android:title="Chat"/>

    <item
        android:id="@+id/ftb_menu_profile"
        android:icon="@drawable/ic_profile_icon"
        android:title="Profile"/>

    <item
        android:id="@+id/ftb_menu_settings"
        android:icon="@drawable/ic_settings_icon"
        android:title="Settings"/>

</menu>
```
* Add FoldingTabBar into your layout
```xml
<RelativeLayout
    android:id="@+id/activity_main"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/colorAccent"
    tools:context="client.yalantis.com.foldingtabbarandroid.MainActivity">

    ...

    <client.yalantis.com.foldingtabbar.FoldingTabBar
        android:id="@+id/folding_tab_bar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerInParent="true"
        android:layout_marginBottom="@dimen/activity_horizontal_margin"
        app:menu="@menu/menu_tab_bar"/>

    ...
    
</RelativeLayout>
```
* Initialize it in your java/kotlin code
```java
FoldingTabBar tabBar = (FoldingTabBar) findViewById(R.id.folding_tab_bar);
```

## Features
* We have useful callbacks for you - 
   Handle menu items clicks:
```java
    tabBar.setOnFoldingItemClickListener(new FoldingTabBar.OnFoldingItemSelectedListener() {
            @Override
            public boolean onFoldingItemSelected(@NotNull MenuItem item) {
                return false;
            }
        });
 ```
 and one more to handle main button clicks:
 ```java
      tabBar.setOnMainButtonClickListener(new FoldingTabBar.OnMainButtonClickedListener() {
            @Override
            public void onMainButtonClicked() {
                
            }
        });
 ```
 * Attributes for customizing FoldingTabBar:

Name | Description |
--- | --- | ---
*app:itemPadding* | sets padding for your menu items. Default item padding is 17dp.
*app:mainImage* | here you can link your image resource for the main image. 
*app:selectionColor* | our menu supports color selection. You can change the menu’s color here.
 
    


## Let us know!

We’d be really happy if you sent us links to your projects where you use our component. Just send an email to github@yalantis.com And do let us know if you have any questions or suggestion regarding the animation. 

P.S. We’re going to publish more awesomeness wrapped in code and a tutorial on how to make UI for iOS (Android) better than better. Stay tuned!

##License

    The MIT License (MIT)

    Copyright © 2016 Yalantis

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
    
