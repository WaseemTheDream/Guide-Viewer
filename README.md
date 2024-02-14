# Guide-Viewer
A simple Android app that shows a list of upcoming events.


## Functionality
This is a sample Android application that fetches a list of upcoming events from an HTTP JSON endpoint. The events are rendered in a simple list with:
- Event Name
- Event Start Date
- Event Icon

The application shows a 'Build Variant' on top of the screen. To deploy the application with a different variant, please select a new build variant under the 'Build Variants' section of Android Studio.

## Libraries Used
<strong>Network Requests: </strong> Retrofit <br>
<strong>Dependency Injection: </strong> Hilt <br>
<strong>Data Architecture: </strong> Repository / MVVM <br>
<strong>UI Architecture: </strong> Jetpack Compose <br>
<strong>Image Rendering: </strong> Coil <br>
<strong>Unit Testing: </strong> Turbine + OkHttp MockWebserver

## Key Files
- GuideApi.kt: Retrofit API for fetching upcoming events
- Guide.kt: Data model that represents an individual event
- GuideRepositoryImpl.kt: Repository that provides list of upcoming events
- GuideListScreen.kt: Jetpack Compose screen that renders a list of upcoming events
- GuideListViewModel.kt: ViewModel that backs GuideListScreen

## Unit Tests
- GuideListViewModelTest.kt: Ensures correct data types are returned according to mocked web server responses

## Next Steps
- Add a test that verifies UI rendering/state functionality
- Improve UI layer to show additional metadata / context for events
