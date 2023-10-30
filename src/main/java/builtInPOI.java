import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class builtInPOI implements ActionListener{

    private JFrame frame;
    private JPanel panel;
    private JLabel building;
    private JLabel floor;
    private JLabel name;
    private JLabel number;
    private JLabel type;
    private JLabel description;
    private JLabel buildingText;
    private JLabel floorText;
    private JTextField nameText;
    private JTextField numberText;
    private JComboBox typeText;
    private JTextArea descriptionText;
    private JButton create;
    private POI newPOI;
    private String newPOI_building;
    private int newPOI_floor;
    private int newPOI_X;
    private int newPOI_Y;
    private developerMode DM;


    public builtInPOI(developerMode a ,int x,int y,String tempBuilding, int tempFloor){


        Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame();
        panel = new JPanel();
        frame.setSize(500,500);
        frame.setTitle("Built-in POI");
        frame.setLocation(dim.width/2-frame.getSize().width/2,dim.height/2-frame.getSize().height/2);
        frame.add(panel);
        panel.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);

        building=new JLabel("Building");
        building.setBounds(10,20,100,25);
        floor = new JLabel("Floor");
        floor.setBounds(10,80,100,25);
        name = new JLabel("Name");
        name.setBounds(10,140,100,25);
        number=new JLabel("Room Number");
        number.setBounds(10,200,100,25);
        type=new JLabel("Type");
        type.setBounds(10,260,100,25);
        description=new JLabel("Description");
        description.setBounds(10,320,100,25);
        panel.add(building);
        panel.add(floor);
        panel.add(name);
        panel.add(number);
        panel.add(type);
        panel.add(description);
        buildingText = new JLabel();
        buildingText.setBounds(120,20,200,25);
        buildingText.setText(tempBuilding);
        floorText=new JLabel();
        floorText.setBounds(120,80,200,25);
        floorText.setText(Integer.toString(tempFloor));
        nameText=new JTextField();
        nameText.setBounds(120,140,200,25);
        numberText=new JTextField();
        numberText.setBounds(120,200,200,25);
        typeText=new JComboBox<>();
        typeText.setBounds(120,260,200,25);
        typeText.addItem("Classroom");
        typeText.addItem("Lab");
        typeText.addItem("Restaurant");
        typeText.addItem("Collaborative Room");
        typeText.addItem("Other");
        descriptionText=new JTextArea();
        descriptionText.setLineWrap(true);
        descriptionText.setBorder(new LineBorder(Color.black,2));

        descriptionText.setBounds(120,320,300,100);
        panel.add(buildingText);
        panel.add(floorText);
        panel.add(nameText);
        panel.add(numberText);
        panel.add(typeText);
        panel.add(descriptionText);

        create = new JButton("Create");
        create.setBounds(200,430,80,25);
        create.addActionListener( this);
        panel.add(create);

        newPOI_building=tempBuilding;
        newPOI_floor=tempFloor;
        newPOI_X=x;
        newPOI_Y=y;
        DM = a;

    }

    @Override
    /**  We can not throw exception because actionperformed is an override function */
    public void actionPerformed(ActionEvent e){
        newPOI = new POI(newPOI_X,newPOI_Y,nameText.getText(),numberText.getText(),(String)typeText.getSelectedItem(),newPOI_building,newPOI_floor,descriptionText.getText());

        Main.allPOI.add(newPOI);

        // add the new built in POI
        BufferedImage pin = null;
        try {
            pin = ImageIO.read(new File("pictures/pin.png"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        JLabel tempIcon = new JLabel(new ImageIcon(pin));

            DM.discoveryPin.setVisible(false);
            DM.discoveryPin.removeAll();
            DM.discoveryPin = tempIcon;
            DM.discoveryPin.setLocation(newPOI.getX()-30,newPOI.getY()-35);
            DM.discoveryPin.setSize(50, 50);
            DM.floorList.get(newPOI.getFloor() - 1).add(DM.discoveryPin);
            DM.discoveryPin.setVisible(true);
            DM.frame.repaint();



        developerMode.allPOIInThisMap.addItem(newPOI.getName());

        DM.userDefined.setVisible(false);
        DM.userCreate.setVisible(false);

        DM.showMessage(newPOI.getX()-30,newPOI.getY()-35);


        try {
            builtInManage bm = new builtInManage(newPOI);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }


        frame.dispose();

    }
}
