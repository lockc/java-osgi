package lockc.osgi.ddf.input.transformers.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ddf.catalog.data.Metacard;
import ddf.catalog.data.MetacardImpl;
import ddf.catalog.transform.CatalogTransformerException;
import ddf.catalog.transform.InputTransformer;

@SuppressWarnings("deprecation")
public class SampleXmlInputTransformer implements InputTransformer {
    
    private static final Logger LOG = LoggerFactory.getLogger(SampleXmlInputTransformer.class);
    
    @Override
    public Metacard transform(InputStream input) throws IOException, CatalogTransformerException {
    
        return transform(input, "Unknown");
    }
    
    @Override
    public Metacard transform(InputStream input, String id) throws CatalogTransformerException {
    
        LOG.info("Executing transform : " + getClass().getName());
        
        MetacardImpl metacard = new MetacardImpl();
        metacard.setSourceId(id);
        
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new AnyXmlSaxHandler(metacard);
            saxParser.parse(input, handler);
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            LOG.error("Error occurred during metadata to metacard transformation.", e);
            throw new CatalogTransformerException(e);
            
        }
        
        LOG.info("Transform complete");
        return metacard;
    }
    
}
