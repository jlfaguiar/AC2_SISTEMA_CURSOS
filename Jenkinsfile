pipeline {
    agent any

    environment {
        REPO_NAME = "jlfaguiar/ac2devops"  // Nome do repositório Docker (Docker Hub, por exemplo)
        DOCKER_TAG = "latest"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Push Image for DEV') {
            environment {
                PROFILE = 'test'  // Define o perfil como "test" para o ambiente DEV
                IMAGE_NAME = "${REPO_NAME}:${PROFILE}-${DOCKER_TAG}"
            }
            steps {
                script {
                    // Constrói a imagem Docker diretamente a partir do Dockerfile e a nomeia para DEV
                    bat "docker build --build-arg PROFILE=${PROFILE} -t ${IMAGE_NAME} ."

                    // Faz o push da imagem para o repositório Docker
                    bat "docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%"
                    bat "docker push ${IMAGE_NAME}"
                }
            }
        }

        stage('Deploy to DEV') {
            environment {
                PROFILE = 'test'
            }
            steps {
                script {
                    // Sobe os serviços com docker-compose no ambiente "DEV"
                    bat "set PROFILE=${PROFILE} && docker-compose -f docker-compose.yml up -d --build"
                }
            }
        }

        stage('Build and Push Image for Staging') {
            environment {
                PROFILE = 'staging'
                IMAGE_NAME = "${REPO_NAME}:${PROFILE}-${DOCKER_TAG}"
            }
            steps {
                script {
                    // Constrói a imagem Docker diretamente a partir do Dockerfile e a nomeia para Staging
                    bat "docker build --build-arg PROFILE=${PROFILE} -t ${IMAGE_NAME} ."
                    bat "docker push ${IMAGE_NAME}"
                }
            }
        }

        stage('Deploy to Staging') {
            environment {
                PROFILE = 'staging'
            }
            steps {
                script {
                    // Sobe os serviços com docker-compose no ambiente "staging"
                    bat "set PROFILE=${PROFILE} && docker-compose -f docker-compose.yml up -d --build"
                }
            }
        }

        stage('Promote Image to Production') {
            when {
                branch 'main' // Promove para produção apenas quando estiver na branch principal
            }
            environment {
                PROFILE = 'prod'
                IMAGE_NAME = "${REPO_NAME}:${PROFILE}-${DOCKER_TAG}"
            }
            steps {
                script {
                    // Marca a imagem de Staging para Produção
                    bat "docker tag ${REPO_NAME}:staging-${DOCKER_TAG} ${IMAGE_NAME}"
                    bat "docker push ${IMAGE_NAME}"
                }
            }
        }

        stage('Deploy to Production') {
            when {
                branch 'main'
            }
            environment {
                PROFILE = 'prod'
            }
            steps {
                script {
                    // Sobe os serviços com docker-compose no ambiente "prod"
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
