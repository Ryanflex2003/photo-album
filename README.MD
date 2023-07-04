# Photo-Album

Application that reaches out to a photo-album API based on a user given album ID
and returns the photo Ids and titles corresponding to the album ID.

A list of album Ids can be retrieved by entering 0 or simply following the prompts.

## Prerequisites

- Jetbrains IntelliJ 2023
- Kotlin JVM 1.8.21
- Gradle 7.3.3
  - Modify the `gradle/gradle-wrapper.properties` file to specify the version.
- Corretto 17
  - Can be downloaded via Chocolatey on Windows or Homebrew on Mac

## Building

- To build clone down the repo from Github
  - using Git CLI run the `git clone git@github.com:Ryanflex2003/photo-album.git` command and copy the github source location

## Contributing

- Fork the repository from github
- Create a branch using `git checkout -b {branch name}`
- Open up a pull request within github merging into `main` branch

## Running Tests
- After building the project right click on the `src\test\kotlin`directory and `Run Tests`
- Another option is to run `./gradlew build` to see the success of the tests.
  - If no code changes, to re-run tests you may need to execute `./gradlew clean build`

### Test Coverage
- Test coverage utilizes Jacoco dependency.
- To run jacoco tests, just run a gradle build.  Tests will be located in `/build/jacocoHtml` directory.