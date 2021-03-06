# 🎄 Advent of Code 🎅

[![Stars 2015](https://img.shields.io/static/v1?label=2015&message=28*&color=yellow)](https://adventofcode.com/2015)
[![Stars 2016](https://img.shields.io/static/v1?label=2016&message=20*&color=yellow)](https://adventofcode.com/2016)
[![Stars 2017](https://img.shields.io/static/v1?label=2017&message=20*&color=yellow)](https://adventofcode.com/2017)
[![Stars 2018](https://img.shields.io/static/v1?label=2018&message=50*&color=green)](https://adventofcode.com/2018)
[![Stars 2019](https://img.shields.io/static/v1?label=2019&message=50*&color=green)](https://adventofcode.com/2019)
[![Stars 2020](https://img.shields.io/static/v1?label=2020&message=50*&color=green)](https://adventofcode.com/2020)

My [Advent of Code](https://adventofcode.com/) solutions.


## Goals
- have fun
- have fun
- [learn something new](topics/index.md)
- [solve as many puzzles as possible within the time limit](https://www.reddit.com/r/adventofcode/comments/7m9mg8/all_years_all_days_solve_them_within_the_time/)
- (over)use the following Java features: `Lambda`, `Stream`, `Optional`, `var`, `switch expressions`


## Requirements
The project uses java 15 with *preview features*.

You will need JDK 15. By default the project will look for it in `/Library/Java/JavaVirtualMachines/openjdk.jdk/Contents/Home`

You can also specify your own JDK path like this when using gradle:

`./gradlew <command> -Dorg.gradle.java.home=/path/to/java15`

## Run Unit Tests
Run all tests:

`./gradlew clean test`

Run a specific year:

`./gradlew clean test --tests *2019*`

Run a specific day:

`./gradlew clean test --tests *132019*`


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
