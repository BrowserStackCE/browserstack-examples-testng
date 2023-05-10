import org.jenkinsci.plugins.pipeline.modeldefinition.Utils
node {
    try {
        properties([
        parameters([
            credentials(credentialType: 'com.browserstack.automate.ci.jenkins.BrowserStackCredentials', defaultValue: '', description: 'Select your BrowserStack Username', name: 'BROWSERSTACK_USERNAME', required: true),
            choice(
                choices: [
                    'bstack-single',
                    'bstack-parallel',
                    'bstack-parallel-browsers',
                    'bstack-local',
                    'bstack-local-parallel',
                    'bstack-local-parallel-browsers'
                ],
                description: 'Select the test you would like to run',
                name: 'TEST_TYPE'
            )
        ])
    ])

        stage('Pull code from Github') {
            dir('test') {
                git branch: 'main', changelog: false, poll: false, url: 'https://github.com/browserstack/browserstack-examples-testng'
            }
        }

        stage('Run Test') {
            browserstack(credentialsId: "${params.BROWSERSTACK_USERNAME}") {
                def user = "${env.BROWSERSTACK_USERNAME}"
                if (user.contains('-')) {
                    user = user.substring(0, user.lastIndexOf('-'))
                }
                withEnv(['BROWSERSTACK_USERNAME=' + user]) {
                    sh label: '', returnStatus: true, script:'''#!/bin/bash -l
                cd test
                ln src/test/resources/conf/capabilities/${TEST_TYPE}.yml browserstack.yml
                mvn clean test -P ${TEST_TYPE} 
                
                export TEST_MANAGEMENT_API_TOKEN="8a1598ba-531e-4264-ad52-e8d73d1be900"
                export TEST_MANAGEMENT_PROJECT_NAME="Webinar"
                export JUNIT_XML_FILE_PATH="/var/lib/jenkins/workspace/browserstack-testng-webinar/test/target/surefire-reports/junitreports/*.xml"
                export TEST_RUN_NAME="test-2"
                export USER_EMAIL="arpit+demo@browserstack.com"

                echo "Hello Testing"  
                pwd

                curl -k -X POST https://test-management.browserstack.com/api/v1/import/results/xml/junit \
                -H "API-TOKEN:$TEST_MANAGEMENT_API_TOKEN" \
                -F project_name="$TEST_MANAGEMENT_PROJECT_NAME" \
                -F "file_path=@$JUNIT_XML_FILE_PATH" \
                -F test_run_name="$TEST_RUN_NAME" \
                -F user_email=$USER_EMAIL
                
                '''
                }
            }
        }
        
        stage('Generate Report') {
            browserStackReportPublisher 'automate'
        }

    }
  catch (e) {
        currentBuild.result = 'FAILURE'
        echo e
        throw e
  } finally {
      
        notifySlack(currentBuild.result)
    }
}
def notifySlack(String buildStatus = 'STARTED') {
    // Build status of null means success.
    buildStatus = buildStatus ?: 'SUCCESS'

    def color

    if (buildStatus == 'STARTED') {
        color = '#D4DADF'
    } else if (buildStatus == 'SUCCESS') {
        color = '#BDFFC3'
   } else if (buildStatus == 'UNSTABLE') {
        color = '#FFFE89'
   } else {
        color = '#FF9FA1'
    }

    def msg = "${buildStatus}: `${env.JOB_NAME}` #${env.BUILD_NUMBER}:\n${env.BUILD_URL}"
    if (buildStatus != 'STARTED' && buildStatus != 'SUCCESS') {
        slackSend(color: color, message: msg)
    }
}
