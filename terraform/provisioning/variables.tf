variable "env" {
  default = "dev"
}
variable "vpc_cidr_block" {
  default = "10.0.0.0/16"
}
variable "subnet_cidr_block" {
  default = "10.0.10.0/24"
}
variable "availability_zone" {
  default = "us-east-1b"
}
variable "my_ip" {
  default = "76.71.165.155/32"
}
variable "jenkins_server_ip" {
  default = "159.203.13.189/32"
}

variable "public_key_file_path" {
  default = "~/.ssh/id_rsa.pub"
}
