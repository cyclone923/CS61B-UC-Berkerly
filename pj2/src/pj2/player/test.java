package pj2.player;

public class test {
	
	private static final int black = 1;
	private static final int white = 2;
	
	private static final int nobodyWin = 0;
	private static final int blackWin = 1;
	private static final int whiteWin = 2;
	
	public static void main(String[] args)
	{
		MachinePlayer MP1 = new MachinePlayer(white,3);
		MachinePlayer MP2 = new MachinePlayer(black,3);
		int result = nobodyWin;
		for(int i = 0;;i++)
		{
			System.out.println("\nRound" + i);
			Move m1 = MP1.chooseMove();	
			System.out.println("white:" + m1 + "\n");
			result = MP1.getResult();
			if(result != nobodyWin)
			{
				System.out.println((result==1)?"Black Win!!!!!!":"White Win!!!!!!");
				break;
			}
			
			Move m2 = MP2.chooseMove();	
			System.out.println("black:" + m2 + "\n");
			result = MP2.getResult();
			if(result != nobodyWin)
			{
				System.out.println((result==1)?"Black Win!!!!!!":"White Win!!!!!!");
				break;
			}
		}
		


		

		
		
	}

}
