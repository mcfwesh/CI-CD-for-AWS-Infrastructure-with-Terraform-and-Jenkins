def gv
pipeline{

    agent any

    tools {
        maven 'maven-3.9'
    }

    // parameters {
    //     choice(name: 'VERSION', description: 'App version', choices:['1.1', '1.2', '1.3'])
    //     booleanParam(name: 'executeTests', description: 'Test Exec', defaultValue: true)
    // }

    stages {
        stage('init') {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }

        stage('increment') {
            steps {
                script {
                    gv.incrementVersion()
                }
            }
        }

        stage('build jar file'){
            steps {
               script {
                gv.buildJar()
               }
            }
        }

        stage('build docker image') {

            steps {
               script {
                gv.buildDocker()
               }
            }
        }

        stage('push docker image'){
            steps {
               script {
                    gv.pushDocker()
               }

            }
        }
    }
}
