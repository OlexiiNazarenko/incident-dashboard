#!/bin/bash
yum update –y
amazon-linux-extras install java-openjdk11 -y
wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
rpm --import https://jenkins-ci.org/redhat/jenkins-ci.org.key
amazon-linux-extras install epel -y
yum-config-manager --enable epel
yum install daemonize -y
sudo tee /etc/yum.repos.d/jenkins.repo<<EOF
[jenkins]
name=Jenkins
baseurl=http://pkg.jenkins.io/redhat
gpgcheck=0
EOF
yum install jenkins -y
systemctl start jenkins
yum install docker -y
systemctl start docker
groupadd docker
usermod -aG docker jenkins
usermod -aG docker ec2-user
newgrp docker
#cat /var/lib/jenkins/secrets/initialAdminPassword

