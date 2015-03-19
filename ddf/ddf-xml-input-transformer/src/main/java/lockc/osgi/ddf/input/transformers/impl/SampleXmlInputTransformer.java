package lockc.osgi.ddf.input.transformers.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ddf.catalog.data.Metacard;
import ddf.catalog.data.impl.AttributeImpl;
import ddf.catalog.data.impl.MetacardImpl;
import ddf.catalog.transform.CatalogTransformerException;
import ddf.catalog.transform.InputTransformer;

public class SampleXmlInputTransformer implements InputTransformer {
    
    private static final Logger LOG = LoggerFactory.getLogger(SampleXmlInputTransformer.class);
    
    @Override
    public Metacard transform(InputStream input) throws IOException, CatalogTransformerException {
    
        return transform(input, "SourceABC");
    }
    
    @Override
    public Metacard transform(InputStream input, String id) throws IOException, CatalogTransformerException {
    
        Metacard metacard = new MetacardImpl();
        
        LOG.info("Executing transform : " + getClass().getName());
        
        String xml = inputStreamToString(input);
        
        metacard.setSourceId(id);
        metacard.setAttribute(new AttributeImpl("xml", xml));
        
        return metacard;
    }
    
    private String inputStreamToString(InputStream input) {
    
        StringBuilder builder = new StringBuilder();
        
        try (InputStreamReader streamReader = new InputStreamReader(input);
                BufferedReader befferedReader = new BufferedReader(streamReader)) {
            
            String line = null;
            while ((line = befferedReader.readLine()) != null) {
                builder.append(line);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                input.close();
            }
            catch (Exception e) {
                // swallow
            }
        }
        
        return builder.toString();
    }
    
}
