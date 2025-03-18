data "aws_ami" "selected_ami" {
  owners      = ["amazon"]
  most_recent = true

  filter {
    name   = "name"
    values = ["al2023-*-x86_64"]
  }

  filter {
    name   = "virtualization-type"
    values = ["hvm"]
  }

  filter {
    name   = "architecture"
    values = ["x86_64"]
  }
}

resource "aws_vpc" "tf_app_vpc" {
  cidr_block = var.vpc_cidr_block
  tags = {
    Name        = "${var.env}-tf_app_vpc"
    environment = var.env
  }
}

resource "aws_subnet" "tf_app_public_subnet" {
  vpc_id                  = aws_vpc.tf_app_vpc.id
  cidr_block              = var.subnet_cidr_block
  availability_zone       = var.availability_zone
  map_public_ip_on_launch = true
  tags = {
    Name        = "${var.env}-tf_app_public_subnet"
    environment = var.env
  }
}

resource "aws_route_table" "tf_app_rtb" {
  vpc_id = aws_vpc.tf_app_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.tf_app_igw.id

  }
  tags = {
    Name        = "${var.env}-tf_app_rtb"
    environment = var.env
  }
}

resource "aws_internet_gateway" "tf_app_igw" {
  vpc_id = aws_vpc.tf_app_vpc.id
}

resource "aws_route_table_association" "tf-app-rtb-assoc" {
  route_table_id = aws_route_table.tf_app_rtb.id
  subnet_id      = aws_subnet.tf_app_public_subnet.id
}

resource "aws_security_group" "tf_app_sg" {
  name   = "${var.env}-tf_app_sg"
  vpc_id = aws_vpc.tf_app_vpc.id

  tags = {
    Name        = "${var.env}-tf_app_sg"
    environment = var.env
  }
}

resource "aws_vpc_security_group_ingress_rule" "tf_app_ingress_sh_local" {
  security_group_id = aws_security_group.tf_app_sg.id
  ip_protocol       = "TCP"
  from_port         = 22
  to_port           = 22
  cidr_ipv4         = var.my_ip
}

resource "aws_vpc_security_group_ingress_rule" "tf_app_ingress_sh_jenkins" {
  security_group_id = aws_security_group.tf_app_sg.id
  ip_protocol       = "TCP"
  from_port         = 22
  to_port           = 22
  cidr_ipv4         = var.jenkins_server_ip
}

resource "aws_vpc_security_group_ingress_rule" "tf_app_ingress_others" {
  security_group_id = aws_security_group.tf_app_sg.id
  ip_protocol       = "TCP"
  from_port         = 8080
  to_port           = 8080
  cidr_ipv4         = "0.0.0.0/0"
}

resource "aws_vpc_security_group_egress_rule" "tf_app_egress" {
  security_group_id = aws_security_group.tf_app_sg.id
  ip_protocol       = "-1"
  cidr_ipv4         = "0.0.0.0/0"
}

resource "aws_key_pair" "tf_app_server_1_key_pair" {
  key_name   = "tf_app_server_1_key_pair"
  public_key = file(var.public_key_file_path)
  tags = {
    Name        = "${var.env}-tf_app_server_1_key_pair"
    environment = var.env
  }
}

resource "aws_instance" "tf_app_server_1" {
  subnet_id                   = aws_subnet.tf_app_public_subnet.id
  ami                         = data.aws_ami.selected_ami.id
  instance_type               = "t3.micro"
  vpc_security_group_ids      = [aws_security_group.tf_app_sg.id]
  key_name                    = aws_key_pair.tf_app_server_1_key_pair.key_name
  user_data                   = file("user-data-script.sh")
  user_data_replace_on_change = true
  tags = {
    Name        = "${var.env}-tf_app_server_1"
    environment = var.env
  }
}
