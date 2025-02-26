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

        stage('build'){

            steps {
                echo "Building the app"
                // echo "Get first cred: ${USERNAMEPWD}"
                // withCredentials([
                //     usernamePassword(credentials:'519d8533-cffc-47a6-a958-955bb6578fbd', usernameVariable: USER, passwordVariable: PWDD )
                // ]){
                //     sh "Second cred: ${USER} - ${PWD}"
                // }
            }
        }

        stage('test'){
            when {
                expression {
                    param.executeTests == true
                }
            }

            steps {
                echo "Testing the app"

            }
        }

        stage('deploy'){

            steps {
                echo "Deploying the app, version ${param.VERSION}"

            }
        }
    }
}
