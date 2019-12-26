# Advent of Code
My [Advent of Code](https://adventofcode.com/) solutions.


## Goals
- have fun
- have fun
- [solve all the puzzles within the time limit](https://www.reddit.com/r/adventofcode/comments/7m9mg8/all_years_all_days_solve_them_within_the_time/)
- try out (and overuse) the following Java features: `Lambda`, `Stream`,
  `Optional`, `var`, 
  [switch expressions](https://openjdk.java.net/jeps/325)


## Run Unit Tests
Run all tests:

`./gradlew clean test`

Run a specific year:

`./gradlew clean test --tests *2019*`

Run a specific day:

`./gradlew clean test --tests *132019*`

You need JDK 12.
By default the project will look for it in `/Library/Java/JavaVirtualMachines/openjdk-12.jdk/Contents/Home`

You can also specify your own JDK path like this:

`./gradlew clean test -Dorg.gradle.java.home=/path/to/java12`


## Enable printing to `stdout`
Print a representation of some puzzles to `stdout`:

`./gradlew clean test -Dprint=true`


## Enable interactive mode
Following days have a `main` method that enables a basic interactive mode:
- 13th December 2019
- 25th December 2019

Enjoy! 🕹️

### TODO
- try out Kotlin
- move to JUnit 5
