pipeline {
    agent none

    environment {
        MAVEN_OPTS = '-Duser.home=/tmp/docker-cache-maven'
    }
    
    stages {
        stage('Prepare') {
            steps {
                    sh 'mkdir -p $HOME/docker-cache-maven && chmod 777 $HOME/docker-cache-maven'
                    sh 'export PATH=/usr/local/bin:$PATH'
                    sh 'chmod 777 -R $WORKSPACE'
                }    
        stage('Building') {
            agent {
                docker {
                        image 'maven:latest'
                        args  '-v /$HOME/docker-cache-maven:/tmp/docker-cache-maven'
                    }
            steps {
                sh 'mvn clean install'
                    }      
                }   
            }
        }
    }
}