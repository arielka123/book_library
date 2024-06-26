package pl.moja.biblioteczka.database.dbuitls;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import pl.moja.biblioteczka.database.models.Author;
import pl.moja.biblioteczka.database.models.Book;
import pl.moja.biblioteczka.database.models.Category;
import pl.moja.biblioteczka.utils.FillDatabase;

import java.sql.SQLException;

public class DbManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbManager.class);

//    private static final String JDBC_DRIVER_HD = "jdbc:h2:./database2";
    private static final String JDBC_DRIVER_SQLITE = "jdbc:sqlite:database.db";
//    private static final String USER = "admin";
//    private static final String PASS = "admin";

    public DbManager() throws Exception {
        ConnectionSource connectionSource = new JdbcConnectionSource(JDBC_DRIVER_SQLITE);
        connectionSource.close();
    }

    private static ConnectionSource connectionSource;

    public static void initDatabase(){
        createConnectionSource();
        createNewTables();
        closeConnectionSource();
    }

    private static void createNewTables() {
        dropTable();
        createTable();
        FillDatabase.fillDatabase();
    }

    private static void createConnectionSource(){
        try {
            connectionSource = new JdbcConnectionSource(JDBC_DRIVER_SQLITE);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public static ConnectionSource getConnectionSource(){
        if(connectionSource == null){
            createConnectionSource();
        }
        return connectionSource;
    }

    public static void closeConnectionSource(){
        if(connectionSource!=null){
            try {
                connectionSource.close();
            } catch (Exception e) {
                LOGGER.warn(e.getMessage());
            }
        }
    }

    private static void createTable(){
        try {
            TableUtils.createTableIfNotExists(connectionSource, Author.class);
            TableUtils.createTableIfNotExists(connectionSource, Book.class);
            TableUtils.createTableIfNotExists(connectionSource, Category.class);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    private  static  void  dropTable(){
        try {
            TableUtils.dropTable(connectionSource, Author.class, true);
            TableUtils.dropTable(connectionSource, Book.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }

}
