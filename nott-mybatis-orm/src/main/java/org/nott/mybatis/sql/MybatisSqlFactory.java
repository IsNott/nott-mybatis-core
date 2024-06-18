package org.nott.mybatis.sql;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.nott.mybatis.mapper.NottMapper;
import org.nott.mybatis.model.Page;
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
            String joinQuerySql = SqlBuilder.SqlStrBuilder.buildJoinQuerySql(builder);
            List<Map<String, Object>> objects = mapper.joinQuery(joinQuerySql);
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

    public static <T> Page<T> doExecutePage(Class<T> tClass, ComplexityWrapper builder) {
        Page<T> result = setPage(builder);
        SqlSessionFactory sqlSessionFactory = MyBatisStaticUtil.getSqlSessionFactory();
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            NottMapper mapper = sqlSession.getMapper(NottMapper.class);
            String joinQuerySql = SqlBuilder.SqlStrBuilder.buildJoinQuerySql(builder);
            String countSql = SqlBuilder.SqlStrBuilder.buildCountSql(joinQuerySql);
            Long count = mapper.count(countSql);
            result.setTotalResult(count);
            if(count > 0){
                List<Map<String, Object>> maps = mapper.joinQuery(joinQuerySql);
                result = assemblePageByClass(builder,maps,tClass,result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return result;
    }

    private static <T> Page<T> assemblePageByClass(ComplexityWrapper builder, List<Map<String, Object>> queryResultList, Class<T> tClass,Page<T> result) {
        List<T> ts = assembleResultByClass(queryResultList, tClass);
        result.setRecords(ts);
        return result;
    }

    private static <T> Page<T> setPage(ComplexityWrapper builder) {
        RowBounds rowBound = builder.getRowBound();
        int limit = rowBound.getLimit();
        int offset = rowBound.getOffset();
        Page<T> page = new Page<T>(offset / limit, limit);
        return page;
    }

    // Transform Result List to Bean
    private static <T> List<T> assembleResultByClass(List<Map<String, Object>> queryResultList, Class<T> tClass) {
        List<T> ts = new ArrayList<>();
        T t;
        for (Map<String, Object> map : queryResultList) {
            t = setTypeValue(map,tClass);
            ts.add(t);
        }
        return ts;
    }

    private static <T> T setTypeValue(Map<String, Object> map, Class<T> tClass) {
        T t = null;
        try {
            Field[] declaredFields = tClass.getDeclaredFields();
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
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

}
