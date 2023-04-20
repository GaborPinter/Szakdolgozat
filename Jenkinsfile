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
                    bat 'docker build -t autoker3 .'
                }
            }
        }
        stage('Login') {

			steps {
				bat 'docker login -u gaborpinter -p Katuska05+'
			}
		}
        
        stage('Push') {

			steps {
				bat 'docker push autoker3'
			}
		}
	}
    
    post {
		always {
			sh 'docker logout'
		}
	}
    
}
