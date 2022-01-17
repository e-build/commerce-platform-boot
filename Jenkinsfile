pipeline {
    agent any
    options {
        timeout(time: 1, unit: 'HOURS')
    }
    environment {
        CONTAINER_IMG_TAG = "e-build/commerce"
        CONTAINER_IMG_REGISTRY = 'https://ghcr.io/'
        GITHUB_CREDENTIALS_ID = 'e-build'
        commerceImage = ''
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
        stage('Build docker image') {
            steps {
                sh 'docker build -t $CONTAINER_IMG_TAG:latest .'
            }
        }
        stage('Check docker image') {
            steps {
                sh 'docker images | grep $CONTAINER_IMG_TAG'
            }
        }
        stage('Deploy docker image') {
            steps {
                withDockerRegistry([credentialsId: GITHUB_CREDENTIALS_ID, url: CONTAINER_IMG_REGISTRY]){
                    sh 'docker push $CONTAINER_IMG_TAG:latest'
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



