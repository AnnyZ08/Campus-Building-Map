import java.sql.*;


public class builtInManage {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS ="CS2212";
    private PreparedStatement ps=null;
    private static Connection conn;
    private String insert="INSERT INTO poi VALUES(?,?,?,?,?,?,?,?,?)";
    public  builtInManage(POI newCreated) throws SQLException, ClassNotFoundException {

            addBuiltIn(newCreated);


    }


    private void addBuiltIn(POI add)throws ClassNotFoundException,SQLException{
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
        ps.setString(6,"Yes");
        ps.setString(7,add.getBuilding());
        ps.setInt(8,add.getFloor());
        ps.setString(9,add.getDescription());


        ps.execute();
        ps.close();
        stmt.close();
        conn.close();

    }



    public void deleteBuiltIn(POI temp)throws ClassNotFoundException,SQLException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt=null;
        stmt = conn.createStatement();
        String query = "DELETE FROM poi WHERE Xcoordinate ="+"'"+temp.getX()+"'"+"AND Ycoordinate ="+"'"+temp.getY()+"'"+"AND RoomNumber ="+"'"+temp.getRoomNumber()+"'"+"AND Type ="+"'"+temp.getType()+"'"+"AND building ="+"'"+temp.getBuilding()+"'"+"AND Floor ="+"'"+temp.getFloor()+"'";

        stmt.executeUpdate(query);
        ps.close();

    }


    public void deleteBuiltInFav(POI temp)throws ClassNotFoundException,SQLException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt=null;
        stmt = conn.createStatement();
        String query = "DELETE FROM userfavourite WHERE Xcoordinate ="+"'"+temp.getX()+"'"+"AND Ycoordinate ="+"'"+temp.getY()+"'"+"AND RoomNumber ="+"'"+temp.getRoomNumber()+"'"+"AND Type ="+"'"+temp.getType()+"'"+"AND building ="+"'"+temp.getBuilding()+"'"+"AND Floor ="+"'"+temp.getFloor()+"'";

        stmt.executeUpdate(query);
        ps.close();

    }

}
