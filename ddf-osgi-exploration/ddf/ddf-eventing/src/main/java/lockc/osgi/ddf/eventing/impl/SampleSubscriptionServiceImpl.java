package lockc.osgi.ddf.eventing.impl;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import lockc.osgi.ddf.eventing.api.SampleSubscriptionService;

import org.opengis.filter.Filter;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ddf.catalog.event.DeliveryMethod;
import ddf.catalog.event.Subscription;
import ddf.catalog.event.SubscriptionImpl;
import ddf.catalog.filter.FilterBuilder;

@SuppressWarnings("deprecation")
public class SampleSubscriptionServiceImpl implements SampleSubscriptionService {
    
    private static final Logger LOG = LoggerFactory.getLogger(SampleSubscriptionServiceImpl.class);
    
    private Map<String, ServiceRegistration<Subscription>> subscriptions;
    
    /**
     * Injected via blueprint
     */
    private BundleContext bundleContext;
    
    /**
     * One of these is already registered in DDF as an OSGi service and hence injected 
     * into here via a Blueprint 'reference'.
     */
    private FilterBuilder filterBuilder;
    
    public SampleSubscriptionServiceImpl() {
    
        subscriptions = new HashMap<>();
    }
    
    public void setBundleContext(BundleContext bundleContext) {
    
        this.bundleContext = bundleContext;
    }
    
    public void setFilterBuilder(FilterBuilder filterBuilder) {
    
        this.filterBuilder = filterBuilder;
    }
    
    @Override
    public String registerSubscription(String attribute, String text, Set<String> sourceIds) {
    
        if (sourceIds == null) {
            sourceIds = new HashSet<String>();
            sourceIds.add("ddf.distribution");
        }
        
        if (text == null) {
            text = "*";
        }
        
        // Create the subscription using the default implementation
        Filter filter = filterBuilder.attribute(attribute).is().text(text);
        DeliveryMethod deliveryMethod = new SampleDeliveryMethod();
        boolean isEnterprise = true;
        Subscription subscription = new SubscriptionImpl(filter, deliveryMethod, sourceIds, isEnterprise);
        
        // Register the Subscription in OSGi registry
        String subscriptionId = UUID.randomUUID().toString();
        Dictionary<String, String> properties = new Hashtable<String, String>();
        properties.put("subscription-id", subscriptionId);
        
        ServiceRegistration<Subscription> serviceRegistration = bundleContext.registerService(
                Subscription.class, subscription, properties);
        
        // Resister ServiceRegistry internally for later manipulation 
        subscriptions.put(subscriptionId, serviceRegistration);
        
        return subscriptionId;
    }
    
    @Override
    public void deleteSubscription(String id) {
    
        // Obtain service registration from subscriptions Map based on subscription ID
        ServiceRegistration<Subscription> sr = subscriptions.get(id);
        
        // Unregister Subscription from OSGi Service Registry
        sr.unregister();
        
        // Remove Subscription from Map keeping track of registered Subscriptions.
        subscriptions.remove(id);
    }

    @Override
    public Set<String> subscriptions() {
    
        return subscriptions.keySet();
    }
    
}
