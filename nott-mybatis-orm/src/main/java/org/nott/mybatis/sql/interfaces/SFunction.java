package org.nott.mybatis.sql.interfaces;

import java.io.Serializable;
import java.util.function.Function;

public interface SFunction<T, R> extends Function<T, R> , Serializable {
}
