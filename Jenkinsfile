pipeline {
    agent any
    options {
        timeout(time: 1, unit: 'HOURS')
    }
    environment {
        GITHUB_SOURCE_URL = 'https://github.com/e-build/commerce-platform-boot.git'
        GITHUB_SOURCE_BRANCH = 'main'
        CONTAINER_IMG_TAG = "commerce"
        CONTAINER = "commerce"
        CONTAINER_IMG_REGISTRY = 'ghcr.io/e-build'
        DEPLOY_SERVER = "ebuild@172.17.0.1"
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
        stage('Test') {
            steps {
                sh './gradlew clean test jacocoTestReport'
            }
        }
        stage('Build Jar') {
            steps {
                sh './gradlew clean build -x test'
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
                sh 'docker build -t $CONTAINER_IMG_REGISTRY/$CONTAINER_IMG_TAG:latest .'
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
                sh 'docker push $CONTAINER_IMG_REGISTRY/$CONTAINER_IMG_TAG:latest'
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
