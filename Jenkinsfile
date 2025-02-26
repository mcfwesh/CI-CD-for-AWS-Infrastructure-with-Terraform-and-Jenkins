def gv
pipeline{

    agent any

    // environment {
    //     USERNAMEPWD = credentials('519d8533-cffc-47a6-a958-955bb6578fbd')
    // }

    parameters {
        choice(name: 'VERSION', description: 'App version', choices:['1.1', '1.2', '1.3'])
        booleanParam(name: 'executeTests', description: 'Test Exec', defaultValue: true)
    }

    stages {

        stage('init') {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }

        }

        stage('build'){

            steps {
               script {
                gv.buildApp()
               }
            }
        }

        stage('test'){
            when {
                expression {
                    params.executeTests == true
                }
            }

            steps {
               script {
                gv.testApp()
               }
            }
        }

        stage('deploy'){
            steps {
                env.ENV = input message: "Select the deployment environment", ok: "Done!", parameters: [choice(name: 'ENV', description: 'ENV choice', choices:['dev', 'qa', 'prod'])]
               script {
                gv.deployApp()
               }

            }
        }
    }
}
