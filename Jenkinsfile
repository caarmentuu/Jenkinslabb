pipeline {
    agent any

    stages {
        stage('build') {
            steps {
                bat "mvn compile"
            }
        }

        stage('test') {

            steps {

                bat "mvn test"
            }

        }

        stage('run test') {

            steps {

                junit '**/TEST*.xml' 

            }
        }

        stage('selenium and report') {

            steps {
                bat 'robot --outputdir results/ tests/'
            }
            
            post {
                always {
                robot outputPath: '.', logFileName: 'log.html', outputFileName: 'output.xml', reportFileName: 'report.html', passThreshold: 100, unstableThreshold: 75.0
                }

            }
        }
    }
}
