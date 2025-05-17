# 🎛️ ThreeDButtonLayout

[![](https://jitpack.io/v/AD0608/ThreeDButtonLayout.svg)](https://jitpack.io/#AD0608/ThreeDButtonLayout)

A simple and elegant 3D-style button layout for Android, designed to give your UI a more tactile and dynamic feel. Built using custom drawables and animations with touch feedback.

✨ Features:
* Customizable base color via XML
* Dynamic elevation and translationZ to control shadow and depth
* Smooth press-in and release-out animations
* Configurable cornerRadius and vertical padding
* Supports standard click handling (performClick)
* Drop-in replacement that supports child views (like TextView, ImageView, etc.)

## 📸 Preview

![3D Button Demo](https://github.com/AD0608/ThreeDButtonLayout/blob/main/screenshots/demo.gif?raw=true)

## 📦 Installation

Add JitPack to your **root `build.gradle`** or `settings.gradle`:

```groovy
// settings.gradle
dependencyResolutionManagement {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Then add the library dependency to your **module's `build.gradle`**:

```groovy
dependencies {
    implementation("com.github.AD0608:ThreeDButtonLayout:1.0.0")
}
```

## 💡 Usage

### 1. Add in XML

```xml
<com.ad0608.threedbutton.ThreeDButtonLayout
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:padding="16dp"
    android:elevation="8dp"
    app:tdbl_buttonBaseColor="#FF5722"
    app:tdbl_cornerRadius="12"
    app:tdbl_verticalPadding="14dp">

    <TextView
        android:text="3D Button"
        android:textColor="@android:color/white"
        android:textStyle="bold"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
</com.ad0608.threedbutton.ThreeDButtonLayout>
```

### 2. Custom Attributes

| Attribute              | Description                          | Default     |
|------------------------|--------------------------------------|-------------|
| `tdbl_buttonBaseColor` | Base color of the button             | `#C70900`   |
| `tdbl_cornerRadius`    | Corner radius in dp                  | `9dp`       |
| `tdbl_verticalPadding` | Vertical padding inside the button   | `14dp`      |
| `tdbl_elevation`       | Elevation of the button              | `8dp`       |
| `tdbl_translationZ`    | Z-axis translation of the button     | `8dp`       |

## Contribution

-   Bug reports and pull requests are welcome.

## Author

Developed with ❤️ by [AD0608](https://github.com/AD0608)

## 🛠️ License

   Copyright 2025 Apache License Version 2.0

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

