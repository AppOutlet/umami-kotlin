# Contributing to umami-kotlin-sdk

First off, thank you for considering contributing to `umami-kotlin-sdk`\! It’s people like you that make the open-source community such a fantastic place. We're thrilled you're here and welcome all positive contributions, whether it's reporting a bug, submitting a fix, proposing a new feature, or improving documentation.

This document provides a set of guidelines to help you get started. Our goal is to make the contribution process as easy and transparent as possible for everyone.

## 📜 Code of Conduct

To ensure a welcoming and inclusive environment, we have a [**Code of Conduct**](CODE_OF_CONDUCT.md) that we expect all contributors to adhere to. Please take a moment to read it before participating.

## 🤔 How Can I Contribute?

There are many ways to contribute to the project.

### 🐛 Reporting Bugs

If you encounter a bug, please [**open a new issue**](https://github.com/AppOutlet/umami-kotlin-sdk/issues/new) on our GitHub repository. A great bug report is detailed and reproducible. Please include:

  * A clear and descriptive title.
  * The version of the SDK you are using.
  * A description of the steps needed to reproduce the behavior.
  * What you expected to happen and what actually happened.
  * Any relevant code snippets or logs.

### ✨ Suggesting Enhancements

Have an idea for a new feature or an improvement to an existing one? We'd love to hear it\! Please [**open a new issue**](https://github.com/AppOutlet/umami-kotlin-sdk/issues/new) to describe your proposal.

Provide as much context as you can, including the problem you're trying to solve and why the enhancement would be valuable.

### 💻 Your First Code Contribution

Ready to write some code? The best way to start is by looking for an existing issue to work on. For newcomers, we highly recommend picking an issue tagged with [`good first issue`](https://github.com/AppOutlet/umami-kotlin-sdk/issues?q=state%3Aopen%20label%3A%22good%20first%20issue%22), as these are designed to be great entry points into the project.

Once you've found an issue you'd like to tackle, leave a comment to let us know you're working on it.

## 🚀 Development Setup

To get the project running on your local machine, you'll need a couple of things.

  * **IDE**: The SDK is a Kotlin Multiplatform project built with Gradle. We recommend using [**IntelliJ IDEA Community Edition**](https://www.jetbrains.com/idea/download/), which has excellent support for Kotlin and Gradle right out of the box.
  * **Project Dependencies**: The core of this SDK is an abstraction layer built on top of the Umami REST API. We use the powerful [**Ktor**](https://ktor.io/) library to handle networking. Familiarity with these resources will be helpful:
      * [**Ktor Documentation**](https://ktor.io/docs/welcome.html)
      * [**Umami REST API Documentation**](https://umami.is/docs/api)
  * **Verify Your Setup**: Once you've cloned the repository, open it in IntelliJ IDEA. It should automatically sync the Gradle project. You can verify everything is working by running the tests or building the project using the Gradle wrapper:

    ```bash
    ./gradlew build
    ```

## 📝 Pull Request Process

We use the standard GitHub Fork & Pull Request workflow. If you are new to this process, you can read GitHub's guide on [how to create a pull request](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request). Here is the step-by-step process:

1.  **Fork the Repository**
    Click the "Fork" button at the top right of the [main repository page](https://github.com/AppOutlet/umami-kotlin-sdk).

2.  **Clone Your Fork**
    Clone your forked repository to your local machine.

    ```bash
    git clone https://github.com/YOUR_USERNAME/umami-kotlin-sdk.git
    cd umami-kotlin-sdk
    ```

3.  **Create a New Branch**
    Create a descriptive branch name for your changes.

    ```bash
    # For a new feature
    git checkout -b feature/your-cool-feature-name

    # For a bug fix
    git checkout -b fix/issue-short-description
    ```

4.  **Make Your Changes**
    Write your code and make sure to add or update tests as necessary. This project uses `.editorconfig` to help maintain a consistent coding style, but please also ensure you follow the official [**Kotlin coding conventions**](https://kotlinlang.org/docs/coding-conventions.html).

5.  **Commit Your Changes**
    Commit your changes with a clear and concise commit message.

    ```bash
    git add .
    git commit -m "feat: Add support for X feature"
    # or
    git commit -m "fix: Resolve crash when Y occurs"
    ```

6.  **Push to Your Fork**
    Push your branch to your forked repository on GitHub.

    ```bash
    git push origin feature/your-cool-feature-name
    ```

7.  **Submit a Pull Request (PR)**
    Go to your fork on GitHub and click the "Compare & pull request" button. This will open a PR against the main `umami-kotlin-sdk` repository.

    In your PR description, please:

      * Provide a clear explanation of the "what" and "why" of your changes.
      * Link to the issue your PR resolves using keywords like `Fixes #123` or `Closes #123`.
      * Wait for a maintainer to review your code. We will provide feedback and guide you through any necessary changes.

Thank you again for your interest in contributing. We look forward to seeing your PR!
