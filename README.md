# StickyScrollView
Sticky header and footer for android ScrollView.

## UPDATE
Kotlin

Fixed : Scroll Issue with recycler View

### Install

Add jitpack to your root `build.gradle`
```
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

Add the dependency
```
	dependencies {
	        implementation 'com.github.amarjain07:StickyScrollView:1.0.2'
	}
```

### Usage
```
	<com.amar.library.ui.StickyScrollView
   		xmlns:app="http://schemas.android.com/apk/res-auto"
		android:layout_width="match_parent"
    		android:layout_height="match_parent"
		app:stickyHeader="@+id/titleLayout"
    		app:stickyFooter="@+id/buttonLayout">
		<LinearLayout
            		android:layout_width="match_parent"
            		android:layout_height="wrap_content">
		    <LinearLayout
            		android:id="@+id/titleLayout"
            		android:layout_width="match_parent"
            		android:layout_height="wrap_content">
			        ...
        	    </LinearLayout>		
		    <LinearLayout
            		android:id="@+id/buttonLayout"
            		android:layout_width="match_parent"
            		android:layout_height="wrap_content">
			        ...
        	    </LinearLayout>
        	</LinearLayout>
		...
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
