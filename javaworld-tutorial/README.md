# OSGi Tutorial by JavaWorld

A grass roots tutorial on creating and executing 'bundles' within the Eclipse OSGi runtime framework.

Part 2 of the tutorial uses Spring DM which today is a 'dead' project, it seems Spring have dropped the project so only part 1 of the tutorial has been followed.

* http://www.javaworld.com/article/2077837/java-se/hello--osgi--part-1--bundles-for-beginners.html
* http://www.javaworld.com/article/2077853/java-se/hello--osgi--part-2--introduction-to-spring-dynamic-modules.html
* http://www.javaworld.com/article/2077868/java-se/hello--osgi--part-3--take-it-to-the-server-side.html


N.B.
This requires the Plug-in Development Environment within Eclipse.  

Also I had to add the following JVM argument when running the examples to avoid connection issues with 0.0.0.0:80 - _-Dorg.osgi.service.http.port=8380_
