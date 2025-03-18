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

3. **Build and Push Docker Image**

   - In the Jenkins pipeline, the `build docker image` stage builds the Docker image and pushes it to the Docker registry.

4. **Deploy Application**

   - The `aws ec2 build container!` stage in the Jenkins pipeline deploys the application on the provisioned EC2 instance.

5. **Access Application**
   - Access the application via the public IP of the EC2 instance on port 8080.

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

## Technologies Used

- **Terraform**: Used for infrastructure provisioning and management on AWS.
- **Jenkins**: Automates the CI/CD pipeline for building and deploying applications.
- **AWS**: Provides cloud infrastructure services including EC2, S3, and VPC.
- **Docker**: Containerizes the application for consistent deployment environments.

## Screenshots
