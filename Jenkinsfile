pipeline {
    agent any

    environment {
        // Docker Hub repo name (update with your username)
        DOCKER_IMAGE = "prit2004/java-docker-app"
        TAG = "${env.BUILD_NUMBER}"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Priti26104/javadockerapp.git'
            }
        }

        stage('Build with Maven') {
            steps {
                echo 'Building application with Maven...'
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker image...'
                    bat "docker build -t %DOCKER_IMAGE%:%TAG% -t %DOCKER_IMAGE%:latest ."
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    echo 'Pushing image to Docker Hub...'
                    withCredentials([usernamePassword(credentialsId: 'prit2004/Govind108', usernameVariable: 'prit2004', passwordVariable: 'Govind108')]) {
                        bat """
                        echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                        docker push %DOCKER_IMAGE%:%TAG%
                        docker push %DOCKER_IMAGE%:latest
                        docker logout
                        """
                    }
                }
            }
        }

        stage('Run Application Container') {
            steps {
                script {
                    echo 'Running the Docker container...'
                    bat "docker run -d -p 8080:8080 %DOCKER_IMAGE%:%TAG%"
                }
            }
        }
    }

    post {
        success {
            echo "✅ Build and deployment successful!"
            echo "Image pushed: ${env.DOCKER_IMAGE}:${env.TAG}"
        }
        failure {
            echo "❌ Build failed. Check logs."
        }
    }
}
