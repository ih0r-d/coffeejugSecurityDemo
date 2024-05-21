# CoffeeJug Demo: Security in Design

![](https://img.shields.io/badge/Language-Java-informational?style=flat&logo=java&logoColor=white&color=2bbc8a)
![](https://img.shields.io/badge/Tools-Terraform-informational?style=flat&logo=java&logoColor=white&color=2bbc8a)
![](https://img.shields.io/badge/Tools-Maven-informational?style=flat&logo=apache-maven&logoColor=white&color=2bbc8a)

![](https://img.shields.io/badge/Tools-Hadolint-informational?style=flat&logo=hadolint&logoColor=white&color=2bbc8a)
![](https://img.shields.io/badge/Tools-Checkmarx-informational?style=flat&logo=checkmarx&logoColor=white&color=2bbc8a)
![](https://img.shields.io/badge/Tools-SonarQube-informational?style=flat&logo=sonarqube&logoColor=white&color=2bbc8a)
![](https://img.shields.io/badge/Tools-Trivy-informational?style=flat&logo=trivy&logoColor=white&color=2bbc8a)
![](https://img.shields.io/badge/Tools-Snyk-informational?style=flat&logo=snyk&logoColor=white&color=2bbc8a)
![](https://img.shields.io/badge/Tools-TruffleHog-informational?style=flat&logo=trufflehog&logoColor=white&color=2bbc8a)
![](https://img.shields.io/badge/Tools-Gitleaks-informational?style=flat&logo=gitleaks&logoColor=white&color=2bbc8a)


This repository contains a demo of java application and terraform code.

## Table of Contents

- [Security Problems and Solutions](#application)
  - [Detecting Issues in Dockerfiles](#hadolint)
  - [Ensuring Code Quality and Security](#sonarqube)
  - [Identifying Static Application Security Flaws](#checkmarks)
  - [Container Vulnerability Scanning](#trivy)
  - [Managing Vulnerabilities in Open Source Dependencies](#snyk)
  - [Detecting Secrets in Code Repositories](#trufflehog)
  - [Finding Leaks in Git Repositories](#gitleaks)

[//]: # (Detecting Issues in Dockerfiles &#40;Hadolint&#41;)
[//]: # (Ensuring Code Quality and Security &#40;SonarQube&#41;)
[//]: # (Identifying Static Application Security Flaws &#40;SAST&#41; &#40;Checkmarx&#41;)
[//]: # (Scanning for Vulnerabilities in Containers &#40;Trivy&#41;)
Managing Vulnerabilities in Open Source Dependencies (Snyk)
Detecting Secrets in Code Repositories (TruffleHog)
Finding Leaks in Git Repositories (Gitleaks)

## Tools overview

### Detecting Issues in Dockerfiles

* Scan `v0`
```shell
hadolint app/Dockerfile.v0
```

### Full `yaml` schema
```yaml
failure-threshold: string               # name of threshold level (error | warning | info | style | ignore | none)
format: string                          # Output format (tty | json | checkstyle | codeclimate | gitlab_codeclimate | gnu | codacy)
ignored: [string]                       # list of rules
label-schema:                           # See Linting Labels below for specific label-schema details
  author: string                        # Your name
  contact: string                       # email address
  created: timestamp                    # rfc3339 datetime
  version: string                       # semver
  documentation: string                 # url
  git-revision: string                  # hash
  license: string                       # spdx
no-color: boolean                       # true | false
no-fail: boolean                        # true | false
override:
  error: [string]                       # list of rules
  warning: [string]                     # list of rules
  info: [string]                        # list of rules
  style: [string]                       # list of rules
strict-labels: boolean                  # true | false
disable-ignore-pragma: boolean          # true | false
trustedRegistries: string | [string]    # registry or list of registries
```

* Scan `v0` with custom config
```shell
hadolint --config app/hadolint-config.yaml  app/Dockerfile.v0 | jq
```

* Build `v0`
```shell
mvn clean package && \
docker buildx build . -t coffee-jug-demo:v0 -f app/Dockerfile.v0 
```

* Scan `v0-fixed` with custom config
```shell
hadolint --config app/hadolint-config.yaml app/Dockerfile.v0-fixed | jq
```
```shell
hadolint --config app/hadolint-config.yaml app/Dockerfile.v0 | tidy -xml -iq
```

* Build `v0-fixed` docker container
```shell
docker buildx build . -t coffee-jug-demo:v0-fixed -f app/Dockerfile.v0-fixed
```

* Scan `v1` via hadolint
```shell
hadolint app/Dockerfile.v1
```

* Build `v1` docker container
```shell
docker buildx build . -t coffee-jug-demo:v1-fixed -f app/Dockerfile.v1-fixed
```

### Ensuring Code Quality and Security
=======

1. Start `sonarqube` via docker-compose
```shell
docker-compose -f app/sonarqube.yml up -d
```

2. Execute SonarScanner via maven
```shell
  mvn -f app/pom.xml clean verify sonar:sonar \
  -Dsonar.projectKey=CoffeeJugDemo \
  -Dsonar.projectName='CoffeeJugDemo' \
  -Dsonar.host.url=http://0.0.0.0:9000 \
  -Dsonar.token=sqp_04b3cdb170e93f2d376e9e9549de51fc20a32ae1
```

### Identifying Static Application Security Flaws

1. Go to `app` project to review `pom.xml`
   ![alt text](images/checkmarks-info.png)

2. Checkout `checkmarks: update dependency versions to resolve vulnerabilities` commit


### Scanning for Vulnerabilities in Containers
1. Overview of doc https://aquasecurity.github.io/trivy/v0.51/docs
2. Image scan
```shell
trivy image openjdk:21-jdk
```

2. App full scan in `fs` mode scan
```shell
trivy fs --scanners vuln,secret,misconfig app/
```

3. Terraform full scan in `fs` mode scan
```shell
trivy fs --scanners vuln,secret,misconfig terraform/
```

4. Actions
* https://github.com/aquasecurity/trivy-action
* https://github.com/lazy-actions/gitrivy
* CodeQL tool -> https://github.com/ih0r-d/coffeeJugSecurityDemo/settings/security_analysis


### Managing Vulnerabilities in Open Source Dependencies
```shell
snyk --help
```
* https://security.snyk.io/vuln

[![Watch the video](https://i.ytimg.com/vi/BQWesBxbqWQ/hq720.jpg?sqp=-oaymwEcCNAFEJQDSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLBsBBxzmLGJL-HKIqmln8Ice9PiEg)](https://www.youtube.com/watch?v=BQWesBxbqWQ)

### Detecting Secrets in Code Repositories
Link: https://github.com/trufflesecurity/trufflehog

```shell
trufflehog github --only-verified --repo https://github.com/ih0r-d/coffeeJugSecurityDemo 
```

```shell
trufflehog filesystem ./ --only-verified --fail --json | jq 
```

### Finding Leaks in Git Repositories

Link: https://github.com/gitleaks/gitleaks
