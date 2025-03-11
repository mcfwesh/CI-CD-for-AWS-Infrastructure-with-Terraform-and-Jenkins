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
        usernamePassword(credentialsId:'docker-hub', usernameVariable: "USER", passwordVariable: "PWD" )
        ]){
            sh """
                echo $PWD | docker login -u $USER --password-stdin
                docker push mcfwesh/java-maven-app:$IMAGE_NAME
            """
        }
    echo "Pushing completed!"
}

def terraformProvisioning() {
    dir("terraform/provisioning") {
        sh "terraform init"
        sh "terraform apply --auto-approve"
        env.EC2_PUBLIC_IP = sh(
            script: "terraform output tf_app_server_1_public_ip",
            returnStdout: true
        ).trim()
    }
}

def deployViaEC2() {
    echo "Waiting for EC2 instance provisioning ..."
    if (env.EC2_PUBLIC_IP.isEmpty()) {
        sleep(90)
    }

    echo "Deploying docker image to EC2 ...."
    echo "version - ${IMAGE_NAME}"
    def buildContainer = "bash ./server-cmds.sh ${IMAGE_NAME} ${DOCKER_CRED_USR} ${DOCKER_CRED_PSW}"
    def ec2Instance = "ec2-user@${EC2_PUBLIC_IP}"

    sshagent(['docker-ec2-server']) {
        sh """
            scp -o StrictHostKeyChecking=no docker-compose.yml ${ec2Instance}:/home/ec2-user
            scp -o StrictHostKeyChecking=no server-cmds.sh ${ec2Instance}:/home/ec2-user
            ssh -o StrictHostKeyChecking=no ${ec2Instance} ${buildContainer}
        """
    }
}

def commitToRepo() {
    echo "Commiting to git repo..."
    withCredentials([
    usernamePassword(credentialsId:'gitlab-access', usernameVariable: "USER", passwordVariable: "PWD" )
    ]){
        sh """
            git config --global user.email "jenkins@example.com"
            git config --global user.name "jenkins"
            git remote set-url origin https://${USER}:${PWD}@gitlab.com/mcfwesh/jenkins-java-maven-app.git
            git add .
            git commit -m "ci: incrementing version"
            git push origin HEAD:${BRANCH_NAME}
        """
    }
}

return this