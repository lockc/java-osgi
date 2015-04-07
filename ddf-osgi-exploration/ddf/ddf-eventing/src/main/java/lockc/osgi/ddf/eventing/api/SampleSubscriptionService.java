package lockc.osgi.ddf.eventing.api;

import java.util.Set;

public interface SampleSubscriptionService {
 
    String registerSubscription(String attribute, String text, Set<String> sourceIds);
    
    void deleteSubscription(String id);
    
    Set<String> subscriptions();
        
}
