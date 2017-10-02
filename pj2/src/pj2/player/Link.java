package pj2.player;

public class Link {
	
	private static final int NotLinked = 0;
	private static final int Vertically = 1;
	private static final int Horizontally = 2;
	private static final int DiagonallyP = 3;
	private static final int DiagonallyN = 4;
	
	private static final int isblack = 1;
	private static final int iswhite = 2;
	
	private static final int Invalid = 0;
	private static final int StoM = 1;
	private static final int MtoM = 2;
	private static final int MtoG = 3;
	
	private int LinkWay;
	private int Color;
	private Chip ChipA;
	private Chip ChipB;
	private int LinkType;
	
	public Link(int way,int color,Chip a,Chip b)
	{
		LinkWay = way;
		Color = color;
		ChipA = a;
		ChipB = b;
		if(a.isInStartArea())
		{
			if(b.isInMidArea())
			{
				LinkType = StoM;
			}
			else
			{
				LinkType = Invalid;
			}
		}
		if(a.isInGoalArea())
		{
			if(b.isInMidArea())
			{
				LinkType = MtoG;
			}
			else
			{
				LinkType = Invalid;
			}
		}
		if(a.isInMidArea())
		{
			if(b.isInStartArea())
			{
				LinkType = StoM;
			}
			if(b.isInMidArea())
			{
				LinkType = MtoM;
			}
			if(b.isInGoalArea())
			{
				LinkType = MtoG;
			}
		}
	}
	
	public int getLinkWay()
	{
		return LinkWay;
	}
	
	public int getLinkType()
	{
		return LinkType;
	}
	
	public Chip getChipA()
	{
		return ChipA;
	}
	
	public Chip getChipB()
	{
		return ChipB;
	}
	
	public String toString()
	{
		String way = "";
		switch(LinkWay)
		{
		case Vertically:
			way = "V";
			break;
		case Horizontally:
			way = "H";
			break;
		case DiagonallyP:
			way = "DP";
			break;
		case DiagonallyN:
			way = "DN";
			break;
		}
		return ChipA + "-" + way + "-" + ChipB;
	}
	

}