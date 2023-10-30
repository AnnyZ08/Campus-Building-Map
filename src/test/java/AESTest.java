import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class AESTest {

    private String Text;
    private String result;


    @Test
    void encryptPWTest1() throws Exception {
        Text = AES.encryptPW("test encryptPW");
        assertEquals("W5cP6NiDFwAiPZTmEbS6KA==", Text);

    }

    @Test
    void encryptPWTest2() throws Exception {
        Text = AES.encryptPW("CS2212");
        assertEquals("AyA0tDtxgdEU40He4KDz4A==", Text);
    }

    @Test
    void encryptPWTest3(){

        assertThrows(Exception.class,
                () -> {
                    Text = AES.encryptPW(null);
                });
    }


    @Test
    void decryptPWTest1() throws Exception {
        result = AES.decryptPW("W5cP6NiDFwAiPZTmEbS6KA==");
        assertEquals("test encryptPW",result);

    }


    @Test
    void decryptPWTest2() throws Exception {
        result = AES.decryptPW("AyA0tDtxgdEU40He4KDz4A==");
        assertEquals("CS2212",result);

    }


    @Test
    void decryptPWTest3() {
        assertThrows(Exception.class,
                () -> {
                    Text = AES.decryptPW(null);
                });

    }

}