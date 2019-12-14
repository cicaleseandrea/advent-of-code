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
`./gradlew clean test`

You need JDK 12.
By default the project will look for it in `/Library/Java/JavaVirtualMachines/openjdk-12.jdk/Contents/Home`

You can also specify your own JDK path like this:

`./gradlew clean test -Dorg.gradle.java.home=/path/to/java12`

## Enable printing to `stdout`
Some days have a flag to enable printing a representation of the puzzle to `stdout`.

## Enable interactive mode
13th December 2019 has a `main` method that enables a really basic interactive mode. Enjoy! 🕹️

## TODO
- try out Kotlin
- move to JUnit 5
