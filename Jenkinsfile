pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'Dockertoken') {
                        def app = docker.build("jlfaguiar/ac2devops:latest")
                        app.push()
                    }
                }
            }
        }
    }
}