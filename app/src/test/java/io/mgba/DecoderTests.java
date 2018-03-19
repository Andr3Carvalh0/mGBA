package io.mgba;

import org.junit.Test;

import io.mgba.Model.IO.Decoder;
import static org.junit.Assert.*;

public class DecoderTests {

    //Pokemon fire red value
    private byte[] bytes = {-30, 110, -32, -44, 78,-128, -109, 81, -56, -50, 45, 115, -57, 64, 12, -35};
    private String EXPECTED_VALUE = "E26EE0D44E809351C8CE2D73C7400CDD";

    @Test
    public void isCalculatingMD5Correctly(){
        assertEquals(EXPECTED_VALUE, Decoder.calculateMD5(bytes));
    }

}
