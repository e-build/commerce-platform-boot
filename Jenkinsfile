pipeline {
    agent any
    options {
        timeout(time: 1, unit: 'HOURS')
    }
    environment {
        CONTAINER_IMG_TAG = "app/commerce"
        CONTAINER_IMG_REGISTRY = 'ghcr.io/e-build'
        GITHUB_CREDENTIALS_ID = 'e-build'
    }
    stages {

        stage('Init') {
            steps {
                echo 'clear'
                deleteDir()
            }
        }
        stage('Git Clone') {
            steps {
                git url: 'https://github.com/e-build/commerce-platform-boot.git',
                    branch: 'develop'
                sh 'ls -al'
            }
        }
        stage('Build Jar') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Check') {
            steps {
                echo 'current directory tree'
                sh 'ls -al'
                sh 'ls -al build/libs'
            }
        }
        stage('Build & Push docker image') {
            steps {
                dockerfile {
                    filename 'Dockerfile'
                    dir '.'
                    registryUrl 'https://ghcr.io/'
                    registryCredentialsId '$GITHUB_CREDENTIALS_ID'
                }
            }
        }
        stage('Complete') {
            steps {
                echo 'complete!'
            }
        }
    }
}
