pipeline{
    agent any
    tools{
        maven 'maven_3_8_6'
    }
    stages{
        stage('Build Maven'){
            steps{
           		checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/GaborPinter/Szakdolgozat']]])
                bat 'mvn clean install'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    bat 'docker build -t gaborpinter/autoker3 .'
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'dockerhub-pwd-ok', variable: 'dockerhubpwdok')]) {
                    bat 'docker login -u gaborpinter -p ${dockerhubpwdok}'
                    
}
                    bat 'docker push gaborpinter/Szakdolgozat'
                }
            }
        }
    }
}
