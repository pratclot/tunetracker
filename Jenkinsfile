pipeline {
    agent any

    stages {
        stage('Lint') {
            steps {
                sh """
                    ./gradlew ktLintDebugCheck
                """
            }
        }
        stage('Build') {
            steps {
                sh """
                    ./gradlew clean buildDebugPreBundle
                """
            }
        }
        stage('Tests') {
            steps {
                sh """
                    ./gradlew testDebugUnitTest
                """
            }
        }
    }

    post {
        always {
            junit "**/build/test-results/**/*.xml"
        }
    }
}
