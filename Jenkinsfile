pipeline {
    agent any
    tools {
        maven 'M3'
        jdk 'JDK-11'
    }

    environment {
        registry = 'jwcarman/demo'
        registryCredential = 'docker'
    }
    stages {
        stage('CI Build') {
            steps {
                configFileProvider([configFile(fileId: 'maven-settings', variable: 'MAVEN_SETTINGS')]) {
                    sh 'mvn -B -s $MAVEN_SETTINGS clean test'
                }
            }
        }

        stage('Build Image') {
            steps {
                configFileProvider([configFile(fileId: 'maven-settings', variable: 'MAVEN_SETTINGS')]) {
                    sh 'mvn -DskipTests -Drevision=$BUILD_NUMBER -s $MAVEN_SETTINGS spring-boot:build-image'
                }
            }
        }

        stage('Push Image') {
            steps {
                script {
                  docker.push registry + ":$BUILD_NUMBER"
                }
            }
        }
    }
}