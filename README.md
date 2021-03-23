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


## About the tests in this repository

This repository contains the following Selenium tests:

| Module   | Test name                          | Description |
  | ---   | ---                                   | --- |
| E2E      | End to End Scenario                | This test scenario verifies successful product purchase lifecycle end-to-end. It demonstrates the [Page Object Model design pattern](https://www.browserstack.com/guide/page-object-model-in-selenium) and is also the default test executed in all the single test run profiles. |
| Login    | Login with given username          | This test verifies the login workflow with different types of valid login users. |
| Login    | Login as Locked User               | This test verifies the login workflow error for a locked user. |
| Offers   | Offers for Mumbai location     | This test mocks the GPS location for Mumbai and verifies that the product offers applicable for the Mumbai location are shown.   |
| Product  | Apply Apple Vendor Filter          | This test verifies that the Apple products are only shown if the Apple vendor filter option is applied. |
| Product  | Apply Lowest to Highest Order By   | This test verifies that the product prices are in ascending order when the product sort "Lowest to Highest" is applied. |
| User     | Login as User with no image loaded | This test verifies that the product images load for user: "image_not_loading_user" on the e-commerce application. Since the images do not load, the test case assertion fails.|
| User     | Login as User with existing Orders |  This test verifies that existing orders are shown for user: "existing_orders_user"  |
  
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

  [File name / path]
  [Configuration attribute] = [Configuration value]

  Gradle:

  [File name / path]
  [Configuration attribute] = [Configuration value]

## Test Reporting

- [Allure reports](#generating-allure-reports)

---

# On Premise / Self Hosted

This infrastructure points to running the tests on your own machine using a browser (e.g. Chrome) using the browser's driver executables (e.g. ChromeDriver for Chrome). #{ Selenium enables this functionality using WebDriver for many popular browsers.}

## Prerequisites

- For this infrastructure configuration (i.e on-premise), ensure that the ChromeDriver executable is placed in the `/src/test/resources/drivers` folder.

Note: The ChromeDriver version must match the Chrome browser version on your machine.

## Running Your Tests

### Run a specific test on your own machine

- How to run the test?

  To run the default test scenario (e.g. End to End Scenario) on your own machine, use the following command:

  Maven:
    ```sh
  mvn install -P on-prem
  ```

  Gradle:
    ```sh 
  <Gradle command>
  ```

  To run a specific test scenario, use the following command with the additional 'test-name' argument:

  Maven:
  ```sh
  mvn install -P on-prem -Dtest-name="<Test scenario name>"
  ```

  Gradle:
  ```sh
  <Gradle command>
  ```

  where,  the argument 'test-name' can be any Cucumber scenario name configured in this repository.

  E.g. "Login as username", "Login as Locked User", "Offers for mumbai geo-location" or any of the other test scenario names, as outlined in [About the tests in this repository](#About-the-tests-in-this-repository) section.

- Output

  This run profile executes a specific test scenario on a single browser instance on your own machine.


### Run the entire test suite on your own machine

- How to run the test?

  To run the entire test suite on your own machine, use the following command:

  Maven:
  ```sh
  mvn install -P on-prem-suite
  ```

  Gradle:
  ```sh
  <Gradle command>
  ```

- Output

  This run profile executes the entire test suite sequentially on a single browser, on your own machine.

  
---

# Docker

[Docker](https://docs.docker.com/get-started/overview/) is an open source platform that provides the ability to package and test applications in an isolated environment called containers.

## Prerequisites

- Install and start [Docker](https://docs.docker.com/get-docker/).
- Note: Docker should be running on the test machine. Ensure Docker Compose is installed as well.
- Run `docker-compose pull` from the current directory of the repository.

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
  mvn test -P docker
  ```

  Gradle:
    ```sh
  <Gradle command>
  ```

  To run a specific test scenario, use the following command with the additional 'test-name' argument:

  Maven:
  ```sh
  mvn install -P docker -Dtest-name="<Test scenario name>"
  ```

  Gradle:
  ```sh
  <Gradle command>
  ```

  where,  the argument 'test-name' can be any Cucumber scenario name configured in this repository.

  E.g. "Login as username", "Login as Locked User", "Offers for mumbai geo-location" or any of the other test scenario names, as outlined in [About the tests in this repository](#About-the-tests-in-this-repository) section.


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
  mvn test -P docker-parallel
  ```

  Gradle:
  ```sh
  <Gradle command>
  ```

   - After the tests are complete stop the Selenium grid by running the following command:

  ```sh
  docker-compose down
  ```

- Output

  This run profile executes the entire test suite in parallel on a single browser, deployed on a docker image.

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

  Alternatively, you can also hardcode username and access_key objects in the [caps.json](resources/conf/caps/caps.json) file.

Note:
- We have configured a list of test capabilities in the [caps.json](resources/conf/caps/caps.json) file. You can certainly update them based on your device / browser test requirements.
- The exact test capability values can be easily identified using the [Browserstack Capability Generator](https://browserstack.com/automate/capabilities)


## Running Your Tests

### Run a specific test on BrowserStack

In this section, we will run a single test on Chrome browser on Browserstack. To change test capabilities for this configuration, please refer to the `single` object in `caps.json` file.

- How to run the test?

   - To run the default test scenario (e.g. End to End Scenario) on your own machine, use the following command:

  Maven:
  ```sh
  mvn test -P bstack-single
  ```

  Gradle:
    ```sh
  <Gradle command>
  ```

  To run a specific test scenario, use the following command with the additional 'test-name' argument:
  Maven:
  ```sh
  mvn install -P bstack-single -Dtest-name="<Test scenario name>"
  ```

  Gradle:
  ```sh
  <Gradle command>
  ```

  where,  the argument 'test-name' can be any Cucumber scenario name configured in this repository.

  E.g. "Login as username", "Login as Locked User", "Offers for mumbai geo-location" or any of the other test scenario names, as outlined in [About the tests in this repository](#About-the-tests-in-this-repository) section.


- Output

  This run profile executes a single test on a single browser on BrowserStack. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.


### Run the entire test suite in parallel on a single BrowserStack browser

In this section, we will run the tests in parallel on a single browser on Browserstack. Refer to `single` object in `caps.json` file to change test capabilities for this configuration.

- How to run the test?

  To run the entire test suite in parallel on a single BrowserStack browser, use the following command:

  Maven:
  ```sh
  mvn install -P bstack-parallel
  ```
  Gradle:
    ```sh
  <Gradle command>
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
  mvn compile exec:java -P bstack-parallel-browsers
  ```

  Gradle:
  ```sh
  <Gradle command>
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
  mvn test -P bstack-local
  ```

  Gradle:
    ```sh
  <Gradle command>
  ```

  To run a specific test scenario, use the following command with the additional test-name argument:
  Maven:
  ```sh
  mvn install -P bstack-local -Dtest-name="<Test scenario name>"
  ```

  Gradle:
  ```sh
  <Gradle command>
  ```

  where,  the argument 'test-name' can be any Cucumber scenario name configured in this repository.

  E.g. "Login as username", "Login as Locked User", "Offers for mumbai geo-location" or any of the other test scenario names, as outlined in [About the tests in this repository](#About-the-tests-in-this-repository) section.


- Output

  This run profile executes a single test on an internally hosted web application on a single browser on BrowserStack. Please refer to your BrowserStack dashboard(https://automate.browserstack.com/) for test results.


### [Web application hosted on internal environment] Run the entire test suite in parallel on a single BrowserStack browser using BrowserStackLocal

In this section, we will run the test cases to test the internally hosted website in parallel on a single browser on Browserstack. Refer to the `single_local` object in `caps.json` file to change test capabilities for this configuration.

- How to run the test?

  To run the entire test suite in parallel on a single BrowserStack browser using BrowserStackLocal, use the following command:
  Maven:
  ```sh
  mvn install -P bstack-local-parallel
  ```

  Gradle:
  ```sh
  <Gradle command>
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
  mvn compile exec:java -P bstack-local-parallel-browsers
  ```

  Gradle:
    ```sh
  <Gradle command>
  ```

- Output

  This run profile executes the entire test suite on an internally hosted web application on multiple browsers on BrowserStack. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.

- Note: By default, this execution would run maximum 5 test threads in parallel on BrowserStack. Refer to the section ["Configuring the maximum parallel test threads for this repository"](#Configuring-the-maximum-parallel-test-threads-for-this-repository) for updating the parallel thread count based on your requirements.

## Generating Allure Reports

- Generate Report using the following command: `mvn allure:generate`
- Serve the Allure report on a server: `mvn allure:serve`

## Additional Resources

- View your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)
- Documentation for writing [Automate test scripts in Java](https://www.browserstack.com/automate/java)
- Customizing your tests capabilities on BrowserStack using our [test capability generator](https://www.browserstack.com/automate/capabilities)
- [List of Browsers & mobile devices](https://www.browserstack.com/list-of-browsers-and-platforms?product=automate) for automation testing on BrowserStack #{ Replace link for non-Selenium frameworks. }
- [Using Automate REST API](https://www.browserstack.com/automate/rest-api) to access information about your tests via the command-line interface
- Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)
- For testing public web applications behind IP restriction, [Inbound IP Whitelisting](https://www.browserstack.com/local-testing/inbound-ip-whitelisting) can be enabled with the [BrowserStack Enterprise](https://www.browserstack.com/enterprise) offering

## Observations

<Placeholder section for any other technical or general observations specific to the repository. If none, please remove the section>

## Open Issues

<Placeholder section for any known open issues (some test known to not work or is flaky). If none, please remove the section>
