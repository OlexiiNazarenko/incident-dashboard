pipeline {
    agent any
    tools {
        maven 'Maven 3.6.3'
        jdk 'jdk16'
    }
    stages {
        stage("build") {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }
    }
}