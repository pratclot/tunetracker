@Library('jenkins_library@v0.1.2')
import com.pratclot.*

slack = new Slack(this).startThread()
extendedSteps = new ExtendedSteps(this)

pipeline {
    agent any

    stages {
        stage('Lint') {
            steps {
                script {
                    extendedSteps """
                        ./gradlew ktLintDebugCheck
                    """
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    extendedSteps """
                        ./gradlew clean buildDebugPreBundle
                    """
                }
            }
        }
        stage('Unit Tests') {
            steps {
                script {
                    extendedSteps """
                        ./gradlew testDebugUnitTest
                    """
                }
            }
        }
        stage('UI Tests') {
            steps {
                script {
                    extendedSteps """
                        ./gradlew connectedDebugAndroidTest
                    """
                }
            }
        }
    }

    post {
        success {
            script {
                slack.markThreadAsGreen()
            }
        }
        failure {
            script {
                slack.markThreadAsRed()
            }
        }
        always {
            junit "**/build/test-results/**/*.xml"
        }
    }
}
