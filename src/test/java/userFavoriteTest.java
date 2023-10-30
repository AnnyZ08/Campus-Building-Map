import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class userFavoriteTest {


    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "CS2212";
    private PreparedStatement ps=null;
    private String updateSQL=null;
    private Statement stmt = null;
    private static Connection conn;
    private static String FavSQL = "SELECT * FROM userfavourite";

    private ResultSet resultSet;
    private ArrayList<POI> testAL = new ArrayList<>();
    private userFavorite fav;
    private POI add;



    @BeforeEach
    void connectDB() throws SQLException, ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();
        resultSet = stmt.executeQuery(FavSQL);
        add = new POI(1, 1,"TestRoom","TestNumber","Classroom","TestBuilding",0,"Test Description");


    }

    @Test
    void DBFavPOITest() throws SQLException, ClassNotFoundException {
        fav = new userFavorite();
        testAL = fav.DBFavPOI();
        assertNotNull(testAL);
    }

    @Test
    void addFavroiteTest() throws SQLException, ClassNotFoundException {
        fav = new userFavorite();
        fav.addFavroite(add);
        resultSet.close();
        conn.close();
        stmt.close();

    }

    @Test
    void deleteFavTest() throws SQLException, ClassNotFoundException {
        fav = new userFavorite();
        fav.deleteFav(add);
        resultSet.close();
        conn.close();
        stmt.close();
    }
}