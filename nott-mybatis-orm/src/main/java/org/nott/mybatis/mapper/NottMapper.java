package org.nott.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface NottMapper {

    List<Map<String,Object>> joinQuery(String statement);

    Long count(String statement);
}
