package org.phinix.lib.common.dao.dml;

import org.phinix.lib.common.util.Model;

public interface DMLDao {
    <T extends Model> int insert(T Model);
    <T extends Model> int update(T Model);
    <T extends Model> int delete(T Model);
}
