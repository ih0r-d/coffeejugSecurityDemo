# CoffeeJug Demo: Security in Design

![](https://img.shields.io/badge/Language-Java-informational?style=flat&logo=java&logoColor=white&color=2bbc8a)
![](https://img.shields.io/badge/Tools-Terraform-informational?style=flat&logo=java&logoColor=white&color=2bbc8a)
![](https://img.shields.io/badge/Tools-Maven-informational?style=flat&logo=apache-maven&logoColor=white&color=2bbc8a)

This repository contains a demo of java application and terraform code.

## Table of Contents

- [Tools overview](#application)
    - [Hadolint](#hadolint)
    - [SonarQube](#sonarqube)


## Tools overview

### Hadolint

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

### SonarQube
