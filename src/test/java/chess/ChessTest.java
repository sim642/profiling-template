package chess;

import chess.pieces.Knight;
import org.junit.Assert;
import org.junit.Test;

/*
 * If any of those fail, then you broke something in the algorithm
 */
public class ChessTest {

    @Test
    public void testUnreachableBoards() {
        Assert.assertFalse(App.Check.whether(new Knight()).canReach(4, 4).from(0, 1).usingBoardSize(112).run());
        Assert.assertFalse(App.Check.whether(new Knight()).canReach(4, 4).from(0, 1).usingBoardSize(108).run());
        Assert.assertFalse(App.Check.whether(new Knight()).canReach(4, 4).from(0, 1).usingBoardSize(104).run());
        Assert.assertFalse(App.Check.whether(new Knight()).canReach(4, 4).from(0, 1).usingBoardSize(100).run());
    }

    @Test
    public void testReachableBoards() {
        Assert.assertTrue(App.Check.whether(new Knight()).canReach(62, 40).from(0, 1).usingBoardSize(112).run());
        Assert.assertTrue(App.Check.whether(new Knight()).canReach(62, 40).from(0, 1).usingBoardSize(108).run());
        Assert.assertTrue(App.Check.whether(new Knight()).canReach(62, 40).from(0, 1).usingBoardSize(104).run());
        Assert.assertTrue(App.Check.whether(new Knight()).canReach(62, 40).from(0, 1).usingBoardSize(100).run());
    }
}
