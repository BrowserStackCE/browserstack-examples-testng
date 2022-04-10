![Logo](https://www.browserstack.com/images/static/header-logo.jpg)

# BrowserStack Examples TestNG

## Introduction

TestNG is a testing framework inspired from JUnit and NUnit but introducing some new functionalities that make it more powerful.

This BrowserStack Example repository demonstrates a Selenium test framework written in TestNG with parallel testing capabilities. The Selenium test scripts are written for the open source [BrowserStack Demo web application](https://bstackdemo.com) ([Github](https://github.com/browserstack/browserstack-demo-app)). This BrowserStack Demo App is an e-commerce web application which showcases multiple real-world user scenarios. The app is bundled with offers data, orders data and products data that contains everything you need to start using the app and run tests out-of-the-box.

The Selenium tests are run on different platforms like on-prem, docker and BrowserStack using various run configurations and test capabilities.

---

## Repository setup

- Clone the repository

- Ensure you have the following dependencies installed on the machine
   - Java >= 8
   - Maven >= 3.1+

  Maven:
    ```sh
     mvn install -DskipTests
    ```

  Gradle:

    ```sh
     gradle clean build
    ```


## About the tests in this repository

This repository contains the following Selenium tests:

| Module   | Test name                          | Description |
  | ---   | ---                                   | --- |
| E2E      | OrderTest                       | This test scenario verifies successful product purchase lifecycle end-to-end. It demonstrates the [Page Object Model design pattern](https://www.browserstack.com/guide/page-object-model-in-selenium) and is also the default test executed in all the single test run profiles. |
| Login    | LoginTest                       | This test verifies the login workflow with different types of valid login users. |
| Login    | LoginFailTest                   | This test verifies the login workflow error. |
| Login    | LoginDataDrivenTest             | This test verifies the login for all error cases in a datadriven way |
| Login    | LoginDataDrivenReadFromCSVTest  | This test verifies the login for all error cases in a datadriven way with CSV-file  |
| Login    | LoginRequestedTest              | This test verifies that the login page is shown when you access the favourites page with being logged in  |
| Offers   | OfferTest                       | This test mocks the GPS location for Singapore and verifies that the product offers applicable for the Singapore location are shown.   |
| Product  | FilterTest                      | This test verifies that both filter options are working |
| User     | UserTest                        | The first test verifies that the product images load for user: "image_not_loading_user" on the e-commerce application. Since the images do not load, the test case assertion fails. The second test verifies that existing orders are shown for user: "existing_orders_user" |
  
---


## Test infrastructure environments

- [On-premise/self-hosted](#on-premise-self-hosted)
- [Docker](#docker)
- [BrowserStack](#browserstack)


## Configuring the maximum parallel test threads for this repository

For all the parallel run configuration profiles, you can configure the maximum parallel test threads by changing the settings below.

- Docker

  [File name / path]
  [Configuration attribute] = [Configuration value]

- BrowserStack

  Maven:

  `pom.xml`
  ```xml
  <testng.parallel>classes</testng.parallel>
  <testng.threadCount>5</testng.threadCount>
  ```

  Gradle:

  `gradle.properties`
  ```sh
  testngParallel=classes
  testngThreadCount=5
  ```

## Test Reporting

- [Allure reports](#generating-allure-reports)

---

# On Premise / Self Hosted

This infrastructure points to running the tests on your own machine using a browser (e.g. Chrome) using the browser's driver executables (e.g. ChromeDriver for Chrome). Selenium enables this functionality using WebDriver for many popular browsers.

## Prerequisites

- For this infrastructure configuration (i.e on-premise), ensure that the ChromeDriver executable is placed in the `/src/test/resources/drivers` folder.

Note: The ChromeDriver version must match the Chrome browser version on your machine.

## Running Your Tests

### Run a specific test on your own machine

- How to run the test?

  To run the default test scenario (e.g. End to End Scenario) on your own machine, use the following command:

  Maven:
    ```sh
  mvn clean test -P on-prem
  ```

  Gradle:
    ```sh 
  gradle clean on-prem
  ```

  To run a specific test scenario, use the following command with the additional 'test' argument:

  Maven:
  ```sh
  mvn clean test -P on-prem -Dtest=LoginDataDrivenTest

  ```

  Gradle:
  ```sh
  gradle clean on-prem -Ptest-name=LoginDataDrivenTest
  ```

  where, the argument `test` or `test-name` can be any testclass implemented this repository.

- Output

  This run profile executes a specific test scenario on a single browser instance on your own machine.


### Run the entire test suite on your own machine

- How to run the test?

  To run the entire test suite on your own machine, use the following command:

  Maven:
  ```sh
  mvn clean test -P on-prem-suite
  ```

  Gradle:
  ```sh
  gradle clean on-prem-suite
  ```

- Output

  This run profile executes the entire test suite sequentially on a single browser, on your own machine.

  
---

# Docker

[Docker](https://docs.docker.com/get-started/overview/) is an open source platform that provides the ability to package and test applications in an isolated environment called containers.

## Prerequisites

- Install and start [Docker](https://docs.docker.com/get-docker/).
- Note: Docker should be running on the test machine. Ensure Docker Compose is installed as well.

- Run `docker-compose pull` from the `docker` directory of the repository.

## Running Your Tests

### Run a specific test on the docker infrastructure

- How to run the test?

   - Start the Docker by running the following command:

  ```sh
  docker-compose up -d
  ```

   - To run the default test scenario (e.g. End to End Scenario) on your own machine, use the following command:

  Maven:
  ```sh
  mvn clean test -P docker
  ```

  Gradle:
    ```sh
  gradle clean docker
  ```

  To run a specific test scenario, use the following command with the additional 'test-name' argument:

  Maven:
  ```sh
  mvn clean test -P docker -Dtest=LoginDataDrivenTest
  ```

  Gradle:
  ```sh
  gradle clean docker -Ptest-name=LoginDataDrivenTest
  ```

  where,  the argument `test` or `test-name` can be any testclass implemented in this repository.


- After tests are complete, you can stop the Docker by running the following command:

  ```sh
  docker-compose down
  ```

- Output

  This run profile executes a specific test scenario on a single browser deployed on a docker image.


### Run the entire test suite in parallel using Docker

- How to run the test?

   - Start the docker image first by running the following command:

  ```sh
  docker-compose up -d
  ```

   - To run the entire test suite in parallel on the docker image, use the following command:

  Maven:
  ```sh
  mvn clean test -P docker-parallel
  ```

  Gradle:
  ```sh
  gradle clean docker-parallel
  ```

   - After the tests are complete stop the Selenium grid by running the following command:

  ```sh
  docker-compose down
  ```

- Output

  This run profile executes the entire test suite in parallel across multiple instances of the same browser, deployed on a docker image.

- Note: By default, this execution would run maximum 5 test threads in parallel on Docker. Refer to the section ["Configuring the maximum parallel test threads for this repository"](#Configuring-the-maximum-parallel-test-threads-for-this-repository) for updating the parallel thread count based on your requirements.

---

# BrowserStack

[BrowserStack](https://browserstack.com) provides instant access to 2,000+ real mobile devices and browsers on a highly reliable cloud infrastructure that effortlessly scales as testing needs grow.

## Prerequisites

- Create a new [BrowserStack account](https://www.browserstack.com/users/sign_up) or use an existing one.
- Identify your BrowserStack username and access key from the [BrowserStack Automate Dashboard](https://automate.browserstack.com/) and export them as environment variables using the below commands.

   - For \*nix based and Mac machines:

  ```sh
  export BROWSERSTACK_USERNAME=<browserstack-username> &&
  export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```

   - For Windows:

  ```shell
  set BROWSERSTACK_USERNAME=<browserstack-username>
  set BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```

  Alternatively, you can also hardcode username and access_key objects in the [test_caps.json](resources/conf/capabilities/test_caps.json) file.

Note:
- We have configured a list of test capabilities in the [test_caps.json](resources/conf/capabilities/test_caps.json) file. You can certainly update them based on your device / browser test requirements.
- The exact test capability values can be easily identified using the [Browserstack Capability Generator](https://browserstack.com/automate/capabilities)


## Running Your Tests

### Run a specific test on BrowserStack

In this section, we will run a single test on Chrome browser on Browserstack. To change test capabilities for this configuration, please refer to the `single` object in `caps.json` file.

- How to run the test?

   - To run the default test scenario (e.g. End to End Scenario) on your own machine, use the following command:

  Maven:
  ```sh
  mvn clean test -P bstack-single
  ```

  Gradle:
    ```sh
  gradle clean bstack-single
  ```

  To run a specific test scenario, use the following command with the additional 'test-name' argument:
  Maven:
  ```sh
  mvn clean test -P bstack-single -Dtest=LoginDataDrivenTest
  ```

  Gradle:
  ```sh
  gradle clean bstack-single -Ptest-name=LoginDataDrivenTest
  ```

  where, the argument `test` or `test-name` can be any testclass implemented in this repository.


- Output

  This run profile executes a single test on a single browser on BrowserStack. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.


### Run the entire test suite in parallel on a single BrowserStack browser

In this section, we will run the tests in parallel on a single browser on Browserstack. Refer to `single` object in `test_caps.json` file to change test capabilities for this configuration.

- How to run the test?

  To run the entire test suite in parallel on a single BrowserStack browser, use the following command:

  Maven:
  ```sh
  mvn clean test -P bstack-parallel
  ```
  Gradle:
    ```sh
  gradle clean bstack-parallel
  ```


- Output

  This run profile executes the entire test suite in parallel on a single BrowserStack browser. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.

   - Note: By default, this execution would run maximum 5 test threads in parallel on BrowserStack. Refer to the section ["Configuring the maximum parallel test threads for this repository"](#Configuring-the-maximum-parallel-test-threads-for-this-repository) for updating the parallel thread count based on your requirements.


### Run the entire test suite in parallel on multiple BrowserStack browsers

In this section, we will run the tests in parallel on multiple browsers on Browserstack. Refer to the `parallel` object in `caps.json` file to change test capabilities for this configuration.

- How to run the test?

  To run the entire test suite in parallel on multiple BrowserStack browsers, use the following command:

  Maven:
  ```sh
  mvn clean test -P bstack-parallel-browsers
  ```

  Gradle:
  ```sh
  gradle clean bstack-parallel-browsers
  ```

### [Web application hosted on internal environment] Running your tests on BrowserStack using BrowserStackLocal

#### Prerequisites

- Clone the [BrowserStack demo application](https://github.com/browserstack/browserstack-demo-app) repository.
  ```sh
  git clone https://github.com/browserstack/browserstack-demo-app
  ``` 
- Please follow the README.md on the BrowserStack demo application repository to install and start the dev server on localhost.
- In this section, we will run a single test case to test the BrowserStack Demo app hosted on your local machine i.e. localhost. Refer to the `single_local` object in `caps.json` file to change test capabilities for this configuration.
- Note: You may need to provide additional BrowserStackLocal arguments to successfully connect your localhost environment with BrowserStack infrastructure. (e.g if you are behind firewalls, proxy or VPN).
- Further details for successfully creating a BrowserStackLocal connection can be found here:

   - [Local Testing with Automate](https://www.browserstack.com/local-testing/automate)
   - [BrowserStackLocal Java GitHub](https://github.com/browserstack/browserstack-local-java)


### [Web application hosted on internal environment] Run a specific test on BrowserStack using BrowserStackLocal

- How to run the test?

   - To run the default test scenario (e.g. End to End Scenario) on a single BrowserStack browser using BrowserStackLocal, use the following command:

  Maven:
  ```sh
  mvn clean test -P bstack-local
  ```

  Gradle:
    ```sh
  gradle clean bstack-local
  ```

  To run a specific test scenario, use the following command with the additional test-name argument:
  Maven:
  ```sh
  mvn clean test -P bstack-local -Dtest=LoginDataDrivenTest
  ```

  Gradle:
  ```sh
  gradle clean bstack-local -Ptest-name=LoginDataDrivenTest
  ```

  where, the argument `test` or `test-name` can be any testclass implemented in this repository.


- Output

  This run profile executes a single test on an internally hosted web application on a single browser on BrowserStack. Please refer to your BrowserStack dashboard(https://automate.browserstack.com/) for test results.


### [Web application hosted on internal environment] Run the entire test suite in parallel on a single BrowserStack browser using BrowserStackLocal

In this section, we will run the test cases to test the internally hosted website in parallel on a single browser on Browserstack. Refer to the `single_local` object in `caps.json` file to change test capabilities for this configuration.

- How to run the test?

  To run the entire test suite in parallel on a single BrowserStack browser using BrowserStackLocal, use the following command:
  Maven:
  ```sh
  mvn clean test -P bstack-local-parallel
  ```

  Gradle:
  ```sh
  gradle clean bstack-local-parallel
  ```

- Output

  This run profile executes the entire test suite on an internally hosted web application on a single browser on BrowserStack. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.

- Note: By default, this execution would run maximum 5 test threads in parallel on BrowserStack. Refer to the section ["Configuring the maximum parallel test threads for this repository"](#Configuring-the-maximum-parallel-test-threads-for-this-repository) for updating the parallel thread count based on your requirements.

### [Web application hosted on internal environment] Run the entire test suite in parallel on multiple BrowserStack browser using BrowserStackLocal

In this section, we will run the test cases to test the internally hosted website in parallel on multiple browsers on Browserstack. Refer to the `parallel_local` object in `caps.json` file to change test capabilities for this configuration.

- How to run the test?

  To run the entire test suite in parallel on a single BrowserStack browser using BrowserStackLocal, use the following command:

  Maven:
  ```sh
  mvn clean test -P bstack-local-parallel-browsers
  ```

  Gradle:
    ```sh
  gradle clean bstack-local-parallel-browsers
  ```

- Output

  This run profile executes the entire test suite on an internally hosted web application on multiple browsers on BrowserStack. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.

- Note: By default, this execution would run maximum 5 test threads in parallel on BrowserStack. Refer to the section ["Configuring the maximum parallel test threads for this repository"](#Configuring-the-maximum-parallel-test-threads-for-this-repository) for updating the parallel thread count based on your requirements.

## Generating Allure Reports

- Generate Report using the following command: 

  Maven:
  ```sh

  mvn allure:report
  ```

  Gradle:
    ```sh
  gradle allureReport
  ```

- Serve the Allure report on a server: 

  Maven:
  ```sh
  mvn allure:serve
  ```

  Gradle:
    ```sh
  gradle allureServe
  ```

# Percy

[Percy](https://percy.io) provides a visual review platform.

## Prerequisites

- Sign up with your BrowserStack account, or create a new [BrowserStack account](https://www.browserstack.com/users/sign_up).
- Create a new Percy project.
- Go to the Project's Settings page, identify your Percy Token, and export them as environment variables using the following commands.

### Installation

1. Install the Percy CLI.

```shell
npm install @percy/cli
```

### Export the Percy Token

- For \*nix based and Mac machines:

  ```sh
  export PERCY_TOKEN=[your-project-token]
  ```

- For Windows:

  ```shell
  set PERCY_TOKEN=[your-project-token]
  ```

## Run your first Visual Test

In this section, we will run the test cases to detect the visual differences. We run the test first, then toggle the property that instructs our test to change some CSS.

1. Run the test.
   Maven:
  ```sh
  npx percy exec -- mvn clean test -P percy
  ```

Gradle:
  ```sh
  npx percy exec -- gradle clean percy
  ```

2. Set the `changeCSS` property in the `src/test/java/com/browserstack/test/login/LoginVisuaalTest` directory to true.

3. Run the test again.

4. View your Project in the Percy dashboard and verify the differences.

## Run the Ignore Region Test

In this section, we will run the test that ignores a specific area of a webpage. In a real-world situation, you can think of dynamic content and advertisements where this use case applies.

1. Run the test.
   Maven:
  ```sh
  npx percy exec -- mvn clean test -P percy-ignore
  ```
Gradle:
  ```sh
  npx percy exec -- gradle clean percy-ignore
  ```
2. View your Project in the Percy dashboard and verify the differences.


## Additional Resources

- View your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)
- Documentation for writing [Automate test scripts in Java](https://www.browserstack.com/automate/java)
- Customizing your tests capabilities on BrowserStack using our [test capability generator](https://www.browserstack.com/automate/capabilities)
- [List of Browsers & mobile devices](https://www.browserstack.com/list-of-browsers-and-platforms?product=automate) for automation testing on BrowserStack
- [Using Automate REST API](https://www.browserstack.com/automate/rest-api) to access information about your tests via the command-line interface
- Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)
- For testing public web applications behind IP restriction, [Inbound IP Whitelisting](https://www.browserstack.com/local-testing/inbound-ip-whitelisting) can be enabled with the [BrowserStack Enterprise](https://www.browserstack.com/enterprise) offering