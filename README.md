# Hi-Ponic
## Your Automated Hydroponic Assistant

### Mobile Development
Our Mobile Development (MD) team is dedicated to crafting a robust Android application. Hi-Ponic features four main menus, including Home, Knowledge Base, Shop, and Profile, with three being interactive. The homepage highlights two primary functions: viewing plant lists and adding new plants.

### Logo
In accordance with our app's vision, I designed a logo that symbolizes the essence of water and plants.
![Hi-Ponic Logo](https://github.com/HI-PONIC/Mobile-Development/assets/100133797/6ed7e733-daf8-4e8d-ab35-49943440e316)

### User Experience
To ensure optimal user engagement, I meticulously drafted the wireframe. For detailed insights, explore our designs through this [Figma link](https://www.figma.com/design/BbO3VWk35BVkAzQxRB17V4/Hi-Ponic?node-id=0-1&t=vF8Mnng3zaIonYan-0).

### User Interface
Our team primarily developed the UI in Android Studio, guided by our initial wireframe. Collaborating with Fikri from CC, we harmonized on color schemes and UI elements for login, registration, and home screens. Other UI components were designed in line with Fikri's aesthetic principles.

| Login                                                | Register                                               |
|-------------------------------------------------------|--------------------------------------------------------|
| ![Login](https://github.com/HI-PONIC/Mobile-Development/assets/100133797/3bb5cd54-05c0-4a63-a53a-b06db86e06c4) | ![Register](https://github.com/HI-PONIC/Mobile-Development/assets/100133797/43995d0d-f738-468f-b64c-dade88fcb8a0) |

### Resources
In developing this application, not all assets were created in-house; we sourced some from:
- [Canva](https://www.canva.com/)
- [HugeIcons](https://hugeicons.com/)
- [Tokopedia](https://www.tokopedia.com/)

### Data
The information for our Knowledge Base was obtained from:
- [ChatGPT](https://chatgpt.com/)

### Application Development
To meet the demands of our application development, we utilized a variety of dependencies.

#### Core Libraries
These are fundamental to Android development, providing essential functionalities and UI components.
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
These libraries facilitate the integration of Material Design components and styling.
```groovy
implementation(libs.material)
implementation(libs.material3)
implementation("androidx.compose.material3:material3-window-size-class:1.2.1")
implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.3.0-beta02")
```

#### Navigation
Utilized for seamless navigation within the app.
```groovy
implementation(libs.androidx.navigation.fragment.ktx)
implementation(libs.androidx.navigation.ui.ktx)
```

#### TensorFlow Lite
Integrated for deploying machine learning models using TensorFlow Lite.
```groovy
implementation(libs.tensorflow.lite.support)
implementation(libs.tensorflow.lite.metadata)
implementation(libs.play.services.tflite.java)
implementation(libs.play.services.tflite.gpu)
```

#### Testing
Employed for rigorous testing procedures.
```groovy
testImplementation(libs.junit)
androidTestImplementation(libs.androidx.junit)
androidTestImplementation(libs.androidx.espresso.core)
```

#### Data Storage
Facilitates efficient storage and management of application preferences.
```groovy
implementation(libs.datastore.preferences)
```

#### Lifecycle & LiveData
Enables effective management of Android lifecycle-aware components and LiveData.
```groovy
implementation(libs.lifecycle.viewmodel)
implementation(libs.lifecycle.livedata)
```

#### Networking
Used for handling network requests and API responses.
```groovy
implementation(libs.retrofit)
implementation(libs.retrofit.gson)
implementation(libs.okhttp.logging)
```

#### Coroutines
Enhances the readability and manageability of asynchronous code.
```groovy
implementation(libs.coroutines.core)
implementation(libs.coroutines.android)
```

#### UI Components
Provides essential UI components and facilitates image loading.
```groovy
implementation(libs.circleimageview)
implementation(libs.glide)
annotationProcessor(libs.glide.compiler)
```

---
