import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS ="CS2212";
    private PreparedStatement ps=null;
    private static Connection conn;
    private Statement stmt = null;
    private ArrayList<POI> testPOI = new ArrayList<>();
    private ArrayList<building> testBuilding = new ArrayList<>();
    private ArrayList<users> testUser = new ArrayList<>();


    @BeforeEach
    void connectDB() throws SQLException, ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();
    }

    @Test
    void dbPOITest() throws SQLException, ClassNotFoundException {
        testPOI = Main.dbPOI();
        assertNotNull(testPOI);
    }

    @Test
    void dbBuildingTest() throws SQLException, ClassNotFoundException {
        testBuilding = Main.dbBuilding();
        assertNotNull(testBuilding);
    }

    @Test
    void dbUsersTest() throws SQLException, ClassNotFoundException {
        testUser = Main.dbUsers();
        assertNotNull(testUser);
    }

    @AfterEach
    void disconnect() throws SQLException {
        conn.close();
        stmt.close();
    }
}