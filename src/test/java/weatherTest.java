import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class weatherTest {

    @Test
    void getImage() throws Exception {
        weather testWeather = new weather();
        assertNotNull(testWeather.getImage());
    }



    @Test
    void getTemp() throws Exception {
        weather testWeather = new weather();
        assertNotNull(testWeather.getTemp());
    }

    /** Important: Run this test only when have no internet connection */
    @Test
    void NoInternet() {

        assertThrows(Exception.class,
                () -> {
                    weather testWeather = new weather();
                });
    }


}