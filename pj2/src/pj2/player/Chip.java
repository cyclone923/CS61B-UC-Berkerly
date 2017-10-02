package pj2.player;
import list.*;

public class Chip {
	
	private static final int isblack = 1;
	private static final int iswhite = 2;
	
	private int Color;
	private int Xcor;
	private int Ycor;
	
	
	public Chip(int color,int x,int y)
	{
		Color = color;
		Xcor = x;
		Ycor = y;
	}
	
	public int getColor()
	{
		return Color;
	}
	
	public int getXcor()
	{
		return Xcor;
	}
	
	public int getYcor()
	{
		return Ycor;
	}
	
	public boolean equals(Chip another)
	{
		if(Xcor == another.getXcor() && Ycor == another.getYcor() && Color == another.getColor())
		{
			return true;
		}
		return false;
	}
	
	public boolean isInStartArea()
	{
		if(Color == isblack)
		{
			return(Ycor == 0);
		}
		return (Xcor == 0);
	}
	
	public boolean isInGoalArea()
	{
		if(Color == isblack)
		{
			return(Ycor == 7);
		}
		return (Xcor == 7);
	}
	
	public boolean isInMidArea()
	{
		return (Xcor > 0 && Xcor < 7 && Ycor > 0 && Ycor < 7);
	}
	
	public String toString()
	{
		if(Color == isblack)
		{
			return "B"+ Xcor + Ycor + (isInStartArea()?"S":"") + (isInGoalArea()?"G":"") + (isInMidArea()?"M":"");
		}
		return "W"+ Xcor + Ycor + (isInStartArea()?"S":"") + (isInGoalArea()?"G":"") + (isInMidArea()?"M":"");

	}

}
