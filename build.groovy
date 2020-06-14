
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
            }
        }
        stage('Build') {
            steps {

                // For debugging purposes, it is always useful to print info
                // about build environment that is seen by shell during the build
                sh 'env'
                sh """
                    SHORTREV=`git rev-parse --short HEAD`
                  """

                script {
                    def pom = readMavenPom file: 'pom.xml'
                    // Now you have access to raw version string in pom.version
                    // Based on your versioning scheme, automatically calculate the next one
                    VERSION = pom.version.replaceAll('SNAPSHOT', BUILD_TIMESTAMP + "." + "${SHORTREV}")
                }
                // We never build a SNAPSHOT
                // We explicitly set versions.
                sh """
          mvn -B org.codehaus.mojo:versions-maven-plugin:3.6.3:set -DprocessAllModules -DnewVersion=${VERSION}  $MAVEN_OPTIONS
      """
                sh """
        mvn -B clean compile $MAVEN_OPTIONS
      """
            }


        }
    }
}