# akka-persistence-jdbc-serialization-json
A JSON serializer for akka-persistence-jdbc, it uses spray-json. This plugin makes assumptions about the JSON it
writes to the data store. If you can agree with the choices made, you can extend this plugin to support writing your
own case classes as a payload to the data store. Please see the 
[akka-persistence-jdbc-play](https://github.com/dnvriend/akka-persistence-jdbc-play) example project that does exactly this.

# Dependency
To include the Serializer to your sbt project, add the following lines to your build.sbt file:

    resolvers += "dnvriend at bintray" at "http://dl.bintray.com/dnvriend/maven"

    libraryDependencies += "com.github.dnvriend" %% "akka-persistence-jdbc-serialization-json" % "1.0.0"

## What's new?

### 1.0.0
 - Initial release, only writes journal entries as JSON to the data store. 
 
