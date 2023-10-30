import java.sql.*;

public class POI {
    private int Xcoordinate;
    private int Ycoordinate;
    private String name;
    private String RoomNumber;
    private String Type;
    private String building;
    private int floor;
    private String description;

    public POI(ResultSet rs) throws ClassNotFoundException, SQLException{
        this.Xcoordinate = rs.getInt("Xcoordinate");
        this.Ycoordinate = rs.getInt("Ycoordinate");
        this.name = rs.getString("Name");
        this.RoomNumber = rs.getString("RoomNumber");
        this.Type = rs.getString("Type");
        this.building = rs.getString("building");
        this.floor = rs.getInt("Floor");
        this.description = rs.getString("description");



    }

    public POI(int newX,int newY,String newName,String newNumber,String newType,String newBuilding,int newFloor,String newDescription)
    {
        this.Xcoordinate = newX;
        this.Ycoordinate=newY;
        this.name=newName;
        this.RoomNumber = newNumber;
        this.Type = newType;
        this.building=newBuilding;
        this.floor=newFloor;
        this.description=newDescription;
    }


    public int getX(){
        return this.Xcoordinate;
    }

    public int getY(){
        return this.Ycoordinate;
    }

    public String getName(){
        return this.name;
    }

    public String getRoomNumber(){
        return this.RoomNumber;
    }

    public String getType(){
        return this.Type;
    }


    public String getBuilding(){
        return this.building;
    }

    public int getFloor(){
        return this.floor;
    }

    public String getDescription(){
        return this.description;
    }

    public Boolean checkEqual(POI A,POI B)
    {
        if(A.getX()==B.getX()&&A.getY()==B.getY()&&A.getName().equals(B.getName())&&A.getRoomNumber().equals(B.getRoomNumber())&&A.getType().equals(B.getType())&&A.getFloor()==B.getFloor()&&A.getBuilding().equals(B.getBuilding())&&A.getDescription().equals(B.getDescription()))
        {
            return true;
        }
        else
            return false;
    }

}
