import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The createPinTest class contains JUnit tests for the createPin class, which is responsible for creating
 * pins on the map for different types of points of interest (POIs). Each test checks whether the expected
 * POIs are returned by the createPin object for a specific type of POI.
 */
class createPinTest {
    /**
     * Tests the getPOIClassroom method of the createPin class.
     * checks that the expected POIs are returned by the getPOIClassroom method.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getPOIClassroomTrue() throws Exception {
            // Create a test ArrayList of POI objects
            ArrayList<POI> expectedClassroomPOI = new ArrayList<>();
            POI add = new POI(0,0,"test","0","Classroom",
                    "Middlesex College", 0, "test description");
            Main.allPOI = new ArrayList<>();
            Main.allPOI.add(add);
            expectedClassroomPOI.add(add);
            createPin Pin = new createPin(expectedClassroomPOI);
            ArrayList<POI> actualClassroomPOI = Pin.getPOIClassroom();

            assertEquals(expectedClassroomPOI, actualClassroomPOI);
    }
    /**
     * Tests the getPOIClassroom method of the createPin class with an incorrect input.
     * Checks that the actual POIs returned by the method are not equal to an empty ArrayList.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getPOIClassroomFalse() throws Exception {
        // Create a test ArrayList of POI objects
        ArrayList<POI> wrongClassroomPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Classroom",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        wrongClassroomPOI.add(add1);
        createPin Pin = new createPin(wrongClassroomPOI);
        ArrayList<POI> actualClassroomPOI = Pin.getPOIClassroom();
        //they should not equal
        assertNotEquals(0, actualClassroomPOI.size());
    }
    /**
     * Tests the getPOILab method of the createPin class.
     * Checks that the expected POIs are returned by the getPOILab method.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getPOILabTrue() throws Exception{
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedLabPOI = new ArrayList<>();
        POI add = new POI(0,0,"test","0","Lab",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add);
        expectedLabPOI.add(add);
        createPin Pin = new createPin(expectedLabPOI);
        ArrayList<POI> actualLabPOI = Pin.getPOILab();

        assertEquals(expectedLabPOI, actualLabPOI);
    }
    /**
     * Tests the getPOILab method of the createPin class with an incorrect input.
     * Checks that the actual POIs returned by the method are not equal to an empty ArrayList.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getPOILabFalse() throws Exception {
        // Create a test ArrayList of POI objects
        ArrayList<POI> actualLabPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Lab",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        actualLabPOI.add(add1);
        createPin Pin = new createPin(actualLabPOI);
        int size = Pin.getPOILab().size();
        //they should not equal
        assertNotEquals(0, size);
    }
    /**
     * Tests the getPOIRestaurant method of the createPin class.
     * Checks that the expected POIs are returned by the getPOIRestaurant method.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getPOIRestaurantTrue() throws Exception {
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedResPOI = new ArrayList<>();
        POI add = new POI(0,0,"test","0","Restaurant",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add);
        expectedResPOI.add(add);
        createPin Pin = new createPin(expectedResPOI);
        //check if the content are equal
        assertEquals(expectedResPOI, Pin.getPOIRestaurant());
    }
    /**
     * Tests the getPOIRestaurant method of the createPin class with an incorrect input.
     * Checks that the actual POIs returned by the method are not equal to an empty ArrayList.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getPOIRestaurantFalse() throws Exception{
        // Create a test ArrayList of POI objects
        ArrayList<POI> actualResPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Restaurant",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        actualResPOI.add(add1);
        createPin Pin = new createPin(actualResPOI);
        int size = Pin.getPOIRestaurant().size();
        //they should not equal
        assertNotEquals(0, size);
    }
    /**
     * Tests the getPOICollab method of the createPin class.
     * Checks that the expected POIs are returned by the getPOICollab method.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getPOICollabTrue() throws Exception {
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedResPOI = new ArrayList<>();
        POI add = new POI(0,0,"test","0","Collaborative Room",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add);
        expectedResPOI.add(add);
        createPin Pin = new createPin(expectedResPOI);
        //check if the content are equal
        assertEquals(expectedResPOI, Pin.getPOICollab());
    }
    /**
     * Tests the getPOIColla method of the createPin class with an incorrect input.
     * Checks that the actual POIs returned by the method are not equal to an empty ArrayList.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getPOICollabFalse() throws Exception {
        // Create a test ArrayList of POI objects
        ArrayList<POI> actualResPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Collaborative Room",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        actualResPOI.add(add1);
        createPin Pin = new createPin(actualResPOI);
        int size = Pin.getPOICollab().size();
        //they should not equal
        assertNotEquals(0, size);
    }
    /**
     * Tests the getPOIFav method of the createPin class.
     * Checks if the favorite POIs are correctly returned.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getPOIFavTrue() throws Exception{
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedResPOI = new ArrayList<>();
        POI add = new POI(0,0,"test","0","Fav",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add);
        Main.allPOI.add(add);
        Main.allPOI.add(add);
        expectedResPOI.add(add);
        createPin Pin = new createPin(expectedResPOI,expectedResPOI,expectedResPOI);
        //check if the content are equal
        assertEquals(expectedResPOI, Pin.getPOIFav());
    }
    /**
     * Tests the getPOIFav method of the createPin class with an incorrect input.
     * Checks that the actual POIs returned by the method are not equal to an empty ArrayList.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getPOIFavFalse() throws Exception{
        // Create a test ArrayList of POI objects
        ArrayList<POI> actualResPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Fav",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        Main.allPOI.add(add1);
        Main.allPOI.add(add1);
        actualResPOI.add(add1);
        createPin Pin = new createPin(actualResPOI,actualResPOI,actualResPOI);
        int size = Pin.getPOIFav().size();
        //they should not equal
        assertNotEquals(0, size);

    }
    /**
     * Test method for getPOIUserDefine method.
     * Checks if the user-defined POIs are correctly returned.
     * @throws Exception if an exception is thrown
     */
    @Test
    void getPOIUserDefineTrue() throws Exception{
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedResPOI = new ArrayList<>();
        POI add = new POI(0,0,"test","0","UserDefine",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add);
        Main.allPOI.add(add);
        Main.allPOI.add(add);
        expectedResPOI.add(add);
        createPin Pin = new createPin(expectedResPOI,expectedResPOI,expectedResPOI);
        //check if the content are equal
        assertEquals(expectedResPOI, Pin.getPOIUserDefine());
    }
    /**
     * Tests the getPOIUserDefine method of the createPin class with an incorrect input.
     * Checks that the actual POIs returned by the method are not equal to an empty ArrayList.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getPOIUserDefineFalse() throws Exception{
        // Create a test ArrayList of POI objects
        ArrayList<POI> actualResPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","UserDefine",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        Main.allPOI.add(add1);
        Main.allPOI.add(add1);
        actualResPOI.add(add1);
        createPin Pin = new createPin(actualResPOI,actualResPOI,actualResPOI);
        int size = Pin.getPOIUserDefine().size();
        //they should not equal
        assertNotEquals(0, size);
    }
    /**
     * Test method for getClassroom method in the creatPin class.
     * Checks if the classroom label are correctly returned.
     * @throws Exception if an exception is thrown
     */
    @Test
    void getClassroomTrue() throws Exception {
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedClassroomPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Classroom",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        expectedClassroomPOI.add(add1);
        createPin Pin = new createPin(expectedClassroomPOI);
        ArrayList<JLabel> actualClassLabel = Pin.getClassroom();
        //compare the size of classroom
        int size = actualClassLabel.size();
        assertEquals(1,size);
    }
    /**
     * Tests the getClassroom method of the createPin class with an incorrect input.
     * Checks that the actual label returned by the method are not equal to an empty ArrayList.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getClassroomFalse() throws Exception {
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedClassroomPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Classroom",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        expectedClassroomPOI.add(add1);
        createPin Pin = new createPin(expectedClassroomPOI);
        ArrayList<JLabel> actualClassLabel = Pin.getClassroom();
        //compare the size of classroom
        int size = actualClassLabel.size();
        assertNotEquals(0, size);
    }
    /**
     * Test method for getLab method in the creatPin class.
     * Checks if the lab label are correctly returned.
     * @throws Exception if an exception is thrown
     */
    @Test
    void getLabTrue() throws Exception{
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedLabPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Lab",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        expectedLabPOI.add(add1);
        createPin Pin = new createPin(expectedLabPOI);
        ArrayList<JLabel> actualLabLabel = Pin.getLab();
        //compare the size of lab
        int size = actualLabLabel.size();
        assertEquals(1,size);
    }
    /**
     * Tests the getLab method of the createPin class with an incorrect input.
     * Checks that the actual label returned by the method are not equal to an empty ArrayList.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getLabFalse() throws Exception{
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Lab",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        expectedPOI.add(add1);
        createPin Pin = new createPin(expectedPOI);
        ArrayList<JLabel> actualLabel = Pin.getLab();
        //compare the size of lab
        int size = actualLabel.size();
        assertNotEquals(0, size);
    }
    /**
     * Test method for getRestaurant method in the creatPin class.
     * Checks if the restaurant label are correctly returned.
     * @throws Exception if an exception is thrown
     */
    @Test
    void getRestaurantTrue() throws Exception{
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Restaurant",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        expectedPOI.add(add1);
        createPin Pin = new createPin(expectedPOI);
        ArrayList<JLabel> actualLabLabel = Pin.getRestaurant();
        //compare the size of restaurant
        int size = actualLabLabel.size();
        assertEquals(1,size);
    }
    /**
     * Tests the getRestaurant method of the createPin class with an incorrect input.
     * Checks that the actual label returned by the method are not equal to an empty ArrayList.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getRestaurantFalse() throws Exception{
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Restaurant",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        expectedPOI.add(add1);
        createPin Pin = new createPin(expectedPOI);
        ArrayList<JLabel> actualLabel = Pin.getRestaurant();
        //compare the size of restaurant
        int size = actualLabel.size();
        assertNotEquals(0, size);
    }
    /**
     * Test method for getCollaborative method in the creatPin class.
     * Checks if the collaborative label are correctly returned.
     * @throws Exception if an exception is thrown
     */
    @Test
    void getCollaborativeTrue() throws Exception{
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Collaborative Room",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        expectedPOI.add(add1);
        createPin Pin = new createPin(expectedPOI);
        ArrayList<JLabel> actualLabLabel = Pin.getCollaborative();
        //compare the size of collab
        int size = actualLabLabel.size();
        assertEquals(1,size);
    }
    /**
     * Tests the getCollaborative method of the createPin class with an incorrect input.
     * Checks that the actual label returned by the method are not equal to an empty ArrayList.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getCollaborativeFalse() throws Exception{
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Collaborative Room",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        expectedPOI.add(add1);
        createPin Pin = new createPin(expectedPOI);
        ArrayList<JLabel> actualLabel = Pin.getCollaborative();
        //compare the size of collab
        int size = actualLabel.size();
        assertNotEquals(0, size);
    }
    /**
     * Test method for getsearch method in the creatPin class.
     * Checks if the search label are correctly returned.
     * @throws Exception if an exception is thrown
     */
    @Test
    void getSearchTrue() throws Exception {
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Search",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        expectedPOI.add(add1);
        createPin Pin = new createPin(expectedPOI);
        ArrayList<JLabel> actualLabel = Pin.getSearch();
        //compare the size of search
        int size = actualLabel.size();
        assertEquals(1,size);
    }
    /**
     * Tests the getSearch method of the createPin class with an incorrect input.
     * Checks that the search label returned by the method are not equal to an empty ArrayList.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getSearchFalse() throws Exception {
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Search",
                "Middlesex College", 0, "test description");
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        expectedPOI.add(add1);
        createPin Pin = new createPin(expectedPOI);
        ArrayList<JLabel> actualLabel = Pin.getSearch();
        //compare the size of search
        int size = actualLabel.size();
        assertNotEquals(1, size);
    }
    /**
     * Test method for getTotal method in the creatPin class.
     * Checks if all the label are correctly returned.
     * @throws Exception if an exception is thrown
     */
    @Test
    void getTotalTrue() throws Exception {
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Collaborative Room",
                "Middlesex College", 0, "test description");
        POI add2 = new POI(0,0,"test","0","Lab",
                "Middlesex College", 0, "test description");
        POI add3 = new POI(0,0,"test","0","Classroom",
                "Middlesex College", 0, "test description");
        //add to main so the size equals, it allows to enter the if statement
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        Main.allPOI.add(add2);
        Main.allPOI.add(add3);
        expectedPOI.add(add1);
        expectedPOI.add(add2);
        expectedPOI.add(add3);

        createPin Pin = new createPin(expectedPOI);
        ArrayList<JLabel> actualLabel = Pin.getTotal();
        //compare the size of total
        int size = actualLabel.size();
        assertEquals(3, size);
    }
    /**
     * Tests the getTotal method of the createPin class with an incorrect input.
     * Checks that the total label returned by the method are not equal to a different size
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getTotalFalse() throws Exception {
        // Create a test ArrayList of POI objects
        ArrayList<POI> expectedPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Collaborative Room",
                "Middlesex College", 0, "test description");
        POI add2 = new POI(0,0,"test","0","Lab",
                "Middlesex College", 0, "test description");
        POI add3 = new POI(0,0,"test","0","Search",
                "Middlesex College", 0, "test description");
        //add to main so the size equals,add one search, the total should not count that
        Main.allPOI = new ArrayList<>();
        Main.allPOI.add(add1);
        Main.allPOI.add(add2);

        expectedPOI.add(add1);
        expectedPOI.add(add2);
        expectedPOI.add(add3);

        createPin Pin = new createPin(expectedPOI);
        ArrayList<JLabel> actualLabel = Pin.getTotal();
        //compare the size of total
        int size = actualLabel.size();
        assertNotEquals(3, size);
    }
    /**
     * Test method for getFav method in the creatPin class.
     * Checks if the Fav label are correctly returned.
     * @throws Exception if an exception is thrown
     */
    @Test
    void getFavTrue() throws Exception{
        ArrayList<POI> expectedPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Fav",
                "Middlesex College", 0, "test description");

        Main.allPOI = new ArrayList<>();
        expectedPOI.add(add1);

        createPin Pin = new createPin(expectedPOI,expectedPOI);
        ArrayList<JLabel> actualLabel = Pin.getFav();
        //compare the size of total
        int size = actualLabel.size();
        assertEquals(1, size);
    }
    /**
     * Tests the getFav method of the createPin class with an incorrect input.
     * Checks that the Fav label returned by the method are not equal to an empty ArrayList.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getFavFalse() throws Exception{
        ArrayList<POI> expectedPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Fav",
                "Middlesex College", 0, "test description");

        Main.allPOI = new ArrayList<>();
        expectedPOI.add(add1);

        createPin Pin = new createPin(expectedPOI,expectedPOI);
        ArrayList<JLabel> actualLabel = Pin.getFav();
        //compare the size of total
        int size = actualLabel.size();
        assertNotEquals(0, size);
    }
    /**
     * Test method for getCreated method in the creatPin class.
     * Checks if the created label are correctly returned.
     * @throws Exception if an exception is thrown
     */
    @Test
    void getCreatedTrue() throws Exception {
        ArrayList<POI> expectedPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Created",
                "Middlesex College", 0, "test description");
        //because it doesn't compare with the main size, so don't have to add anything to Main.allPOI
        Main.allPOI = new ArrayList<>();
        expectedPOI.add(add1);

        createPin Pin = new createPin(expectedPOI,expectedPOI);
        ArrayList<JLabel> actualLabel = Pin.getCreated();
        //compare the size of total
        int size = actualLabel.size();
        assertEquals(1, size);
    }
    /**
     * Tests the getCreated method of the createPin class with an incorrect input.
     * Checks that the created label returned by the method are not equal to an empty ArrayList.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getCreatedFalse() throws Exception{
        ArrayList<POI> expectedPOI = new ArrayList<>();
        POI add1 = new POI(0,0,"test","0","Created",
                "Middlesex College", 0, "test description");
        //because it doesn't compare with the main size, so don't have to add anything to Main.allPOI
        Main.allPOI = new ArrayList<>();
        expectedPOI.add(add1);
        createPin Pin = new createPin(expectedPOI,expectedPOI);
        ArrayList<JLabel> actualLabel = Pin.getCreated();
        //compare the size of total
        int size = actualLabel.size();
        assertNotEquals(0, size);
    }

}