import java.sql.ResultSet;
import java.sql.SQLException;

public class building {

    private String name;
    private int floor;

    public building(ResultSet building) throws SQLException{
        this.name = building.getString("Name");
        this.floor = building.getInt("Floor");
    }

    public String getName(){
        return this.name;
    }

    public int getFloor(){
        return this.floor;
    }
}
