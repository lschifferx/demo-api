pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build') {
      steps {
        sh 'mvn -B clean compile'
      }
    }

    stage('Unit Tests & Verify') {
      steps {
        sh 'mvn -B test verify'
      }
    }

    stage('Package') {
      steps {
        sh 'mvn -B package -DskipTests'
        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
      }
    }

    stage('Docker Build') {
      steps {
        sh 'docker build -t demo-api:latest .'
      }
    }

    stage('Deploy (Docker Compose)') {
      when { expression { fileExists('docker-compose.yml') } }
      steps {
        sh 'docker compose down || true'
        sh 'docker compose up -d --build'
      }
    }
  }
}
