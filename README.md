# CV

My CV Project, where you can find my current portfolio, know something about my hobbies, and have possibility to contact with me by LinkedIn.

![hobbies](https://user-images.githubusercontent.com/9393891/88526422-3409ff00-cffc-11ea-8c45-6819f522610a.gif)
![mycv](https://user-images.githubusercontent.com/9393891/88526248-fdcc7f80-cffb-11ea-8a2a-d214d8f6f85c.gif)
![linkedin](https://user-images.githubusercontent.com/9393891/88526271-06bd5100-cffc-11ea-8991-a008e48e1b0c.gif)

# Summary

Android Components:
* Androidx
* ConstraintLayout
* LiveData

Used libraries:
* Lottie
* Koin
* Rxjava2
* Retrofit
* OkHttp3
* Moshi

Tests:
* Espresso
* jUnitTest

System Crash Report:
* Firebase Crashlytics

# Project
Used Github's project's panel to create tasks for each part of the project. Each task has his own feature branch.
For each feature branch pull request was made, reviewed (by myself :D ) and merge into main branch.

# Background
Simple CV application, where user can read about my hobbies, my company portfolio and can contact with me by LinkedIn.
App is done in Clean Architecure principles: 
[Article about Clean Architecture in Android](https://medium.com/@dmilicic/a-detailed-guide-on-developing-android-apps-using-the-clean-architecture-pattern-d38d71e94029)

So we have presentation, domain and data layer. Architecture use also ViewModel with MVI concept.
Threw presentation layer viewModel asks threw independent useCases for things, which it needs.
useCases stored in domain layer retrieve models from repositories, which are stored in data layer.

After ViewModel retrieves info from UseCase threw observer, then it updates screen state. Thanks to that screen knows what has to be done (by example adapter will update items, network error will be shown and so on).
[MVI Android Article](https://www.raywenderlich.com/817602-mvi-architecture-for-android-tutorial-getting-started)

# What to improve?
1. UI experience - card items can be done better, post detail could be improved
2. Transition between screens could use element transition.
3. Validation
4. More UI tests
5. Application could store data in offline mode and show to the user info that he is currently offline in the case, if there is something stored in data repository. (by example Room from Android Components library).
6. Code cleaning, image shrinking, proguard or r8 obfuscation
7. Exclude dimens, strings, themes, styles

# Testing
jUnit tests can be started threw Terminal by writing:
` ./gradlew test`

UI tests can be started threw Terminal by writing:
` ./gradlew connectedAndroidTest`
