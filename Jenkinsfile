pipeline {
    agent any

    environment {
        REPO_NAME = "jlfaguiar/ac2devops"
        DOCKER_TAG = "latest"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Push Image') {
            environment {
                PROFILE = 'test'  // Alterar para 'staging' ou 'prod' conforme o ambiente
                IMAGE_NAME = "${REPO_NAME}:${PROFILE}-${DOCKER_TAG}"
            }
            steps {
                script {
                    // Constrói a imagem Docker usando o perfil especificado e faz o push para o repositório
                    bat "docker build --build-arg PROFILE=${PROFILE} -t ${IMAGE_NAME} ."
                    bat "docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%"
                    bat "docker push ${IMAGE_NAME}"
                }
            }
        }

        stage('Deploy') {
            environment {
                PROFILE = 'test'  // Alterar conforme o ambiente de deploy
            }
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
