# StickyScrollView
Sticky header and footer for android ScrollView.

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
	        compile 'com.github.amarjain07:StickyScrollView:1.0.1'
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
		...
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
		...
	</com.amar.library.ui.StickyScrollView>
	
```

### Demo
![StickyScrollViewGif](demo/StickyScroll.gif)


License
-------

    Copyright 2017 Amar Chand Jain

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
