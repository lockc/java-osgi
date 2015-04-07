package lockc.osgi.ddf.metacard;

import java.net.URI;
import java.net.URISyntaxException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ddf.catalog.data.AttributeImpl;
import ddf.catalog.data.Metacard;

@SuppressWarnings("deprecation")
public class CustomXmlSaxHandler extends DefaultHandler {
    
    private Metacard metacard;
    
    private boolean title = false;
    private boolean resourceUri = false;
    private boolean location = false;
    private boolean metadata = false;
    private boolean custom = true;
    
    private StringBuilder metadataBuilder = new StringBuilder();
    
    public CustomXmlSaxHandler(Metacard metacard) {
    
        if (metacard == null) {
            throw new IllegalArgumentException("Metacard cannot be null.");
        }
        
        this.metacard = metacard;
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    
        if (qName.equals("Title")) {
            title = true;
        }
        else if (qName.equals("Location")) {
            location = true;
        }
        else if (qName.equals("ResourceURI")) {
            resourceUri = true;
        }
        else if (qName.equals("Metadata")) {
            metadata = true;
        } else if (qName.equals("CustomAttr")) {
            custom = true;
        }
        else if (metadata) {
            
            metadataBuilder.append("<").append(qName).append(">");
        }
        
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    
        if (qName.equals("Title")) {
            title = false;
        }
        else if (qName.equals("Location")) {
            location = false;
        }
        else if (qName.equals("ResourceURI")) {
            resourceUri = false;
        }
        else if (qName.equals("Metadata")) {
            metadata = false;
            metacard.setAttribute(new AttributeImpl(Metacard.METADATA, metadataBuilder.toString()));
        } else if (qName.equals("CustomAttr")) {
            custom = false;
        }
        else if (metadata) {
            
            metadataBuilder.append("</").append(qName).append(">");
        }
    }
    
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
    
        if (title) {
            
            metacard.setAttribute(new AttributeImpl(Metacard.TITLE, new String(ch, start, length)));
        }
        else if (resourceUri) {
            try {
                metacard.setAttribute(new AttributeImpl(Metacard.RESOURCE_URI, new URI(new String(ch, start, length))));
            }
            catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        else if (location) {
            
            metacard.setAttribute(new AttributeImpl(Metacard.GEOGRAPHY, new String(ch, start, length)));
        }
        else if (metadata) {
            
            metadataBuilder.append(new String(ch, start, length));
        }
        else if (custom) {
            metacard.setAttribute(new AttributeImpl(CustomMetacardTypeImpl.CUSTOM_ATTR, new String(ch, start, length)));
        }
    }
}
