pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps { checkout scm }
    }
    stage('Build & Verify') {
      steps {
        sh 'mvn -B clean verify'
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