import java.sql.*;
import java.util.ArrayList;


public class userDefined {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS ="CS2212";
    private PreparedStatement ps=null;
    private static Connection conn;
    private String insert="INSERT INTO usercreated VALUES(?,?,?,?,?,?,?,?,?)";
    private static String selectDefSQL = "SELECT * FROM usercreated WHERE userAccount = ";
    private static ResultSet rsUserCreated;
    private static String userCreatedSQL = "SELECT * FROM usercreated ";
    private static ResultSet rsDefinePOI;
    private ArrayList<POI> alluserCreated = new ArrayList<>();
    public  userDefined(POI newCreated) throws SQLException, ClassNotFoundException {

//        try {
            addUserDefined(newCreated);
//        }
//        catch (Exception e){
//        }
    }

    public userDefined() throws SQLException, ClassNotFoundException {
//        try {
            getUserCreated();
//        }
//        catch (Exception e)
//        {
//
//        }
    }

    public void addUserDefined(POI add)throws ClassNotFoundException,SQLException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt=null;
        stmt = conn.createStatement();
        ps=conn.prepareStatement(insert);
        ps.setInt(1,add.getX());
        ps.setInt(2,add.getY());
        ps.setString(3,add.getName());
        ps.setString(4,add.getRoomNumber());
        ps.setString(5,add.getType());
        ps.setString(6,add.getBuilding());
        ps.setInt(7,add.getFloor());
        ps.setString(8,add.getDescription());
        ps.setString(9,login.thisUsername);

        ps.execute();
        ps.close();
        stmt.close();
        conn.close();

    }
    private void getUserCreated() throws ClassNotFoundException, SQLException{

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn.createStatement();
        rsUserCreated = stmt.executeQuery(userCreatedSQL);

        while (rsUserCreated.next() && rsUserCreated.getString(9).equals(login.thisUsername)) {
            POI thisPOI = new POI(rsUserCreated);
            alluserCreated.add(thisPOI);
        }

        rsUserCreated.close();
        conn.close();
        stmt.close();

    }


    public void deleteCreate(POI temp)throws ClassNotFoundException,SQLException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt=null;
        stmt = conn.createStatement();
        String query = "DELETE FROM usercreated WHERE Xcoordinate ="+"'"+temp.getX()+"'"+"AND Ycoordinate ="+"'"+temp.getY()+"'"+"AND RoomNumber ="+"'"+temp.getRoomNumber()+"'"+"AND Type ="+"'"+temp.getType()+"'"+"AND building ="+"'"+temp.getBuilding()+"'"+"AND Floor ="+"'"+temp.getFloor()+"'"+"AND userAccount ="+"'"+login.thisUsername+"'";

        stmt.executeUpdate(query);
//        ps.close();

    }
    public ArrayList<POI> DBDefPOI() throws SQLException, ClassNotFoundException {
        ArrayList<POI> temp = new ArrayList<>();
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn.createStatement();
        String query = selectDefSQL + "'" + login.thisUsername + "'";
        rsDefinePOI = stmt.executeQuery(query);

        while (rsDefinePOI.next()) {
            POI thisFav = new POI(rsDefinePOI);
            temp.add(thisFav);
        }

        rsDefinePOI.close();
        conn.close();
        stmt.close();

        return temp;
    }


}
