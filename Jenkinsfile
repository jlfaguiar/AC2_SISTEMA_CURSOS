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
                    // Compila o projeto e cria o JAR
                    sh 'mvn clean package -DskipTests'

                    // Constrói a imagem Docker usando o Dockerfile e a nomeia para DEV
                    sh "docker build -t ${IMAGE_NAME} ."

                    // Faz o push da imagem para o repositório Docker
                    sh "docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD"
                    sh "docker push ${IMAGE_NAME}"
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
                    sh "PROFILE=${PROFILE} docker-compose -f docker-compose.yml up -d --build"
                }
            }
        }

        stage('Build and Push Image for Staging') {
            environment {
                PROFILE = 'staging'  // Define o perfil como "staging"
                IMAGE_NAME = "${REPO_NAME}:${PROFILE}-${DOCKER_TAG}"
            }
            steps {
                script {
                    // Constrói a imagem Docker para Staging e a envia para o repositório Docker
                    sh "docker build -t ${IMAGE_NAME} ."
                    sh "docker push ${IMAGE_NAME}"
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
                    sh "PROFILE=${PROFILE} docker-compose -f docker-compose.yml up -d --build"
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
                    sh "docker tag ${REPO_NAME}:staging-${DOCKER_TAG} ${IMAGE_NAME}"
                    sh "docker push ${IMAGE_NAME}"
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
                    sh "PROFILE=${PROFILE} docker-compose -f docker-compose.yml up -d --build"
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
