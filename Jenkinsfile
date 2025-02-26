pipeline{

    agent any

    environment {
        USERNAMEPWD = credentials('519d8533-cffc-47a6-a958-955bb6578fbd')
    }

    stages {

        stage('build'){

            steps {
                echo "Building the app"
                echo "Get first cred: ${USERNAMEPWD}"
                withCredentials([
                    usernamePassword(credentials:'519d8533-cffc-47a6-a958-955bb6578fbd', usernameVariable: USER, passwordVariable: PWDD )
                ]){
                    sh "Second cred: ${USER - PWDD}"
                }
            }
        }

        stage('test'){

            steps {
                echo "Testing the app"

            }
        }

        stage('deploy'){

            steps {
                echo "Deploying the app"

            }
        }
    }
}
