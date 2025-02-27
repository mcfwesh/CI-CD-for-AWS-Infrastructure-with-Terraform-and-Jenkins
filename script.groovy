def incrementVersion() {
    sh """
        mvn build-helper:parse-version versions:set /
        -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion} /
        versions:commit
    """
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    def version = matcher[0][1]
    env.IMAGE_NAME = "$version.$BUILD_NUMBER"
}

def buildJar() {
    echo "Building the app...."
    sh 'mvn clean package'
}

def buildDocker() {
    echo "Building the image...."
     sh "docker build -t mcfwesh/java-maven-app:$IMAGE_NAME ."
}

def pushDocker() {
  echo "Pushing image to dockerhub repo... "
    withCredentials([
        usernamePassword(credentialsId:'a967aeaf-43d9-49de-a9a1-5725c0918685', usernameVariable: "USER", passwordVariable: "PWD" )
        ]){
            sh """
                echo $PWD | docker login -u $USER --password-stdin
                docker push mcfwesh/java-maven-app:$IMAGE_NAME
            """
        }
    echo "Pushing completed!"
}

return this