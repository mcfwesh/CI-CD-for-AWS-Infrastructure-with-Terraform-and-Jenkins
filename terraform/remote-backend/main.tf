terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.20.1"
    }
  }
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_s3_bucket" "tf_app_bucket_mcfwesh" {
  bucket = "tf-app-bucket-mcfwesh"

}

resource "aws_s3_bucket_versioning" "tf_app_bucket_mcfwesh_versioning" {
  bucket = aws_s3_bucket.tf_app_bucket_mcfwesh.id
  versioning_configuration {
    status = "Enabled"
  }
}
