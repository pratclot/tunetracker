# tunetracker

<div width="100%">
    <a href='http://jenkins.pratclot.com/job/tunetracker/job/master/'><img src='http://jenkins.pratclot.com/buildStatus/icon?job=tunetracker%2Fmaster'></a>
    <a href='https://play.google.com/store/apps/details?id=com.pratclot.tunetracker&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' height="50" align="right"/></a>
</div>

Android app to help with guitar practice.

Status|Feature|Library
-|-|-
:ok_hand:|Local Storage|Room
:ok_hand:|REST Interaction|Retrofit 2
:ok_hand:|Dependency Injection|Dagger 2
:ok_hand:|Unit Tests|JUnit4
:ok_hand:|UI Tests|Firebase Test Lab
:writing_hand:|Code Analysis|
:ok_hand:|Linting|ktlint
:writing_hand:|CI|Jenkins

<div float="left">
    <img src="assets/Screenshot_1588073261.png" width="300" />
    <img src="assets/Screenshot_1588073264.png" width="300" />
</div>

Some environment variables are required to run Gradle tasks.

Task|Variable|What does
-|-|-
firebaseTestLabExecuteDebugInstrumentationPixel2Debug|PATH_TO_GOOGLE_SERVICE_KEY|Path to Firebase service account .json file
||GOOGLE_PROJECT_ID|Google project ID
bundleRelease|KEYSTORE_PATH|Path to keystore with signing key
||KEYSTORE_PASS|Keystore's password
||KEYSTORE_KEY_NAME|Signing key's alias
||KEYSTORE_KEY_PASS|Signing key's password
