def incrementVersion() {
    echo "Incrementing version..."
    sh 'mvn build-helper:parse-version versions:set \
        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
        versions:commit'
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    def version = matcher[0][1]
    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
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

def commitToRepo() {
    echo "Commiting to git repo..."
    withCredentials([
    usernamePassword(credentialsId:'a30f485a-77fe-4892-bbd8-4cbbeb4f93a9', usernameVariable: "USER", passwordVariable: "PWD" )
    ]){
        sh """
            git config --global user.email "jenkins@example.com"
            git config --global user.name "jenkins"
            git remote set-url origin https://$USER:$PWD@gitlab.com/mcfwesh/jenkins-java-maven-app.git
            git add .
            git commit -m "ci: incrementing version"
            git push origin HEAD:jenkins-jobs
        """
    }
}

return this