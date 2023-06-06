import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

pipeline {
    agent any
    
    environment {
        BROWSERSTACK_CREDENTIALS = credentials('com.browserstack.automate.ci.jenkins.BrowserStackCredentials')
        BROWSERSTACK_USERNAME = "${BROWSERSTACK_CREDENTIALS}"
    }
    
    stages {
        stage('Pull code from GitHub') {
            steps {
                dir('test') {
                    git branch: 'webinar',
                        url: 'https://github.com/browserstack/browserstack-examples-testng'
                }
            }
        }
        
        stage('Run Tests') {
            steps {
                withCredentials([usernamePassword(credentialsId: BROWSERSTACK_CREDENTIALS, usernameVariable: 'BROWSERSTACK_USERNAME', passwordVariable: 'BROWSERSTACK_ACCESS_KEY')]) {
                    sshagent(['samirans89_demo_jenkins']) {
                        dir('test') {
                            sh '''
                                #!/bin/bash -e

                                git checkout webinar
                                mvn clean test -P parallel
                                export PERCY_TOKEN=${PERCY_TOKEN} 
                                export PERCY_BRANCH=testing
                                export PERCY_TARGET_BRANCH=main
                                source ~/.bashrc
                                nvm use 16
                                npm install
                                export BROWSERSTACK_CONFIG_FILE=src/test/resources/conf/capabilities/bstack-single.yml
                                npm run percy:test
                            '''
                        }
                    }
                }
            }
        }
        
        stage('Generate Report') {
            steps {
                script {
                    browserStackReportPublisher 'automate'
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        failure {
            script {
                echo "Pipeline Failed: ${env.JOB_NAME} [${env.BUILD_NUMBER}]"
            }
        }
    }
}
