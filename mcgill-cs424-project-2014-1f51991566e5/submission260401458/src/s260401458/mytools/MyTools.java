package s260401458.mytools;

import halma.CCBoard;
import halma.CCMove;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

public class MyTools {
	
	public static double getSomething(){
		return Math.random();
	}
	
	
	public static final Point[][] bases = 
	{		 
			{ 
				// player 0 base points
				new Point(0, 0),
				
				new Point(0, 1),
				new Point(1, 1),
				new Point(1, 0),
				
				new Point(2, 0),
				new Point(2, 1),
				new Point(2, 2),
				new Point(1, 2),
				new Point(0, 2),
				
				new Point(0, 3),
				new Point(1, 3),
				new Point(3, 1),
				new Point(3, 0)
			} , 
			{
				// player 1 base points
				new Point(15, 0),
				
				new Point(15, 1),
				new Point(14, 1),
				new Point(14, 0),
				
				new Point(13, 0),
				new Point(13, 1),
				new Point(13, 2),
				new Point(14, 2),
				new Point(15, 2),
				
				new Point(15, 3),
				new Point(14, 3),
				new Point(12, 1),
				new Point(12, 0)
			} , 
			{
				// player 2 base points
				new Point(0, 15),
			
				new Point(1, 15),
				new Point(1, 14),
				new Point(0, 14),
			
				new Point(0, 13),
				new Point(1, 13),
				new Point(2, 13),
				new Point(2, 14),
				new Point(2, 15),
			
				new Point(0, 12),
				new Point(1, 12),
				new Point(3, 14),
				new Point(3, 15)
			
			} , 
			{
				// player 3 base points
				new Point(15, 15),
				
				new Point(14, 15),
				new Point(14, 14),
				new Point(15, 14),
				
				new Point(15, 13),
				new Point(14, 13),
				new Point(13, 13),
				new Point(13, 14),
				new Point(13, 15),
				
				new Point(15, 12),
				new Point(14, 12),
				new Point(12, 14),
				new Point(12, 15)
			
			} 
	
	};	
	public static Point closestGoal(int playerNum, CCBoard board)
	{
		Point[] goal;
		if (playerNum == 0)
		{
			goal = bases[3];
					
		}
		else if ( playerNum ==1)
		{
			goal = bases[2];


		}
		else if ( playerNum == 2)
		{
			goal = bases[1];


		}
		else
		{
			goal = bases[0];

		}
		for (int i = 0 ; i < goal.length; i++)
		{
			if(board.getPieceAt(goal[i]) == null || board.getPieceAt(goal[i]) != playerNum)
			{
				return goal[i];
			}
		}
		return null;
	}
	
	public static double getManhat(int playerNum, CCBoard board )
	{
		int manhat = 0;
		ArrayList<Point> pieces = board.getPieces(playerNum);
		
		double goalx = -1;
		double goaly = -1;
		
		Point[] goal;
		Point[] side1;
		Point[] side2;
		if (playerNum == 0)
		{
			goal = bases[3];
			side1 = bases[1];
			side2 = bases[2];
		}
		else if ( playerNum ==1)
		{
			goal = bases[2];

			side1 = bases[0];
			side2 = bases[3];

		}
		else if ( playerNum == 2)
		{
			goal = bases[1];

			side1 = bases[0];
			side2 = bases[3];
		}
		else
		{
			goal = bases[0];

			side1 = bases[1];
			side2 = bases[2];

		}
		for (int i = 0 ; i < goal.length; i++)
		{
			if(board.getPieceAt(goal[i]) == null || board.getPieceAt(goal[i]) != playerNum)
			{
				goalx = goal[i].getX();
				goaly = goal[i].getY();
				break;
			}
		}		
		if(goalx ==-1)
		{
			return 0;
		}
		else
		{
			for (Point p : pieces)
			{
				double x =p.getX();
				double y =p.getY();
				
				manhat += (Math.abs(goalx-x));
				manhat += (Math.abs(goaly-y));
				
			}
			return manhat;		
	
		}
	}
	

	
	public static boolean inGoal(int playerNum,Point p)
	{
		int opNum;
		
		if(playerNum == 0)
		{
			opNum = 3;
		}
		else if ( playerNum == 1)
		{
			opNum = 2;
		}
		else if ( playerNum == 2)
		{
			opNum = 1;
		}
		else 
		{
			opNum = 0;
		}
		
		for(int i = 0 ; i < bases[opNum].length; i++)
		{			
			if( p.equals(bases[opNum][i]))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean inBase(int playerNum,Point p)
	{
		
		
		for(int i = 0 ; i < bases[playerNum].length; i++)
		{			
			if( p.equals(bases[playerNum][i]))
			{
				return true;
			}
		}
		return false;
	}
}
