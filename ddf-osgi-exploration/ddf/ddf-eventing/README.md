# Extending DDF Example

* This example demonstrates how to create a new endpoint in DDF (a new RESt service) 
* How to Extend Eventing in DDF by creating subscriptions to events such as a new metacard being created in the DDF _Catalog Application_  
* Deploy the bundle into a running DDF instance and see it working

### References

* http://codice.github.io/ddf/
* https://tools.codice.org/wiki/display/DDF/Extending+Eventing
* https://tools.codice.org/wiki/display/DDF/Developer+Use+of+OGC+Filter
* https://tools.codice.org/wiki/display/DDF/Integrating+Endpoints#IntegratingEndpoints-DevelopingaNewEndpoint
* https://ops4j1.jira.com/wiki/display/PAXEXAM3/Pax+Exam

### You'll Need:

* DDF v2.6.0
* Java SDK 7 (8 not supportd yet)
* Maven v3.x

### Build & Test

In order to build the example (including unit tests and integration tests) simply clone this repository and run the following command

> mvn -s settings.xml clean install -Pitest

_NB. The initial build will take a while as there is a maven dependency in the integration tests on the DDF distribution which is 400+MB._

If you wish to skip the integration tests (and subsequesntly the long build time) then run the following command

> mvn -s settings.xml clean install

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

Your bundle should now be _ACTIVE_.  

### Using It

Now that your bundle is ready to use lets see it in action.  If you want to see what DDF is doing you can watch the log files, from the command line by running this

> tail -f /${ddf.home}/data/log/ddf.log

Lets create a subscription via the new endpoint

```
> POST http://localhost:8181/services/subscriptions?title=Hello%20Subscriptons
```

This should return you a 201 Created response with the _Loaction_ header in the response.  The subscription is hard coded to match Metacards created in the _'ddf.distribution'_ source
with a title of _'Hello Subscriptons'_.  You should see something like this in the logs

> 2015-03-23 16:52:24,678 | INFO  | p1045570825-1596 | SampleSubscriptionEndpoint       | .impl.SampleSubscriptionEndpoint   47 | 359 - lockc.osgi.ddf.examples.ddf-eventing-example - 1.0.0.SNAPSHOT | Creating new subscription.
> 2015-03-23 16:52:24,680 | INFO  | p1045570825-1596 | EventProcessorImpl               | atalog.pubsub.EventProcessorImpl  170 | 252 - ddf-pubsub - 2.6.1 | Creating Evaluation Criteria... 
> 2015-03-23 16:52:24,683 | INFO  | p1045570825-1596 | SampleSubscriptionEndpoint       | .impl.SampleSubscriptionEndpoint   49 | 359 - lockc.osgi.ddf.examples.ddf-eventing-example - 1.0.0.SNAPSHOT | Subscription created : e1a965bd-c567-451c-9429-128b309628f8

Now we have a subscription registered in DDF, lets create a Metacard with a matching title

```
> POST http://localhost:8181/services/catalog
> Content-Type: application/json;id=geojson
> {
    "properties": {
        "title": "Hello Subscriptons",
        "thumbnail": "CA==",
        "resource-uri": "http://codice.github.io/ddf/",
        "created": "2012-09-01T00:09:19.368+0000",
        "metadata-content-type-version": "myVersion",
        "metadata-content-type": "myType",
        "metadata": "<xml>DDF</xml>",
        "modified": "2012-09-01T00:09:19.368+0000"
    },
    "type": "Feature",
    "geometry": {
        "type": "Point",
        "coordinates": [
            30.0,
            10.0
        ]
    }
} 
```

You should see in the logs something like this:

> 2015-03-23 16:37:21,977 | INFO  | Thread-51 | SampleDeliveryMethod | enting.impl.SampleDeliveryMethod   16 | 359 - lockc.osgi.ddf.examples.ddf-eventing-example - 1.0.0.SNAPSHOT | >>>>> created metacard with title of : Hello Subscriptons

which means the subscriptions criteria have been met and the subscriptions delivery method has been invoked, all this one does is output to the logs.

That's it, you have created a new endpoint in DDF that allows you to register a subscription and recieve notification when the filter for the subscription is matched.