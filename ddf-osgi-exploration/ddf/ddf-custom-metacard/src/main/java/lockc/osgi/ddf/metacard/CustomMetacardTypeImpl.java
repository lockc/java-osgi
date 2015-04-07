package lockc.osgi.ddf.metacard;

import java.util.HashSet;
import java.util.Set;

import ddf.catalog.data.AttributeDescriptor;
import ddf.catalog.data.AttributeDescriptorImpl;
import ddf.catalog.data.BasicTypes;
import ddf.catalog.data.MetacardType;
import ddf.catalog.data.MetacardTypeImpl;

@SuppressWarnings("deprecation")
public class CustomMetacardTypeImpl implements MetacardType {

    public static final String CUSTOM_ATTR = "custom-attr";
    
    private static final long serialVersionUID = 1996115089676292898L;
        
    private MetacardType metacardType;
    
    public CustomMetacardTypeImpl() {
    
        /*
         * Get the basic type and extend it by adding our own attributes
         */
        Set<AttributeDescriptor> attr = new HashSet<>();
        attr.addAll(BasicTypes.BASIC_METACARD.getAttributeDescriptors());
        
        
        attr.add(new AttributeDescriptorImpl(
                "custom-attr", 
                true /* indexed */,
                true /* stored */, 
                false /* tokenized */, 
                false /* multivalued */, 
                BasicTypes.STRING_TYPE));
        
        metacardType = new MetacardTypeImpl("ddf.custom.metacard", attr);
    }
    
    
    
    @Override
    public AttributeDescriptor getAttributeDescriptor(String arg0) {
    
        return metacardType.getAttributeDescriptor(arg0);
    }

    @Override
    public Set<AttributeDescriptor> getAttributeDescriptors() {
    
        return metacardType.getAttributeDescriptors();
    }

    @Override
    public String getName() {
    
        return metacardType.getName();
    }

    
    
    
    
}
