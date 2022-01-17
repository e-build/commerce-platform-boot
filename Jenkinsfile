pipeline {
    agent any
    options {
        timeout(time: 1, unit: 'HOURS')
    }
    environment {
        CONTAINER_IMG_TAG = "app/commerce"
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
                sh 'docker build -t $CONTAINER_IMG_TAG .'
            }
        }
        stage('Check docker image') {
            steps {
                sh 'docker images | grep $CONTAINER_IMG_TAG'
            }
        }
        stage('Deploy docker image') {
            steps {
                sh 'echo $GITHUB_CREDENTIALS \n $GITHUB_CREDENTIALS_USR \n $GITHUB_CREDENTIALS_PSW'
                sh 'echo $GITHUB_CREDENTIALS_PSW | docker login https://ghcr.io -u e-build --password-stdin'
                sh 'docker tag $CONTAINER_IMG_TAG $CONTAINER_IMG_REGISTRY/$CONTAINER_IMG_TAG:$BUILD_NUMBER'
                sh 'docker push $CONTAINER_IMG_REGISTRY/$CONTAINER_IMG_TAG:$BUILD_NUMBER'
                sh 'echo image [$CONTAINER_IMG_TAG] push complete!'
            }
        }
        stage('Complete') {
            steps {
                echo 'complete!'
            }
        }
    }
}
