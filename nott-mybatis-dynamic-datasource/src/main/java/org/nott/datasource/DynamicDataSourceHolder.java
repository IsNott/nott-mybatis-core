package org.nott.datasource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Nott
 * @date 2024-5-20
 */
public class DynamicDataSourceHolder {

    static Log log = LogFactory.getLog(DynamicDataSourceHolder.class);

    private static final ThreadLocal<String> DYNAMIC_DATASOURCE_KEY = new ThreadLocal<>();

    public static void setDynamicDataSourceKey(String key){
        DYNAMIC_DATASOURCE_KEY.set(key);
        if (log.isDebugEnabled()) {
            log.debug("Dynamic data source key set to: " + key);
        }
    }

    public static String getDynamicDataSourceKey(){
        String key = DYNAMIC_DATASOURCE_KEY.get();
        return key == null ? "default" : key;
    }

    public static void removeDynamicDataSourceKey(){
        DYNAMIC_DATASOURCE_KEY.remove();
    }

}
