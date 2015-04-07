package lockc.osgi.ddf.metacard;

import java.net.URI;
import java.util.Date;

import ddf.catalog.data.Attribute;
import ddf.catalog.data.Metacard;
import ddf.catalog.data.MetacardImpl;
import ddf.catalog.data.MetacardType;

@SuppressWarnings("deprecation")
public class CustomMetacardImpl implements Metacard {
    
    private static final long serialVersionUID = -3733668979742534046L;
    
    private Metacard metacard;
    
    public CustomMetacardImpl() {
        metacard = new MetacardImpl(new CustomMetacardTypeImpl());
    }

    @Override
    public Attribute getAttribute(String arg0) {
    
        return metacard.getAttribute(arg0);
    }

    @Override
    public String getContentTypeName() {
    
        return metacard.getContentTypeName();
    }

    @Override
    public URI getContentTypeNamespace() {
    
        return metacard.getContentTypeNamespace();
    }

    @Override
    public String getContentTypeVersion() {
    
        return metacard.getContentTypeVersion();
    }

    @Override
    public Date getCreatedDate() {
    
        return metacard.getCreatedDate();
    }

    @Override
    public Date getEffectiveDate() {
    
        return metacard.getEffectiveDate();
    }

    @Override
    public Date getExpirationDate() {
    
        return metacard.getExpirationDate();
    }

    @Override
    public String getId() {
    
        return metacard.getId();
    }

    @Override
    public String getLocation() {
    
        return metacard.getLocation();
    }

    @Override
    public MetacardType getMetacardType() {
    
        return metacard.getMetacardType();
    }

    @Override
    public String getMetadata() {
    
        return metacard.getMetadata();
    }

    @Override
    public Date getModifiedDate() {
    
        return metacard.getModifiedDate();
    }

    @Override
    public String getResourceSize() {
    
        return metacard.getResourceSize();
    }

    @Override
    public URI getResourceURI() {
    
        return metacard.getResourceURI();
    }

    @Override
    public String getSourceId() {
    
        return metacard.getSourceId();
    }

    @Override
    public byte[] getThumbnail() {
    
        return metacard.getThumbnail();
    }

    @Override
    public String getTitle() {
    
        return metacard.getTitle();
    }

    @Override
    public void setAttribute(Attribute arg0) {
    
        metacard.setAttribute(arg0);
    }

    @Override
    public void setSourceId(String arg0) {
    
        metacard.setSourceId(arg0);
    }
}
