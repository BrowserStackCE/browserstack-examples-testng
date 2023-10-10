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

| Module  | Test name                      | Description                                                                                                                                                                                                                                                                       |
| ------- | ------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| E2E     | OrderTest                      | This test scenario verifies successful product purchase lifecycle end-to-end. It demonstrates the [Page Object Model design pattern](https://www.browserstack.com/guide/page-object-model-in-selenium) and is also the default test executed in all the single test run profiles. |
| Login   | LoginTest                      | This test verifies the login workflow with different types of valid login users.                                                                                                                                                                                                  |
| Login   | LoginFailTest                  | This test verifies the login workflow error.                                                                                                                                                                                                                                      |
| Login   | LoginDataDrivenTest            | This test verifies the login for all error cases in a datadriven way                                                                                                                                                                                                              |
| Login   | LoginDataDrivenReadFromCSVTest | This test verifies the login for all error cases in a datadriven way with CSV-file                                                                                                                                                                                                |
| Login   | LoginRequestedTest             | This test verifies that the login page is shown when you access the favourites page with being logged in                                                                                                                                                                          |
| Offers  | OfferTest                      | This test mocks the GPS location for Singapore and verifies that the product offers applicable for the Singapore location are shown.                                                                                                                                              |
| Product | FilterTest                     | This test verifies that both filter options are working                                                                                                                                                                                                                           |
| User    | UserTest                       | The first test verifies that the product images load for user: "image_not_loading_user" on the e-commerce application. Since the images do not load, the test case assertion fails. The second test verifies that existing orders are shown for user: "existing_orders_user"      |

---

### Run the entire test suite in parallel on multiple BrowserStack browsers

In this section, we will run the tests in parallel on multiple browsers on Browserstack. Refer to the `bstack-parallel-browsers.yml` file to change test capabilities for this configuration.

- Set Environment Variable to pick desired YML file:

    - For \*nix based and Mac machines:

   ```sh
    
    export BROWSERSTACK_CONFIG_FILE="src/test/resources/conf/capabilities/browserstack.yml"
    ```
    - For Windows:
    ```sh
    set BROWSERSTACK_CONFIG_FILE="src\test\resources\conf\capabilities\browserstack.yml"
    ```

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

## Run your first Visual Test

In this section, we will run the test cases to detect the visual differences. We run the test first, then toggle the property that instructs our test to change some CSS.

1. Run the test.
   Maven:

```sh
npx percy exec --  mvn clean test -P bstack-parallel-browsers
```

Gradle:

```sh
npx percy exec -- gradle clean test -P bstack-parallel-browsers
```

2. Set the `changeCSS` property in the `src/test/java/com/browserstack/test/login/LoginVisuaalTest` directory to true.

3. Run the test again.

4. View your Project in the Percy dashboard and verify the differences.



2. View your Project in the Percy dashboard and verify the differences.

## Additional Resources

- View your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)
- Documentation for writing [Automate test scripts in Java](https://www.browserstack.com/automate/java)
- Customizing your tests capabilities on BrowserStack using our [test capability generator](https://www.browserstack.com/automate/capabilities)
- [List of Browsers & mobile devices](https://www.browserstack.com/list-of-browsers-and-platforms?product=automate) for automation testing on BrowserStack
- [Using Automate REST API](https://www.browserstack.com/automate/rest-api) to access information about your tests via the command-line interface
- Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)
- For testing public web applications behind IP restriction, [Inbound IP Whitelisting](https://www.browserstack.com/local-testing/inbound-ip-whitelisting) can be enabled with the [BrowserStack Enterprise](https://www.browserstack.com/enterprise) offering
