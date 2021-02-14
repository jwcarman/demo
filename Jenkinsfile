pipeline {
    agent any
    tools {
        maven 'M3'
        jdk 'JDK-11'
        dockerTool 'local-docker'
    }

    environment {
        POM_VERSION = """${sh(returnStdout: true, script: 'mvn help:evaluate -Drevision=$BUILD_NUMBER -Dexpression=project.version -q -DforceStdout')}""".trim()

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
                    sh 'mvn -DskipTests -Dspring-boot.build-image.imageName=docker.io/jwcarman/$POM_VERSION -s $MAVEN_SETTINGS spring-boot:build-image'
                }
            }
        }

        stage('Push Image') {
            steps {

                withCredentials([usernamePassword(credentialsId: 'docker', passwordVariable: 'DOCKERHUB_PASS', usernameVariable: 'DOCKERHUB_USER')]) {
                    sh 'docker login --username $DOCKERHUB_USER --password $DOCKERHUB_PASS'
                    sh 'docker push docker.io/jwcarman/demo:$POM_VERSION'
                }
            }
        }

        stage('K8S Deploy') {
            steps {
                withKubeConfig(credentialsId: 'kubeconfig-kubert') {
                    sh 'envsubst < src/main/k8s/demo-role.yml | kubectl apply -f -'
                    sh 'envsubst < src/main/k8s/demo-rolebinding.yml | kubectl apply -f -'
                    sh 'envsubst < src/main/k8s/demo-configmap.yml | kubectl apply -f -'
                    sh 'envsubst < src/main/k8s/demo-secret.yml | kubectl apply -f -'
                    sh 'envsubst < src/main/k8s/demo-service.yml | kubectl apply -f -'
                    sh 'envsubst < src/main/k8s/demo-deployment.yml | kubectl apply -f -'
                }
            }
        }
    }
}