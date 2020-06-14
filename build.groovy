
// Define variable
def SHORTREV = null;
def VERSION = null;

pipeline {
    agent any
    tools {
        maven 'Maven 3.6.3'
        //jdk 'jdk8' 
    }
    environment {
        MAVEN_OPTIONS = ' -Denv.build-timestamp=${BUILD_TIMESTAMP} ...'
    }
    stages {
        stage('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage('mock Build') {
            steps {
                echo 'This is a minimal pipeline.'
                sh 'env'
                sh """
                  SHORTREV=`git rev-parse --short HEAD`
                  """
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Unit Tests') {
            // We have seperate stage for tests so
            // they stand out in grouping and visualizations
            steps {
                sh 'mvn -B test'
            }
        }
        stage('ssh') {
            steps {
//                def remote = [: ]
//                remote.name = "k8master"
//                remote.host = "192.168.1.99"
//                remote.port = 22
//                remote.allowAnyHosts = true
//
//                withCredentials([usernamePassword(credentialsId: 'pi-server',passwordVariable: 'password', usernameVariable: 'userName')]) {
//
//                    remote.user = userName
//                    remote.password = password
//
//                    //sshGet(remote: remote, from: 'abc.sh', into: 'abc.sh', override: true)
//                    sshCommand remote: remote, command: "ls -lrt"
//                    sshCommand remote: remote, command: "ps -fea|grep -i java"
////                    sshCommand remote: remote, command: "pkill -9 -f coronavirus-tracker-0.0.1-SNAPSHOT.jar"
////                    sshCommand remote: remote, command: "ps -fea|grep -i java"
////                    sshCommand remote: remote, command: "nohup java -jar coronavirus-tracker-0.0.1-SNAPSHOT.jar"
////                    sshCommand remote: remote, command: "ps -fea|grep -i java"
//                }

            }

        }

    }


    post {
        always {
            archiveArtifacts artifacts: 'target//*.jar', fingerprint: true
            //junit 'build/reports/**/*.xml'
        }
    }
}