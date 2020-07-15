<p align="center">
<a href="https://travis-ci.com/Fbada006/Moveery">
<img src="https://travis-ci.com/Fbada006/Moveery.svg?token=mQy17FzYZ9Tp68NRHPJK&amp;branch=master" alt="Build Status" />
<a href="https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Fbada006/Moveery&amp;utm_campaign=Badge_Grade">
<img src="https://api.codacy.com/project/badge/Grade/1624b5e6304b4104a39e9ad7780404e5"/>
</a>
</p>

# Movies
This is my app working with clean architecture. It gets data from the TMDB API and entertains the user using
clean architecture and MVVM.

<a href="https://bit.ly/moveery">
  <img align="center" alt="Get it on Google Play" src="https://github.com/Fbada006/Moveery/blob/master/art/play.png" />
</a>

## Prerequisite

minSdkVersion -> 21

Gradle build system

Be sure to create a [firebase project](https://firebase.google.com/) so that you can get your own
***google-services.json*** file to take full advantage of features like crashlytics. It can run without
the file but it is better if you do. Head over to the api above and get your own API-KEY as well
although there is a test key provided already for convenience.


## TOC

- [Architecture](#architecture)
- [Flow](#flow)
- [Libraries](#libraries)
- [Extras](#extras)

## Architecture

The App is not organized into multiple modules but follows the same principles of
the Presentation, Domain, and Data Layers.
The presentation layer handles the UI work with the logic contained in the **ViewModel**.
The UI uses a **LiveData** object from the ViewModel and observes it using the **Observer Pattern**.
A ListAdapter handles the actual displaying of the news. Data over the network is retrieved using
**retrofit** and **coroutines** to handle background work asynchronously. Additionally, note that
the ViewModel uses the **viewModelScope** to launch the couroutines while Fragments use the **viewLifeCycleOwner**
to observe data.
The data layer uses the recommended **Repository Pattern** to make the network calls and store the data using
**Room DB**.

## Flow

 **Landing screens**

  Once the app is launched, the user has the option of viewing general movies. Alternatively, using the
  navigation drawer, the user can alo open the discover shows screen. The drawer also has options
  for opening the favourites for both movies and shows.

 **Search Screen**

 This screen is accessible from all the screens and allows the user to search for movies or shows
 depending on the present fragment. If in the movies fragment, then the user will search for movies
 and vice versa


## Libraries

This app takes use of the following libraries:

- [Jetpack](https://developer.android.com/jetpack)🚀
  - [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI data to survive configuration changes and is lifecycle-aware
  - [Data Binding](https://developer.android.com/topic/libraries/data-binding) - Declaratively bind observable data to UI elements
  - [Navigation](https://developer.android.com/guide/navigation/) - Handle everything needed for in-app navigation
  - [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) - Manage your Android background jobs
  - [Room DB](https://developer.android.com/topic/libraries/architecture/room) - Fluent SQLite database access
  - [Paging](https://developer.android.com/topic/libraries/architecture/paging) - Load and display small chunks of data at a time
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Notify views when underlying database changes
- [Retrofit](https://square.github.io/retrofit/) - type safe http client with coroutines support
- [Gson](https://github.com/google/gson) - A Java serialization/deserialization library to convert Java Objects into JSON and back
- [Dagger2](https://github.com/google/dagger) - A fast dependency injector for Android and Java.
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logging HTTP request related data.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines
- [Material Design](https://material.io/develop/android/docs/getting-started/) - build awesome beautiful UIs.🔥🔥
- [Firebase](https://firebase.google.com/) - Backend As A Service for faster mobile development.
  - [Crashylitics](https://firebase.google.com/docs/crashlytics) - Provide Realtime crash reports from users end.
- [Like Button](https://github.com/jd-alexander/LikeButton) - Twitter's heart animation for Android
- [Lottie](https://github.com/airbnb/lottie-android) - Render awesome After Effects animations natively on Android and iOS, Web, and React Native
- [Glide](https://github.com/bumptech/glide) - Hassle-free image loading
- [Timber](https://github.com/JakeWharton/timber) - A logger with a small, extensible API which provides utility on top of Android's normal Log class.


## Extras

#### CI-Pipeline

[Travis CI](https://travis-ci.com/) is used for Continuous Integration every time an update is made
to the repo. The configuration is in the ***.travis.yml*** file

#### Code Analysis and test coverage

This code uses [Codacy](https://www.codacy.com/) for analysing the quality of the code, which is
always going to be A :)
