pipeline {
    agent any

    stages {
        stage("Tests") {
            steps {
                "./gradlew clean check"
            }
        }
    }
}
