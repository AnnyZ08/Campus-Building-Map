import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;


public class userFavorite {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS ="CS2212";
    private static Connection conn;
    private PreparedStatement ps=null;
    private String insertSQL="INSERT INTO userFavourite VALUES(?,?,?,?,?,?,?,?,?)";
    private static String selectFavSQL = "SELECT * FROM userfavourite WHERE userAccount = ";
    private ArrayList<POI> userFavoritePOI;
    private static ResultSet rsFavoritePOI;
    private JMenu fav;


    public userFavorite() throws SQLException, ClassNotFoundException {
        this.userFavoritePOI = DBFavPOI();
        fav = new JMenu("Favorite");
    }


    public JMenu getUserFavorite(){

        for(POI eachFav: userFavoritePOI){
            // building of this favorite item is not in the menu
            if(haveThisBuildingItem(eachFav.getBuilding()) == false){
                JMenu tempBuilding = new JMenu(eachFav.getBuilding());
                JMenu tempFloor = new JMenu("Floor" + eachFav.getFloor());
                JMenuItem tempPOI = new JMenuItem(eachFav.getName());
                tempFloor.add(tempPOI);
                tempBuilding.add(tempFloor);
                fav.add(tempBuilding);
            }
            else{
                String tempBuilding = eachFav.getBuilding();
                if(haveThisFloorItem(tempBuilding,eachFav.getFloor()) == false){
                    for(int i = 0; i < fav.getItemCount(); i++){
                        if(fav.getItem(i).getText().equals(tempBuilding)){
                            JMenuItem tempPOI = new JMenuItem(eachFav.getName());
                            JMenu tempFloor = new JMenu("Floor" + eachFav.getFloor());
                            tempFloor.add(tempPOI);

                            fav.getItem(i).add(tempFloor);
                        }
                    }
                }
                else{
                    for(int i = 0; i < fav.getItemCount(); i++){
                        if(fav.getItem(i).getText().equals(tempBuilding)){
                            JMenu temp = (JMenu) fav.getItem(i);
                            for(int j = 0; j < temp.getItemCount(); j++){
                                if(temp.getItem(j).getText().equals("Floor" + eachFav.getFloor())){
                                    JMenuItem tempPOI = new JMenuItem(eachFav.getName());
                                    temp.getItem(j).add(tempPOI);
                                }
                            }
                        }
                    }

                }
            }

        }
        return fav;
    }


    // find favorite string array of this user
    private String[] findFavoriteArray(){
        ArrayList<String> temp = new ArrayList<>();
        for(POI eachFav: userFavoritePOI){
            temp.add(eachFav.getName());
        }
        String[] result = temp.toArray(new String[0]);
        return result;
    }


    private boolean haveThisBuildingItem(String thisBuilding){
        if(fav.getItemCount() > 0){
            for(int i = 0; i < fav.getItemCount();i++){
                JMenuItem temp = fav.getItem(i);
                if(thisBuilding.equals(fav.getItem(i).getText())){
                    return true;
                }
            }
            return false;
        }
        else{
            return false;
        }
    }


    private boolean haveThisFloorItem(String thisBuilding,int floor){
        for(int i = 0; i < fav.getItemCount(); i++){
            if(fav.getItem(i).getText().equals(thisBuilding)){
                JMenu temp = (JMenu) fav.getItem(i);
                for(int j = 0; j < temp.getItemCount(); j++){
                    if(temp.getItem(j).getText().equals("Floor" + floor)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public ArrayList<POI> DBFavPOI() throws SQLException, ClassNotFoundException {
        ArrayList<POI> temp = new ArrayList<>();
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn.createStatement();
        String query = selectFavSQL + "'" + login.thisUsername + "'";
        rsFavoritePOI = stmt.executeQuery(query);

        while (rsFavoritePOI.next()) {
            POI thisFav = new POI(rsFavoritePOI);
            temp.add(thisFav);
        }

        rsFavoritePOI.close();
        conn.close();
        stmt.close();

        return temp;
    }


    public void addFavroite(POI temp)throws ClassNotFoundException,SQLException{

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt=null;
        stmt = conn.createStatement();
        ps=conn.prepareStatement(insertSQL);
        ps.setInt(1,temp.getX());
        ps.setInt(2,temp.getY());
        ps.setString(3,temp.getName());
        ps.setString(4,temp.getRoomNumber());
        ps.setString(5,temp.getType());
        ps.setString(6,temp.getBuilding());
        ps.setInt(7,temp.getFloor());
        ps.setString(8,temp.getDescription());
        ps.setString(9,login.thisUsername);

        ps.execute();
        ps.close();
        stmt.close();
        conn.close();

    }
    public void deleteFav(POI temp)throws ClassNotFoundException,SQLException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt=null;
        stmt = conn.createStatement();
        String query = "DELETE FROM userfavourite WHERE Xcoordinate ="+"'"+temp.getX()+"'"+"AND Ycoordinate ="+"'"+temp.getY()+"'"+"AND RoomNumber ="+"'"+temp.getRoomNumber()+"'"+"AND Type ="+"'"+temp.getType()+"'"+"AND building ="+"'"+temp.getBuilding()+"'"+"AND Floor ="+"'"+temp.getFloor()+"'"+"AND userAccount ="+"'"+login.thisUsername+"'";
        stmt.executeUpdate(query);
//        ps.close();

    }

}

