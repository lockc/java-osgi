package lockc.osgi.ddf.camel;

import org.apache.camel.builder.RouteBuilder;

public class ExplicitContentRouteBuilder extends RouteBuilder {
    
    @Override
    public void configure() throws Exception {
    
        // @formatter:off
        /**
         * A route that was going to be initiated with a metacard ID being sent 
         * in the mesasge body and then routed to an HTTP request to the Catal 
         * REST service to get the metacard XML.  But it turns out that at this 
         * stage the metacard has not been commited to Solr yet so it returns a 
         * 404 Not Found and breaks the route.
         * 
         */
        from("direct:starter-for-one")
            .recipientList(simple("http4://localhost:8181/services/catalog/${body}?transform=xml"))
            .filter(xpath("//Content-Warning[@explicit='true']"))
            .multicast().parallelProcessing()
                .to("file://explicit-content", "jms:queue:explicit-content");
        // @formatter:on
        
        /**
         * A route that is initiated with the Metacard XML data being sent in
         * the body of the message and then queried using an XPath expression.
         * If the content is deemed to be explicit it routes the message to
         * two places; a file and a JMS queue.
         */
        // @formatter:off
        from("direct:starter-for-ten")
            .filter(xpath("//Content-Warning[@explicit='true']"))
            .multicast().parallelProcessing()
                .to("file://explicit-content", "jms:queue:explicit-content");
        // @formatter:on
    }
    
}
