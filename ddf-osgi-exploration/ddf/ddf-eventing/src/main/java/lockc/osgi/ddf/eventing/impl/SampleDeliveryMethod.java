package lockc.osgi.ddf.eventing.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ddf.catalog.data.Metacard;
import ddf.catalog.event.DeliveryMethod;

public class SampleDeliveryMethod implements DeliveryMethod {
    
    private static final Logger LOG = LoggerFactory.getLogger(SampleDeliveryMethod.class);
    
    @Override
    public void created(Metacard newMetacard) {
        
        LOG.info(">>>>> created metacard with title of : " + newMetacard.getTitle());
    }
    
    @Override
    public void deleted(Metacard oldMetacard) {
    
        LOG.info(">>>>> deleted metacard with title of : " + oldMetacard.getTitle());
    }
    
    @Override
    public void updatedHit(Metacard newMetacard, Metacard oldMetacard) {
    
        LOG.info(">>>>> updatedHit. metacard title : " + oldMetacard.getTitle());
    }
    
    @Override
    public void updatedMiss(Metacard newMetacard, Metacard oldMetacard) {
    
        LOG.info(">>>>> updatedMiss. metacard title : " + oldMetacard.getTitle());
    }
    
}
