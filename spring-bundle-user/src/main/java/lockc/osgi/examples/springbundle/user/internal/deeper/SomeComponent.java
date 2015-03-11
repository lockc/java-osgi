package lockc.osgi.examples.springbundle.user.internal.deeper;

import org.springframework.stereotype.Component;

@Component
public class SomeComponent {
    
    
    public SomeComponent() {
    
        System.out.println("Constructing " + getClass().getName());
    }
}
