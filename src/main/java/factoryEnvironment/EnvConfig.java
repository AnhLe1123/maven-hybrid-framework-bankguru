package factoryEnvironment;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({ "classpath:${env}.properties" })
public interface EnvConfig extends Config{
    @Key("app.url")
    String appUrl();
}
