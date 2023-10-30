import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class userDefinedTest {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS ="CS2212";
    private PreparedStatement ps=null;
    private static Connection conn;
    private Statement stmt = null;


    private String insert="INSERT INTO usercreated VALUES(?,?,?,?,?,?,?,?,?)";
    private static String selectDefSQL = "SELECT * FROM usercreated WHERE userAccount = ";
    private static ResultSet rsUserCreated;
    private static String userCreatedSQL = "SELECT * FROM usercreated ";
    private static ResultSet rsDefinePOI;
    private ArrayList<POI> testCreated = new ArrayList<>();
    private POI add;


    @BeforeEach
    void connectDB() throws SQLException, ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();
        add = new POI(1, 1,"TestDefineRoom","TestDefineNumber","DefineClassroom","TestDefineBuilding",0,"Test Define Description");
    }

    @Test
    void addUserDefined() throws SQLException, ClassNotFoundException {
        userDefined ud = new userDefined();
        ud.addUserDefined(add);
    }

    @Test
    void deleteCreate() throws SQLException, ClassNotFoundException {
        userDefined ud = new userDefined();
        ud.deleteCreate(add);
    }

    @Test
    void DBDefPOI() throws SQLException, ClassNotFoundException {
        userDefined ud = new userDefined();
        testCreated = ud.DBDefPOI();
        assertNotNull(testCreated);

    }

    @AfterEach
    void disconnect() throws SQLException {
        conn.close();
        stmt.close();
    }
}