# Compose Calendar
![cal](https://github.com/lyh990517/compose-calendar/assets/45873564/d5ea635c-87db-40d7-96c0-cce64a11f2f3)
![KakaoTalk_Photo_2024-01-26-14-35-29](https://github.com/lyh990517/Compose-Calendar-Template/assets/45873564/6cdfaeeb-b016-41b5-aa81-97fe46bc6272)


## Overview
Compose Calendar is a sample calendar component built using Jetpack Compose for Android. It provides a customizable calendar view with various features. I encourage you to ⭐star⭐ this repository if you find it useful!

## Quick Start
[![](https://jitpack.io/v/lyh990517/Compose-Calendar-Template.svg)](https://jitpack.io/#lyh990517/Compose-Calendar-Template)
<br>
First, add the JitPack repository to your build script. 

If you're using **Groovy DSL**, 

```gradle
//settings.gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            mavenCentral()
            maven { url 'https://jitpack.io' }
        }
}
//app.gradle
dependencies {
    implementation 'com.github.lyh990517:Compose-Calendar-Template:latest-release'
}
```

<br>

If you're using **Kotlin DSL**
```kotlin
//settings.gradle.kts
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            google()
            mavenCentral()
            maven(url = "https://jitpack.io")
        }
}
//app.gradle.kts
dependencies {
    implementation ("com.github.lyh990517:Compose-Calendar-Template:latest-release")
}
```

## How to Use
To use Compose Calendar in your Android app, follow these steps:

1. Add Compose Calendar as a dependency in your project.
2. Include the `Calendar` composable in your layout.
3. Customize the calendar appearance and behavior as needed.
4. Handle the `onSelect` callback to respond to date selection events.

## Customization
You can customize Compose Calendar in various ways, such as changing the colors, specifying the number of months to display, and adjusting the layout. Refer to the documentation and source code for more details on customization options.

## Usage Examples
Here's an example of how to use Compose Calendar in your Android app:

```kotlin
Calendar { selectedDate ->
    // Handle selected date here
    Toast.makeText(this, "$selectedDate", Toast.LENGTH_SHORT).show()
}

HorizontalCalendar { selectedDate ->
    // Handle selected date here
    Toast.makeText(this, "$selectedDate", Toast.LENGTH_SHORT).show()
} 
```

## Contact
If you have any questions, suggestions, or issues, feel free to reach out to us. You can find our contact information on our GitHub repository.

We appreciate your support and contributions to Compose Calendar!
