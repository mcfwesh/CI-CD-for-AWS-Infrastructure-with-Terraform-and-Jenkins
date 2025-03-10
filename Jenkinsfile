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

        stage('provision infrastructure') {
            environment {
                AWS_ACCESS_KEY_ID = credentials("jenkins-aws-access-key-id")
                AWS_SECRET_ACCESS_KEY = credentials("jenkins-aws-secret-access-key")
                TF_VAR_env = "test"
                EC2_PUBLIC_IP = ""
            }
            steps {
                script {
                    gv.terraformProvisioning()
                }
            }
        }

        stage('aws ec2 build container!') {
            environment {
                DOCKER_CRED = credentials('docker-hub')
            }
            steps {
                script {
                    gv.deployViaEC2()
                }
            }
        }

        stage('commit to repo') {
            steps {
                script {
                    gv.commitToRepo()
                }
            }
        }
    }
}
