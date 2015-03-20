# Extending DDF Example

* This example demonstrates how to develop, build and test an OSGi bundle by extending the DDF _Catalog Application_
* Deploy the bundle into a running DDF instance and see it working

### References

* http://codice.github.io/ddf/
* https://tools.codice.org/wiki/display/DDF/Extending+Catalog+Transformers
* https://ops4j1.jira.com/wiki/display/PAXEXAM3/Pax+Exam

### You'll Need:

DDF v2.6.0
Java SDK 7 (8 not supportd yet)
Maven v3.x

### Build & Test

In order to build the example (including unit tests and integration tests) simply clone this repository and run the following command

> mvn -s settings.xml clean install -Pitest

_NB. The initial build will take a while as there is a maven dependency in the integration tests on the DDF distribution which is 400+MB._

If you wish to skip the integration tests (and subsequesntly the long build time) then run the following command

> mvn -s settings.xml clean install

### Deploy to DDF Instance

Once you have built the project we can now actually deploy the bundle into a proper running instance of DDF.

Assuming you have:

* Downloaded the DDF distro from [here](http://artifacts.codice.org/service/local/artifact/maven/redirect?r=releases&g=ddf.distribution&a=ddf&v=2.6.0&e=zip)
* Unpacked it, set it up and started it, see [here](https://tools.codice.org/wiki/display/DDF/Quick+Start)

Now you are ready to install it into DDF, so assuming your DDF instance is running and you have the DDF terminal up run the following

> osgi:install file:/path/to/your/bundle/jar.jar

That will install the bundle into the OSGi runtime, next list the bundles

> list

You will see a list of bundles, your bundle will be _INSTALLED_ so now you need to activate it

> osgi:start <id>

followed by (make note of the bundle ID)

> list

Your bundle should now be _ACTIVE_.  That's it, the new _InputTransformer_ should be ready to use.

### Using It

Now that your bundle is ready to use lets see it in action.  If you want to see what DDF is doing you can watch the log files, from the command line run this

> tail -f /${ddf.home}/data/log/ddf.log

The way to interface with DDF is via it's REST endpoints.  So lets fire some XML at it and see it catalog the data.

```
> POST http://localhost:8181/services/catalog
> Content-Type: application/xml;id=anyxml
> <SomeTag>Scisys Chippenham</SomeTag>
```

This should return you a 201 Created response with the _Loaction_ header in the response.  Follow the location header URL
and it should return the XML you just sent.

Next lets use the DDF Search UI to find the catalog metacard and locate the resource on the globe.  So navigate to the 
[DDF Seach UI](https://localhost:8993/search/standard/index.html#)

Seach for the keyword *Scisys* and this should find your catalog entry you just created.  Go into the record and 
click _Locate_ and this should take you to Chippenham on the globe.

If you click on _Details_ in the record and click _View_ Metadata then it will get the data from the REST endpoint.

That's it, you have built and deployed a DDF bundle and seen it in action.


