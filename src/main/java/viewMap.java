import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.io.IOException;
import java.util.HashSet;

public class viewMap implements ActionListener,MouseMotionListener {

    public static JFrame frame;
    private static JButton selectFloorB;
    private static JPanel hiddenFloorPanel;
    private static JButton POIListB;
    private static JLabel poil;
    private static JPanel hiddenPOIPanel;
    private static JButton clearSearchB;
    private static JPanel sidePanel;

    private static JScrollPane map;
    private JPanel textFiled;
    private static JMenuBar menuBar;
    private static JLabel showFloor;
    private static JLabel floorPic;
    private static JLabel poiPic;
    private static JLabel washroomPic;
    private static JLabel accessibilityPic;
    private  static  JButton back;
    private static JLabel select;

    private static JMenu favoriteMenu;
    private static JMenu selectBuilding;
    private static JMenu help;
    private static JMenuItem helpGuide;
    private static JMenu about;
    private static JMenuItem aboutUs;


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
    private static JLabel discoveryPin;

    // add search bar
    private static JTextField searchBar;
    private static JButton searchButton;
    private static JLabel searchDesc;
    private static JLabel invalidInput;
    private static JLabel noMatchResult;

    // add layers
    private static JCheckBox classroom;
    private static JCheckBox lab;
    private static JCheckBox userDefine;
    private static JCheckBox userFav;
    private JCheckBox restaurant;
    private JCheckBox collaborative;
    private static JLabel accessibilityLabel;
    private static JLabel washroomLabel;
    private static JLabel currentBuilding;
    private static JComboBox eachFloor;
    private static JButton go;
    public int numButton;
    public String whichBuilding;
    public int whichFLoor;
    public ArrayList<JScrollPane> mapList = new ArrayList<>();
    public JLabel X;
    public JLabel Y;

    public static ArrayList<JLabel> floorList= new ArrayList<>();
    private createPin allPin;
    public static ArrayList<JLabel> pinClassroom;
    public static ArrayList<JLabel> pinLab;
    public static ArrayList<JLabel> pinRestaurant;
    public static ArrayList<JLabel> pinCollaborative;
    private ArrayList<JLabel> totalPin = new ArrayList<>();
    private ArrayList<JLabel> allSearchPin;

    private ArrayList<JLabel> allFavPin;
    public static ArrayList<JLabel> allCreatedPin;
    private ArrayList<JLabel> allVisiablePin = new ArrayList<>();
    private JLabel theFavPinInMenuBar;
    private JLabel newName = new JLabel();
    private JLabel newNumber= new JLabel();
    private JLabel Description = new JLabel();
    private JButton favouriteOption;
    private JButton removeOption =new JButton("Remove Favourite");

    private JButton customizeOption = new JButton("Remove Customize POI");

    private JButton edit = new JButton("Edit");
    public static POI poiInSearch;

    private POI[] allFav;

    private POI tempFav;
    private POI tempDele;

    private POI tempUserCreated;

    public static ArrayList<POI> allFavourite = new ArrayList<>();
    public static ArrayList<POI> allUserCreated = new ArrayList<>();
    public static userFavorite thisUserFavorite;
    private JButton userDefined;
    private JLabel userCreate;
    private int Xcoordinate;
    private int Ycoordinate;
    private userDefined newUD;
    private  createPin allUserPin;
    private Boolean editMode=false;
    private JButton changeCoor=new JButton("Change Coordinate");
    private  JButton completeCoor = new JButton("Complete");
    public static Connection conn;
    public static Connection conn1;
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "12345!@#$%qwert";
    private PreparedStatement ps=null;
    private String updateSQL=null;
    private ArrayList<MouseListener>listenEachFloor=new ArrayList<>();
    private int tempPinID=-1;
    private  JLabel tempMoving;
    private boolean underSearch=false;
    private  boolean underCreated=false;
    private boolean underFav=false;

    private HashSet<Integer> pinX;
    private HashSet<Integer> pinY;

    public viewMap(String buildingName) throws SQLException, ClassNotFoundException {
        whichBuilding = buildingName;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        textFiled = new JPanel();
        textFiled.setBounds(150,(int)dim.getHeight()-150,950,100);

        JLabel testLabel = new JLabel("Test");
        testLabel.setBounds(160,650,100,100);
        textFiled.setLayout(null);
        textFiled.add(testLabel);

        X = new JLabel();
        Y = new JLabel();
        X.setBounds(1200, 300, 200, 100);
        Y.setBounds(1200, 400, 200, 100);

        frame = new JFrame();
        frame.setSize(dim.width, dim.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("View Map");
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        //left side panel
        sidePanel = new JPanel();
        sidePanel.setBackground(new Color(137,142,140));
        sidePanel.setBounds(0, 0, (int)(frame.getWidth()*0.08), frame.getHeight());
        sidePanel.setLayout(null);
        frame.add(sidePanel);

        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        selectBuilding = new JMenu("Building");
        thisUserFavorite = new userFavorite();
        favoriteMenu = thisUserFavorite.getUserFavorite();
        refreshFavorite();
        //help page
        help = new JMenu("Help");
        helpGuide = new JMenuItem("Visit Help Guide");
        helpGuide.addActionListener(this);
        //about page
        about = new JMenu("About");
        aboutUs = new JMenuItem("About us");
        aboutUs.addActionListener(this);

        middlesexCollege = new JMenuItem("Middlesex College");
        alumniStadium = new JMenuItem("Alumni Stadium");
        stagingBuilding = new JMenuItem("Staging Building");
        middlesexCollege.addActionListener(this);
        alumniStadium.addActionListener(this);
        stagingBuilding.addActionListener(this);

        help.add(helpGuide);
        selectBuilding.add(middlesexCollege);
        selectBuilding.add(alumniStadium);
        selectBuilding.add(stagingBuilding);
        about.add(aboutUs);

        menuBar.add(selectBuilding);
        menuBar.add(favoriteMenu);
        menuBar.add(help);
        menuBar.add(about);

        //back button
        back = new JButton("<HTML><U>LOGOUT</U></HTML>");
        back.setBounds(10,600,80,30);
        back.setBackground(new Color(137,142,140));
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Calibri", Font.BOLD, 16));
        back.setBorderPainted(false);
        back.addActionListener(this);
        sidePanel.add(back);

//select floor section
        //select Floor Button
        selectFloorB = new JButton();
        selectFloorB.setBackground(new Color(137,142,140));
        selectFloorB.setBorder(new LineBorder(Color.LIGHT_GRAY));
        selectFloorB.setBounds(0, 0, sidePanel.getWidth(), sidePanel.getWidth());
        selectFloorB.addActionListener(this);
        selectFloorB.setVisible(true);
        selectFloorB.setLayout(null);
        sidePanel.add(selectFloorB);
        //label on the button
        select = new JLabel("  Select Floor");
        select.setBounds((int)(selectFloorB.getWidth()*0.150),(int)(selectFloorB.getHeight()*0.70),130,30);
        select.setForeground(Color.WHITE);
        select.setFont(new Font("Calibri", Font.BOLD, 14));
        selectFloorB.add(select);

        //hidden panel, when user clicks on select floor, it will show up
        hiddenFloorPanel = new JPanel();
        hiddenFloorPanel.setBounds(selectFloorB.getWidth(),24,150,200);
        hiddenFloorPanel.setBorder(new LineBorder(new Color(54,69,79)));
        hiddenFloorPanel.setBackground(Color.WHITE);
        hiddenFloorPanel.setLayout(null);
        hiddenFloorPanel.setVisible(false);

        eachFloor = new JComboBox<>();
        eachFloor.setBounds((int)(hiddenFloorPanel.getWidth()*0.053),(int)(hiddenFloorPanel.getHeight()*0.075),135,30);
        hiddenFloorPanel.add(eachFloor);

        go = new JButton("GO");
        go.setBounds((int)(hiddenFloorPanel.getWidth()*0.26),150,70,30);
        go.setBackground(new Color(79, 38, 131));
        go.setForeground(Color.WHITE);
        go.addActionListener(this);
        hiddenFloorPanel.add(go);

        frame.add(hiddenFloorPanel);

        //indicator of location at the top
        currentBuilding = new JLabel();
        currentBuilding.setLayout(null);
        currentBuilding.setForeground(Color.BLACK);
        currentBuilding.setBounds(50, 0,frame.getWidth(),30);
        currentBuilding.setText("YOU ARE AT " + whichBuilding);
        currentBuilding.setHorizontalAlignment(SwingConstants.CENTER);
        currentBuilding.setFont(new Font("Calibri", Font.BOLD, 17));
        frame.add(currentBuilding);

        numButton = findFloor(whichBuilding);
        // create a list of floor map for this building
        addEachFloor(whichBuilding,numButton, true);

// POI List Button
        POIListB = new JButton();
        POIListB.setBackground(new Color(137,142,140));
        POIListB.setBorder(new MatteBorder(0, 1, 1, 1, Color.LIGHT_GRAY));
        POIListB.setBounds(0, selectFloorB.getHeight(), sidePanel.getWidth(), sidePanel.getWidth());
        POIListB.addActionListener(this);
        POIListB.setVisible(true);
        POIListB.setLayout(null);
        sidePanel.add(POIListB);

        //label for POI button
        poil = new JLabel("POI List");
        poil.setBounds((int)(selectFloorB.getWidth()*0.30),(int)(selectFloorB.getHeight()*0.70),130,30);
        poil.setForeground(Color.WHITE);
        poil.setFont(new Font("Calibri", Font.BOLD, 14));
        POIListB.add(poil);

        //hidden POI Panel
        hiddenPOIPanel = new JPanel();
        hiddenPOIPanel.setBounds(selectFloorB.getWidth(),24,150,470);
        hiddenPOIPanel.setBorder(new LineBorder(new Color(54,69,79)));
        hiddenPOIPanel.setBackground(Color.WHITE);
        hiddenPOIPanel.setLayout(null);
        hiddenPOIPanel.setVisible(false);
        frame.add(hiddenPOIPanel);

        // add search bar to frame
        //search poi
        searchDesc = new JLabel("Search All POI");
        searchDesc.setBounds(10,230,130,20);

        searchBar = new HintTextField("Search POI");
        searchBar.setBounds(10,searchDesc.getY()+searchDesc.getHeight(),130,30);

        searchButton = new JButton("Search");
        searchButton.setBounds(10,searchBar.getY()+searchBar.getHeight(),80,30);
        searchButton.setBackground(new Color(79, 38, 131));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(this);

        hiddenPOIPanel.add(searchDesc);
        hiddenPOIPanel.add(searchBar);
        hiddenPOIPanel.add(searchButton);

        // add two search result to frame
        invalidInput = new JLabel("Invalid Input!");
        invalidInput.setForeground(Color.RED);
        invalidInput.setBounds(10,300,100,30);
        invalidInput.setVisible(false);
        hiddenPOIPanel.add(invalidInput);

        noMatchResult = new JLabel("No result!");
        noMatchResult.setForeground(Color.RED);
        noMatchResult.setBounds(10,300,100,30);
        noMatchResult.setVisible(false);
        hiddenPOIPanel.add(noMatchResult);

        // add discover POI to panel
        allPOIInThisMapLabel = new JLabel("<html>Built-In/User Defined<br/>POI</html>");
        allPOIInThisMapLabel.setBounds(10, 330,150,30);

        String[] nullStr = new String[0];
        allPOIInThisMap= new JComboBox(nullStr);
        allPOIInThisMap.setBounds(10, 360, 130,30);
        // find button
        discoverPOIButton = new JButton("Find");
        discoverPOIButton.setBounds(10, 390, 80,30);
        discoverPOIButton.setBackground(new Color(79, 38, 131));
        discoverPOIButton.setForeground(Color.WHITE);
        discoverPOIButton.addActionListener(this);

        hiddenPOIPanel.add(allPOIInThisMapLabel);
        hiddenPOIPanel.add(allPOIInThisMap);
        hiddenPOIPanel.add(discoverPOIButton);

        //clear search button
        clearSearchB = new JButton("<HTML><U>Clear All<U><HTML>");
        clearSearchB.setBounds(10, 430, 120, 30);
        clearSearchB.setHorizontalAlignment(SwingConstants.LEFT);
        clearSearchB.setBackground(Color.WHITE);
        clearSearchB.setForeground(Color.BLUE);
        clearSearchB.setBorderPainted(false);
        clearSearchB.addActionListener(this);

        hiddenPOIPanel.add(clearSearchB);

        // add layers
        classroom = new JCheckBox("Classroom");
        classroom.setBounds(10, 10,130,25);
        classroom.addActionListener(this);
        classroom.setBackground(Color.WHITE);

        lab = new JCheckBox("Lab");
        lab.setBounds(10, classroom.getY()+classroom.getHeight(),130,25);
        lab.setBackground(Color.WHITE);
        lab.addActionListener(this);

        restaurant = new JCheckBox("Restaurant");
        restaurant.setBounds(10, lab.getY()+lab.getHeight(),130,25);
        restaurant.setBackground(Color.WHITE);
        restaurant.addActionListener(this);

        collaborative = new JCheckBox("Collab Room");
        collaborative.setBounds(10, restaurant.getY()+restaurant.getHeight(),130,25);
        collaborative.setBackground(Color.WHITE);
        collaborative.addActionListener(this);

        userDefine = new JCheckBox("User Define");
        userDefine.setBounds(10, collaborative.getY()+collaborative.getHeight(),130,25);
        userDefine.setBackground(Color.WHITE);
        userDefine.addActionListener(this);

        userFav = new JCheckBox("User Favorite");
        userFav.setBounds(10, userDefine.getY()+userDefine.getHeight(),130,25);
        userFav.setBackground(Color.WHITE);
        userFav.addActionListener(this);

        washroomLabel = new JLabel("Washroom");
        washroomLabel.setBounds(40, userFav.getY()+userFav.getHeight(),130,25);
        washroomLabel.setBackground(Color.WHITE);

        accessibilityLabel = new JLabel("Accessibility");
        accessibilityLabel.setBounds(40, washroomLabel.getY()+washroomLabel.getHeight(),130,25);
        accessibilityLabel.setBackground(Color.WHITE);

        hiddenPOIPanel.add(classroom);
        hiddenPOIPanel.add(lab);
        hiddenPOIPanel.add(userDefine);
        hiddenPOIPanel.add(userFav);
        hiddenPOIPanel.add(restaurant);
        hiddenPOIPanel.add(collaborative);
        hiddenPOIPanel.add(washroomLabel);
        hiddenPOIPanel.add(accessibilityLabel);

        // add logo
        floorPic=new JLabel();
        try {
            BufferedImage logo = ImageIO.read(new File("pictures/floor.png"));
            floorPic = new JLabel(new ImageIcon(logo));
            floorPic.setBounds((int)(selectFloorB.getWidth()*0.1),(int)(selectFloorB.getHeight()*0.01) , 85, 85);
            selectFloorB.add(floorPic);

            logo = ImageIO.read(new File("pictures/poi.png"));
            poiPic = new JLabel(new ImageIcon(logo));
            poiPic.setBounds((int)(selectFloorB.getWidth()*0.2),(int)(selectFloorB.getHeight()*0.1), 60, 60);
            POIListB.add(poiPic);

            logo = ImageIO.read(new File("pictures/washroom.png"));
            washroomPic = new JLabel(new ImageIcon(logo));
            washroomPic.setBounds(5,userFav.getY()+userFav.getHeight(),30,30 );
            hiddenPOIPanel.add(washroomPic);

            logo = ImageIO.read(new File("pictures/accessibility.png"));
            accessibilityPic = new JLabel(new ImageIcon(logo));
            accessibilityPic.setBounds(5,5+washroomLabel.getY()+washroomLabel.getHeight(),30,30 );
            hiddenPOIPanel.add(accessibilityPic);

        } catch (Exception e) {

        }

        // add temperature and weather icon
        JLabel info=new JLabel("<html>Current<br/>temperature<html>");
        info.setForeground(new Color(255,255,255));
        info.setBounds(10,450,140,30);
        sidePanel.add(info);

        currentWeather = new weather();
        temp=new JLabel(currentWeather.getTemp());
        if(temp.getText() == null){
            temp.setText("is not available");
        }
        temp.setForeground(new Color(255,255,255));
        temp.setBounds(10,470,120,30);
        sidePanel.add(temp);

        try {
            icon = new JLabel(new ImageIcon(new URL(currentWeather.getImage())));
            icon.setBounds(10,500,70,70);
            sidePanel.add(icon);

        } catch (MalformedURLException e) {

        }
//
//        toolBar.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();
//                if(e.getComponent().equals(searchBar) == false){
//
//                    clearSearch();
//
//                    if(allSearchPin != null){
//                        for(int i = 0; i < allSearchPin.size(); i++){
//                            allSearchPin.get(i).setVisible(false);
//                        }
//                    }
//
//                    if(!allPOIInThisMap.getSelectedItem().equals("Please select")){
//                        clearSelectDiscovery();
//                    }
//                }
//                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();
//                hideMessage();
//            }
//        });

        frame.add(map);
        frame.add(textFiled);
        frame.setVisible(true);
        frame.requestFocusInWindow();

        removeOption.setBounds(300, 20, 150, 25);

        favouriteOption = new JButton("Set as Favourite");
        favouriteOption.setBounds(300, 20, 150, 25);
        favouriteOption.setBackground(new Color(79, 38, 131));
        favouriteOption.setForeground(Color.WHITE);
        favouriteOption.setVisible(false);
        favouriteOption.addActionListener(this);

        removeOption.setVisible(false);
        removeOption.addActionListener(this);
        removeOption.setBackground(new Color(79, 38, 131));
        removeOption.setForeground(Color.WHITE);

        customizeOption.setBackground(new Color(79, 38, 131));
        customizeOption.setForeground(Color.WHITE);
        customizeOption.setBounds(300,60,200,25);
        customizeOption.setVisible(false);
        customizeOption.addActionListener(this);
        textFiled.add(favouriteOption);
        textFiled.add(removeOption);
        textFiled.add(customizeOption);

        edit.setBounds(560,60,100,25);
        edit.setVisible(false);
        edit.addActionListener(this);
        textFiled.add(edit);

        changeCoor.setBounds(560,20,150,25);
        changeCoor.setVisible(false);
        changeCoor.addActionListener(this);
        textFiled.add(changeCoor);

        completeCoor.setBounds(560,20,150,25);
        completeCoor.setVisible(false);
        completeCoor.addActionListener(this);
        textFiled.add(completeCoor);

        userDefined = new JButton("Create");
        userDefined.setBounds(500,20,100,25);
        userDefined.setVisible(false);
        userDefined.addActionListener(this);
        textFiled.add(userDefined);

        userCreate = new JLabel("  Do you want to create customize POI?");
        userCreate.setBounds(0,10,350,20);
        userCreate.setVisible(false);
        textFiled.add(userCreate);

        allFavourite = thisUserFavorite.DBFavPOI();

        newUD = new userDefined();


        for(POI a: login.allUserCreatePOI){
            allUserCreated.add(a);
        }


        allUserPin = new createPin(allUserCreated, allFavourite);
        allFavPin = new ArrayList<>();
        allCreatedPin = new ArrayList<>();
        allFavPin = allUserPin.getFav();
        allCreatedPin = allUserPin.getCreated();

        // set layer order
        JLayeredPane layeredPane = frame.getLayeredPane();
        layeredPane.add(currentBuilding, JLayeredPane.POPUP_LAYER);
        layeredPane.add(hiddenFloorPanel,JLayeredPane.POPUP_LAYER);
        layeredPane.add(hiddenPOIPanel,JLayeredPane.POPUP_LAYER);

        refreshAllLayer();

    }


    public void actionPerformed(ActionEvent e) {

        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();

        invalidInput.setVisible(false);
        noMatchResult.setVisible(false);

        if(e.getSource()==back)
        {
            try {
                login newLogin = new login();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            frame.dispose();
        }
        //user clicked on the select floor
        else if (e.getSource() == selectFloorB){
            if (hiddenFloorPanel.isVisible()){
                hiddenFloorPanel.setVisible(false);
                selectFloorB.setBackground(new Color(137,142,140));
            }else{
                hiddenFloorPanel.setVisible(true);
                hiddenPOIPanel.setVisible(false);
                selectFloorB.setBackground(new Color(79, 38, 131));
                POIListB.setBackground(new Color(137,142,140));
            }
        }
        else if (e.getSource() == POIListB){
            if (hiddenPOIPanel.isVisible()){
                hiddenPOIPanel.setVisible(false);
                POIListB.setBackground(new Color(137,142,140));
            }else{
                hiddenPOIPanel.setVisible(true);
                hiddenFloorPanel.setVisible(false);
                selectFloorB.setBackground(new Color(137,142,140));
                POIListB.setBackground(new Color(79, 38, 131));
            }
        }
        else if(e.getSource()==go)
        {
            clearInfor();
            unselectAllLayer();

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

            clearFav();


        }
        else if(e.getSource() == helpGuide){
            hiddenFloorPanel.setVisible(false);
            hiddenPOIPanel.setVisible(false);
            selectFloorB.setBackground(new Color(137,142,140));
            POIListB.setBackground(new Color(137,142,140));
            try {
                File myFile = new File("help.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
        else if(e.getSource() == aboutUs){
            hiddenFloorPanel.setVisible(false);
            hiddenPOIPanel.setVisible(false);
            selectFloorB.setBackground(new Color(137,142,140));
            POIListB.setBackground(new Color(137,142,140));
            moreInfo aboutPage = new moreInfo();
        }
        else if(e.getSource() == middlesexCollege){
            clearInfor();
            unselectAllLayer();
            clearFav();
            hiddenFloorPanel.setVisible(false);
            hiddenPOIPanel.setVisible(false);
            selectFloorB.setBackground(new Color(137,142,140));
            POIListB.setBackground(new Color(137,142,140));
            eachFloor.setSelectedIndex(0);
            currentBuilding.setText("YOU ARE AT Middlesex College");
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
            clearInfor();
            unselectAllLayer();
            clearFav();
            hiddenFloorPanel.setVisible(false);
            hiddenPOIPanel.setVisible(false);
            selectFloorB.setBackground(new Color(137,142,140));
            POIListB.setBackground(new Color(137,142,140));
            eachFloor.setSelectedIndex(0);
            currentBuilding.setText("YOU ARE AT Alumni Stadium");
            try {
                addEachFloor("Alumni Stadium",2,false);
            } catch (Exception ex) {

            }
            for(building aBuilding: Main.allBuildings){
                if(aBuilding.getName().equals("Alumni Stadium")){
                    numButton = aBuilding.getFloor();
                }
            }
            allPOIInThisMap.removeAllItems();

        }
        else if(e.getSource() == stagingBuilding){
            clearInfor();
            unselectAllLayer();
            clearFav();
            hiddenFloorPanel.setVisible(false);
            hiddenPOIPanel.setVisible(false);
            selectFloorB.setBackground(new Color(137,142,140));
            POIListB.setBackground(new Color(137,142,140));
            eachFloor.setSelectedIndex(0);
            currentBuilding.setText("YOU ARE AT Staging Building");
            try {
                addEachFloor("Staging Building",2,false);
            } catch (Exception ex) {
            }
            for(building aBuilding: Main.allBuildings){
                if(aBuilding.getName().equals("Staging Building")){
                    numButton = aBuilding.getFloor();
                }
            }
            allPOIInThisMap.removeAllItems();
        }

        // search POI
        else if(e.getSource() == searchButton){

            clearFav();
            if(allSearchPin != null){
                for(int i = 0; i < allSearchPin.size(); i++){
                    allSearchPin.get(i).setVisible(false);
                }
            }

            unselectAllLayer();

            POI[] searchResult = checkSearchInput();
            ArrayList<POI> searchPOI = new ArrayList<>();

            if(searchResult == null){

            }
            else{
                for(POI a: searchResult){
                    searchPOI.add(a);
                }
                JLabel searchPinLabel= floorList.get(whichFLoor - 1);

                try {
                    createPin searchPin= new createPin(searchPOI);
                    allSearchPin = searchPin.getSearch();
                } catch (Exception ex) {

                }
                for(int i = 0; i < allSearchPin.size(); i++){
                    allSearchPin.get(i).setLocation(searchResult[i].getX() - 30,searchResult[i].getY() - 35);
                    allSearchPin.get(i).setSize(50, 50);

                    searchPinLabel.add(allSearchPin.get(i));
                    allSearchPin.get(i).setVisible(true);
                }
                frame.repaint();
            }
            hideMessage();

        }
        // discover all POI in this map
        else if (e.getSource() == discoverPOIButton) {
            clearFav();
            unselectAllLayer();

            if(allPOIInThisMap.getSelectedItem().toString().equals("Please select")){

            }
            else{
                if(discoveryPin != null && discoveryPin.isVisible()){
                    discoveryPin.removeAll();
                    discoveryPin.setVisible(false);

                }
                userCreate.setVisible(false);
                userDefined.setVisible(false);

                // create the discovery pin
                try {
                    BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                    discoveryPin = new JLabel(new ImageIcon(pin));
                } catch (Exception ex) {

                }
                int thisX = 0;
                int thisY = 0;

                for(POI thisPOI : Main.allPOI){
                    if(thisPOI.getBuilding().equals(whichBuilding) && thisPOI.getFloor() == whichFLoor
                            && thisPOI.getName().equals(allPOIInThisMap.getSelectedItem().toString())){
                        thisX = thisPOI.getX();
                        thisY = thisPOI.getY();
                        customizeOption.setVisible(false);
                        edit.setVisible(false);
                        changeCoor.setVisible(false);
                        completeCoor.setVisible(false);
                    }
                }
                for(POI i: allUserCreated)
                {
                    if(i.getBuilding().equals(whichBuilding) && i.getFloor() == whichFLoor
                            && i.getName().equals(allPOIInThisMap.getSelectedItem().toString())){
                        thisX = i.getX();
                        thisY = i.getY();
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
        else if (e.getSource()==removeOption)
        {
            hideMessage();

            tempFav=tempDele;
            if(theFavPinInMenuBar != null){
                theFavPinInMenuBar.setVisible(false);
            }

            try {
                thisUserFavorite.deleteFav(tempDele);
            }
            catch (Exception f)
            {

            }
            allFavourite.remove(tempDele);
            try {
                allFavourite = thisUserFavorite.DBFavPOI();
            }
            catch (Exception ef)
            {

            }
            for(int i=0;i<allFavPin.size();i++)
            {

                if(allFavPin.get(i).getX()==tempDele.getX()-30&&allFavPin.get(i).getY()==tempDele.getY()-35)
                {
                    allFavPin.get(i).setVisible(false);
                    allFavPin.remove(i);

                }
            }


            try{
                refreshFavorite();
                frame.repaint();
            }
            catch (Exception ex)
            {
            }

        }
        else if (e.getSource()==favouriteOption)
        {
            favouriteOption.setVisible(false);
            removeOption.setVisible(true);
            tempDele=tempFav;
            if(theFavPinInMenuBar != null){
                theFavPinInMenuBar.setVisible(false);
            }

            try {
                thisUserFavorite.addFavroite(tempFav);
            }
            catch (Exception f)
            {

            }
            allFavourite.add(tempFav);
            try{
                BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                JLabel tempIcon = new JLabel(new ImageIcon(pin));
                allFavPin.add(tempIcon);

            }
            catch(Exception m){

            }
            try{
                refreshFavorite();
                frame.repaint();
            }
            catch (Exception ex)
            {
            }

        }
        else if(e.getSource()==customizeOption)
        {
            hideMessage();

            boolean bothList=false;
            for(int i=0;i<allFavourite.size();i++)
            {
                if(allFavourite.get(i).checkEqual(allFavourite.get(i),tempUserCreated))
                {
                    bothList=true;
                }
            }
            if(bothList)
            {
                try {
                    thisUserFavorite.deleteFav(tempUserCreated);
                }
                catch (Exception f)
                {
                }
                allFavourite.remove(tempUserCreated);
                try {
                    allFavourite = thisUserFavorite.DBFavPOI();
                }
                catch (Exception ef)
                {

                }

                // delete user defined poi enter from fav menu bar
                for(int i = 0; i < allFavPin.size(); i++){
                    if(allFavPin.get(i).isVisible()){
                        int xtemp = (int) allFavPin.get(i).getLocation().getX();
                        int ytemp = (int) allFavPin.get(i).getLocation().getY();
                        if(xtemp == tempUserCreated.getX() - 30 && ytemp == tempUserCreated.getY() - 35){
                            mapList.get(whichFLoor-1).remove(allFavPin.get(i));
                            allFavPin.get(i).setVisible(false);
                        }
                    }
                }
                allFavPin.remove(allFavPin.size()-1);

                try{
                    refreshFavorite();
                    frame.repaint();
                }
                catch (Exception ex)
                {

                }

            }
            try {
                newUD.deleteCreate(tempUserCreated);

            }
            catch (Exception ud)
            {

            }
            allUserCreated.remove(tempUserCreated);
            try {
                allUserCreated = newUD.DBDefPOI();
            }
            catch (Exception ef)
            {

            }

            allPOIInThisMap.removeItem(tempUserCreated.getName());

            try {
                refreshFavorite();
            } catch (SQLException ex) {

            } catch (ClassNotFoundException ex) {

            }

            if(tempUserCreated.getType().equals("Classroom"))
            {
                for(int i=0;i<pinClassroom.size();i++)
                {
                    if(pinClassroom.get(i).getX()==tempUserCreated.getX()-30&&pinClassroom.get(i).getY()==tempUserCreated.getY()-35)
                    {
                        pinClassroom.get(i).setVisible(false);
                        pinClassroom.remove(i);
                        allCreatedPin.remove(allCreatedPin.size()-1);
                    }
                }
            }
            else if(tempUserCreated.getType().equals("Restaurant"))
            {
                for(int i=0;i<pinRestaurant.size();i++)
                {
                    if(pinRestaurant.get(i).getX()==tempUserCreated.getX()-30&&pinRestaurant.get(i).getY()==tempUserCreated.getY()-35)
                    {
                        pinRestaurant.get(i).setVisible(false);
                        pinRestaurant.remove(i);
                        allCreatedPin.remove(allCreatedPin.size()-1);
                    }
                }
            }
            else if(tempUserCreated.getType().equals("Lab"))
            {
                for(int i=0;i<pinLab.size();i++)
                {
                    if(pinLab.get(i).getX()==tempUserCreated.getX()-30&&pinLab.get(i).getY()==tempUserCreated.getY()-35)
                    {
                        pinLab.get(i).setVisible(false);
                        pinLab.remove(i);
                        allCreatedPin.remove(allCreatedPin.size()-1);
                    }
                }
            }
            else if(tempUserCreated.getType().equals("Collaborative Room"))
            {
                for(int i=0;i<pinCollaborative.size();i++)
                {
                    if(pinCollaborative.get(i).getX()==tempUserCreated.getX()-30&&pinCollaborative.get(i).getY()==tempUserCreated.getY()-35)
                    {
                        pinCollaborative.get(i).setVisible(false);
                        pinCollaborative.remove(i);
                        allCreatedPin.remove(allCreatedPin.size()-1);
                    }
                }
            }



            for(int i=0;i<allCreatedPin.size();i++)
            {

                if(allCreatedPin.get(i).getX()==tempUserCreated.getX()-30&&allCreatedPin.get(i).getY()==tempUserCreated.getY()-35)
                {
                    allCreatedPin.get(i).setVisible(false);
                    allCreatedPin.remove(i);
                    if(tempUserCreated.getType().equals("Classroom"))
                    {
                        pinClassroom.remove(pinClassroom.size()-1);
                    }
                    else if(tempUserCreated.getType().equals("Restaurant"))
                    {
                        pinRestaurant.remove(pinRestaurant.size()-1);
                    }
                    else if(tempUserCreated.getType().equals("Lab"))
                    {
                        pinLab.remove(pinLab.size()-1);
                    }
                    else if(tempUserCreated.getType().equals("Collaborative Room"))
                    {
                        pinCollaborative.remove(pinCollaborative.size()-1);
                    }

                }
            }

            try {
                refreshAllLayer();
            } catch (SQLException ex) {

            } catch (ClassNotFoundException ex) {

            }

            frame.repaint();


        }
        else if(e.getSource()==userDefined)
        {

            customizePOI cp = new customizePOI(this,Xcoordinate,Ycoordinate,whichBuilding,whichFLoor);

        }
        else if(e.getSource()==edit)
        {
            editUserDefined eu=new editUserDefined(this,tempUserCreated,whichBuilding,whichFLoor);
        }
        else if(e.getSource()==changeCoor)
        {
            hiddenSamePin();
            editMode=true;
            changeCoor.setVisible(false);
            completeCoor.setVisible(true);
            if(allSearchPin!=null) {
                for (int i = 0; i < allSearchPin.size(); i++) {
                    if (allSearchPin.get(i).isVisible()) {
                        underSearch = true;
                    }
                }
            }

            for(int i=0;i<allCreatedPin.size();i++)
            {
                if(allCreatedPin.get(i).isVisible()){
                    underCreated=true;
                }
            }
            for(int i=0;i<allFavPin.size();i++)
            {
                if(allFavPin.get(i).isVisible())
                {
                    underFav=true;
                }
            }


            if(discoveryPin!=null&&discoveryPin.isVisible()){

                discoveryPin.addMouseMotionListener(this);
                tempMoving = discoveryPin;

            }
            else {

                if (underSearch) {
                    for (int i = 0; i < allSearchPin.size(); i++) {
                        if (allSearchPin.get(i).getX() + 30 == tempUserCreated.getX() && allSearchPin.get(i).getY() + 35 == tempUserCreated.getY()) {
                            allSearchPin.get(i).addMouseMotionListener(this);
                            tempPinID = i;
                            tempMoving = allSearchPin.get(i);
                        }
                    }
                } else if (underCreated) {
                    for (int i = 0; i < allCreatedPin.size(); i++) {
                        if (allCreatedPin.get(i).getX() + 30 == tempUserCreated.getX() && allCreatedPin.get(i).getY() + 35 == tempUserCreated.getY()) {
                            allCreatedPin.get(i).addMouseMotionListener(this);
                            tempPinID = i;
                            tempMoving = allCreatedPin.get(i);
                        }
                    }
                } else if (underFav) {
                    for (int i = 0; i < allFavPin.size(); i++) {
                        if (allFavPin.get(i).getX() + 30 == tempUserCreated.getX() && allFavPin.get(i).getY() + 35 == tempUserCreated.getY()) {
                            allFavPin.get(i).addMouseMotionListener(this);
                            tempPinID = i;
                            tempMoving = allFavPin.get(i);
                        }
                    }
                }
//                else {
                if(underSearch == false && underFav == false && underCreated == false){



                    if (tempUserCreated.getType().equals("Classroom")) {
                        for (int i = 0; i < pinClassroom.size(); i++) {
                            if (pinClassroom.get(i).getX() + 30 == tempUserCreated.getX() && pinClassroom.get(i).getY() + 35 == tempUserCreated.getY()) {
                                pinClassroom.get(i).addMouseMotionListener(this);
                                tempPinID = i;
                                tempMoving = pinClassroom.get(i);
                            }
                        }

                    } else if (tempUserCreated.getType().equals("Restaurant")) {
                        for (int i = 0; i < pinRestaurant.size(); i++) {
                            if (pinRestaurant.get(i).getX() + 30 == tempUserCreated.getX() && pinRestaurant.get(i).getY() + 35 == tempUserCreated.getY()) {
                                pinRestaurant.get(i).addMouseMotionListener(this);
                                tempPinID = i;
                                tempMoving = pinRestaurant.get(i);
                            }
                        }

                    } else if (tempUserCreated.getType().equals("Lab")) {
                        for (int i = 0; i < pinLab.size(); i++) {
                            if (pinLab.get(i).getX() + 30 == tempUserCreated.getX() && pinLab.get(i).getY() + 35 == tempUserCreated.getY()) {
                                pinLab.get(i).addMouseMotionListener(this);
                                tempPinID = i;
                                tempMoving = pinLab.get(i);
                            }
                        }

                    } else if (tempUserCreated.getType().equals("Collaborative Room")) {
                        for (int i = 0; i < pinCollaborative.size(); i++) {
                            if (pinCollaborative.get(i).getX() + 30 == tempUserCreated.getX() && pinCollaborative.get(i).getY() + 35 == tempUserCreated.getY()) {
                                pinCollaborative.get(i).addMouseMotionListener(this);
                                tempPinID = i;
                                tempMoving = pinCollaborative.get(i);
                            }
                        }

                    }
                }

            }

            for(int i=0;i<mapList.size();i++)
            {
                if(mapList.get(i).isVisible())
                {
                    floorList.get(i).removeMouseListener(listenEachFloor.get(i));
                }
            }

        }
        else if(e.getSource()==completeCoor){
            showSamePin();
            editMode=false;
            completeCoor.setVisible(false);
            changeCoor.setVisible(true);
            int tempX=0;
            int tempY=0;
            if(discoveryPin!=null&&discoveryPin.isVisible()) {
                discoveryPin.removeMouseMotionListener(this);
                tempX = discoveryPin.getX() + 30;
                tempY = discoveryPin.getY() + 35;

            }else{



                if(underSearch)
                {
                    allSearchPin.get(tempPinID).removeMouseMotionListener(this);
                    tempX = allSearchPin.get(tempPinID).getX() + 30;
                    tempY = allSearchPin.get(tempPinID).getY() + 35;
                }
                else if(underCreated){
                    allCreatedPin.get(tempPinID).removeMouseMotionListener(this);
                    tempX = allCreatedPin.get(tempPinID).getX() + 30;
                    tempY = allCreatedPin.get(tempPinID).getY() + 35;
                }
                else if(underFav){
                    allFavPin.get(tempPinID).removeMouseMotionListener(this);
                    tempX = allFavPin.get(tempPinID).getX() + 30;
                    tempY = allFavPin.get(tempPinID).getY() + 35;
                }

                //            else{
                if(underSearch == false && underCreated == false && underFav == false){


                    if (tempUserCreated.getType().equals("Classroom")) {
                        pinClassroom.get(tempPinID).removeMouseMotionListener(this);
                        tempX = pinClassroom.get(tempPinID).getX() + 30;
                        tempY = pinClassroom.get(tempPinID).getY() + 35;
                    } else if (tempUserCreated.getType().equals("Restaurant")) {
                        pinRestaurant.get(tempPinID).removeMouseMotionListener(this);
                        tempX = pinRestaurant.get(tempPinID).getX() + 30;
                        tempY = pinRestaurant.get(tempPinID).getY() + 35;
                    } else if (tempUserCreated.getType().equals("Lab")) {
                        pinLab.get(tempPinID).removeMouseMotionListener(this);
                        tempX = pinLab.get(tempPinID).getX() + 30;
                        tempY = pinLab.get(tempPinID).getY() + 35;
                    } else if (tempUserCreated.getType().equals("Collaborative Room")) {
                        pinCollaborative.get(tempPinID).removeMouseMotionListener(this);
                        tempX = pinCollaborative.get(tempPinID).getX() + 30;
                        tempY = pinCollaborative.get(tempPinID).getY() + 35;
                    }

                }
//            }
            }

            if(tempDele!=null) {
                if (tempUserCreated.checkEqual(tempUserCreated, tempDele)) {
                    try {
                        editUserFavCoordinate(tempUserCreated, tempX, tempY);
                        allFavourite = thisUserFavorite.DBFavPOI();
                    } catch (Exception ed) {

                    }

                }
            }

            try {
                editUserCreatedCoordinate(tempUserCreated, tempX, tempY);
                allUserCreated=newUD.DBDefPOI();
            }
            catch (Exception ed)
            {

            }
            for(int i=0;i<mapList.size();i++)
            {
                if(mapList.get(i).isVisible())
                {
                    floorList.get(i).addMouseListener(listenEachFloor.get(i));
                }
            }
            tempPinID=-1;
            underFav=false;
            underCreated=false;
            underSearch=false;
        }
        else {
            check();
            frame.repaint();
        }

        // reset the search bar hint
        if(e.getSource() == clearSearchB){
            clearSearch();
            clearSelectDiscovery();
            unselectAllLayer();
        }

        // jump to the favorite POI
        userFavorite thisUserFavorite = null;
        String[] thisUserFavArray;

        Object source = e.getSource();

        // if source menu bar
        if(source instanceof JMenuItem){
            // find parent level menu, confirm should click favorite POI or not
            JMenuItem sourceMenu = (JMenuItem) e.getSource();
            JPopupMenu parent = (JPopupMenu) sourceMenu.getParent();
            JMenuItem parentMenu = (JMenuItem) parent.getInvoker();

            // not click favorite
            if(parentMenu.getText().equals("Building") || parentMenu.getText().equals("Help") || parentMenu.getText().equals("About")){

            }
            else{

                hiddenFloorPanel.setVisible(false);
                hiddenPOIPanel.setVisible(false);
                selectFloorB.setBackground(new Color(137,142,140));
                POIListB.setBackground(new Color(137,142,140));

                JMenuItem clickedFav = (JMenuItem) e.getSource();
                String name = clickedFav.getText();

                JPopupMenu clickedFloorItem = (JPopupMenu) clickedFav.getParent();
                JMenuItem clickedFloor = (JMenuItem) clickedFloorItem.getInvoker();
                String floor = clickedFloor.getText();

                JPopupMenu clickedBuildingItem = (JPopupMenu) clickedFloor.getParent();
                JMenuItem clickedBuilding = (JMenuItem) clickedBuildingItem.getInvoker();
                String building = clickedBuilding.getText();


                try {
                    jumpToFav(name, floor, building);
                } catch (Exception ex) {

                }
            }
        }


        if(e.getSource() == edit ||
                e.getSource() == customizeOption ){

            try {
                refreshFavorite();
                refreshAllLayer();
            } catch (Exception ex) {

            }
        }
        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();


    }


    private  void check() {
        try {
            refreshAllLayer();
        } catch (Exception ex) {

        }
        if (classroom.isSelected()) {
            int tempNum = 0;
            for (int i = 0; i < mapList.size(); i++) {
                if (mapList.get(i).isVisible()) {
                    JLabel tempLable = floorList.get(i);
                    for (POI eachPOI : Main.allPOI) {
                        if (eachPOI.getType().equals("Classroom") && eachPOI.getBuilding().equals(whichBuilding) && eachPOI.getFloor() == i + 1) {

                            pinClassroom.get(tempNum).setLocation(eachPOI.getX() - 30, eachPOI.getY() - 35);
                            pinClassroom.get(tempNum).setSize(50, 50);
                            tempLable.add(pinClassroom.get(tempNum));
                            tempNum++;
                        }
                    }
                    for (int j = 0; j < allUserCreated.size(); j++) {
                        if (allUserCreated.get(j).getType().equals("Classroom") && allUserCreated.get(j).getBuilding().equals(whichBuilding) && allUserCreated.get(j).getFloor() == i + 1) {

                            pinClassroom.get(tempNum).setLocation(allUserCreated.get(j).getX() - 30, allUserCreated.get(j).getY() - 35);
                            pinClassroom.get(tempNum).setSize(50, 50);
                            tempLable.add(pinClassroom.get(tempNum));
                            tempNum++;
                        }
                    }
                }
            }
            for (int k = 0; k < tempNum; k++) {
                pinClassroom.get(k).setVisible(true);
            }
        } else {
            for (int i = 0; i < pinClassroom.size(); i++) {
                pinClassroom.get(i).setVisible(false);
                newName.setText(" ");
                newNumber.setText(" ");
                Description.setText(" ");
                favouriteOption.setVisible(false);
                removeOption.setVisible(false);
                customizeOption.setVisible(false);
                edit.setVisible(false);
                changeCoor.setVisible(false);
                completeCoor.setVisible(false);

            }
        }

        if (restaurant.isSelected()) {
            int tempNum = 0;
            for (int i = 0; i < mapList.size(); i++) {
                if (mapList.get(i).isVisible()) {
                    JLabel tempLable = floorList.get(i);
                    for (POI eachPOI : Main.allPOI) {
                        if (eachPOI.getType().equals("Restaurant") && eachPOI.getBuilding().equals(whichBuilding) && eachPOI.getFloor() == i + 1) {
                            pinRestaurant.get(tempNum).setLocation(eachPOI.getX() - 30, eachPOI.getY() - 35);
                            pinRestaurant.get(tempNum).setSize(50, 50);
                            tempLable.add(pinRestaurant.get(tempNum));
                            tempNum++;

                        }
                    }

                    for (int j = 0; j < allUserCreated.size(); j++) {
                        if (allUserCreated.get(j).getType().equals("Restaurant") && allUserCreated.get(j).getBuilding().equals(whichBuilding) && allUserCreated.get(j).getFloor() == i + 1) {

                            pinRestaurant.get(tempNum).setLocation(allUserCreated.get(j).getX() - 30, allUserCreated.get(j).getY() - 35);
                            pinRestaurant.get(tempNum).setSize(50, 50);
                            tempLable.add(pinRestaurant.get(tempNum));
                            tempNum++;
                        }
                    }
                }
            }
            for (int k = 0; k < tempNum; k++) {
                pinRestaurant.get(k).setVisible(true);
            }
        } else {
            for (int i = 0; i < pinRestaurant.size(); i++) {
                pinRestaurant.get(i).setVisible(false);
                newName.setText(" ");
                newNumber.setText(" ");
                Description.setText(" ");
                favouriteOption.setVisible(false);
                removeOption.setVisible(false);
                customizeOption.setVisible(false);
                edit.setVisible(false);
                changeCoor.setVisible(false);
                completeCoor.setVisible(false);

            }
        }


        if (lab.isSelected()) {
            int tempNum = 0;
            for (int i = 0; i < mapList.size(); i++) {
                if (mapList.get(i).isVisible()) {
                    JLabel tempLable = floorList.get(i);
                    for (POI eachPOI : Main.allPOI) {
                        if (eachPOI.getType().equals("Lab") && eachPOI.getBuilding().equals(whichBuilding) && eachPOI.getFloor() == i + 1) {
                            pinLab.get(tempNum).setLocation(eachPOI.getX() - 30, eachPOI.getY() - 35);
                            pinLab.get(tempNum).setSize(50, 50);
                            tempLable.add(pinLab.get(tempNum));
                            tempNum++;
                        }
                    }
                    for (int j = 0; j < allUserCreated.size(); j++) {
                        if (allUserCreated.get(j).getType().equals("Lab") && allUserCreated.get(j).getBuilding().equals(whichBuilding) && allUserCreated.get(j).getFloor() == i + 1) {

                            pinLab.get(tempNum).setLocation(allUserCreated.get(j).getX() - 30, allUserCreated.get(j).getY() - 35);
                            pinLab.get(tempNum).setSize(50, 50);
                            tempLable.add(pinLab.get(tempNum));
                            tempNum++;
                        }
                    }
                }
            }
            for (int k = 0; k < tempNum; k++) {
                pinLab.get(k).setVisible(true);
            }
        } else {
            for (int i = 0; i < pinLab.size(); i++) {
                pinLab.get(i).setVisible(false);
                newName.setText(" ");
                newNumber.setText(" ");
                Description.setText(" ");
                favouriteOption.setVisible(false);
                removeOption.setVisible(false);
                customizeOption.setVisible(false);
                edit.setVisible(false);
                changeCoor.setVisible(false);
                completeCoor.setVisible(false);

            }
        }

        if (collaborative.isSelected()) {
            int tempNum = 0;
            for (int i = 0; i < mapList.size(); i++) {
                if (mapList.get(i).isVisible()) {
                    JLabel tempLable = floorList.get(i);
                    for (POI eachPOI : Main.allPOI) {
                        if (eachPOI.getType().equals("Collaborative Room") && eachPOI.getBuilding().equals(whichBuilding) && eachPOI.getFloor() == i + 1) {
                            pinCollaborative.get(tempNum).setLocation(eachPOI.getX() - 30, eachPOI.getY() - 35);
                            pinCollaborative.get(tempNum).setSize(50, 50);
                            tempLable.add(pinCollaborative.get(tempNum));
                            tempNum++;
                        }
                    }
                    for (int j = 0; j < allUserCreated.size(); j++) {
                        if (allUserCreated.get(j).getType().equals("Collaborative Room") && allUserCreated.get(j).getBuilding().equals(whichBuilding) && allUserCreated.get(j).getFloor() == i + 1) {

                            pinCollaborative.get(tempNum).setLocation(allUserCreated.get(j).getX() - 30, allUserCreated.get(j).getY() - 35);
                            pinCollaborative.get(tempNum).setSize(50, 50);
                            tempLable.add(pinCollaborative.get(tempNum));
                            tempNum++;
                        }
                    }
                }
            }
            for (int k = 0; k < tempNum; k++) {
                pinCollaborative.get(k).setVisible(true);
            }

        } else {
            for (int i = 0; i < pinCollaborative.size(); i++) {
                pinCollaborative.get(i).setVisible(false);
                newName.setText(" ");
                newNumber.setText(" ");
                Description.setText(" ");
                favouriteOption.setVisible(false);
                removeOption.setVisible(false);
                customizeOption.setVisible(false);
                edit.setVisible(false);
                changeCoor.setVisible(false);
                completeCoor.setVisible(false);

            }
        }
        if (userFav.isSelected()&&allFavourite.size()>0) {
            int tempNum = 0;
            for (int i = 0; i < mapList.size(); i++) {
                if (mapList.get(i).isVisible()) {
                    JLabel tempLable = floorList.get(i);
                    for (int j = 0; j < allFavourite.size(); j++) {
                        if (allFavourite.get(j).getBuilding().equals(whichBuilding) && allFavourite.get(j).getFloor() == i + 1) {
                            allFavPin.get(tempNum).setLocation(allFavourite.get(j).getX() - 30, allFavourite.get(j).getY() - 35);
                            allFavPin.get(tempNum).setSize(50, 50);
                            tempLable.add(allFavPin.get(tempNum));
                            tempNum++;
                        }
                    }

                }
                for (int k = 0; k < tempNum; k++) {
                    allFavPin.get(k).setVisible(true);
                }

            }
        } else {
            for (int i = 0; i < allFavPin.size(); i++) {
                allFavPin.get(i).setVisible(false);
                newName.setText(" ");
                newNumber.setText(" ");
                Description.setText(" ");
                favouriteOption.setVisible(false);
                removeOption.setVisible(false);
                customizeOption.setVisible(false);
                edit.setVisible(false);
                changeCoor.setVisible(false);
                completeCoor.setVisible(false);

            }
        }
        if (userDefine.isSelected()) {
            int tempNum = 0;
            for (int i = 0; i < mapList.size(); i++) {
                if (mapList.get(i).isVisible()) {
                    JLabel tempLable = floorList.get(i);
                    for (int j = 0; j < allUserCreated.size(); j++) {
                        if (allUserCreated.get(j).getBuilding().equals(whichBuilding) && allUserCreated.get(j).getFloor() == i + 1) {
                            allCreatedPin.get(tempNum).setLocation(allUserCreated.get(j).getX() - 30, allUserCreated.get(j).getY() - 35);
                            allCreatedPin.get(tempNum).setSize(50, 50);
                            tempLable.add(allCreatedPin.get(tempNum));
                            tempNum++;
                        }
                    }

                }
                for (int k = 0; k < tempNum; k++) {
                    allCreatedPin.get(k).setVisible(true);
                }

            }

        } else {
            for (int i = 0; i < allCreatedPin.size(); i++) {
                allCreatedPin.get(i).setVisible(false);
                newName.setText(" ");
                newNumber.setText(" ");
                Description.setText(" ");
                favouriteOption.setVisible(false);
                removeOption.setVisible(false);
                customizeOption.setVisible(false);
                edit.setVisible(false);
                changeCoor.setVisible(false);
                completeCoor.setVisible(false);

            }
        }
    }


    // find the array of all POI's name in current map from all POI array
    public String[] discoveryPOI(String building){
        ArrayList<String> temp = new ArrayList<>();
        for(POI aPOI : Main.allPOI){
            if(aPOI.getBuilding().equals(building) &&
                    aPOI.getFloor() == whichFLoor){
                temp.add(aPOI.getName());
            }
        }
        for(POI i : allUserCreated)
        {
            if(i.getBuilding().equals(building) &&
                    i.getFloor() == whichFLoor)
                temp.add(i.getName());
        }
        String[] result = temp.toArray(new String[0]);
        return result;
    }

    // this function return how many floor of this building
    public int findFloor(String thisBuilding){
        for(building aBuilding: Main.allBuildings){
            if(aBuilding.getName().equals(thisBuilding)){
                return aBuilding.getFloor();
            }
        }
        return 0;

    }

    // create a map list for each building
    public void addEachFloor(String current, int numFloor, boolean newMap) throws SQLException, ClassNotFoundException {

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

        // create all layer pin
        if(newMap == true){
            allPin = new createPin(allUserCreated, allFavourite, Main.allPOI);
            pinClassroom = allPin.getClassroom();
            pinLab = allPin.getLab();
            pinRestaurant = allPin.getRestaurant();
            pinCollaborative = allPin.getCollaborative();
            totalPin = allPin.getTotal();
        }
        else{
            refreshAllLayer();
        }

        for (int i = 1; i <= numFloor; i++) {
            eachFloor.addItem("Floor" + i);
            try {
                BufferedImage differentFloor = ImageIO
                        .read(new File("pictures/" + whichBuilding + "/floor" + i + ".jpg"));
                showFloor = new JLabel(new ImageIcon(differentFloor));
                floorList.add(showFloor);
                map = new JScrollPane(showFloor, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                map.setBounds(sidePanel.getWidth(), 0, frame.getWidth()-sidePanel.getWidth()-10, 500);
                map.setVisible(false);
                frame.add(map);
                mapList.add(map);

                MouseListener ml=new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        Xcoordinate = e.getX();
                        Ycoordinate = e.getY();
                        X.setText("X=" + e.getX());
                        Y.setText("Y=" + e.getY());
                        userDefined.setVisible(true);
                        userCreate.setVisible(true);
                        newName.setText(" ");
                        newNumber.setText(" ");
                        Description.setText(" ");
                        favouriteOption.setVisible(false);
                        removeOption.setVisible(false);
                        customizeOption.setVisible(false);
                        edit.setVisible(false);
                        completeCoor.setVisible(false);
                        changeCoor.setVisible(false);
                        for (int i = 0; i < totalPin.size(); i++) {
                            if (totalPin.get(i).isVisible()) {

                                if ((e.getX() >= totalPin.get(i).getX() && e.getX() <= totalPin.get(i).getX() + totalPin.get(i).getSize().getWidth()) && (e.getY() >= totalPin.get(i).getY() && e.getY() <= totalPin.get(i).getY() + totalPin.get(i).getSize().getHeight())) {
                                    textFiled.repaint();
                                    userDefined.setVisible(false);
                                    userCreate.setVisible(false);

                                    showMessage(totalPin.get(i).getX(), totalPin.get(i).getY());
                                }
                            }
                        }
                        if (allSearchPin != null) {
                            for (int j = 0; j < allSearchPin.size(); j++) {
                                if (allSearchPin.get(j).isVisible()) {
                                    if ((e.getX() >= allSearchPin.get(j).getX() && e.getX() <= allSearchPin.get(j).getX() + allSearchPin.get(j).getSize().getWidth()) && (e.getY() >= allSearchPin.get(j).getY() && e.getY() <= allSearchPin.get(j).getY() + allSearchPin.get(j).getSize().getHeight())) {
                                        textFiled.repaint();
                                        userDefined.setVisible(false);
                                        userCreate.setVisible(false);
                                        showMessage(allSearchPin.get(j).getX(), allSearchPin.get(j).getY());
                                    }
                                }


                            }
                        } else if (discoveryPin != null) {
                            if ((e.getX() >= discoveryPin.getX() && e.getX() <= discoveryPin.getX() + discoveryPin.getSize().getWidth()) && (e.getY() >= discoveryPin.getY() && e.getY() <= discoveryPin.getY() + discoveryPin.getSize().getHeight())) {
                                textFiled.repaint();
                                userDefined.setVisible(false);
                                userCreate.setVisible(false);
                                showMessage(discoveryPin.getX(), discoveryPin.getY());
                            }

                        }
                        if (allFavPin.size() > 0) {

                            for (int j = 0; j < allFavPin.size(); j++) {
                                if (allFavPin.get(j).isVisible()) {

                                    if ((e.getX() >= allFavPin.get(j).getX() && e.getX() <= allFavPin.get(j).getX() + allFavPin.get(j).getSize().getWidth()) && (e.getY() >= allFavPin.get(j).getY() && e.getY() <= allFavPin.get(j).getY() + allFavPin.get(j).getSize().getHeight())) {
                                        textFiled.repaint();
                                        userDefined.setVisible(false);
                                        userCreate.setVisible(false);
                                        showMessage(allFavPin.get(j).getX(), allFavPin.get(j).getY());
                                    }
                                }


                            }


                        }
                        if (allCreatedPin.size() > 0) {

                            for (int j = 0; j < allCreatedPin.size(); j++) {
                                if (allCreatedPin.get(j).isVisible()) {

                                    if ((e.getX() >= allCreatedPin.get(j).getX() && e.getX() <= allCreatedPin.get(j).getX() + allCreatedPin.get(j).getSize().getWidth()) && (e.getY() >= allCreatedPin.get(j).getY() && e.getY() <= allCreatedPin.get(j).getY() + allCreatedPin.get(j).getSize().getHeight())) {
                                        textFiled.repaint();
                                        userDefined.setVisible(false);
                                        userCreate.setVisible(false);
                                        showMessage(allCreatedPin.get(j).getX(), allCreatedPin.get(j).getY());
                                    }
                                }


                            }


                        }

                    }


                };
                showFloor.addMouseListener(ml);
                listenEachFloor.add(ml);

                frame.add(X);
                frame.add(Y);

            } catch (Exception e) {

            }
        }
    }

    // check search bar input, return matched POI list, or null
    private POI[] checkSearchInput(){

        ArrayList<POI> temp = new ArrayList<>();
        String input = searchBar.getText();
        if(input.equals("") || input.equals("Search POI")){
            invalidInput.setVisible(true);
            return null;
        }
        for(POI thisPOI:Main.allPOI){
            if(thisPOI.getBuilding().equals(whichBuilding)){
                if(thisPOI.getFloor() == whichFLoor){
                    if(thisPOI.getName().equalsIgnoreCase(input)
                            || thisPOI.getName().toUpperCase().contains(input.toUpperCase())
                            || thisPOI.getRoomNumber().equalsIgnoreCase(input)
                            || thisPOI.getDescription().equalsIgnoreCase(input)
                            || thisPOI.getDescription().toUpperCase().contains(input.toUpperCase())){
                        temp.add(thisPOI);

                    }
                }
            }

        }
        for(POI thisPOI:allUserCreated){
            if(thisPOI.getBuilding().equals(whichBuilding)){
                if(thisPOI.getFloor() == whichFLoor){
                    if(thisPOI.getName().equalsIgnoreCase(input)
                            || thisPOI.getName().toUpperCase().contains(input.toUpperCase())
                            || thisPOI.getRoomNumber().equalsIgnoreCase(input)
                            || thisPOI.getDescription().equalsIgnoreCase(input)
                            || thisPOI.getDescription().toUpperCase().contains(input.toUpperCase())){
                        temp.add(thisPOI);

                    }
                }
            }

        }


        if(temp.isEmpty()){
            noMatchResult.setVisible(true);
            return null;
        }
        POI[] result = new POI[temp.size()];
        for(int i =0; i < temp.size(); i++){
            result[i] = temp.get(i);
        }
        return result;

    }


    // unselect all layers
    private void unselectAllLayer(){
        classroom.setSelected(false);
        lab.setSelected(false);
        restaurant.setSelected(false);
        collaborative.setSelected(false);
        userDefine.setSelected(false);
        userFav.setSelected(false);

        for(JLabel aPin: pinClassroom){
            aPin.setVisible(false);
        }

        for(JLabel aPin: pinLab){
            aPin.setVisible(false);
        }

        for(JLabel aPin: pinRestaurant){
            aPin.setVisible(false);
        }

        for(JLabel aPin: pinCollaborative){
            aPin.setVisible(false);
        }

    }

    // clear search bar history
    private void clearSearch(){
        searchBar.setText("Search POI");
        searchBar.setFont(new Font("Tahoma", Font.ITALIC, 11));
        searchBar.setForeground(Color.GRAY);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();
        noMatchResult.setVisible(false);
        invalidInput.setVisible(false);

        if(allSearchPin != null){
            for(int i = 0; i < allSearchPin.size(); i++){
                allSearchPin.get(i).setVisible(false);
            }
        }

    }

    //clear selected discover POI pin, set select item to default
    private void clearSelectDiscovery(){
        if(discoveryPin != null){
            discoveryPin.setVisible(false);
        }
        if(allPOIInThisMap != null && allPOIInThisMap.getItemCount() != 0 ){
            allPOIInThisMap.setSelectedIndex(0);
        }
    }
    public void showMessage(int coordinateX, int coordinateY){
        userCreate.setVisible(false);
        userDefined.setVisible(false);

        for(int j=0;j<mapList.size();j++)
        {
            if(mapList.get(j).isVisible())
            {
                for(POI poi: Main.allPOI)
                {
                    if(poi.getFloor()==j+1 && poi.getBuilding().equals(whichBuilding) && poi.getX()-30==coordinateX && poi.getY()-35==coordinateY)
                    {
                        newName.setText("  Name: "+ poi.getName());
                        newNumber.setText("  Room Number: "+ poi.getRoomNumber());
                        Description.setText("  Descritpion: "+ poi.getDescription());
                        newName.setBounds(0,10,200,20);
                        newNumber.setBounds(0,40,200,20);
                        Description.setBounds(0,70,400,20);

                        if(allFavourite.size()==0)
                        {
                            favouriteOption.setVisible(true);
                            removeOption.setVisible(false);
                            tempFav=poi;
                        }
                        else {
                            boolean find=false;
                            for (int n = 0; n < allFavourite.size(); n++) {
                                if (allFavourite.get(n).checkEqual(allFavourite.get(n), poi)) {


                                    removeOption.setVisible(true);
                                    favouriteOption.setVisible(false);

                                    tempDele = poi;

                                    find=true;

                                }

                            }
                            if(find==false) {
                                favouriteOption.setVisible(true);
                                removeOption.setVisible(false);

                                tempFav = poi;
                            }
                        }
                        textFiled.add(newName);
                        textFiled.add(newNumber);
                        textFiled.add(Description);
                    }

                }
                for(int k=0;k<allUserCreated.size();k++)
                {
                    if(allUserCreated.get(k).getFloor()==j+1&&allUserCreated.get(k).getBuilding().equals(whichBuilding)&&allUserCreated.get(k).getX()-30==coordinateX&&allUserCreated.get(k).getY()-35==coordinateY)
                    {
                        newName.setText("  Name: "+allUserCreated.get(k).getName());
                        newNumber.setText("  Room Number: "+allUserCreated.get(k).getRoomNumber());
                        Description.setText("  Descritpion: "+allUserCreated.get(k).getDescription());
                        newName.setBounds(0,10,200,20);
                        newNumber.setBounds(0,40,200,20);
                        Description.setBounds(0,70,400,20);
                        tempUserCreated=allUserCreated.get(k);
                        if(allFavourite.size()==0)
                        {
                            favouriteOption.setVisible(true);
                            removeOption.setVisible(false);
                            customizeOption.setVisible(true);
                            edit.setVisible(true);
                            changeCoor.setVisible(true);

                            tempFav=allUserCreated.get(k);
                        }
                        else {
                            boolean find=false;
                            for (int n = 0; n < allFavourite.size(); n++) {
                                if (allFavourite.get(n).checkEqual(allFavourite.get(n), allUserCreated.get(k))) {

                                    removeOption.setVisible(true);
                                    favouriteOption.setVisible(false);
                                    customizeOption.setVisible(true);
                                    edit.setVisible(true);
                                    changeCoor.setVisible(true);
                                    tempDele = allUserCreated.get(k);

                                    find=true;

                                }

                            }
                            if(find==false) {
                                favouriteOption.setVisible(true);
                                removeOption.setVisible(false);
                                customizeOption.setVisible(true);
                                edit.setVisible(true);
                                changeCoor.setVisible(true);
                                tempFav = allUserCreated.get(k);
                            }
                        }






                        textFiled.add(newName);
                        textFiled.add(newNumber);
                        textFiled.add(Description);

                    }

                }
            }
        }
    }

    private void clearInfor(){
        hideMessage();
        unselectAllLayer();
    }

    public void hideMessage(){
        newName.setText(" ");
        newNumber.setText(" ");
        Description.setText(" ");
        favouriteOption.setVisible(false);
        removeOption.setVisible(false);
        userDefined.setVisible(false);
        userCreate.setVisible(false);
        customizeOption.setVisible(false);
        edit.setVisible(false);
        changeCoor.setVisible(false);
        completeCoor.setVisible(false);
    }

    // refresh favorite menu in menu bar
    public void refreshFavorite() throws SQLException, ClassNotFoundException {

        // refresh favorite list
        favoriteMenu = new JMenu("Favorite");
        favoriteMenu.removeAll();
        userFavorite thisUserFavorite = new userFavorite();
        favoriteMenu = thisUserFavorite.getUserFavorite();

        if(menuBar.getMenuCount() >= 3){
            menuBar.removeAll();
            menuBar.add(selectBuilding);
            menuBar.add(favoriteMenu);
            menuBar.add(help);
            menuBar.add(about);
        }
        frame.repaint();



        // add action listener to each item
        for(int k = 0; k < favoriteMenu.getMenuComponents().length; k++){
            JMenu buildingItem = (JMenu) favoriteMenu.getItem(k);

            for(int j = 0; j < buildingItem.getItemCount(); j++){
                JMenu floorItem = (JMenu) buildingItem.getItem(j);
                for(int l = 0; l < floorItem.getItemCount(); l++){
                    floorItem.getItem(l).addActionListener(this);
                }
            }
        }
    }


    private void jumpToFav(String POIname, String floor, String building) throws SQLException, ClassNotFoundException {

        whichBuilding = building;
        floor = floor.replaceAll("[^0-9]", "");

        whichFLoor = Integer.parseInt(floor);

        clearInfor();
        unselectAllLayer();

        numButton = findFloor(whichBuilding);

        addEachFloor(whichBuilding,numButton, false);
        currentBuilding.setText("  " + whichBuilding);

        // change this map visible
        mapList.get(whichFLoor - 1).setVisible(true);

        // change select floor combobox to current floor
        eachFloor.setSelectedIndex(whichFLoor);

        // add all POI
        String[] allPOIInThisMapStr = new String[0];
        allPOIInThisMapStr = discoveryPOI(whichBuilding);
        allPOIInThisMap.removeAllItems();
        allPOIInThisMap.addItem("Please select");
        for(int i = 0; i < allPOIInThisMapStr.length; i++){
            String temp = allPOIInThisMapStr[i];
            allPOIInThisMap.addItem(temp);
        }


        // find the favorite POI
        int x = 0;
        int y = 0;
        for(POI thisPOI:allFavourite){
            if(thisPOI.getBuilding().equals(whichBuilding) &&thisPOI.getFloor() == whichFLoor&&thisPOI.getName().equals(POIname)){

                x = thisPOI.getX();
                y = thisPOI.getY();
                break;

            }
        }
        showMessage(x-30,y-35);


        for(int i=0;i<allFavPin.size();i++)
        {
            if(allFavPin.get(i).isVisible()==false){
                allFavPin.get(i).setLocation(x-30,y-35);
                allFavPin.get(i).setSize(50, 50);
                floorList.get(whichFLoor - 1).add(allFavPin.get(i));
                allFavPin.get(i).setVisible(true);
                break;
            }
        }
    }

    private void clearFav(){
        if(whichFLoor <= 0){

        }
        else{
            floorList.get(whichFLoor - 1).removeAll();
            frame.repaint();
        }

        if(theFavPinInMenuBar != null){
            theFavPinInMenuBar.setVisible(false);
        }

    }


    // update four category of layer pin
    private void refreshAllLayer() throws SQLException, ClassNotFoundException {

        createPin newAllpin = new createPin(allUserCreated, allFavourite, Main.allPOI);

        // if add a new label, update the classroom pin
        for(POI a : newAllpin.getPOIClassroom()){
            for(POI b: allPin.getPOIClassroom()){
                if(a.getBuilding().equals(b.getBuilding()) && a.getFloor() == b.getFloor()
                        && a.getX() == b.getX() && a.getY() == b.getY()){
                    break;
                }

            }
            ArrayList<POI> temp = new ArrayList<>();
            temp.add(a);
            createPin tempNewPin = new createPin(temp);
            pinClassroom.add(tempNewPin.getSearch().get(0));
            totalPin.add(tempNewPin.getSearch().get(0));

        }

        // if delete a label, update the classroom pin
        for(int i = 0; i < allPin.getPOIClassroom().size(); i++){
            JLabel temp = pinClassroom.get(i);
            for(POI b: newAllpin.getPOIClassroom()){
                if(allPin.getPOIClassroom().get(i).getBuilding().equals(b.getBuilding()) && allPin.getPOIClassroom().get(i).getFloor() == b.getFloor()
                        && allPin.getPOIClassroom().get(i).getX() == b.getX() && allPin.getPOIClassroom().get(i).getY() == b.getY()){
                    break;
                }

            }
            temp.setVisible(false);
            pinClassroom.remove(temp);
            totalPin.remove(temp);

        }

        // if add a new label, update the lab pin
        for(POI a : newAllpin.getPOILab()){
            for(POI b: allPin.getPOILab()){
                if(a.getBuilding().equals(b.getBuilding()) && a.getFloor() == b.getFloor()
                        && a.getX() == b.getX() && a.getY() == b.getY()){
                    break;
                }
            }
            ArrayList<POI> temp = new ArrayList<>();
            temp.add(a);
            createPin tempNewPin = new createPin(temp);
            pinLab.add(tempNewPin.getSearch().get(0));
            totalPin.add(tempNewPin.getSearch().get(0));
        }


        // if delete a label, update the lab pin
        for(int i = 0; i < allPin.getPOILab().size(); i++){
            JLabel temp = pinLab.get(i);
            for(POI b: newAllpin.getPOILab()){
                if(allPin.getPOILab().get(i).getBuilding().equals(b.getBuilding()) && allPin.getPOILab().get(i).getFloor() == b.getFloor()
                        && allPin.getPOILab().get(i).getX() == b.getX() && allPin.getPOILab().get(i).getY() == b.getY()){
                    break;
                }

            }
            temp.setVisible(false);
            pinLab.remove(temp);
            totalPin.remove(temp);

        }


        // if add a new label, update the restaurant pin
        for(POI a : newAllpin.getPOIRestaurant()){
            for(POI b: allPin.getPOIRestaurant()){
                if(a.getBuilding().equals(b.getBuilding()) && a.getFloor() == b.getFloor()
                        && a.getX() == b.getX() && a.getY() == b.getY()){
                    break;
                }

            }
            ArrayList<POI> temp = new ArrayList<>();
            temp.add(a);
            createPin tempNewPin = new createPin(temp);
            pinRestaurant.add(tempNewPin.getSearch().get(0));
            totalPin.add(tempNewPin.getSearch().get(0));

        }

        // if delete a label, update the restaurant pin
        for(int i = 0; i < allPin.getPOIRestaurant().size(); i++){
            JLabel temp = pinRestaurant.get(i);
            for(POI b: newAllpin.getPOIRestaurant()){
                if(allPin.getPOIRestaurant().get(i).getBuilding().equals(b.getBuilding()) && allPin.getPOIRestaurant().get(i).getFloor() == b.getFloor()
                        && allPin.getPOIRestaurant().get(i).getX() == b.getX() && allPin.getPOIRestaurant().get(i).getY() == b.getY()){
                    break;
                }

            }
            temp.setVisible(false);
            pinRestaurant.remove(temp);
            totalPin.remove(temp);
        }



        // if add a new label, update the collaborative pin
        for(POI a : newAllpin.getPOICollab()){
            for(POI b: allPin.getPOICollab()){
                if(a.getBuilding().equals(b.getBuilding()) && a.getFloor() == b.getFloor()
                        && a.getX() == b.getX() && a.getY() == b.getY()){
                    break;
                }

            }
            ArrayList<POI> temp = new ArrayList<>();
            temp.add(a);
            createPin tempNewPin = new createPin(temp);
            pinCollaborative.add(tempNewPin.getSearch().get(0));
            totalPin.add(tempNewPin.getSearch().get(0));
        }

        // if delete a label, update the collaborative pin
        for(int i = 0; i < allPin.getPOICollab().size(); i++){
            JLabel temp = pinCollaborative.get(i);
            for(POI b: newAllpin.getPOICollab()){
                if(allPin.getPOICollab().get(i).getBuilding().equals(b.getBuilding()) && allPin.getPOICollab().get(i).getFloor() == b.getFloor()
                        && allPin.getPOICollab().get(i).getX() == b.getX() && allPin.getPOICollab().get(i).getY() == b.getY()){
                    break;
                }

            }
            temp.setVisible(false);
            pinCollaborative.remove(temp);
            totalPin.remove(temp);
        }

    }


    public void mouseMoved(MouseEvent me){

    }
    public void mouseDragged(MouseEvent me){

        tempMoving.setBounds( tempMoving.getX()+me.getX()-25, tempMoving.getY()+me.getY()-25, 50, 50);
    }
    private void editUserCreatedCoordinate(POI a,int newX,int newY)throws SQLException, ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn.createStatement();
        updateSQL = "UPDATE usercreated SET Xcoordinate="+"'"+newX+"'"+",Ycoordinate ="+"'"+newY+"'"+"WHERE building =" + "'" + a.getBuilding() + "'" + "AND Floor =" + "'" + a.getFloor() + "'" + "AND Name =" + "'" + a.getName() + "'"+"AND userAccount ="+"'"+login.thisUsername + "'";
        ps = conn.prepareStatement(updateSQL);
        ps.executeUpdate();
        ps.close();
        stmt.close();
        conn.close();

    }
    private void editUserFavCoordinate(POI a,int newX,int newY)throws SQLException, ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
        conn1 = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn1.createStatement();
        updateSQL = "UPDATE userfavourite SET Xcoordinate="+"'"+newX+"'"+",Ycoordinate ="+"'"+newY+"'"+"WHERE building =" + "'" + a.getBuilding() + "'" + "AND Floor =" + "'" + a.getFloor() + "'" + "AND Name =" + "'" + a.getName() + "'"+"AND userAccount ="+"'"+login.thisUsername + "'";
        ps = conn1.prepareStatement(updateSQL);
        ps.executeUpdate();
        ps.close();
        stmt.close();
        conn1.close();

    }

    // hide duplicated pin
    private void hiddenSamePin(){


        int rmSize = allVisiablePin.size();
        if(allVisiablePin.size() > 0){
            for(int i = rmSize; i > 0 ; i--){
                allVisiablePin.remove(i-1);

            }
        }

        for(JLabel a: allFavPin){
            if(a.isVisible() && a.getX() != 0 && a.getY() != 0){
                allVisiablePin.add(a);
            }
        }
        for(JLabel a: allCreatedPin){
            if(a.isVisible() && a.getX() != 0 && a.getY() != 0){
                allVisiablePin.add(a);
            }
        }

        for(JLabel a: pinClassroom){
            if(a.isVisible() && a.getX() != 0 && a.getY() != 0){
                allVisiablePin.add(a);
            }
        }
        for(JLabel a: pinLab){
            if(a.isVisible() && a.getX() != 0 && a.getY() != 0){
                allVisiablePin.add(a);
            }
        }
        for(JLabel a: pinRestaurant){
            if(a.isVisible() && a.getX() != 0 && a.getY() != 0){
                allVisiablePin.add(a);
            }
        }
        for(JLabel a: pinCollaborative){
            if(a.isVisible() && a.getX() != 0 && a.getY() != 0){
                allVisiablePin.add(a);
            }
        }
        boolean addListener = false;
        if(pinX != null && pinY != null){
            addListener = true;
        }


        // create two hashset to compare the pin location is duplicate or not
        pinX = new HashSet<>();
        pinY = new HashSet<>();

        pinX.clear();
        pinY.clear();

        for(JLabel thisPin : allVisiablePin){

//            thisPin.addMouseMotionListener(this);
            if(addListener){
                thisPin.addMouseMotionListener(this);
            }

            if(pinX.contains(thisPin.getX()) == true){
                if(pinY.contains(thisPin.getY())){
                    thisPin.setVisible(false);
                }
                else{
                    pinX.add(thisPin.getX());
                    pinY.add(thisPin.getY());
                }

            }
            else{
                pinX.add(thisPin.getX());
                pinY.add(thisPin.getY());
            }


        }
    }

    // show all hidden pin except for the one changed the coordinate
    private void showSamePin(){
        for(JLabel a: allVisiablePin){
            if(a.getX() == tempUserCreated.getX() - 30 && a.getY() == tempUserCreated.getY() - 35){
            }else{
                a.setVisible(true);
                a.removeMouseMotionListener(this);
            }

        }
    }

    private void hideChangedPin(){

        for(JLabel a: allVisiablePin){
            if(a.getX() == tempUserCreated.getX() && a.getY() == tempUserCreated.getY()){

            }else{
                a.setVisible(true);
            }

        }
    }

}

