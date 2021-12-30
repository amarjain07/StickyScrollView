# StickyScrollView
Sticky header and footer for android ScrollView.

[![](https://jitpack.io/v/amarjain07/StickyScrollView.svg)](https://jitpack.io/#amarjain07/StickyScrollView)

### Install

Add jitpack to your root's `build.gradle`
```groovy
allprojects {
    repositories {
	    maven { url "https://jitpack.io" }
	}
}
```

Add the dependency
```groovy
dependencies {
    implementation 'com.github.amarjain07:StickyScrollView:<latest-version>'
}
```

### Usage
```xml
<?xml version="1.0" encoding="utf-8"?>
<com.amar.library.ui.StickyScrollView
    android:id="@+id/scrollView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:stickyHeader="@+id/title"
    app:stickyFooter="@+id/buttons">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">
        <LinearLayout
            android:id="@+id/title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@android:color/white"
            android:orientation="vertical"
            android:padding="15dp">
            ...
        </LinearLayout>
        <LinearLayout
            android:id="@+id/buttons"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            ...
        </LinearLayout>
    </LinearLayout>
</com.amar.library.ui.StickyScrollView>
```

### Demo
![StickyScrollViewGif](demo/StickyScroll.gif)

License
-------

    MIT License

    Copyright (c) 2017 Amar Jain

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
