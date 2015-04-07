package lockc.osgi.examples.maven.myproject.user.impl;

import lockc.osgi.examples.maven.myproject.api.NiceApi;

public class NiceApiUser {
    
    private NiceApi niceApiService;
    
    private String name;
    
    public NiceApi getNiceApiService() {
    
        return niceApiService;
    }
    
    public void setNiceApiService(NiceApi niceApiService) {
    
        this.niceApiService = niceApiService;
    }
    
    public String getName() {
    
        return name;
    }
    
    public void setName(String name) {
    
        this.name = name;
    }
    
    public void startUp() {
    
        niceApiService.sayHello(name);    
    }
}
