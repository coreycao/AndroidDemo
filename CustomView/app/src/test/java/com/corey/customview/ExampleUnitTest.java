package com.corey.customview;

import org.junit.Test;

import static com.corey.customview.chart.DataProcessor.ceil2Int;
import static com.corey.customview.chart.DataProcessor.currencyFormat;
import static com.corey.customview.chart.DataProcessor.floor2Int;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void floor2Int_isCorrect() {
        long d = -55;
        assertEquals(floor2Int(d), 50, 0);
    }

    @Test
    public void ceil2Int_isCorrect() {
        long d = 66;
        assertEquals(ceil2Int(d), 70, 0);
    }

    @Test
    public void decimalFormat_isCorrect() {
        double d = 906222100d;
        assertEquals(currencyFormat(d), "1");

    }

}