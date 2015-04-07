package lockc.osgi.examples.maven.myproject.unit;

import static org.junit.Assert.*;
import lockc.osgi.examples.maven.myproject.impl.NiceApiImpl;

import org.junit.Test;


public class NiceApiImplTest {
    
    @Test
    public void test() {
        NiceApiImpl nice = new NiceApiImpl();
        int res = nice.gimmeSomeMoney(100);
        assertEquals(200, res);
    }
    
}
