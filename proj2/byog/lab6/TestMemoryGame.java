package byog.lab6;

import edu.princeton.cs.algs4.StdDraw;

import javax.swing.plaf.metal.MetalMenuBarUI;
import java.awt.*;
import java.util.Random;
import org.junit.Test;

public class TestMemoryGame {
    public static void main(String[] args) {
        // test generateRandomString, passed
        /*
        MemoryGame m = new MemoryGame(123);
        String s = m.generateRandomString(8);
        System.out.println(s);
        */

        // test drawFrame(s), passed
        String s = "123456789";
        MemoryGame m = new MemoryGame(123);
        //m.drawFrame(s);

        // test drawFrame(s), passed
        //m.flashSequence(s);
        m.solicitNCharsInput(10);
        //





    }
}
