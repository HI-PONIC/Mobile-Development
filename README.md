
# Hi-Ponic
## Your Automated Hydroponic Companion

### Mobile Development
The Mobile Development (MD) team is responsible for creating a functional Android app. The Hi-Ponic app will feature four main menus, with three being selectable: Home, Knowledge Base, Shop, and Profile. On the homepage, there will be two main features: list plants and add plants.

### Logo
To align with our app goals, I designed a logo incorporating water and plants.
![Hi-Ponic Logo](https://github.com/HI-PONIC/Mobile-Development/assets/100133797/6ed7e733-daf8-4e8d-ab35-49943440e316)

### User Experience
To ensure users have the best experience while using our app, I designed the wireframe. For more details, click this [Figma link](https://www.figma.com/design/BbO3VWk35BVkAzQxRB17V4/Hi-Ponic?node-id=0-1&t=vF8Mnng3zaIonYan-0).

### User Interface
We primarily developed the UI directly in Android Studio, based on the wireframe we created. Our colleague from CC, Fikri, assisted us in determining the color palette and some UI elements for the login, registration, and home screens. For other UI designs, we followed the layouts created by Fikri.

| Login                                                | Register                                               |
|-------------------------------------------------------|--------------------------------------------------------|
| ![Login](https://github.com/HI-PONIC/Mobile-Development/assets/100133797/3bb5cd54-05c0-4a63-a53a-b06db86e06c4) | ![Register](https://github.com/HI-PONIC/Mobile-Development/assets/100133797/43995d0d-f738-468f-b64c-dade88fcb8a0)
 |

### Resources
In developing this application, not all assets were created by us. Some were sourced from the following websites:
- [Canva](https://www.canva.com/)
- [HugeIcons](https://hugeicons.com/)
- [Tokopedia](https://www.tokopedia.com/)

### Data
The information prepared for the Knowledge Base was sourced from:
- [ChatGPT](https://chatgpt.com/)

### Application Development
We used several dependencies to meet our development needs for this application.

#### Core Libraries
These libraries are essential for Android development, providing fundamental functionality and UI components.
```groovy
implementation(libs.androidx.core.ktx)
implementation(libs.androidx.appcompat)
implementation(libs.androidx.activity)
implementation(libs.androidx.constraintlayout)
implementation(libs.androidx.lifecycle.runtime.ktx)
implementation(libs.androidx.viewpager2)
implementation(libs.androidx.lifecycle.viewmodel.ktx.v282)
implementation(libs.androidx.lifecycle.livedata.ktx.v282)
```

#### Material Design
These libraries are used to implement Material Design components and styling in the app.
```groovy
implementation(libs.material)
implementation(libs.material3)
implementation("androidx.compose.material3:material3-window-size-class:1.2.1")
implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.3.0-beta02")
```

#### Navigation
These libraries are used for handling navigation within the app.
```groovy
implementation(libs.androidx.navigation.fragment.ktx)
implementation(libs.androidx.navigation.ui.ktx)
```

#### TensorFlow Lite
These libraries are used for integrating machine learning models with TensorFlow Lite.
```groovy
implementation(libs.tensorflow.lite.support)
implementation(libs.tensorflow.lite.metadata)
implementation(libs.play.services.tflite.java)
implementation(libs.play.services.tflite.gpu)
```

#### Testing
These libraries are used for writing and running tests.
```groovy
testImplementation(libs.junit)
androidTestImplementation(libs.androidx.junit)
androidTestImplementation(libs.androidx.espresso.core)
```

#### Data Storage
This library is used for storing and managing application preferences.
```groovy
implementation(libs.datastore.preferences)
```

#### Lifecycle & LiveData
These libraries are used for managing Android lifecycle-aware components and LiveData.
```groovy
implementation(libs.lifecycle.viewmodel)
implementation(libs.lifecycle.livedata)
```

#### Networking
These libraries are used for making network requests and handling API responses.
```groovy
implementation(libs.retrofit)
implementation(libs.retrofit.gson)
implementation(libs.okhttp.logging)
```

#### Coroutines
These libraries are used for writing asynchronous code in a more readable and manageable way.
```groovy
implementation(libs.coroutines.core)
implementation(libs.coroutines.android)
```

#### UI Components
These libraries are used for various UI components and image loading.
```groovy
implementation(libs.circleimageview)
implementation(libs.glide)
annotationProcessor(libs.glide.compiler)
```

---
