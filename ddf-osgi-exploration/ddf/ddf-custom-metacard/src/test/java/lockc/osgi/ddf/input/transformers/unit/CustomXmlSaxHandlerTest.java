package lockc.osgi.ddf.input.transformers.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import lockc.osgi.ddf.metacard.CustomMetacardImpl;
import lockc.osgi.ddf.metacard.CustomMetacardTypeImpl;
import lockc.osgi.ddf.metacard.CustomXmlInputTransformer;

import org.junit.Test;

import ddf.catalog.data.Metacard;
import ddf.catalog.transform.InputTransformer;


public class CustomXmlSaxHandlerTest {
    
    @Test
    public void test() throws Exception {
    
        String xml = "<?xml version=\"1.0\"?>"
                + "<Something><Title>SCISYS Location</Title>"
                + "<Location>POINT(-2.142417 51.447569)</Location>"
                + "<ResourceURI>http://www.scisys.co.uk/</ResourceURI>"
                + "<CustomAttr>Wow!</CustomAttr>"
                + "<Metadata><SomeTag>Scisys Chippenham Office</SomeTag>"
                + "</Metadata></Something>";
        
        ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes());
        InputTransformer inputTransformer = new CustomXmlInputTransformer();
        Metacard metacard  = inputTransformer.transform(in);
        
        assertTrue(metacard instanceof CustomMetacardImpl);
        assertNotNull(metacard);
        
        assertEquals("Wow!", metacard.getAttribute(CustomMetacardTypeImpl.CUSTOM_ATTR).getValue());
        assertEquals("SCISYS Location", metacard.getTitle());
        assertEquals("<SomeTag>Scisys Chippenham Office</SomeTag>", metacard.getMetadata());
    }
    
}
