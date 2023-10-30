import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class usersTest {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "CS2212";
    private PreparedStatement ps=null;
    private String updateSQL=null;
    private Statement stmt = null;
    private static Connection conn;
    private static String usersSQL = "SELECT UserName,PassWord,OperatorType FROM login";

    private ResultSet resultSet;
    private String userName;
    private String password;
    private String operatorType;
    private users user = null;


    @BeforeEach
    void connectDB() throws SQLException, ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();
        resultSet = stmt.executeQuery(usersSQL);

    }

    @Test
    void testConstructor() {
        users user = new users("testuser", "testpassword", "testtype");
        assertEquals("testuser", user.getUsername());
        assertEquals("testpassword", user.getPassword());
        assertEquals("testtype", user.getOperatorType());
    }

    @Test
    void GetUserNameTest() throws SQLException {

        while(resultSet.next()){
            user = new users(resultSet);
            break;
        }

        assertEquals("Alex@uwo.ca", user.getUsername());


    }


    @Test
    void testGetUsername() throws SQLException {
        while(resultSet.next()){
            user = new users(resultSet);
            break;
        }

        assertEquals("Alex@uwo.ca", user.getUsername());
    }

    @Test
    void testPassword() throws SQLException {
        while(resultSet.next()){
            user = new users(resultSet);
            break;
        }

        assertNotNull(user.getPassword());
    }

    @Test
    void testType() throws SQLException {
        while(resultSet.next()){
            user = new users(resultSet);
            break;
        }

        assertEquals("Developer", user.getOperatorType());
    }



}