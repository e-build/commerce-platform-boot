pipeline {
    agent any
    options {
        timeout(time: 1, unit: 'HOURS')
    }
    environment {
        GITHUB_SOURCE_URL = 'https://github.com/e-build/commerce-platform-boot.git'
        GITHUB_SOURCE_BRANCH = 'develop'
        CONTAINER_IMG_TAG = "commerce"
        CONTAINER_IMG_REGISTRY = 'ghcr.io/e-build'
        GITHUB_CREDENTIALS = credentials('e-build')
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
                git url: GITHUB_SOURCE_URL,
                    branch: GITHUB_SOURCE_BRANCH
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
                sh 'docker build -t $CONTAINER_IMG_REGISTRY/$CONTAINER_IMG_TAG:$BUILD_NUMBER .'
            }
        }
        stage('Check docker image') {
            steps {
                sh 'docker images | grep $CONTAINER_IMG_TAG'
            }
        }
        stage('Push docker image') {
            steps {
                sh 'echo $GITHUB_CREDENTIALS | docker login https://ghcr.io -u e-build --password-stdin'
                sh 'docker push $CONTAINER_IMG_REGISTRY/$CONTAINER_IMG_TAG:$BUILD_NUMBER'
                sh 'echo image $CONTAINER_IMG_TAG push complete!'
            }
        }
        stage('Complete') {
            steps {
                echo 'complete!'
            }
        }
    }
}
