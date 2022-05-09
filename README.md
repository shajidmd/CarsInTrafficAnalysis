
## Running from command prompt

#### Prerequisite for running this application.

* install sbt on machine, for macOS use [SBT] link to install and make sure that sbt directory is in PATH. 
* install scala2 on machine. I have used scala 2.12 version for this project. Use [Scala] link to install scala.
* make sure sbt and scala runs from command prompt.


####From root folder *aips* run following command
### Build and Compile application

    sbt clean compile

### Run Test locally

    sbt test

### Create Jar for running locally

    sbt clean assembly

assembly will create fat jar file *aips-assembly.jar* in target folder.

### Run application locally

     scala -cp ./target/scala-2.12/aips-assembly.jar com.aips.traffic.signal.CarsInTrafficAnalysis


### Running application from using IDE
#### Running in IntelliJ IDEA
Run CarsInTrafficAnalysis object with the following:

##### Module
    aips


[SBT]: https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Mac.html
[Scala]: https://www.scala-lang.org/download/
