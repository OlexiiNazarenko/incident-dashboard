pipeline {
    agent any
    tools {
        maven 'Maven 3.6.3'
        jdk 'jdk11'
    }
    stages {
        stage("test") {
            steps {
                script {
                    sh '''
                        mvn test
                    '''
                }
            }
        }
        stage("build") {
            steps {
                script {
                    sh '''
                        mvn package -DskipTests
                        docker build --memory=512m -t incident_dashboard .
                        docker tag incident_dashboard:latest 830802944459.dkr.ecr.eu-central-1.amazonaws.com/incident_dashboard:latest
                    '''
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    sh '''
                        aws ecr get-login-password --region eu-central-1
                        docker push 830802944459.dkr.ecr.eu-central-1.amazonaws.com/incident_dashboard:latest
                    '''
                }
            }
        }
    }
}