package pj2.player;

public class Network {
	
	private static final int Invalid = 0;
	private static final int StoM = 1;
	private static final int MtoM = 2;
	private static final int MtoG = 3;
	private static final int StoG = 4;
	
	private Chip Start;
	private Chip End;
	private int Length;
	private int Type;
	private int Score;
	
	public Network(Chip start,Chip end,int length)
	{
		Start = start;
		End = end;
		Length = length;
		Type = calcType();
		
		Score = calScore();
	}
	
	private int calcType()
	{
		int type = Invalid;
		if(Start.isInStartArea())
		{
			if(End.isInMidArea())
			{
				type = StoM;
			}
			if(End.isInGoalArea())
			{
				type = StoG;
			}
		}
		if(Start.isInMidArea())
		{
			if(End.isInStartArea())
			{
				type = StoM;
			}
			if(End.isInMidArea())
			{
				type = MtoM;
			}
			if(End.isInGoalArea())
			{
				type = MtoG;
			}
		}
		if(Start.isInGoalArea())
		{
			if(End.isInMidArea())
			{
				type = MtoG;
			}
			if(End.isInStartArea())
			{
				type = StoG;
			}
		}
		return type;
	}
	
	private int calScore()

	{
		int score = 0;
		if(Type == StoG)
		{
			if(Length >= 6)
			{
				score = 6;
			}
			else
			{
				score = Length;
			}
			return score;
		}
		if(Type == StoM || Type == MtoG)
		{
			if(Length >= 5)
			{
				score = 5;
			}
			else
			{
				score = Length;
			}
			return score;
		}
		if(Type == MtoM)
		{
			if(Length >= 4)
			{
				score = 4;
			}
			else
			{
				score = Length;
			}
			return score;
		}
		return score;
		
	}

	public int getScore()
	{
		return Score;
	}
	
 	public boolean equals(Network another)
	{
		if(another.Length == Length)
		{
			if(another.End == Start && another.Start == End)
			{
				return true;
			}
			if(another.Start == Start && another.End == End)
			{
				return true;
			}
		}
		return false;
	}
	
	public String toString()
	{
		return Start + "--" + Length +","+ Score + "--" + End;
	}


}
