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
        Assert.assertFalse(App.isReachable(32, start, destination));
        Assert.assertFalse(App.isReachable(28, start, destination));
        Assert.assertFalse(App.isReachable(24, start, destination));
        Assert.assertFalse(App.isReachable(20, start, destination));
    }

    @Test
    public void testReachableBoards() {
        Square start = new Square(0, 1);
        Square destination = new Square(4, 5);
        Assert.assertTrue(App.isReachable(32, start, destination));
        Assert.assertTrue(App.isReachable(28, start, destination));
        Assert.assertTrue(App.isReachable(24, start, destination));
        Assert.assertTrue(App.isReachable(20, start, destination));
    }
}
