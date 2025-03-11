terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.20.1"
    }
  }
  required_version = ">=0.12"
  backend "s3" {
    region = "us-east-1"
    bucket = "tf-app-bucket-mcfwesh"
    key    = "tf-app/state.tfstate"
  }
}

provider "aws" {
  region = "us-east-1"
}
