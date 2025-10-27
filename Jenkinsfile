pipeline {
  agent any

  environment {
    // Replace with your Docker Hub image name like yourdockerhubuser/java-docker-app
    DOCKER_IMAGE = "yourdockerhubusername/java-docker-app"
    TAG = "${env.BUILD_NUMBER}"
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build (Maven)') {
      steps {
        // Run maven. Requires maven available on agent PATH.
        sh "mvn -B -DskipTests clean package"
      }
    }

    stage('Build Docker Image') {
      steps {
        script {
          // build Docker image and tag it with both build number and latest
          sh "docker build -t ${DOCKER_IMAGE}:${TAG} -t ${DOCKER_IMAGE}:latest ."
        }
      }
    }

    stage('Push to Docker Hub') {
      steps {
        script {
          // Login and push. Uses Jenkins credentials (username/password).
          withCredentials([usernamePassword(credentialsId: 'priti/admin', passwordVariable:'admin', usernameVariable:'priti')]) {
            sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
            sh "docker push ${DOCKER_IMAGE}:${TAG}"
            sh "docker push ${DOCKER_IMAGE}:latest"
            sh 'docker logout'
          }
        }
      }
    }
  }

  post {
    always {
      echo "Done. Image: ${DOCKER_IMAGE}:${TAG}"
    }
  }
}
