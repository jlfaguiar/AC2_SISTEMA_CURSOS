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

        stage('Build and Push Image for DEV') {
            environment {
                PROFILE = 'test'  // Define o perfil como "test" para o ambiente DEV
                IMAGE_NAME = "${REPO_NAME}:${PROFILE}-${DOCKER_TAG}"
            }
            steps {
                script {
                    // Compila o projeto e cria o JAR
                    bat 'mvn clean package -DskipTests -Dspring.profiles.active=test'

                    // Constrói a imagem Docker usando o Dockerfile e a nomeia para DEV
                    bat "docker build -t ${IMAGE_NAME} ."

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
                PROFILE = 'staging'  // Define o perfil como "staging"
                IMAGE_NAME = "${REPO_NAME}:${PROFILE}-${DOCKER_TAG}"
                SPRING_PROFILES_ACTIVE = "staging"  // Define o perfil Spring como "staging"
            }
            steps {
                script {
                    // Compila o projeto com o perfil "staging"
                    bat "mvn clean package -DskipTests -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}"

                    // Constrói a imagem Docker para Staging e a envia para o repositório Docker
                    bat "docker build -t ${IMAGE_NAME} ."
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
                SPRING_PROFILES_ACTIVE = "prod"  // Define o perfil Spring como "prod"
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
