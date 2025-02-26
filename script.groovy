def buildJar() {
    echo "Building the app...."
    sh 'mvn package'
}

def buildDocker() {
    echo "Building the image...."
     sh "docker build -t mcfwesh/java-maven-app:1.1 ."
}

def pushDocker() {
  echo "Pushing image to dockerhub repo... "
    withCredentials([
        usernamePassword(credentials:'a967aeaf-43d9-49de-a9a1-5725c0918685', usernameVariable: USER, passwordVariable: PWD )
        ]){
                sh "echo ${PWD} | docker login -u ${USER} --password-stdin"
                sh "docker build -t mcfwesh/java-maven-app:1.1 ."
                sh "docker push mcfwesh/java-maven-app:1.1"
        }
    echo "Pushing completed!"
}

return this