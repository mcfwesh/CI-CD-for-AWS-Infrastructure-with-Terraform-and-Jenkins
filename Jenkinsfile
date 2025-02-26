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

        stage('build jar file'){
            when {
                expression {
                    BRANCH_NAME == 'jenkins_jobs'
                }
            }

            steps {
               script {
                gv.buildJar()
               }
            }
        }

        stage('build docker image') {
            when {
                expression {
                    BRANCH_NAME == 'jenkins_jobs'
                }
            }

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
