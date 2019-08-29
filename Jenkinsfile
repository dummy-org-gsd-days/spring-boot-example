pipeline {
    agent any

    stages {
        stage("Tests") {
            steps {
                sh "./gradlew clean check"
            }
        }
    }
}
