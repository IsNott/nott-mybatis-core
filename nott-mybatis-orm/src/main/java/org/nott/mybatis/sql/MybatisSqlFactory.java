package org.nott.mybatis.sql;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.nott.mybatis.mapper.NottMapper;
import org.nott.mybatis.sql.builder.ComplexityWrapper;
import org.nott.mybatis.sql.builder.SqlBuilder;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * @author Nott
 * @date 2024-6-5
 */

public class MybatisSqlFactory {

    public static <T> List<T> doExecute(Class<T> tClass, ComplexityWrapper builder) {
        List<T> result;
        SqlSessionFactory sqlSessionFactory = MyBatisStaticUtil.getSqlSessionFactory();
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            NottMapper mapper = sqlSession.getMapper(NottMapper.class);
            List<Map<String, Object>> objects = mapper.joinQuery(SqlBuilder.SqlStrBuilder.buildJoinQuerySql(builder));
            result = CollectionUtils.isEmpty(objects) ? null : assembleResultByClass(objects, tClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return result;
    }

    // Transform Result List to Bean
    private static <T> List<T> assembleResultByClass(List<Map<String, Object>> queryResultList, Class<T> tClass) {
        List<T> ts = new ArrayList<>();
        T t;
        try {
            Field[] declaredFields = tClass.getDeclaredFields();
            for (Map<String, Object> map : queryResultList) {
                t = tClass.getDeclaredConstructor().newInstance();
                Set<String> fields = map.keySet();
                Iterator<String> iterator = fields.iterator();
                while (iterator.hasNext()) {
                    String fieldName = iterator.next();
                    Field field = Arrays.stream(declaredFields).filter(r -> fieldName.equals(r.getName()))
                            .findFirst().orElse(null);
                    if (field != null) {
                        field.setAccessible(true);
                        field.set(t, map.get(fieldName));
                    }
                }
                ts.add(t);
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return ts;
    }

}
