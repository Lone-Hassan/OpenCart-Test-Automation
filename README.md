# OpenCart Test Automation

## Project Overview

This project is an automated testing framework for the OpenCart web application, using **Maven**, **Java**, **Selenium WebDriver**, **TestNG**, and **Cucumber**. The framework is designed to support behavior-driven development (BDD) and ensure the quality of the OpenCart application through automated tests.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Running Tests](#running-tests)
- [Reporting](#reporting)
- [Contributing](#contributing)
- [License](#license)

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java** (version 17 or higher)
- **Maven** (version 3.9.6 or higher)
- **Git** (version control system, git version 2.43.0.windows.1)
- **WebDriver** (e.g., ChromeDriver)
- **Eclipse IDE** (optional, for development)
- **opencart-4.0.2.3** Download from https://www.opencart.com/index.php?route=cms/download
## Project Structure


### **Directory and File Descriptions**

- **`src/main/java/`**
  - **`OpenCart.AbstractComponents`** Contains the helper classes for the project.
    - **`AbstractComponents.java`**:  Contains all Selenium related common actions on each page for example different kind of explicit waits and key presses etc.
    - **`ExtentReporterNG.java`**: For generating Extent reporter object.
    - **`openShopDB.java`**: For interaction with openShop mysql Database.
  - **`OpenCart.PageObjects`**: Implements the Page Object Model (POM) pattern, where each class represents a page in the web application and provides methods to interact with elements on that page.
  - **`resources/globalData.properties`**:  configuration file, such as environment-specific settings or data required for running tests.
- **`src/test/java/`**
  - **`OpenCart.stepDefinitions/`**: Contains Cucumber step definitions that map Gherkin steps in feature files to code and contains TestNG runner classes to configure and execute Cucumber tests.
  - **`Cucumber/`**: Contains Cucumber feature files written in Gherkin syntax, which describe test scenarios and expected behavior of the application.
  - **`OpenCart/`**: Contains all the TestNG classes which describe test cases for different features.
  - **`OpenCart.resources/`**:Includes utility classes for common functions used across test cases, such as WebDriver setup, dataprovide or Listeners.
- **`testng.xml`**: Configuration file for TestNG to specify test suites, groups, and other TestNG-specific configurations.
- **`pom.xml`**
  - **Purpose:** The Maven Project Object Model file that defines the project’s dependencies, plugins, and build configurations. It is used by Maven to build the project and manage its dependencies.
- **`reports/`**
  - **Purpose:** To store reports generated by extent reporter and screenshorts at test failures.
- **`target/*`**
  - **Purpose:** For cucmber and surefire reports and other maven stuff.
- **`test-output/*`**
  - **Purpose:** Directory for testNG html reports and other stuff.
- **`README.md`**
  - **Purpose:** Project documentation providing an overview of the project, instructions for setup and running tests, and other relevant information.

This layout helps in organizing the code and configuration files in a structured way, making it easier to maintain and extend the test automation framework.

## Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/Lone-Hassan/OpenCart-Test-Automation.git
    ```

2. **Navigate to the project directory:**

    ```bash
    cd OpenCart-Test-Automation
    ```

3. **Install dependencies:**

    Use Maven to install all required dependencies:

    ```bash
    mvn clean install
    ```

## Running Tests

You can run the Cucumber tests using TestNG. Below are a few ways to execute the tests:

1. **Using Maven:**

    To run the tests, execute the following command:

    ```bash
    mvn test
    ```

2. **Using TestNG in Eclipse/IDE:**

    - Right-click on the `testng.xml` file.
    - Select **Run As** > **TestNG Suite**.

3. **Running specific Cucumber scenarios:**

    - Modify the `@CucumberOptions` in the runner class to include specific tags or feature files.
    - Run the desired runner class.

## Reporting

The framework generates test reports using TestNG and Cucumber:

1. **TestNG Report:**
   - A default TestNG report is generated in the `test-output` directory after each test run.

2. **Cucumber HTML Report:**
   - A Cucumber HTML report is generated in the `target/` directory.

3. **Other Reports:**
   - Additional reporting tools or integrations can be configured as needed.

## Contributing

Contributions are welcome! Please follow these steps to contribute:

1. **Fork the repository.**
2. **Create a new branch** (`git checkout -b feature-branch`).
3. **Make your changes** and commit them (`git commit -m 'Add new feature'`).
4. **Push to the branch** (`git push origin feature-branch`).
5. **Open a pull request** on GitHub.

Please ensure that your code adheres to the project's coding standards and passes all tests before submitting a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

