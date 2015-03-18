package lockc.osgi.examples.maven.myproject.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lockc.osgi.examples.maven.myproject.api.NiceApi;


public class NiceApiImpl implements NiceApi {
    
    private static final Logger LOG = LoggerFactory.getLogger(NiceApiImpl.class);
    
    @Override
    public void sayHello(String name) {
    
        System.out.println("Hello " + name);
        LOG.info("Hello " + name);
    }
    
    @Override
    public int gimmeSomeMoney(int howMuch) {
    
        System.out.println("You can have that much and 100 quid more!  How nice of me.");
        LOG.info("You can have that much and 100 quid more!  How nice of me.");
        return howMuch + 100;
    }

}
