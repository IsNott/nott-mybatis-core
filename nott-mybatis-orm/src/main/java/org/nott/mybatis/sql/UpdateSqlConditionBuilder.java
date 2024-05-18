package org.nott.mybatis.sql;

import lombok.Data;
import org.nott.mybatis.sql.interfaces.SqlUpdate;
import org.nott.mybatis.sql.model.UpdateCombination;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nott
 * @date 2024-5-15
 */

@Data
public class UpdateSqlConditionBuilder implements SqlUpdate {

    List<UpdateCombination> updateCombinations = new ArrayList<>();

    @Override
    public UpdateSqlConditionBuilder set(String colum, Object val) {
        UpdateCombination combination = new UpdateCombination(colum, val);
        this.updateCombinations.add(combination);
        return this;
    }
}
