import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import javax.swing.JComboBox;
import java.sql.*;
import java.util.ArrayList;
public class selectBuilding implements ActionListener {

    private static JFrame frame;
    private static JPanel panel;
    private static JComboBox building;
    private static JLabel select;
    private static JButton ok;
    private ArrayList<String>buildingList;
    public selectBuilding() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        frame = new JFrame();
        panel = new JPanel();

        frame.setSize(400, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Select Building");
        panel.setBackground(new Color(255, 255, 255));
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height/2);
        frame.add(panel);
        panel.setLayout(null);
        select=new JLabel("Select Building");
        select.setBounds(10,5,140,60);
        try{
            buildingList=findBuilding();
        }
        catch(Exception e){

        }
        building=new JComboBox<>();
        for(int i=0;i<buildingList.size();i++)
        {
            building.addItem(buildingList.get(i));
        }
        building.setBounds(150, 20, 160, 25);
        ok=new JButton("Go");
        ok.setBounds(150, 65, 80, 40);
        ok.addActionListener(this);
        panel.add(building);
        panel.add(ok);
        panel.add(select);
        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        try {
            viewMap vm=new viewMap((String)building.getSelectedItem());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        frame.dispose();
    }


    public ArrayList<String> findBuilding(){
        ArrayList<String> result = new ArrayList<>();
        for(building theBuilding: Main.allBuildings){
            result.add(theBuilding.getName());
        }
        return result;
    }
}