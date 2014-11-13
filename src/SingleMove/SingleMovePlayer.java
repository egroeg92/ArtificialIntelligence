package SingleMove;

import halma.CCBoard;
import halma.CCMove;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import s260401458.mytools.MyTools;
import boardgame.Board;
import boardgame.Move;
import boardgame.Player;

/**
 *A random Halma player.
 */
public class SingleMovePlayer extends Player {
    private static final int DEPTH = 2;
	Random rand = new Random();
    private double prevMan;
    
    /** Provide a default public constructor */
    public SingleMovePlayer() 
    { 
    	super("sSingMovePlayer");
    	prevMan = 1000;
    }
    public SingleMovePlayer(String s) { super(s); }
    
    public Board createBoard() { return new CCBoard(); }

    
    /** Implement a very stupid way of picking moves */
    public Move chooseMove(Board theboard) 
    {
        // Cast the arguments to the objects we want to work with
        CCBoard board = (CCBoard) theboard;

        // Get the list of legal moves.
        ArrayList<CCMove> moves = board.getLegalMoves();
        
        int playerID = board.getIDForName("Player-123");
        
        CCMove move = moves.get(0);

        int playerNum = move.getPlayer_id();
        

        move = singleMove(board, moves, playerNum);

        return move;
//        return (CCMove) moves.get(rand.nextInt(moves.size()));
    }
	private CCMove singleMove(CCBoard board, ArrayList<CCMove> moves, int playerNum) {
        CCMove move = null;
        
        ArrayList<CCMove> backup = new ArrayList<CCMove>();

		double man = 1000;
        double man_t = man;
        double man_backup = man;

        for (CCMove m : moves)
        {
            CCBoard boardcopy = (CCBoard) board.clone();
            
        	boardcopy.move(m);
        	
        	man_t = MyTools.getManhat(playerNum, boardcopy);
        	

        	if(man_t < man)
        	{
        		man = man_t;
        		move = m;
        	}
        	if(man_t < man + 2 )
        	{
        		man_backup = man_t;
        		backup.add(m);
        	}
        }
        
        if(man == prevMan)
        {
        	move = (CCMove) backup.get(rand.nextInt(backup.size()));
        	prevMan = man_backup;
        }
        else
        {
        	prevMan = man;
        }
		return move;
	}
	
    
}
