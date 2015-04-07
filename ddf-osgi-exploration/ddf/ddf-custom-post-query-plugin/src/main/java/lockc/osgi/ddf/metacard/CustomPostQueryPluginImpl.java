package lockc.osgi.ddf.metacard;

import java.io.Serializable;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ddf.catalog.data.Metacard;
import ddf.catalog.data.Result;
import ddf.catalog.operation.QueryResponse;
import ddf.catalog.plugin.PluginExecutionException;
import ddf.catalog.plugin.PostQueryPlugin;
import ddf.catalog.plugin.StopProcessingException;

public class CustomPostQueryPluginImpl implements PostQueryPlugin {
    
    private static final Logger LOG = LoggerFactory.getLogger(CustomPostQueryPluginImpl.class);
    
    @Override
    public QueryResponse process(QueryResponse input) throws PluginExecutionException, StopProcessingException {
    
        LOG.info("Executing custom post query process.");
        
        if (input.getResults() != null && input.getResults().size() > 0) {
            
            Iterator<Result> it = input.getResults().listIterator();
            
            while (it.hasNext()) {
                Result result = it.next();
                Metacard metacard = result.getMetacard();
                Serializable value = metacard.getAttribute("custom-attr").getValue();
                
                if(value == null || ((value instanceof String) && ((String)value).equals("filter me"))) {
                    LOG.info("Removing metacard : {}", metacard.getId());
                    it.remove();
                }
                LOG.info("Metacard custom attr value: {}", value.toString());
            }
        } else {
            LOG.info("No results.");
        }
        
        return input;
    }
    
}
