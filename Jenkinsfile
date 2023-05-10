

pipeline {
    agent any

    tools {
        jdk("OpenJDK 11")
    }

    environment {
        ECR_URL = "015501295117.dkr.ecr.ap-northeast-2.amazonaws.com/bankapp/bankapp"
        registryCredential = "AWS credit"
        // imageName = "${ECR_URL}:Backend${BUILD_NUMBER}"
        dockerImage = ''
    }

    stages {
        // git에서 repository clone
        stage('Prepare') {
            steps {
                echo 'Clonning Repository'
                git url: 'https://github.com/root-devvoo/BankApplication.git',
                branch: 'test',
                credentialsId: 'root-devvoo_git'
            }
            post {
                success { 
                    echo 'Successfully Cloned Repository'
                }
                failure {
                    error 'This pipeline stops here...(Prepare)'
                }
            }
        }

        // gradle
        stage('Bulid Gradle') {
            agent any
            steps {
                echo 'Bulid Gradle'
                dir ('.'){
                    sh "chmod +x gradlew"
                    sh "./gradlew --debug build"
                }
            }
            post {
                failure {
                    error 'This pipeline stops here...(Build Gradle)'
                }
            }
        }

        stage('Docker Image Build') {
            agent any
            steps {
                print("==== Build Docker ====")
                sh "docker image build -t ${ECR_URL}:Backend${BUILD_NUMBER} ."
                // script {
                //     dockerImage = docker.build imageName
                // }
            }
            post {
                failure {
                    echo "Failed Docker Image Build"
                }
            }
        }

        stage('Image push to ECR') {
            agent any
            steps {
                print("==== Image push on ECR ====")
                // sh "docker push ${ECR_URL}:Backend${BUILD_NUMBER}"
                script {
                    docker.withRegistry(ECR_URL, registryCredential) {
                        dockerImage.push("Backend${BUILD_NUMBER}")
                    }
                }
                print("==== Docker remove Images ====")
                sh "docker image prune -a -f"
            }
            post {
                failure {
                    echo "Failed to push image (ECR)"
                }
            }
        }

        stage("K8S Manifest Update") {
            steps {
                print("==== Manifest Update ====")
                sh "whoami"
                sh "sh /home/ubuntu/cicd/BankImageUpdate.sh ${BUILD_NUMBER}"
            }
            post {
                failure {
                    echo "Error! K8S Manifest Update"
                }
            }
        }
    }
}