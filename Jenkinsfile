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
    }
}