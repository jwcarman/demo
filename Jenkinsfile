pipeline {
    agent {
        docker {
            image 'maven:3.6.3-jdk-11'
        }
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