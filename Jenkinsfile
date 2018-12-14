pipeline {
    agent any
   
    tools {
        maven 'default'
    }
    
    stages {
        stage('Prepare') {
            steps {
                script {
                    sh 'mvn clean package'
                }
                build job: 'katalon/staging-plugin'
            }
        }
    }
}
