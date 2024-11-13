pipeline {
    agent any

    environment {
        REPO_NAME = "jlfaguiar/ac2devops"
        DOCKER_TAG = "latest"
        // O valor do PROFILE será o parâmetro fornecido no Jenkins
        IMAGE_NAME = "${REPO_NAME}:${PROFILE}-${DOCKER_TAG}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Push Image') {
            steps {
                script {
                    bat "docker build --build-arg PROFILE=${PROFILE} -t ${IMAGE_NAME} ."
                    bat "docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%"
                    bat "docker push ${IMAGE_NAME}"
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    bat "set PROFILE=${PROFILE} && docker-compose -f docker-compose.yml up -d --build"
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finalizado'
        }
        success {
            echo 'Pipeline concluído com sucesso!'
        }
        failure {
            echo 'Pipeline falhou'
        }
    }
}
