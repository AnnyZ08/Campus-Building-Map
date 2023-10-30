import java.sql.ResultSet;
import java.sql.SQLException;

public class users {

    private String UserName;
    private String PassWord;
    private String OperatorType;

    public users(ResultSet rs) throws SQLException {
        this.UserName = rs.getString("UserName");
        this.PassWord = rs.getString("PassWord");
        this.OperatorType = rs.getString("OperatorType");
    }
    public users(String name,String password,String type){
        UserName=name;
        PassWord=password;
        OperatorType = type;
    }

    public String getUsername(){
        return this.UserName;
    }

    public String getPassword(){
        return this.PassWord;
    }

    public String getOperatorType (){
        return this.OperatorType;
    }
}
