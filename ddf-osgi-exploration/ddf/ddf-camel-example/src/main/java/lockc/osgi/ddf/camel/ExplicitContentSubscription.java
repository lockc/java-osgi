package lockc.osgi.ddf.camel;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.UUID;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ddf.catalog.event.DeliveryMethod;
import ddf.catalog.event.Subscription;
import ddf.catalog.event.SubscriptionImpl;
import ddf.catalog.filter.FilterBuilder;

@SuppressWarnings("deprecation")
public class ExplicitContentSubscription {
    
    private static final Logger LOG = LoggerFactory.getLogger(ExplicitContentSubscription.class);
    
    /**
     * Injected via blueprint bean
     */
    private BundleContext bundleContext;
    
    /**
     * One of these is already registered in DDF as an OSGi service and hence injected
     * into here via a Blueprint 'reference'.
     */
    private FilterBuilder filterBuilder;
    
    /**
     * Injected via blueprint bean
     */
    private DeliveryMethod deliveryMethod;
    
    public void setBundleContext(BundleContext bundleContext) {
    
        this.bundleContext = bundleContext;
    }
    
    public void setFilterBuilder(FilterBuilder filterBuilder) {
    
        this.filterBuilder = filterBuilder;
    }
    
    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
    
        this.deliveryMethod = deliveryMethod;
    }
    
    public void init() {
            
        if (filterBuilder == null) {
            LOG.error("Filter builder cannot be null.");
            throw new IllegalStateException("Filter builder cannot be null.");
        }
        
        if (bundleContext == null) {
            LOG.error("Bundle context cannot be null.");
            throw new IllegalStateException("Bundle context cannot be null.");
        }
        
        if (deliveryMethod == null) {
            LOG.error("Delivery method cannot be null.");
            throw new IllegalStateException("Delivery method cannot be null.");
        }
        
        LOG.info("Initialising subscription.");
        
        Set<String> sourceIds = new HashSet<>();
        sourceIds.add("ddf.distribution");
        boolean isEnterprise = true;
        
        Subscription subscription = new SubscriptionImpl(filterBuilder.attribute("title").is().text("*"),
                deliveryMethod, sourceIds, isEnterprise);
        
        // Register the Subscription in OSGi registry
        String subscriptionId = UUID.randomUUID().toString();
        Dictionary<String, String> properties = new Hashtable<String, String>();
        properties.put("subscription-id", subscriptionId);
        
        bundleContext.registerService(Subscription.class, subscription, properties);
        
        LOG.info("Subscription initialised.");
    }
}
