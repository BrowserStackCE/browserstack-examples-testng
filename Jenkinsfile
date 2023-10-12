import org.jenkinsci.plugins.pipeline.modeldefinition.Utils
node {
    try {
        properties([
        parameters([
            credentials(credentialType: 'com.browserstack.automate.ci.jenkins.BrowserStackCredentials', defaultValue: '', description: 'Select your BrowserStack Username', name: 'BROWSERSTACK_USERNAME', required: true),
            string(name: 'BROWSERSTACK_HUB_URL', defaultValue: '', description: 'Please add the selenium hub-url for your trial grid - You can find it here https://grid.browserstack.com/automation-console', required: true)
        ])
    ])

        stage('Pull code from Github') {
            dir('test') {
                git branch: 'turboscale-develop', changelog: false, poll: false, url: 'https://github.com/BrowserStackCE/browserstack-examples-testng'
            }
        }

        stage('Run Test') {
            browserstack(credentialsId: "${params.BROWSERSTACK_USERNAME}") {
                def user = "${env.BROWSERSTACK_USERNAME}"
                def hub = "${params.BROWSERSTACK_HUB_URL}"
                if (user.contains('-')) {
                    user = user.substring(0, user.lastIndexOf('-'))
                }
                sshagent(['samirans89_demo_jenkins']) {
                    withEnv(['BROWSERSTACK_USERNAME=' + user, 'BROWSERSTACK_HUB_URL=' + hub]) {
                    sh label: '', returnStatus: true, script:'''#!/bin/bash -l
                    cd test
                    mvn clean
                    mvn clean test -P bstack-turboscale-demo
                    '''
                    }
                }
                
            }
        }
        
        stage('Generate Report') {
            browserStackReportPublisher 'automate'
        }

    }
  catch (e) {
        currentBuild.result = 'FAILURE'
        echo e.toString()
        throw e
  } finally {
      
        // notifySlack(currentBuild.result)
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