package lockc.osgi.examples.maven.myproject.impl;

import lockc.osgi.examples.maven.myproject.api.NiceApi;


public class NiceApiImpl implements NiceApi {
    
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
