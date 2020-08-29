package pers.henglin.test;

import pers.henglin.test.bean.Data;
import pers.henglin.test.exception.DatabaseException;

import java.util.Optional;

/**
 * Created by linheng on 25/04/2020.
 */
public class WebDao {

    public Optional<Data> findDataById(int id) throws DatabaseException {
        throw new DatabaseException();
    }

    public Optional<Data> updateData(Data data) throws DatabaseException {
        throw new DatabaseException();
    }
}
