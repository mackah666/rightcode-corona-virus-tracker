
// Define variable
def SHORTREV = null;

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
                 script {
                        def pom = readMavenPom file: 'pom.xml'
                      //Now you have access to raw version string in pom.version
                       // Based on your versioning scheme, automatically calculate the next one
                       VERSION = pom.version.replaceAll('SNAPSHOT', BUILD_TIMESTAMP + "." + "${SHORTREV}")
                    }

                sh 'mvn -B -DskipTests -DnewVersion=${VERSION} clean package'
            }
        }

    }
}