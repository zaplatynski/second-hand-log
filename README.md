#  The Second-Hand Log  [![Build Status](https://travis-ci.org/zaplatynski/second-hand-log.svg?branch=master)](https://travis-ci.org/zaplatynski/second-hand-log) [![GitHub version](https://badge.fury.io/gh/zaplatynski%2Fsecond-hand-log.svg)](https://github.com/zaplatynski/second-hand-log/releases/latest)

The Second-Hand Log is a tiny extension for the [FirstSpirit CMS](http://www.e-spirit.com/) based on [Rob Camick's Message Console](https://tips4java.wordpress.com/2008/11/08/message-console/).

Its aim is to help FirstSpirit template and module developers during their work in the FirstSpirit SiteArchitect by displaying a message console which displays Java's System.out and System.err output.

In contrast to the Java's JConsole it will keep the log and can be reopened.

## Download

The *ready-to-use binary* releases are published here at GitHub: [github.com/zaplatynski/second-hand-log/releases](https://github.com/zaplatynski/second-hand-log/releases).
Built with **Java 8** and compiled against **FirstSpirit Access API 5.2.313** (see [Maven pom](pom.xml)).

This means the FSM should work with newer or older versions of FirstSpirit since the used API in this FSM is quite stable but it has not been tested or verified. Of cause everybody is invited to compile an own version of the FSM with Java 7 or an older version of FirstSpirit.

## Bugs and feature requests

Please file any **bug** or **feature request** here at [github.com/zaplatynski/second-hand-log/issues](https://github.com/zaplatynski/second-hand-log/issues). Thanks!
 
## Compilation instruction

[Maven](http://maven.apache.org/) is used to compile and assemble the FirstSpirit module (FSM):
```
mvn clean verify
```
How to do that please refer to the official developer documentation at the [FSDevTools](https://github.com/e-Spirit/FSDevTools/) for resolving FirstSpirit dependencies such as the FirstSpirit Access API.

If you need to specify an *other FirstSpirit version* (e.g. 5.2.212 instead of 5.2.313) you can bypass the built-in version like this:
```
mvn clean verify -Dfirstspirit.version=5.2.212
```

##  Disclaimer

By using it you agree to the license stated in the file [LICENSE](LICENSE). FirstSpirit is a trade mark by the [e-Spirit AG](http://www.e-spirit.com/).
Use this project and provided binaries at your own risk.


