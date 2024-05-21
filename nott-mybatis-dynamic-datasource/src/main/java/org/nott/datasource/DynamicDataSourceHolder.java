package org.nott.datasource;

/**
 * @author Nott
 * @date 2024-5-20
 */
public class DynamicDataSourceHolder {

    private static final ThreadLocal<String> DYNAMIC_DATASOURCE_KEY = new ThreadLocal<>();

    public static void setDynamicDataSourceKey(String key){
        DYNAMIC_DATASOURCE_KEY.set(key);
    }

    public static String getDynamicDataSourceKey(){
        String key = DYNAMIC_DATASOURCE_KEY.get();
        return key == null ? "default" : key;
    }

    public static void removeDynamicDataSourceKey(){
        DYNAMIC_DATASOURCE_KEY.remove();
    }

}
