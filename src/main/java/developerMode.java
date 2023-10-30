import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class developerMode implements ActionListener,MouseMotionListener{

    public static JFrame frame;
    private static JScrollPane map;
    private static JPanel floor;
    private static JPanel toolBar;

    public static ArrayList<JLabel> floorList= new ArrayList<>();

    private JPanel textFiled;
    private static JMenuBar menuBar;
    private static JLabel showFloor;
    private static JLabel picture;
    private  static  JButton back;
    private static JLabel select;
    private static JMenu selectBuilding;
    private static JMenuItem middlesexCollege;
    private static JMenuItem alumniStadium;
    private static JMenuItem stagingBuilding;
    private static weather currentWeather;
    private static JLabel temp;
    private static JLabel icon;

    // add POI discovery
    public static JComboBox allPOIInThisMap;
    private static JLabel allPOIInThisMapLabel;
    private static JButton discoverPOIButton;
    public static JLabel discoveryPin = new JLabel();
    private POI tempBuiltInCreated;
    private static JLabel currentBuilding;
    private static JComboBox eachFloor;
    private static JButton go;
    private int numButton;
    private String whichBuilding;
    private int whichFLoor;
    private ArrayList<JScrollPane> mapList = new ArrayList<>();
    private JLabel X;
    private JLabel Y;
    private JButton changeCoor=new JButton("Change Coordinate");
    private  JButton completeCoor = new JButton("Complete");
    private static Connection conn;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "CS2212";
    private PreparedStatement ps=null;
    private String updateSQL=null;
    private ArrayList<MouseListener>listenEachFloor=new ArrayList<>();
    private JLabel tempFloor;
    private MouseListener tempListerner;
    private  JLabel tempMoving;
    private JLabel newName = new JLabel();
    private JLabel newNumber= new JLabel();
    private JLabel Description = new JLabel();
    public static JButton userDefined;
    public static JLabel userCreate;
    private JButton edit = new JButton("Edit");
    private JButton delete = new JButton("Remove Built-In POI");
    private int Xcoordinate;
    private int Ycoordinate;
    private Dimension dim;

    public developerMode(String defaultBuilding) throws SQLException, ClassNotFoundException, IOException {
        whichBuilding = defaultBuilding;
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        floor = new JPanel();
        floor.setBackground(new Color(88, 44, 131));
        floor.setBounds(0, 0, 150, (int)dim.getHeight());

        toolBar = new JPanel();
        toolBar.setBackground(new Color(88, 44, 131));
        toolBar.setBounds((int)dim.getWidth()-200, 0, 200, (int)dim.getHeight());

        textFiled = new JPanel();
        textFiled.setBounds(150,(int)dim.getHeight()-150,(int)dim.getWidth()-floor.getWidth()-toolBar.getWidth(),100);

        JLabel testLabel = new JLabel("Test");
        testLabel.setBounds(160,650,100,100);
        textFiled.setLayout(null);
        textFiled.add(testLabel);


        X = new JLabel();
        Y = new JLabel();
        X.setBounds(1200, 300, 200, 100);
        Y.setBounds(1200, 400, 200, 100);
        frame = new JFrame();

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("Developer Mode");
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setResizable(false);

        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        selectBuilding = new JMenu("Building");


        middlesexCollege = new JMenuItem("Middlesex College");
        alumniStadium = new JMenuItem("Alumni Stadium");
        stagingBuilding = new JMenuItem("Staging Building");
        middlesexCollege.addActionListener(this);
        alumniStadium.addActionListener(this);
        stagingBuilding.addActionListener(this);


        selectBuilding.add(middlesexCollege);
        selectBuilding.add(alumniStadium);
        selectBuilding.add(stagingBuilding);

        menuBar.add(selectBuilding);


        back = new JButton("Logout");
        back.setBounds(10,550,80,40);
        back.addActionListener(this);
        frame.add(back);
        select = new JLabel("  Select Floor");
        select.setBounds(10,210,100,50);
        select.setForeground(new Color(255,255,255));
        frame.add(select);
        eachFloor = new JComboBox<>();
        eachFloor.setBounds(10,250,135,30);

        frame.add(eachFloor);
        go = new JButton("go");
        go.setBounds(10,300,70,30);
        go.addActionListener(this);
        frame.add(go);

        numButton = findFloor(whichBuilding);

        currentBuilding = new JLabel();
        currentBuilding.setForeground(Color.white);
        currentBuilding.setBounds(10, 180,135,30);
        currentBuilding.setText("  " + whichBuilding);
        frame.add(currentBuilding);

        // create a list of floor map for this building
        addEachFloor(whichBuilding,numButton, true);


        // add discover POI to frame
        String[] nullStr = new String[0];
        allPOIInThisMap= new JComboBox(nullStr);
        allPOIInThisMap.setBounds((int)dim.getWidth()-190, 50,150,30);
        frame.add(allPOIInThisMap);
        allPOIInThisMapLabel = new JLabel();
        allPOIInThisMapLabel.setText("  Built-In POI");
        allPOIInThisMapLabel.setBounds((int)dim.getWidth()-190, 150,150,30);
        allPOIInThisMapLabel.setForeground(new Color(255,255,255));
        frame.add(allPOIInThisMapLabel);
        discoverPOIButton = new JButton("Find");
        discoverPOIButton.setBounds((int)dim.getWidth()-181, 79, 80,30);
        discoverPOIButton.addActionListener(this);
        frame.add(discoverPOIButton);


        // add logo
        picture=new JLabel();
//        try {
            BufferedImage logo = ImageIO.read(new File("pictures/logo2.png"));
            picture = new JLabel(new ImageIcon(logo));
            picture.setBounds(0, 0, 150, 200);
            floor.add(picture);
//        } catch (Exception e) {
//            throw new Exception();
//        }

        // add edit button
        edit.setBounds(560,60,100,25);
        edit.setVisible(false);
        edit.addActionListener(this);
        textFiled.add(edit);

        delete.setBounds(300,60,200,25);
        delete.setVisible(false);
        delete.addActionListener(this);
        textFiled.add(delete);

        // add temperature and weather icon
        JLabel info=new JLabel("Current temperature");
        info.setForeground(new Color(255,255,255));
        info.setBounds(10,350,140,30);
        frame.add(info);
        currentWeather = new weather();
        temp=new JLabel(currentWeather.getTemp());
        if(temp.getText() == null){
            temp.setText("is not available");
        }
        temp.setForeground(new Color(255,255,255));
        temp.setBounds(10,370,120,30);
        frame.add(temp);
        try {
            icon = new JLabel(new ImageIcon(new URL(currentWeather.getImage())));
            icon.setBounds(10,400,70,70);
            frame.add(icon);
        } catch (MalformedURLException e) {
            throw new MalformedURLException();

        }

        toolBar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();

                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();
                hideMessage();
            }
        });

        frame.add(floor);
        frame.add(toolBar);
        frame.add(map);
        frame.add(textFiled);
        frame.setVisible(true);
        frame.requestFocusInWindow();



        userDefined = new JButton("Create");
        userDefined.setBounds(500,20,100,25);
        userDefined.setVisible(false);
        userDefined.addActionListener(this);
        textFiled.add(userDefined);

        userCreate = new JLabel("  Do you want to create a new built-in POI?");
        userCreate.setBounds(0,10,350,20);
        userCreate.setVisible(false);
        textFiled.add(userCreate);

        changeCoor.setBounds(560,20,150,25);
        changeCoor.setVisible(false);
        changeCoor.addActionListener(this);
        textFiled.add(changeCoor);

        completeCoor.setBounds(560,20,150,25);
        completeCoor.setVisible(false);
        completeCoor.addActionListener(this);
        textFiled.add(completeCoor);

    }


    @Override
    /**  We can not throw exception because actionperformed is an override function */
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==back)
        {
            try {
                login newLogin = new login();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            frame.dispose();
        }


        else if(e.getSource()==go)
        {
            if(tempMoving!=null&&tempFloor!=null) {
                tempMoving.removeMouseMotionListener(this);
                tempFloor.addMouseListener(tempListerner);
            }
            hideMessage();



            String[] allPOIInThisMapStr = new String[0];
            for(int i=1;i<=numButton;i++)
            {
                if(eachFloor.getSelectedItem().equals("Please select")){

                    for(building other: Main.allBuildings){
                        for(int j = 1; j <= mapList.size(); j++){
                            if(mapList.get(j - 1).isVisible() ){
                                mapList.get(j - 1).setVisible(false);
                            }
                        }
                    }
                    break;
                }
                if(eachFloor.getSelectedItem().equals("Floor"+i))
                {
                    whichFLoor = i;
                    mapList.get(i-1).setVisible(true);
                    for (int j = 0; j < mapList.size(); j++){
                        if(j!=i-1&&mapList.get(j).isVisible()){
                            mapList.get(j).setVisible(false);
                        }
                    }

                    allPOIInThisMapStr = discoveryPOI(whichBuilding);
                }

            }

            allPOIInThisMap.removeAllItems();
            allPOIInThisMap.addItem("Please select");
            for(int i = 0; i < allPOIInThisMapStr.length; i++){
                String temp = allPOIInThisMapStr[i];
                allPOIInThisMap.addItem(temp);
            }

        }

        else if(e.getSource() == middlesexCollege){
            if(tempMoving!=null&&tempFloor!=null) {
                tempMoving.removeMouseMotionListener(this);
                tempFloor.addMouseListener(tempListerner);
            }
            hideMessage();
            eachFloor.setSelectedIndex(0);
            currentBuilding.setText("  Middlesex College");
            try {
                addEachFloor("Middlesex College", 5,false);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            for(building aBuilding: Main.allBuildings){
                if(aBuilding.getName().equals("Middlesex College")){
                    numButton = aBuilding.getFloor();
                }
            }
            allPOIInThisMap.removeAllItems();

        }

        else if(e.getSource() == alumniStadium){
            if(tempMoving!=null&&tempFloor!=null) {
                tempMoving.removeMouseMotionListener(this);
                tempFloor.addMouseListener(tempListerner);
            }
            hideMessage();
            eachFloor.setSelectedIndex(0);
            currentBuilding.setText("  Alumni Stadium");

            try {
                addEachFloor("Alumni Stadium",2,false);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


            for(building aBuilding: Main.allBuildings){
                if(aBuilding.getName().equals("Alumni Stadium")){
                    numButton = aBuilding.getFloor();
                }
            }
            allPOIInThisMap.removeAllItems();

        }
        else if(e.getSource() == stagingBuilding){
            if(tempMoving!=null&&tempFloor!=null) {
                tempMoving.removeMouseMotionListener(this);
                tempFloor.addMouseListener(tempListerner);
            }
            hideMessage();
            eachFloor.setSelectedIndex(0);
            currentBuilding.setText("  Staging Building");

            try {
                addEachFloor("Staging Building",2,false);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            for(building aBuilding: Main.allBuildings){
                if(aBuilding.getName().equals("Staging Building")){
                    numButton = aBuilding.getFloor();
                }
            }
            allPOIInThisMap.removeAllItems();

        }

        else if (e.getSource() == discoverPOIButton) {
            if(tempMoving!=null&&tempFloor!=null) {
                tempMoving.removeMouseMotionListener(this);
                tempFloor.addMouseListener(tempListerner);
            }

            userDefined.setVisible(false);
            userCreate.setVisible(false);

            hideMessage();
            if(allPOIInThisMap.getSelectedItem().toString().equals("Please select")){

            }
            else{
                if(discoveryPin != null && discoveryPin.isVisible()){
                    discoveryPin.removeAll();
                    discoveryPin.setVisible(false);
                }
                // create the discovery pin
//                try {
                BufferedImage pin = null;
                try {
                    pin = ImageIO.read(new File("pictures/pin.png"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                discoveryPin = new JLabel(new ImageIcon(pin));
//                } catch (Exception ex) {
//
//                }
                int thisX = 0;
                int thisY = 0;

                // Important: In the POI table, have to comfirm
                for(POI thisPOI : Main.allPOI){
                    if(thisPOI.getBuilding().equals(whichBuilding) && thisPOI.getFloor() == whichFLoor
                            && thisPOI.getName().equals(allPOIInThisMap.getSelectedItem().toString())){
                        thisX = thisPOI.getX();
                        thisY = thisPOI.getY();
                    }
                }
                discoveryPin.setLocation(thisX - 30,thisY - 35);
                discoveryPin.setSize(50, 50);

                discoveryPin.setVisible(true);
                showMessage(discoveryPin.getX(),discoveryPin.getY());
                floorList.get(whichFLoor - 1).add(discoveryPin);
                frame.repaint();
            }


        }

        // add built-in POI
        else if(e.getSource()==userDefined)
        {

            builtInPOI bp = new builtInPOI(this, Xcoordinate,Ycoordinate,whichBuilding,whichFLoor);

        }

        else if(e.getSource() == edit)
        {
            editBuiltIn eb = new editBuiltIn(this,tempBuiltInCreated,whichBuilding,whichFLoor);
            refreshAllBuiltIn();
        }
        else if(e.getSource() == delete)
        {
            hideMessage();




                Main.allPOI.remove(tempBuiltInCreated);
            builtInManage bm = null;
            try {
                bm = new builtInManage(tempBuiltInCreated);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            try {
                bm.deleteBuiltIn(tempBuiltInCreated);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                bm.deleteBuiltInFav(tempBuiltInCreated);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }


            allPOIInThisMap.removeItem(temp.getName());
            refreshAllBuiltIn();
            frame.repaint();


        }
        else if(e.getSource()==changeCoor){
            changeCoor.setVisible(false);
            completeCoor.setVisible(true);
            discoveryPin.addMouseMotionListener(this);
            tempMoving=discoveryPin;
            for(int i=0;i<mapList.size();i++)
            {
                if(mapList.get(i).isVisible())
                {

                    floorList.get(i).removeMouseListener(listenEachFloor.get(i));
                    tempListerner=listenEachFloor.get(i);
                    tempFloor=floorList.get(i);
                }
            }

        }
        else if(e.getSource()==completeCoor){
            completeCoor.setVisible(false);
            changeCoor.setVisible(true);
            int tempX=0;
            int tempY=0;
            discoveryPin.removeMouseMotionListener(this);
            tempX = discoveryPin.getX() + 30;
            tempY = discoveryPin.getY() + 35;
            for(int i=0;i<mapList.size();i++)
            {
                if(mapList.get(i).isVisible())
                {
                    floorList.get(i).addMouseListener(listenEachFloor.get(i));
                }
            }

            try {
                editBuiltIn(tempBuiltInCreated,tempX,tempY);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            try {
                Main.allPOI=Main.dbPOI();
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }

        // reset the discover item to default
        if(e.getSource() != discoverPOIButton && e.getSource() != edit&&e.getSource() != changeCoor&&e.getSource() != completeCoor){
            clearSelectDiscovery();
        }

    }



    private int findFloor(String thisBuilding){
        for(building aBuilding: Main.allBuildings){
            if(aBuilding.getName().equals(thisBuilding)){
                return aBuilding.getFloor();
            }
        }
        return 0;

    }

    private void addEachFloor(String current, int numFloor, boolean newMap) throws SQLException, ClassNotFoundException, IOException {

        // if have shown other map, set the previous map as not visible
        if(newMap == false){
            for(building other: Main.allBuildings){
                if(!other.getName().equals(whichBuilding)){
                    for(int i = 1; i <= mapList.size(); i++){
                        if(mapList.get(i - 1).isVisible() ){
                            mapList.get(i - 1).setVisible(false);
                        }
                    }
                }
            }
        }

        // add each floor item for this building
        floorList.clear();
        eachFloor.removeAllItems();
        eachFloor.addItem("Please select");
        eachFloor.setSelectedIndex(0);
        mapList.clear();
        whichBuilding = current;


        for (int i = 1; i <= numFloor; i++) {
            eachFloor.addItem("Floor" + i);
//            try {
                BufferedImage differentFloor = ImageIO
                        .read(new File("pictures/" + whichBuilding + "/floor" + i + ".jpg"));
                showFloor = new JLabel(new ImageIcon(differentFloor));
                floorList.add(showFloor);
                map = new JScrollPane(showFloor, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                map.setBounds(150, 0, (int)dim.getWidth()-floor.getWidth()-toolBar.getWidth(), (int)dim.getHeight()-150);
                map.setVisible(false);
                frame.add(map);
                mapList.add(map);
                MouseListener ml=new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {

                        Xcoordinate = e.getX();
                        Ycoordinate = e.getY();

                        X.setText("X=" + e.getX());
                        Y.setText("Y=" +e.getY());

                        userDefined.setVisible(true);
                        userCreate.setVisible(true);
                        edit.setVisible(false);
                        delete.setVisible(false);
                        newName.setText(" ");
                        newNumber.setText(" ");
                        Description.setText(" ");
                        completeCoor.setVisible(false);
                        changeCoor.setVisible(false);


                        if(discoveryPin!=null)
                        {
                            if ((e.getX() >= discoveryPin.getX() && e.getX() <= discoveryPin.getX() + discoveryPin.getSize().getWidth()) && (e.getY() >= discoveryPin.getY() && e.getY() <= discoveryPin.getY() + discoveryPin.getSize().getHeight())) {
                                textFiled.repaint();
                                userDefined.setVisible(false);
                                userCreate.setVisible(false);
                                showMessage(discoveryPin.getX(), discoveryPin.getY());
                            }

                        }


                        for(POI a : Main.allPOI){
                            if ((e.getX() >= a.getX() && e.getX() <= a.getX() + 50) && (e.getY() >= a.getY() && e.getY() <= a.getY() + 50)) {
                                textFiled.repaint();
                                userDefined.setVisible(false);
                                userCreate.setVisible(false);
                                showMessage(discoveryPin.getX(), discoveryPin.getY());
                            }

                        }




                    }


                };
                showFloor.addMouseListener(ml);
                listenEachFloor.add(ml);
                frame.remove(toolBar);
                frame.add(X);
                frame.add(Y);
                frame.add(toolBar);

//            } catch (Exception e) {
//
//            }
        }
    }



    private void hideMessage(){
        newName.setText(" ");
        newNumber.setText(" ");
        Description.setText(" ");
        edit.setVisible(false);
        delete.setVisible(false);
        changeCoor.setVisible(false);
        completeCoor.setVisible(false);
    }

    public void showMessage(int coordinateX, int coordinateY){

        for(int j=0;j<mapList.size();j++)
        {
            if(mapList.get(j).isVisible())
            {
                for(int k=0;k<Main.allPOI.size();k++) {
                    if (Main.allPOI.get(k).getFloor() == j + 1 && Main.allPOI.get(k).getBuilding().equals(whichBuilding) && Main.allPOI.get(k).getX() - 30 == coordinateX && Main.allPOI.get(k).getY() - 35 == coordinateY) {
                        newName.setText("  Name: " + Main.allPOI.get(k).getName());
                        newNumber.setText("  Room Number: " + Main.allPOI.get(k).getRoomNumber());
                        Description.setText("  Descritpion: " + Main.allPOI.get(k).getDescription());
                        newName.setBounds(0, 10, 200, 20);
                        newNumber.setBounds(0, 40, 200, 20);
                        Description.setBounds(0, 70, 400, 20);
                        tempBuiltInCreated = Main.allPOI.get(k);

                    }

                    textFiled.add(newName);
                    textFiled.add(newNumber);
                    textFiled.add(Description);

                    edit.setVisible(true);
                    delete.setVisible(true);
                    changeCoor.setVisible(true);

                }

            }
        }

    }


    private String[] discoveryPOI(String building){
        ArrayList<String> temp = new ArrayList<>();
        for(POI aPOI : Main.allPOI){
            if(aPOI.getBuilding().equals(building) &&
                    aPOI.getFloor() == whichFLoor){
                temp.add(aPOI.getName());
            }
        }
        String[] result = temp.toArray(new String[0]);
        return result;
    }

    private void clearSelectDiscovery(){
        if(discoveryPin != null){
            discoveryPin.removeAll();
            discoveryPin.setVisible(false);
        }
        if(allPOIInThisMap != null && allPOIInThisMap.getItemCount() != 0 ){
            allPOIInThisMap.setSelectedIndex(0);
        }
    }

    private void refreshAllBuiltIn(){

        String[] allPOIInThisMapStr = discoveryPOI(whichBuilding);

        frame.remove(allPOIInThisMap);
        allPOIInThisMap.removeAllItems();
        allPOIInThisMap.addItem("Please select");
        for(int i = 0; i < allPOIInThisMapStr.length; i++){
            String temp = allPOIInThisMapStr[i];
            allPOIInThisMap.addItem(temp);
        }

        allPOIInThisMap.setVisible(true);
        frame.remove(toolBar);
        frame.add(allPOIInThisMap);
        frame.add(toolBar);
        frame.repaint();

    }
    public void mouseMoved(MouseEvent me){

    }
    public void mouseDragged(MouseEvent me){

        tempMoving.setBounds( tempMoving.getX()+me.getX()-25, tempMoving.getY()+me.getY()-25, 50, 50);
    }
    private void editBuiltIn(POI a,int newX,int newY)throws SQLException, ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn.createStatement();
        updateSQL = "UPDATE poi SET Xcoordinate="+"'"+newX+"'"+",Ycoordinate ="+"'"+newY+"'"+"WHERE building =" + "'" + a.getBuilding() + "'" + "AND Floor =" + "'" + a.getFloor() + "'" + "AND Name =" + "'" + a.getName() + "'";
        ps = conn.prepareStatement(updateSQL);
        ps.executeUpdate();
        ps.close();
        stmt.close();
        conn.close();

    }


}




