package org.nott.datasource;

/**
 * @author Nott
 * @date 2024-5-20
 */
public class DynamicDataSourceHolder {

    private static final ThreadLocal<String> DYNAMIC_DATASOURCE_KEY = new ThreadLocal<>();

    public static void setDynamicDataSourceKey(String key){
//        log.info("数据源切换为：{}",key);
        DYNAMIC_DATASOURCE_KEY.set(key);
    }

    public static String getDynamicDataSourceKey(){
        String key = DYNAMIC_DATASOURCE_KEY.get();
        return key == null ? "default" : key;
    }

    public static void removeDynamicDataSourceKey(){
//        log.info("移除数据源：{}",DYNAMIC_DATASOURCE_KEY.get());
        DYNAMIC_DATASOURCE_KEY.remove();
    }

}
