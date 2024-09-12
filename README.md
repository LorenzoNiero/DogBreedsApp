# DogBreedsApp

A sample Android native app that shows a list of dog with sub breed using the [Dog API](https://dog.ceo/dog-api/).
The app work offline and show list and detail.
The app is based on [Android developers architecture](https://developer.android.com/jetpack/guide?gclsrc=ds&gclsrc=ds)
developed with [Jetpack Compose](https://developer.android.com/jetpack/compose).

## Getting Started
To run the app locally, follow these steps:

1. Clone this repository.
2. Open the project in Android Studio.
3. Select build variant *devDebug* in *app* module
4. Build and run the app on an Android emulator or device.

I have used a emulator "Pixel 7" to reference

## Architecture and library used
The app follow this components:
- Project use **MVVM** architecture and Clean architecture;
- divided project in sub-module;
- used Jetpack **Compose** and **Material Design 3**;
- **Retrofit** to request network;
- **Coil** to download image;
- **Room** to manage database;
- **Flow** to manage change state of data;
- **Hilt/Dagger** for dependency injection;
- **WorkManager** for dispatch request api and save on database;
- **Mockk** to mock data on unit test
- Can find some unit test in the network module, domain module.


## Modularazition
The app follows an implementation of mudularazition by features:
- *app* : main module to run app
- *data* : it contains repository and work manager
- *common* : classes used
- *database* : contains utilit to mange db, models database and dao;
- *model* : contains UI models class
- *network* : it implements manage network,  network models, parse of data,
- *ui* : it contains app theme and custom view;
- *feature/list* : implements list dod page (homepage) and owr viewmodel;
- *feature/detail* : implements detail dog page and owr view model. Created to show navigation

This the graph of dependencies:

<img width="371" alt="Dogapp" src="https://github.com/LorenzoNiero/DogBreedsApp/blob/develop/gradle/dependency-graph/project.dot.png">

## Synchronization
The application has been designed as an offline-first app.
When the app is launched, the list of dog breeds is fetched from the list page and stored in the database.
A Flow observes changes in the "breed" table and displays the list on the page.
When the list is displayed, if the dog image URL hasn't been previously downloaded, a WorkManager is initiated.
The WorkManager acts as a queue and updates the image URL in the corresponding entity.
Every time the refresh button is pressed, all data is deleted from the database and the process of fetching and storing the list of breeds is repeated.


### Todo
- obfuscation code with R8
- implement more unit test

### Dependences libraries and version
Add your dependencies in libs.versions.toml on directory gradle

#### Generate module dependency graph
In order to generate a graphical representation of the dependencies between the modules of the app I used 'Graphviz'.
These are the instructions to setup this tool in a local machine (only first time):
1. install the libraries
    ```sh
    brew install graphviz
    brew install autoconf autogen automake
    brew install lasi poppler devil gtk
    brew install xquart --cask
    ```

To run select configuration on Android Studio
```
RefreshDependencyGraph
```

To manually regenerate the dependency graph run this command in the terminal:
```sh
./gradlew projectDependencyGraph
```