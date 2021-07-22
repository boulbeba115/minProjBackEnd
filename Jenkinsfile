pipeline{
    agent any
    tools {
        jdk "JAVA"
        maven 'maven'
    }
    environment {
        REPO_URL= 'https://github.com/boulbeba115/minProjBackEnd.git'
        //Nexus Config
            NEXUS_VERSION = 'nexus3'
            NEXUS_PROTOCOL = 'http'
            NEXUS_URL = 'host.docker.internal:8081'
            NEXUS_REPOSITORY = 'backEnd'
            NEXUS_CREDENTIAL_ID = 'Nexus-credentials'
        //SonarCube
        SONARQUBE_URL = 'http://host.docker.internal'
        SONARQUBE_PORT = '9000'
    }
    stages {
        stage ('SCM'){
            steps{
            git credentialsId: 'GIT-CREDENTIALS',
            url: 'https://github.com/boulbeba115/minProjBackEnd.git'
            }
        }
        stage ('Maven Build'){
            steps{
            sh "mvn clean package -Dmaven.test.skip=true -X"
            }
        }
        stage ('Tests'){
            steps{
            sh "mvn test"
            }
        }
        stage('SonarQube') {
            steps {
            sh " mvn sonar:sonar -Dsonar.host.url=$SONARQUBE_URL:$SONARQUBE_PORT"
            }
        }
        stage('Deploy Artifact To Nexus') {
            steps {
                script {
                // Read POM xml file using 'readMavenPom' step , this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps
                pom = readMavenPom file: "pom.xml";
                // Find built artifact under target folder
                filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                // Print some info from the artifact found
                echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                // Extract the path from the File found
                artifactPath = filesByGlob[0].path;
                // Assign to a boolean response verifying If the artifact name exists
                artifactExists = fileExists artifactPath;
                if (artifactExists) {
                nexusArtifactUploader(
                nexusVersion: NEXUS_VERSION,
                protocol: NEXUS_PROTOCOL,
                nexusUrl: NEXUS_URL,
                groupId: pom.groupId,
                version: pom.version,
                repository: NEXUS_REPOSITORY,
                credentialsId: NEXUS_CREDENTIAL_ID,
                artifacts: [
                    // Artifact generated such as .jar, .ear and .war files.
                    [artifactId: pom.artifactId,
                    classifier: '',
                    file: artifactPath,
                    type: pom.packaging
                    ],
                    // Lets upload the pom.xml file for additional information for Transitive dependencies
                    [artifactId: pom.artifactId,
                    classifier: '',
                    file: "pom.xml",
                    type: "pom"
                    ]
                ]
                )
                } else {
                error "*** File: ${artifactPath}, could not be found";
                }
                }
            }
        }
        stage ('deploy'){
            steps{
            sh '''
            cp target/miniProj-0.0.1-SNAPSHOT.jar .
            docker-compose down
            docker-compose build
            docker-compose up -d '''
            }
        }
    }
}