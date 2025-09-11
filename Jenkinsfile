pipeline {
  agent any

  environment {
    REGISTRY             = 'docker.io'
    REGISTRY_NAMESPACE   = 'lschifferx'
    IMAGE_NAME           = 'demo-api'
    REGISTRY_CREDENTIALS = 'dockerhub-creds'
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build') {
      steps { sh 'mvn -B clean compile' }
    }

    stage('Unit Tests & Verify') {
      steps { sh 'mvn -B test verify' }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
        }
      }
    }

    stage('Package') {
      steps {
        sh 'mvn -B package -DskipTests'
        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
      }
    }

    stage('Docker Build & Tag') {
      steps {
        script {
          def version  = sh(script: "mvn -q -Dexec.executable=echo -Dexec.args='\\\${project.version}' --non-recursive exec:exec", returnStdout: true).trim()
          def shortSha = sh(script: "git rev-parse --short=7 HEAD", returnStdout: true).trim()
          env.IMAGE_TAG = "${version}-b${env.BUILD_NUMBER}-${shortSha}"
          env.IMAGE_URI = "${REGISTRY}/${REGISTRY_NAMESPACE}/${IMAGE_NAME}"

          sh """
            docker build \
              -t ${IMAGE_URI}:${IMAGE_TAG} \
              -t ${IMAGE_URI}:latest \
              .
          """
        }
      }
    }

    stage('Docker Login & Push') {
      steps {
        withCredentials([usernamePassword(credentialsId: env.REGISTRY_CREDENTIALS, usernameVariable: 'USER', passwordVariable: 'PASS')]) {
          sh """
            echo "\$PASS" | docker login ${REGISTRY} -u "\$USER" --password-stdin
            docker push ${IMAGE_URI}:${IMAGE_TAG}
            docker push ${IMAGE_URI}:latest
            docker logout ${REGISTRY} || true
          """
        }
      }
    }
  }

  post {
    success {
      echo "Imagem publicada em ${env.IMAGE_URI}:${env.IMAGE_TAG}"
    }
  }
}
