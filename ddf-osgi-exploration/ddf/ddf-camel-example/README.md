# Camel & DDF Example

This example demonstrates how to:

* Use Camel in DDF

### References

* http://codice.github.io/ddf/
* http://camel.apache.org/index.html
* http://camel.apache.org/java-dsl.html
* http://camel.apache.org/using-osgi-blueprint-with-camel.html
* http://camel.apache.org/pojo-producing.html
* http://camel.apache.org/http4.html
* http://camel.apache.org/jms.html

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

To use this bundle you need to configure DDF with some features so follow these instructions, so assuming your DDF instance is running and you have the DDF terminal up, run the following:

Camel features setup
> features:chooseurl camel 2.14.0
> features:install camel-jms/2.14.0 
> features:install camel-http4/2.14.0

AtiveMQ features setup
> features:chooseurl activemq 5.11.1
> features:install activemq-camel/5.11.1

Now you are ready to install the bundle it into DDF, 

> install mvn:lockc.osgi.ddf.examples/ddf-camel-example/1.0-SNAPSHOT

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

The way to interface with DDF is via it's REST endpoints.  So lets fire some data at the Catalog endpoint and see it in action

```
> POST http://localhost:8181/services/catalog
> Content-Type: application/json;id=geojson
{
    "properties": {
        "title": "Office Location",
        "resource-uri": "http://www.scisys.co.uk/"
        "metadata": "<SomeTag><Content-Warning explicit=\"true\"/><Office>Scisys Chippenham Office</Office></SomeTag>"
    },
    "type": "Feature",
    "geometry": {
        "type": "Point",
        "coordinates": [
            -2.538274,
    		51.433768
        ]
    }
} 
```

If successful you should see that the metacard data has been saved to file under the 'explicit-content' folder under the DDF root directory and also sent 
to a JMS queue.  You should see some JMS type logs in the ddf.log file.

The example uses an in memory ActiveMQ JMS instance but is easily switched to use a real ActiveMQ JMS instance by configuring 
the bundle-blueprint.xml as follows:

Instead of this

```
<bean id="jms"
    class="org.apache.activemq.camel.component.ActiveMQComponent">
    <property name="connectionFactory">
        <bean class="org.apache.activemq.ActiveMQConnectionFactory">
            <property name="brokerURL"
                value="vm://localhost?broker.persistent=false" />
        </bean>
    </property>
</bean>
```

Use this

```
<bean id="jms"
    class="org.apache.activemq.camel.component.ActiveMQComponent">
    <property name="connectionFactory">
        <bean class="org.apache.activemq.ActiveMQConnectionFactory">
            <property name="brokerURL"
                value="tcp://localhost:61616" />
        </bean>
    </property>
</bean>
```

Then you should see the messages arrive onto the queue.  Note: You need to create the queue yourself in ActiveMQ.