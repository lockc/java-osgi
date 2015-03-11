package lockc.osgi.examples.springbundle.user.internal.deeper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SomeConfiguration {
    
    @Bean
    public String someBean() {
        System.out.println("creating WOW bean");
        return "wow!";
    }
}
