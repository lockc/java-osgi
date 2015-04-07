package lockc.osgi.ddf.eventing.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ddf.catalog.data.Metacard;
import lockc.osgi.ddf.eventing.api.SampleSubscriptionService;

/**
 * An REST service that will be available in DDF under at:
 *  
 * http://<server>:<port>/services/subscriptions
 * 
 * @author lockc
 *
 */
@Path("/subscriptions")
public class SampleSubscriptionEndpoint {
    
    private static final Logger LOG = LoggerFactory.getLogger(SampleSubscriptionEndpoint.class);
    
    /**
     * Injected via Blueprint
     */
    private SampleSubscriptionService sampleSubscriptionService;
        
    public void setSampleSubscriptionService(SampleSubscriptionService sampleSubscriptionService) {
    
        this.sampleSubscriptionService = sampleSubscriptionService;
    }
    
    @POST
    public Response createSubscription(@QueryParam("title") String title) throws URISyntaxException {
    
        if(title == null) {
            return Response.status(400).build();
        }
        
        LOG.info("Creating new subscription.");
        String id = sampleSubscriptionService.registerSubscription(Metacard.TITLE, title, null);
        LOG.info("Subscription created : " + id);
        ResponseBuilder builder = Response.created(new URI("http://10.224.128.44:8181/services/subscriptions/" + id));
        return builder.build();
    }
    
    @GET
    public Response getSubscriptionIds()  {
    
        LOG.info("Getting subscriptions IDs.");
        Set<String> ids = sampleSubscriptionService.subscriptions();
        LOG.info("Subscription IDs : " + ids);
        ResponseBuilder builder = Response.ok().entity(ids);
        return builder.build();
    }
    
    @DELETE
    @Path("{id}")
    public Response deleteSubscription(@PathParam("id") String id, @Context HttpServletRequest httpRequest) {
    
        if(id == null) {
            return Response.status(400).build();
        }
        
        LOG.info("Deleting subscription : " + id);
        sampleSubscriptionService.deleteSubscription(id);
        ResponseBuilder builder = Response.ok();
        return builder.build();
    }
    
    
}
