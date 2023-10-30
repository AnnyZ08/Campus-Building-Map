import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class FAQPage implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JLabel FAQ;
    private JLabel HelpGuide;
    private JLabel background;
    private JLabel arrow;
    private JLabel helpPDF;

    public FAQPage() throws Exception {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        frame = new JFrame();
        panel = new JPanel();
        frame.setSize(800, 650);
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setTitle("FAQ Page");

        // Create the panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panel.setBounds(30,15,730, 600);
        //  panel.setVisible(true);
        panel.setBackground(Color.WHITE);
        frame.add(panel);

        //Help Guide title
        HelpGuide = new JLabel("Help Guide");
        HelpGuide.setFont(new Font("Fira Sans", Font.PLAIN, 20));
        HelpGuide.setForeground(new Color(79, 38, 131));
        panel.add(HelpGuide);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        helpPDF = new JLabel("<HTML><U>Click Here for Full Help Guide</U></HTML>");
        helpPDF.setForeground(Color.BLUE);
        helpPDF.setFont(new Font("Fira Sans", Font.PLAIN, 14));

        panel.add(helpPDF);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        helpPDF.addMouseListener(new MouseAdapter() {

            @Override
            /**  We can not throw exception because actionperformed is an override function */
            public void mouseClicked(MouseEvent e) {
                if(e.getSource() == helpPDF){
                    try {
                        File myFile = new File("help.pdf");
                        Desktop.getDesktop().open(myFile);
                    } catch (IOException ex) {
                        // no application registered for PDFs

                    }
                }
            }
        });

        //FAQ Title
        FAQ = new JLabel("Frequently Asked Questions");
        FAQ.setFont(new Font("Fira Sans", Font.PLAIN, 20));
        FAQ.setForeground(new Color(79, 38, 131));
        panel.add(FAQ);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        // Add questions and answers
        addQA("How to get started",
                "First, you need to register your personal account and set a password for each subsequent login. ");
        addQA("How do I select the building I want to browse?",
                "You can click on the first 'Building' button in the top left corner to select the building you want to browse. " +
                        "Currently, we offer three buildings: Middlesex College, Alumni Stadium, and Staging Building.");
        addQA("How do I select the floor I want to browse",
                "Once you have located the building you want to browse, click on the 'Select Floor' button on the left," +
                        "choose the floor you want to browse, and then click the 'Go' button below.");
        addQA("Can I search for POIs?",
                "Yes, " + "Our program offers the ability for users to mark POIs. " +
                        "Once you enter the map you want to browse, you can enter the POI you want to find in the top right corner and click the 'Search' button. " +
                        "If there is a match, the map will display a star symbol to indicate its location.");
        addQA("Are there any other POI features available?",
                "In addition to marking POIs, our program also provides other POI features. For example, you can view a list of marked POIs on the map, select different POI categories to browse, and click on a POI icon on the map to view detailed information.");


//        try {
            BufferedImage logo = ImageIO.read(new File("pictures/white.png"));
            background = new JLabel(new ImageIcon(logo));
            background.setBounds(0, 0, 800, 500);
            frame.add(background);
//        } catch (Exception e) {
//
//        }
        frame.setVisible(true);
    }

    // Add a question and answer to the panel
    private void addQA(String question, String answer) throws Exception {
        // add the downward arrow
        ImageIcon icon = new ImageIcon("pictures/arrow.png");
        // Create the question label
        JLabel questionLabel = new JLabel(question,icon,JLabel.LEFT);
        //questionLabel.setPreferredSize(new Dimension(730,20));
        questionLabel.setFont(new Font("Fira Sans", Font.PLAIN, 16));
        questionLabel.setForeground(new Color(79, 38, 131));
        panel.add(questionLabel);

        // Create the answer label and hide it initially
        JLabel answerLabel = new JLabel("<html>"+answer+"<html>");
        answerLabel.setFont(new Font("Overpass", Font.PLAIN, 14));
        answerLabel.setVisible(false);
        panel.add(answerLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 5))); // Adds vertical space
//        try {
            BufferedImage logo = ImageIO.read(new File("pictures/line.png"));
            arrow = new JLabel(new ImageIcon(logo));
            panel.add(arrow);
//        } catch (Exception e) {
//            throw new Exception();
//        }
        // Add an action listener to the question label
        questionLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                answerLabel.setVisible(!answerLabel.isVisible()); // Toggle the visibility of the answer
                if (questionLabel.getForeground().equals(Color.BLUE)){
                    questionLabel.setForeground(new Color(79, 38, 131));
                } else questionLabel.setForeground(Color.BLUE);

                panel.revalidate(); // Revalidate the panel to update the layout
            }
        });
        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Adds vertical space

    }

    @Override
    /**  We can not throw exception because actionperformed is an override function */
    public void actionPerformed(ActionEvent e) {
//        // Handle button clicks here
//        try {
//                File myFile = new File("help.pdf");
//                Desktop.getDesktop().open(myFile);
//            } catch (IOException ex) {
//                // no application registered for PDFs
//            }

    }
}
