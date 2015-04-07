# Extending DDF Catalog

This example demonstrates how to:
* Extend Catalog Plugins
* Create custom a `PostQueryPlugin` to filter out the results before returning
* This example needs the [ddf-custom-metacard](http://svr-met-code1/iep/ddf-osgi-exploration/tree/master/ddf/ddf-custom-metacard) bundle to be intalled.

### References

* http://codice.github.io/ddf/
* https://tools.codice.org/wiki/display/DDF/Extending+Catalog+Plugins
* https://ops4j1.jira.com/wiki/display/PAXEXAM3/Pax+Exam

### You'll Need:

* DDF v2.6.0
* Java SDK 7 (8 not supportd yet)
* Maven v3.x

### Build & Test

In order to build the example (including unit tests and integration tests) simply clone this repository and run the following command

> mvn -s ../settings.xml clean install -Pitest

_NB. The initial build will take a while as there is a maven dependency in the integration tests on the DDF distribution which is 400+MB._

If you wish to skip the integration tests (and subsequesntly the long build time) then run the following command

> mvn -s ../settings.xml clean install

### Deploy to DDF Instance

Once you have built the project you can now actually deploy the bundle into a proper running instance of DDF.

Assuming you have:

* Downloaded the DDF distro from [here](http://artifacts.codice.org/service/local/artifact/maven/redirect?r=releases&g=ddf.distribution&a=ddf&v=2.6.0&e=zip)
* Unpacked it, set it up and started it, see [here](https://tools.codice.org/wiki/display/DDF/Quick+Start)

Now you are ready to install it into DDF, so assuming your DDF instance is running and you have the DDF terminal up run the following

> install file:/path/to/your/bundle/jar.jar

That will install the bundle into the OSGi runtime, next list the bundles (make note of the bundle ID)

> list

You will see a list of bundles, your bundle will be _INSTALLED_ so now you need to activate it

> start ${bundle id}

followed by 

> list

Your bundle should now be _ACTIVE_.  That's it, the new `PostQueryPlugin` should be ready to use.

### Using It

Now that your bundle is ready to use lets see it in action.  If you want to see what DDF is doing you can watch the log files, from the command line by running this

> tail -f /${ddf.home}/data/log/ddf.log

The way to interface with DDF is via it's REST endpoints.  Lets create some catalog entries including one that should get filtered out.  The `PostQueryPlugin` that has been implemented should filter out any metacard that has an attroibute called _custom-attr_ whose value is equal to 'filter me'.

Create a few of these:

```
> POST http://localhost:8181/services/catalog
> Content-Type: application/xml;id=custom
```
```xml
<?xml version="1.0"?>
<Something>
  <Title>Office Location</Title>
  <Location>POINT(-2.142417 51.447569)</Location>
  <ResourceURI>http://www.scisys.co.uk/</ResourceURI>
  <CustomAttr>Wow!</CustomAttr>
  <Metadata>
    <SomeTag>Scisys Chippenham Office</SomeTag>
  </Metadata>
</Something>
```

And a few of these (these should be filtered):

```
> POST http://localhost:8181/services/catalog
> Content-Type: application/xml;id=custom
```
```xml
<?xml version="1.0"?>
<Something>
  <Title>Office Location</Title>
  <Location>POINT(-2.142417 51.447569)</Location>
  <ResourceURI>http://www.scisys.co.uk/</ResourceURI>
  <CustomAttr>filter me</CustomAttr>
  <Metadata>
    <SomeTag>Scisys Chippenham Office</SomeTag>
  </Metadata>
</Something>
```

Now in a browser query the catalog API for the metacards

> http://localhost:8181/services/catalog/query?q=scisys&format=geojson


In the logs you should see something like this:

> 2015-03-31 10:17:20,623 | INFO  | tp884242855-8970 | CustomPostQueryPluginImpl | .... | Executing custom post query process.
> 2015-03-31 10:17:20,624 | INFO  | tp884242855-8970 | CustomPostQueryPluginImpl | .... | Metacard custom attr value: Wow!
> 2015-03-31 10:17:20,624 | INFO  | tp884242855-8970 | CustomPostQueryPluginImpl | .... | Metacard custom attr value: Wow!
> 2015-03-31 10:17:20,625 | INFO  | tp884242855-8970 | CustomPostQueryPluginImpl  | .... | Removing metacard : 509ffff57dcf44fb8b63d770e028ab35
> 2015-03-31 10:17:20,626 | INFO  | tp884242855-8970 | CustomPostQueryPluginImpl  | .... | | Metacard custom attr value: filter me

That's it, you should not see any metacard in the results that has a _custom-attr_ with a value of 'filter me'