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

resource "aws_s3_bucket" "tf_app_bucket" {
  bucket = "tf-app-bucket-mcfwesh"

  tags = {
    environment = "dev"
  }
}

resource "aws_s3_bucket_acl" "tf_app_bucket_acl" {
  bucket = aws_s3_bucket.tf_app_bucket.id
  acl    = "private"
}

resource "aws_s3_bucket_versioning" "tf_app_bucket_versioning" {
  bucket = aws_s3_bucket.tf_app_bucket.id
  versioning_configuration {
    status = "Enabled"
  }
}
