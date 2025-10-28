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
                git branch: 'main', url: 'https://github.com/<your-username>/<your-repo>.git'
            }
        }

        stage('Build with Maven') {
            steps {
                echo 'Building application with Maven...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker image...'
                    sh "docker build -t ${DOCKER_IMAGE}:${TAG} -t ${DOCKER_IMAGE}:latest ."
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    echo 'Pushing image to Docker Hub...'
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                        sh "docker push ${DOCKER_IMAGE}:${TAG}"
                        sh "docker push ${DOCKER_IMAGE}:latest"
                        sh 'docker logout'
                    }
                }
            }
        }

        stage('Run Application Container') {
            steps {
                script {
                    echo 'Running the Docker container...'
                    sh "docker run -d -p 8080:8080 ${DOCKER_IMAGE}:${TAG}"
                }
            }
        }
    }

    post {
        success {
            echo "✅ Build and deployment successful!"
            echo "Image pushed: ${DOCKER_IMAGE}:${TAG}"
        }
        failure {
            echo "❌ Build failed. Check logs."
        }
    }
}
