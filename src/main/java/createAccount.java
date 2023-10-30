import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

/**
 * create account page
 * User can input username, password, and type.
 * If no errors appear, the account will be created and put into the database
 */
public class createAccount implements ActionListener{
    /** frame for the create account box */
    JFrame frame;
    /** panel inside the frame */
    JPanel panel;
    private static JLabel newAccount;
    private static JLabel newPassword;
    private static JLabel confirmPass;
    private static JLabel emptyText;
    private static JLabel pNotMatch;
    private static JLabel accountExist;
    private static JTextField newAccountText;
    private static JTextField newPasswordText;
    private static JTextField ConfirmPassText;
    private static JButton create;
    private static JLabel operatorType;
    private static JComboBox operator;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS ="CS2212";
    /** connection for the database */
    private Connection conn;
    /** prepared statement for the database */
    private PreparedStatement ps=null;
    /** result for access the rows and columns in the database */
    private ResultSet rs;
    /** statement to put into prepared statement for the database */
    private String insert="INSERT INTO login VALUES(?,?,?)";
    /** statement to find the result in the database */
    private String search="SELECT UserName,PassWord,OperatorType FROM login";

    /**
     * constructor for createAccount class, creates a new createAccount object
     */
    public createAccount(){
        Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
        // initialize frame
        frame = new JFrame();
        panel = new JPanel();
        frame.setSize(320,400);
        frame.setTitle("Create Account");
        frame.setLocation(dim.width/2-frame.getSize().width/2,dim.height/2-frame.getSize().height/2);
        frame.add(panel);
        frame.setResizable(false);
        panel.setLayout(null);

        // add place to put new account name, password
        newAccount = new JLabel("New Account Name");
        newPassword = new JLabel("New Password");
        confirmPass = new JLabel("Confirm Password");
        newAccount.setBounds(35, 20, 160, 25);
        newPassword.setBounds(35, 90, 160, 25);
        confirmPass.setBounds(35,160,160,25);
        panel.add(newAccount);
        panel.add(newPassword);
        panel.add(confirmPass);
        // textfield
        newAccountText=new JTextField();
        newPasswordText=new JPasswordField();
        ConfirmPassText = new JPasswordField();
        newAccountText.setBounds(35,45,250,25);
        newPasswordText.setBounds(35,115,250,25);
        ConfirmPassText.setBounds(35, 185, 250, 25);
        panel.add(newAccountText);
        panel.add(newPasswordText);
        panel.add(ConfirmPassText);

        // operator function
        operatorType = new JLabel("Select Operator");
        operatorType.setBounds(35,230,160,25);
        operator = new JComboBox<>();
        operator.setBounds(35,255,250,25);
        operator.addItem("User");
        operator.addItem("Developer");
        panel.add(operatorType);
        panel.add(operator);

        // add create button
        create = new JButton("Create");
        create.setBackground(new Color(79, 38, 131));
        create.setForeground(Color.WHITE);
        create.setFont(new Font("Helvetica", Font.BOLD, 17));
        create.setBounds(100, 305, 120, 40);

        create.setBorderPainted(false);
        create.addActionListener(this);
        create.setVisible(true);
        create.setOpaque(true);
        create.setBorderPainted(false);
        create.setFocusPainted(false);
        panel.add(create);

        //error messages
        emptyText = new JLabel("Please enter Username or Password");
        emptyText.setBounds(35, 70, 250, 25);
        emptyText.setForeground(Color.red);
        emptyText.setVisible(false);
        panel.add(emptyText);
        pNotMatch = new JLabel("Password does not match");
        pNotMatch.setBounds(35, 210, 250, 25);
        pNotMatch.setForeground(Color.red);
        pNotMatch.setVisible(false);
        panel.add(pNotMatch);
        accountExist = new JLabel("Account Exist");
        accountExist.setBounds(35, 70, 100, 25);
        accountExist.setForeground(Color.red);
        accountExist.setVisible(false);
        panel.add(accountExist);

        frame.setVisible(true);
    }

    /**
     * uses actionlistener interface
     * take input actions from user and process them
     * We can not throw exception because actionperformed is an override function
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e){

        String u=newAccountText.getText();
        String p=newPasswordText.getText();
        String c=ConfirmPassText.getText();
        if(u.isEmpty()||p.isEmpty()) {
            accountExist.setVisible(false);
            pNotMatch.setVisible(false);
            emptyText.setVisible(true);
        }
        else if (!p.equals(c)){
            accountExist.setVisible(false);
            emptyText.setVisible(false);
            pNotMatch.setVisible(true);
        }else{

            try {
                DBconnected(u, p);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        }

    }

    /**
     * connection to the database that contains all user information
     * checks for existing accounts, and adds new account to the database
     * @param userName
     * @param passWord
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void DBconnected(String userName, String passWord) throws Exception {
        boolean match=false;
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt=null;
        stmt = conn.createStatement();
        ps=conn.prepareStatement(insert);
        rs = stmt.executeQuery(search);

        // check if the account already existed
        while(rs.next()) {
            if(rs.getString("UserName").equals(userName)) {
                match=true;
                emptyText.setVisible(false);
                pNotMatch.setVisible(false);
                accountExist.setVisible(true);
                break;
            }
        }
        // add new user to the database
        if(match==false) {
            ps.setString(1,userName);
            ps.setString(2,AES.encryptPW(passWord));
            // check if user selected User or Developer
            if(operator.getSelectedItem().equals("User")) {
                ps.setString(3,"User");
                Main.allUsers.add(new users(userName,AES.encryptPW(passWord),"User"));
            }
            else {
                ps.setString(3,"Developer");
                Main.allUsers.add(new users(userName,AES.encryptPW(passWord),"Developer"));
            }

            ps.execute();
            frame.dispose();

        }
        ps.close();
        rs.close();
        stmt.close();
        conn.close();

    }


}
