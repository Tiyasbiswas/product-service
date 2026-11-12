pipeline {
    agent any

    tools {
       jdk 'JDK-25'
       maven 'Maven-3'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out Product Service source code'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package'
            }
        }
    }
}