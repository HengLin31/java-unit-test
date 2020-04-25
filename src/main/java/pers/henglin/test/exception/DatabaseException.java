package pers.henglin.test.exception;

/**
 * Created by linheng on 25/04/2020.
 */
public class DatabaseException extends Exception {
    private static final String UNABLE_CONN_DB_EXCEPTION = "Unable to connect to database.";

    public DatabaseException() {
        super(UNABLE_CONN_DB_EXCEPTION);
    }
}
