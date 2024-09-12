# DogBreedsApp

### Dependences libraries and version
Add your dependencies in libs.versions.toml on directory gradle

### Generate module dependency graph
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

### Todo
- obfuscation code with R8
- implement more unit test