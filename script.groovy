def buildApp() {
    echo "Building the app"
        // echo "Get first cred: ${USERNAMEPWD}"
        // withCredentials([
        //     usernamePassword(credentials:'519d8533-cffc-47a6-a958-955bb6578fbd', usernameVariable: USER, passwordVariable: PWDD )
        // ]){
        //     sh "Second cred: ${USER} - ${PWD}"
        // }
}

def testApp() {
    echo "Testing the app"
}

def deployApp() {
  echo "Deploying the app to ${ENV}, version ${params.VERSION}"
}

return this