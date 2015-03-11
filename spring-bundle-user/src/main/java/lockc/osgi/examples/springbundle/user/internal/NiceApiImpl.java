package lockc.osgi.examples.springbundle.user.internal;

import org.springframework.stereotype.Component;

import lockc.osgi.examples.springbundle.user.api.NiceApi;

@Component
public class NiceApiImpl implements NiceApi {
    
    
    public NiceApiImpl() {
    
        System.out.println("Constructed: " + getClass().getName());
    }
    
    @Override
    public void sayHello(String name) {
    
        System.out.println("Hello " + name);
    }
    
    @Override
    public int gimmeSomeMoney(int howMuch) {
    
        System.out.println("You can have that much and 100 quid more!  How nice of me.");
        return howMuch + 100;
    }

}
