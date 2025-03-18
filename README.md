# Module 12b: CI/CD AWS Infrastructure Deployment with Terraform and Jenkins

This project automates the deployment of a scalable application infrastructure on AWS using **Terraform** for infrastructure as code and **Jenkins** for continuous integration and deployment.

## Tasks from Module 12

### **"Automate AWS Infrastructure"**

- Automated provisioning of AWS components (VPC, subnets, EC2) and Docker deployment.

### **"Terraform & AWS EKS"**

- Provisioned an EKS cluster for scalable container orchestration.

### **"Configure a Shared Remote State"**

- Set up S3 for remote storage of Terraform state files.

### **"Complete CI/CD with Terraform"**

- Integrated CI/CD pipeline for automated server provisioning and application deployment.

## Project Overview

This project aims to streamline the deployment of a web application on AWS by leveraging Infrastructure as Code (IaC) principles. The main components include a VPC, EC2 instances, and Docker containers, all managed through Terraform scripts and a Jenkins pipeline. Key technical implementations involve the use of AWS services for hosting and managing application resources, ensuring scalability and reliability.

## Learning Progression/Steps

1. **Initialize Terraform**
   - Navigate to the remote backend directory and run the initialization command to set up the backend.
   - Command: `cd terraform/remote-backend && terraform init`

2. **Provision Infrastructure**
   - The infrastructure provisioning is automated through the Jenkins pipeline. The `provision infrastructure` stage in the Jenkinsfile applies the Terraform configuration to provision the defined infrastructure on AWS. 
   - During this stage, the `terraformProvisioning` function is executed, which:
     - Initializes the Terraform configuration in the `terraform/provisioning` directory.
     - Applies the Terraform scripts to create the necessary AWS resources.
     - Dynamically retrieves the public IP of the provisioned EC2 instance using the command `terraform output tf_app_server_1_public_ip`, which is stored in the environment variable `EC2_PUBLIC_IP` for later use.

3. **Build and Push Docker Image**
   - In the Jenkins pipeline, the `build docker image` stage builds the Docker image and pushes it to the Docker registry.

4. **Deploy Application**
   - The `aws ec2 build container!` stage in the Jenkins pipeline deploys the application on the provisioned EC2 instance. 
   - The `deployViaEC2` function handles this process by:
     - Waiting for the EC2 instance to be fully provisioned. If the public IP is not yet available, it sleeps for 90 seconds.
     - Using SSH to securely copy the necessary files (like `docker-compose.yml` and `server-cmds.sh`) to the EC2 instance.
     - Executing the deployment command on the EC2 instance to start the application using the specified Docker image.

## Repository Structure

- **terraform/**

  - Contains all Terraform configurations for provisioning AWS resources.

- **terraform/provisioning/**

  - `main.tf`: Defines the infrastructure resources including VPC, subnets, security groups, and EC2 instances.
  - `variables.tf`: Contains variable definitions for configuration such as CIDR blocks and IP addresses.
  - `outputs.tf`: Outputs the public IP of the EC2 instance.
  - `user-data-script.sh`: Script to install Docker on EC2 instances.

- **terraform/remote-backend/**

  - `main.tf`: Configures the S3 backend for Terraform state management.

- **Jenkinsfile**
  - Defines the CI/CD pipeline for building and deploying the application. Key stages include:
    - **Initialization**: Loads the Groovy script for managing build tasks.
    - **Version Increment**: Automatically increments the application version.
    - **Build**: Compiles the application and creates a JAR file.
    - **Docker Build**: Builds the Docker image and pushes it to the Docker registry.
    - **Provision Infrastructure**: Applies the Terraform configuration to provision AWS resources.
    - **Deploy Application**: Deploys the application on the provisioned EC2 instance.

## Key Configuration
- Local private and public keys were added to the Jenkins server Docker container to facilitate secure access to the EC2 instance. The owner and group of the `.ssh` folder was changed to `jenkins:jenkins`.

## Technologies Used

- **Terraform**: Used for infrastructure provisioning and management on AWS.
- **Jenkins**: Automates the CI/CD pipeline for building and deploying applications.
- **AWS**: Provides cloud infrastructure services including EC2, S3, and VPC.
- **Docker**: Containerizes the application for consistent deployment environments.

## Screenshots

- ![AWS S3 Backend](screenshots/aws-s3-backend.png)
  *AWS S3 bucket for remote state storage.*

- ![AWS EC2 Instance](screenshots/aws-ec2.png)
  *Provisioned EC2 instance configuration.*

- ![Jenkins Pipeline](screenshots/jenkins.png)
  *Jenkins pipeline execution stages.*
