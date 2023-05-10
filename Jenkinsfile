def ECR_URL = "015501295117.dkr.ecr.ap-northeast-2.amazonaws.com/bankapp/bankapp"

pipeline {
    agent any

    tools {
        jdk("OpenJDK 11")
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
        


    // gradle build
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
            steps {
                print('==== ECR Login ====')
                // sh "aws ecr get-login --region ap-northeast-2 --no-include-email | sh"
                sh "docker login --username AWS -p $(aws ecr get-login-password --region ap-northeast-2) 015501295117.dkr.ecr.ap-northeast-2.amazonaws.com/"
                print("==== Build Docker ====")
                sh "docker image build -t ${ECR_URL}:Backend${BUILD_NUMBER} ."
            }
            post {
                failure {
                    echo "Failed Docker Image Build"
                }
                success {
                    echo "Build success!"
                }
            }
        }

        stage('Image push to ECR') {
            steps {
                print("==== Image push on ECR ====")
                sh "docker push ${ECR_URL}:Backend${BUILD_NUMBER}"
                print("==== Docker remove Images ====")
                sh "docker image rm ${ECR_URL}:Backend${BUILD_NUMBER}"
            }
            post {
                failure {
                    echo "Failed to push image (ECR)"
                }
                success {
                    echo "Success! Image push on ECR"
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
                success {
                    echo "success! K8S Manifest Update"
                }
            }
        }
    }
}