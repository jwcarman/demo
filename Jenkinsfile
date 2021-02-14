pipeline {
    agent any
    tools {
        maven 'M3'
        jdk 'JDK-11'
    }

    stages {
        stage('CI Build') {
            steps {
                sh 'mvn -B clean test'
            }
        }
        stage('Build Image') {
            steps {
                sh 'mvn -DskipTests -Drevision=$BUILD_NUMBER spring-boot:build-image'
            }
        }
    }
}