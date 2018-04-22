package chess;

import org.junit.Assert;
import org.junit.Test;

/*
 * If any of those fail, then you broke something in the algorithm
 */
public class ChessTest {

    @Test
    public void testUnreachableBoards() {
        Square start = new Square(0, 1);
        Square destination = new Square(4, 4);
        Assert.assertFalse(App.isReachable(112, start, destination));
        Assert.assertFalse(App.isReachable(108, start, destination));
        Assert.assertFalse(App.isReachable(104, start, destination));
        Assert.assertFalse(App.isReachable(100, start, destination));
    }

    @Test
    public void testReachableBoards() {
        Square start = new Square(0, 1);
        Square destination = new Square(62, 40);
        Assert.assertTrue(App.isReachable(112, start, destination));
        Assert.assertTrue(App.isReachable(108, start, destination));
        Assert.assertTrue(App.isReachable(104, start, destination));
        Assert.assertTrue(App.isReachable(100, start, destination));
    }
}
