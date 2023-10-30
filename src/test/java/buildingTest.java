
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;

import static org.junit.Assert.assertEquals;

class buildingTest {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "CS2212";
    private PreparedStatement ps=null;
    private String updateSQL=null;
    private Statement stmt = null;
    private static Connection conn;
    private static String buildingSQL = "SELECT Name,Floor FROM building";

    private ResultSet resultSet;
    private building testBuilding = null;


    @BeforeEach
    void connectDB() throws SQLException, ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();
        resultSet = stmt.executeQuery(buildingSQL);

    }


    @Test
    void getNameTest() throws SQLException {

        while (resultSet.next()) {
            testBuilding = new building(resultSet);
            break;

        }
        resultSet.close();
        conn.close();
        stmt.close();

        assertEquals("Alumni Stadium",testBuilding.getName());

    }



    @Test
    void getFloorTest() throws ClassNotFoundException, SQLException {

        while (resultSet.next()) {
            testBuilding = new building(resultSet);
            break;
        }

        resultSet.close();
        conn.close();
        stmt.close();

        assertEquals(2,testBuilding.getFloor());
    }
}