pipeline {
    agent any

    stages {
        stage('Validate tools'){
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Build and Publish'){
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'github-packages-maven',
                    usernameVariable: 'GITHUB_USER',
                    passwordVariable: 'GITHUB_TOKEN'
                )]){
                   sh '''
                       mkdir -p ~/.m2

                       cat > ~/.m2/settings.xml <<EOF
                       <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
                                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                 xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
                           <servers>
                               <server>
                                   <id>github</id>
                                   <username>${GITHUB_USER}</username>
                                   <password>${GITHUB_TOKEN}</password>
                               </server>
                           </servers>
                       </settings>
                       EOF

                       mvn clean deploy
                   '''
               }
            }
        }

    }

}