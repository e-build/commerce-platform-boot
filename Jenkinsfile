pipeline {
    agent any
    options {
        timeout(time: 1, unit: 'HOURS')
    }
    environment {
        CONTAINER_IMG_TAG = "e-build/commerce"
        CONTAINER_IMG_REGISTRY = 'https://ghcr.io/'
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
        stage('Build docker image') {
            agent {
                dockerfile {
                    filename 'Dockerfile'
                    dir '.'
                    registryUrl 'https://ghcr.io/e-build/'
                    registryCredentialsId '$GITHUB_CREDENTIALS_ID'
                }
            }
            steps {
                script {
                    commerceImage = docker.build '$CONTAINER_IMG_TAG'
                }
                sh 'docker images | grep $CONTAINER_IMG_TAG'
            }
        }
        stage('Push docker image') {
            steps {
                script {
                    docker.withRegistry('$CONTAINER_IMG_REGISTRY', '$GITHUB_CREDENTIALS_ID'){
                        commerceImage.push('$BUILD_NUMBER')
                        commerceImage.push('latest"')
                    }
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



