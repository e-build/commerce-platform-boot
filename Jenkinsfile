pipeline {
    agent any
    options {
        timeout(time: 1, unit: 'HOURS')
    }
    environment {
        DOCKER_IMG_TAG = "app/commerce"
        IMG_REGISTRY = 'dockerhub'
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
                sh 'docker build -t $DOCKER_IMG_TAG .'
            }
        }
        stage('Check docker image') {
            steps {
                sh 'docker images | grep $DOCKER_IMG_TAG'
            }
        }
        stage('Deploy docker image') {
            steps {
                echo 'image [$DOCKER_IMG_TAG] push complete!'
            }
        }
        stage('Complete') {
            steps {
                echo 'complete!'
            }
        }
    }
}
