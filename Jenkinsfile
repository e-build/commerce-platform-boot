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
        stage('Deploy docker container') {
            steps {
                sh '''
                ssh $DEPLOY_SERVER
                "
                docker stop $CONTAINER;
                docker rm $CONTAINER;
                docker rmi $CONTAINER_IMG_TAG;
                echo $GITHUB_CREDENTIALS | docker login ghcr.io -u e-build --password-stdin;
                docker run -d -p 8080:8080 --name=commerce $CONTAINER_IMG_REGISTRY/$CONTAINER_IMG_TAG:latest;
                docker ps -a;
                docker logout;
                "
                '''
            }
        }
        stage('Complete') {
            steps {
                echo 'complete!'
            }
        }
    }
}
