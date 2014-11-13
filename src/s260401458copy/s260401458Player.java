package s260401458copy;

import halma.CCBoard;
import halma.CCMove;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import s260401458.mytools.MyTools;
import boardgame.Board;
import boardgame.Move;
import boardgame.Player;

/**
 *A random Halma player.
 */
public class s260401458Player extends Player {
    private static final int DEPTH = 1;
	Random rand = new Random();
    private double prevMan;
    private double cur_Man;
    private ArrayList<CCMove> finish_list;
    private ArrayList<CCMove> tmp_moves;
    private int turn;
    private CCMove from;
    /** Provide a default public constructor */
    public s260401458Player() 
    { 
    	super("s260401458");
    	prevMan = 1000;
    	cur_Man = 1000;
    	finish_list = new ArrayList<CCMove>();
    	tmp_moves = new ArrayList<CCMove>();
    	from = null;
    	turn = 0;
    	
    }
    public s260401458Player(String s) { super(s); }
    
    public Board createBoard() { return new CCBoard(); }

    
    public Move chooseMove(Board theboard) 
    {
        // Cast the arguments to the objects we want to work with
        CCBoard board = (CCBoard) theboard;

        // Get the list of legal moves.
        ArrayList<CCMove> moves = board.getLegalMoves();
        
        int playerID = board.getIDForName("Player-260401458");
        System.out.println("PLAYER ID :"+playerID);
        
        CCMove move = moves.get(0);
        

        int playerNum = move.getPlayer_id();
        
        cur_Man = MyTools.getManhat(playerNum, board);


        CCBoard board_copy = (CCBoard) board.clone();
        
        int i = 0;
        if(tmp_moves.isEmpty())
        {
            recursiveDFS(board,playerNum,0,cur_Man);       	
        }
        if (tmp_moves.size() == 0)
        {
        	double dist = 100;
        	double dist_t;
        	System.out.println("SINGLEMOVE");
        	for(CCMove m : moves)
        	{
        		if(m.getFrom() == null)
        		{
        			continue;
        		}
        		if(MyTools.inGoal(playerNum,m.getFrom()))
        		{
        			System.out.println("IN GOAL "+ m.getFrom());
        			continue;
        		}
        		if(MyTools.inGoal(playerNum,m.getTo()))
        		{
        			System.out.println("MOVE INTO GOAL "+ m.getTo());
        			move = m;
        			break;
        		}
        		dist_t =  m.getTo().distance(MyTools.closestGoal(playerNum, board_copy));
        		System.out.println(" DIST ="+ dist_t + " "+ m.getFrom()+ " ->"+m.getTo());
        		if(dist > dist_t )
        		{
        			if( from == null || !m.getTo().equals(from.getFrom()))
        			{
        				dist = dist_t;
        				move = m;
        			}
        			else if(m.getTo().equals(from.getFrom()))
        			{
        				continue;
        			}
        		}
        	}
        	from = move;
//        		move = singleMove(board,moves,playerNum);
        }
        else
        {
        	move = tmp_moves.remove(0);
        }
        turn++;

        System.out.println(move.toPrettyString());
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
        	man_t = MyTools.getManhat(playerNum,boardcopy);
        	

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
	
	//TODO : 
	// 
	// remove recusive moves (use from and to)
	private double recursiveDFS(CCBoard board, int playerNum,int count,double score)
	{
		if( count == DEPTH)
		{
			return MyTools.getManhat(playerNum, board);
	    }
		
		double score_tmp = score;
		double score_t = score;
		
		ArrayList<CCMove> moves = (ArrayList<CCMove>) board.getLegalMoves().clone();
		ArrayList<CCMove> new_moves = (ArrayList<CCMove>) board.getLegalMoves().clone();
		
		ArrayList<CCMove> tmp = new ArrayList<CCMove>();
		double random;
		
		System.out.println(count+" pre "+moves.size());
		
//		if ((count == 0 && new_moves.size() > 100))
//		{
//			
//			for(CCMove m : new_moves )
//			{
//				random = Math.random();
//				if( random <= .2)
//				{
//					removeMove(moves,m);
//
//				}
//			}
//		}
//		else if( count > 0)
//		{
//			for(CCMove m : new_moves )
//			{
//				random = Math.random();
//				if( random <= .99)
//				{
//					removeMove(moves,m);
//				}
//			}
//		}

		System.out.println("post "+moves.size());
		
		
		for(CCMove m:moves)
		{
			CCBoard board_copy = (CCBoard) board.clone();
			
			//Complete a turn
			board_copy.move(m);
						
//			System.out.println(m.toPrettyString());
			tmp.add(m);
			
			
//			c is whose turn it is
			int c = board_copy.getTurn();
			
			
			//if still players turn, finish by choosing optimal 
			
			while (c == playerNum)
			{			
				CCMove mo = singleMove(board_copy,board_copy.getLegalMoves(),c);
				board_copy.move(mo);
				c = board_copy.getTurn();
				tmp.add(mo);
			}
			
			//finish turns for all opponents
			while(c!=playerNum)
			{
				board_copy.move(singleMove(board_copy,board_copy.getLegalMoves(),c));
				c = board_copy.getTurn();

			}
			
			//recursive call - get the next move

			score_t = recursiveDFS(board_copy,playerNum,count+1,score_tmp);
			
			if(score_t<score_tmp)
			{	
				score_tmp = score_t;
				tmp_moves.clear();
				
				for(CCMove x : tmp)
				{
					tmp_moves.add(x);
				}
			}
			tmp.clear();
		}
		return score_tmp;
	}
	private void removeMove(ArrayList<CCMove> moves, CCMove m) {
		
		for(CCMove mo : moves)
		{
			if(m.getTo().equals(mo.getTo())&& m.getFrom().equals(mo.getFrom()))
			{
				moves.remove(mo);
				break;
			}
		}
		
	}


    
}
