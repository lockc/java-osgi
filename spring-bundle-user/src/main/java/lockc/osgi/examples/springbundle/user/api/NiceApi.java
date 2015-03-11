package lockc.osgi.examples.springbundle.user.api;

import lockc.osgi.examples.springplugin.api.RegisteredService;

@RegisteredService(serviceFactory = NiceApiServiceFactory.class)
public interface NiceApi {
    
    void sayHello(String name);
    
    int gimmeSomeMoney(int howMuch);
}
