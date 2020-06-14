
pipeline {
    agent any
    tools { 
        maven 'Maven 3.6.3' 
        //jdk 'jdk8' 
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                ''' 
            }
        }
        stage ('Build') {
            steps {
                script {
                    def pom = readMavenPom file: 'pom.xml'
                    // Now you have access to raw version string in pom.version
                    // Based on your versioning scheme, automatically calculate the next one
                    VERSION = pom.version.replaceAll('SNAPSHOT', BUILD_TIMESTAMP + "." + SHORTREV)
                }
                // We never build a SNAPSHOT
                // We explicitly set versions.
                sh """
                      mvn -B org.codehaus.mojo:versions-maven-plugin:2.5:set -DprocessAllModules -DnewVersion=${VERSION}  $MAVEN_OPTIONS
                  """
                sh """
                    mvn -B clean compile $MAVEN_OPTIONS
                  """

//        stage ('Build') {
//            steps {
//                echo 'This is a minimal pipeline.'
//            }
//        }
    }
}
