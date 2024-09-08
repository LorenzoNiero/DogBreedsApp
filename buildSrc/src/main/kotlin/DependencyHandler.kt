import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementation(dependency: String) {
    add("implementation", dependency)
}

fun DependencyHandler.debugImplementation(dependency: String) {
    add("debugImplementation", dependency)
}

fun DependencyHandler.test(dependency: String) {
    add("testImplementation", dependency)
}

fun DependencyHandler.androidTest(dependency: String) {
    add("androidTest", dependency)
}

fun DependencyHandler.kapt(dependency: String) {
    add("kapt", dependency)
}

fun DependencyHandler.kaptTest(dependency: String) {
    add("kaptTest", dependency)
}

fun DependencyHandler.platformImplementation(dependency: String) {
    add("implementation", platform(dependency))
}

fun DependencyHandler.annotationProcessor(dependency: String) {
    add("annotationProcessor", dependency)
}

fun DependencyHandler.ksp(dependency: String) {
    add("ksp", dependency)
}
