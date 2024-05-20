package org.nott.datasource.provider;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.nott.datasource.config.MultiplyDataSourceConfig;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

/**
 * @author Nott
 * @date 2024-5-20
 */

@RequiredArgsConstructor
@Data
public class YamlDataSourceInfoProvider {

    private MultiplyDataSourceConfig multiplyDataSourceConfig;

    public MultiplyDataSourceConfig loadDataSourceConfigs() {
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("data-source.yml");
        Constructor constructor = new Constructor(MultiplyDataSourceConfig.class, new LoaderOptions());
        TypeDescription typeDescription = new TypeDescription(MultiplyDataSourceConfig.class);
        constructor.addTypeDescription(typeDescription);
        Yaml yaml = new Yaml(constructor);
        MultiplyDataSourceConfig multiplyDataSourceConfig = yaml.loadAs(inputStream, MultiplyDataSourceConfig.class);
        return multiplyDataSourceConfig;
    }


}
