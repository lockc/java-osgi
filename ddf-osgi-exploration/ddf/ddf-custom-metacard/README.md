# Extending DDF Example

This example demonstrates how to:
* Create custom ```MetacardType```s with custom attributes
* Have a custom metcard returned by the catalog REST endpoint

### References

* http://codice.github.io/ddf/
* https://tools.codice.org/wiki/display/DDF/Extending+Catalog+Transformers
* https://tools.codice.org/wiki/display/DDF/Extending+Data+and+Metadata+Basics
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

Your bundle should now be _ACTIVE_.  That's it, the new _InputTransformer_ should be ready to use.

### Using It

Now that your bundle is ready to use lets see it in action.  If you want to see what DDF is doing you can watch the log files, from the command line by running this

> tail -f /${ddf.home}/data/log/ddf.log

The way to interface with DDF is via it's REST endpoints.  So lets fire some XML at the Catalog endpoint and see it in action

```
> POST http://localhost:8181/services/catalog
> Content-Type: application/xml;id=custom
> <?xml version="1.0"?>
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

In the logs you should see something like this

> 2015-03-24 11:37:29,307 | INFO  | tp1044095304-472 | CustomXmlInputTransformer  ..... | Executing transform : lockc.osgi.ddf.metacard.CustomXmlInputTransformer
> 2015-03-24 11:49:00,225 | INFO  | tp1044095304-813 | CustomXmlInputTransformer ..... | Transform complete

This should return you a 201 Created response with the _Loaction_ header in the response.  Follow the location header URL
and it should return the _Metacard_ for the XML data you just sent with the new attribute in the XML like this...

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<ns3:metacard xmlns:ns1="http://www.opengis.net/gml" xmlns:ns2="http://www.w3.org/1999/xlink" xmlns:ns3="urn:catalog:metacard" xmlns:ns4="http://www.w3.org/2001/SMIL20/" xmlns:ns5="http://www.w3.org/2001/SMIL20/Language" ns1:id="ca9bfbca9afc4a3b8c49ca15ce8d3758">
    <ns3:type>ddf.custom.metacard</ns3:type>
    <ns3:source>ddf.distribution</ns3:source>
    <ns3:string name="custom-attr">
        <ns3:value>Wow!</ns3:value>
    </ns3:string>
    <ns3:string name="resource-uri">
        <ns3:value>http://www.scisys.co.uk/</ns3:value>
    </ns3:string>
    <ns3:dateTime name="modified">
        <ns3:value>2015-03-30T11:15:30.226+01:00</ns3:value>
    </ns3:dateTime>
    <ns3:string name="title">
        <ns3:value>Office Location</ns3:value>
    </ns3:string>
    <ns3:geometry name="location">
        <ns3:value>
            <ns1:Point>
                <ns1:pos>-2.142417 51.447569</ns1:pos>
            </ns1:Point>
        </ns3:value>
    </ns3:geometry>
    <ns3:stringxml name="metadata">
        <ns3:value>
            <SomeTag xmlns="">Scisys Chippenham Office</SomeTag>
        </ns3:value>
    </ns3:stringxml>
    <ns3:dateTime name="created">
        <ns3:value>2015-03-30T11:15:30.226+01:00</ns3:value>
    </ns3:dateTime>
</ns3:metacard>
```

You should also be able to request the metacard in different formats from the Catalog REST endpoint like this

http://localhost:8181/services/catalog/${id}?transform=xml
http://localhost:8181/services/catalog/${id}?transform=geojson