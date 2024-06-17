package org.nott.mybatis.sql;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.nott.mybatis.sql.builder.ComplexityWrapper;
import org.nott.mybatis.sql.builder.SqlBuilder;
import java.util.List;


/**
 * @author Nott
 * @date 2024-6-5
 */



public class MybatisSqlFactory {

    public static <T> List<T> doExecute(Class<T> tClass, ComplexityWrapper builder) {
        List<T> result;
        SqlBuilder.buildJoinQuerySql(builder);
        SqlSessionFactory sqlSessionFactory = MyBatisStaticUtil.getSqlSessionFactory();
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            // todo Generate BaseMapper join Table Query SQL Value.
            List<Object> objects = sqlSession.selectList("BaseMapper.joinQuery");
            result = assembleResultByClass(objects,tClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return result;
    }

    // TODO Transform Result List to Bean
    private static <T> List<T> assembleResultByClass(List<Object> queryResultList, Class<T> tClass) {
        return null;
    }

}
