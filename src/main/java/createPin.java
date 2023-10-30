import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class createPin {

    private ArrayList<JLabel> pinClass =new ArrayList<>();
    private ArrayList<JLabel> pinLab =new ArrayList<>();
    private ArrayList<JLabel> pinRest =new ArrayList<>();
    private ArrayList<JLabel> pinCollab =new ArrayList<>();
    private ArrayList<POI> classroomPOI =new ArrayList<>();
    private ArrayList<POI> labPOI =new ArrayList<>();
    private ArrayList<POI> restPOI =new ArrayList<>();
    private ArrayList<POI> collabPOI =new ArrayList<>();
    private ArrayList<POI> POIBuiltIn = new ArrayList<>();
    private ArrayList<POI> POIFav = new ArrayList<>();
    private  ArrayList<POI> POIUserCreated = new ArrayList<>();
    private ArrayList<JLabel> allSearchPin =new ArrayList<>();
    private ArrayList<JLabel> allFavouritPin = new ArrayList<>();
    private  ArrayList<JLabel> allUserCreatedPin = new ArrayList<>();
    private ArrayList<JLabel> totalPin = new ArrayList<>();
    private ArrayList<JLabel> allBulitInPin = new ArrayList<>();


    public createPin(ArrayList<POI> addPin){
        if(addPin.size() == Main.allPOI.size()){
            for(POI eachPOI: addPin) {
                if (eachPOI.getType().equals("Classroom")) {

                    try {

                        BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                        JLabel tempIcon = new JLabel(new ImageIcon(pin));
                        tempIcon.setLocation(eachPOI.getX() - 30, eachPOI.getY() - 35);
                        pinClass.add(tempIcon);
                        totalPin.add(tempIcon);
                        classroomPOI.add(eachPOI);
                    } catch (Exception e) {

                    }
                } else if (eachPOI.getType().equals("Restaurant")) {

                    try {

                        BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                        JLabel tempIcon = new JLabel(new ImageIcon(pin));
                        tempIcon.setLocation(eachPOI.getX() - 30, eachPOI.getY() - 35);
                        pinRest.add(tempIcon);
                        totalPin.add(tempIcon);
                        restPOI.add(eachPOI);
                    } catch (Exception e) {

                    }
                } else if (eachPOI.getType().equals("Lab")) {
                    try {

                        BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                        JLabel tempIcon = new JLabel(new ImageIcon(pin));
                        tempIcon.setLocation(eachPOI.getX() - 30, eachPOI.getY() - 35);
                        pinLab.add(tempIcon);
                        totalPin.add(tempIcon);
                        labPOI.add(eachPOI);

                    } catch (Exception e) {


                    }

                } else if (eachPOI.getType().equals("Collaborative Room")) {
                    try {

                        BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                        JLabel tempIcon = new JLabel(new ImageIcon(pin));
                        tempIcon.setLocation(eachPOI.getX() - 30, eachPOI.getY() - 35);
                        pinCollab.add(tempIcon);
                        totalPin.add(tempIcon);
                        collabPOI.add(eachPOI);

                    } catch (Exception e) {

                    }
                }
            }
        }
        else{
            try {

                for(POI a : addPin){
                    BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                    JLabel tempIcon = new JLabel(new ImageIcon(pin));
                    tempIcon.setLocation(a.getX() - 30, a.getY() - 35);
                    allSearchPin.add(tempIcon);
                }
            } catch (Exception e) {

            }
        }
    }



    public createPin(ArrayList<POI> temp1,ArrayList<POI> temp2){
        for(int i=0;i<temp1.size();i++)
        {
            try {

                BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                JLabel tempIcon = new JLabel(new ImageIcon(pin));
                tempIcon.setLocation(temp1.get(i).getX() - 30, temp1.get(i).getY() - 35);
                allUserCreatedPin.add(tempIcon);

            } catch (Exception e) {

            }

        }
        for(int i=0;i<temp2.size();i++)
        {
            try {

                BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                JLabel tempIcon = new JLabel(new ImageIcon(pin));
                tempIcon.setLocation(temp2.get(i).getX() - 30, temp2.get(i).getY() - 35);
                allFavouritPin.add(tempIcon);


            } catch (Exception e) {

            }

        }
    }


    // create bulit-in, user created, user favorite;
    public createPin(ArrayList<POI> temp1,ArrayList<POI> temp2, ArrayList<POI> temp3){
        ArrayList<POI> all = new ArrayList<>();
        // add built in and user create to a new arraylist
        for(POI a: temp1){
            all.add(a);
        }
        for(POI a: temp3){
            all.add(a);
        }


        for(int i=0;i<temp1.size();i++)
        {
            try {

                BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                JLabel tempIcon = new JLabel(new ImageIcon(pin));
                tempIcon.setLocation(temp1.get(i).getX() - 30, temp1.get(i).getY() - 35);
                allUserCreatedPin.add(tempIcon);
                totalPin.add(tempIcon);
                POIUserCreated.add(temp1.get(i));

            } catch (Exception e) {

            }

        }
        for(int i=0;i<temp2.size();i++)
        {
            try {

                BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                JLabel tempIcon = new JLabel(new ImageIcon(pin));
                tempIcon.setLocation(temp2.get(i).getX() - 30, temp2.get(i).getY() - 35);
                allFavouritPin.add(tempIcon);
                POIFav.add(temp2.get(i));

            } catch (Exception e) {

            }

        }
        for(int i=0;i<temp3.size();i++)
        {
            try {

                BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                JLabel tempIcon = new JLabel(new ImageIcon(pin));
                tempIcon.setLocation(temp3.get(i).getX() - 30, temp3.get(i).getY() - 35);
                allBulitInPin.add(tempIcon);
                totalPin.add(tempIcon);
                POIBuiltIn.add(temp3.get(i));
            } catch (Exception e) {

            }

        }

        for(POI eachPOI: all) {
            if (eachPOI.getType().equals("Classroom")) {

                try {

                    BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                    JLabel tempIcon = new JLabel(new ImageIcon(pin));
                    tempIcon.setLocation(eachPOI.getX() - 30, eachPOI.getY() - 35);
                    pinClass.add(tempIcon);
                    totalPin.add(tempIcon);
                    classroomPOI.add(eachPOI);
                } catch (Exception e) {

                }
            } else if (eachPOI.getType().equals("Restaurant")) {

                try {

                    BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                    JLabel tempIcon = new JLabel(new ImageIcon(pin));
                    tempIcon.setLocation(eachPOI.getX() - 30, eachPOI.getY() - 35);
                    pinRest.add(tempIcon);
                    totalPin.add(tempIcon);
                    restPOI.add(eachPOI);
                } catch (Exception e) {

                }
            } else if (eachPOI.getType().equals("Lab")) {
                try {

                    BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                    JLabel tempIcon = new JLabel(new ImageIcon(pin));
                    tempIcon.setLocation(eachPOI.getX() - 30, eachPOI.getY() - 35);
                    pinLab.add(tempIcon);
                    totalPin.add(tempIcon);
                    labPOI.add(eachPOI);

                } catch (Exception e) {


                }

            } else if (eachPOI.getType().equals("Collaborative Room")) {
                try {

                    BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                    JLabel tempIcon = new JLabel(new ImageIcon(pin));
                    tempIcon.setLocation(eachPOI.getX() - 30, eachPOI.getY() - 35);
                    pinCollab.add(tempIcon);
                    totalPin.add(tempIcon);
                    collabPOI.add(eachPOI);

                } catch (Exception e) {

                }
            }
        }

    }

    public ArrayList<POI> getPOIClassroom(){
        return this.classroomPOI;
    }
    public ArrayList<POI> getPOILab(){
        return this.labPOI;
    }
    public ArrayList<POI> getPOIRestaurant(){
        return this.restPOI;
    }
    public ArrayList<POI> getPOICollab(){
        return this.collabPOI;
    }
    public ArrayList<POI> getPOIFav(){
        return this.POIFav;
    }
    public ArrayList<POI> getPOIUserDefine(){
        return this.POIUserCreated;
    }
    public ArrayList<JLabel> getClassroom(){
        return this.pinClass;
    }
    public ArrayList<JLabel> getLab(){
        return this.pinLab;
    }
    public ArrayList<JLabel> getRestaurant(){
        return this.pinRest;
    }
    public ArrayList<JLabel> getCollaborative(){
        return this.pinCollab;
    }
    public ArrayList<JLabel> getSearch(){
        return this.allSearchPin;
    }
    public ArrayList<JLabel> getTotal(){
        return this.totalPin;
    }
    public  ArrayList<JLabel> getFav(){
        return allFavouritPin;
    }
    public ArrayList<JLabel> getCreated(){
        return allUserCreatedPin;
    }

}


