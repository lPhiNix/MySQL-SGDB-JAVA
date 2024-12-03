package org.phinix.common.example.dao;

import org.phinix.lib.common.dao.dml.AbstractDMLDao;
import org.phinix.lib.common.util.Model;
import org.phinix.lib.service.MySQLConnection;

public class BusDrivePlaceDao extends AbstractDMLDao {
    public BusDrivePlaceDao(MySQLConnection database) {
        super(database);
    }

    @Override
    public <T extends Model> int update(T model) {
        return super.update(model);
    }

    @Override
    public <T extends Model> int delete(T model) {
        return super.delete(model);
    }
}
