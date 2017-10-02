/* MachinePlayer.java */

package pj2.player;
import list.*;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

	//player info
	private static final int black = 1;
	private static final int white = 2;
	private int color;
	private int searchDepth;
	
	//Grid info
	private static int[][] Grid = new int[8][8];
	private static int blackleft;
	private static int whiteleft;
	private static SList blackNetworks;
	private static SList whiteNetworks;
	private static final int nobodyWin = 0;
	private static final int blackWin = 1;
	private static final int whiteWin = 2;
	private static final int isblank = 0;
	private static final int isblack = 1;
	private static final int iswhite = 2;
	private static int result = nobodyWin;
	
	//Link way
	private static final int NotLinked = 0;
	private static final int Vertically = 1;
	private static final int Horizontally = 2;
	private static final int DiagonallyP = 3;
	private static final int DiagonallyN = 4;
	
	//Link type
	private static final int Invalid = 0;
	private static final int StoM = 1;
	private static final int MtoM = 2;
	private static final int MtoG = 3;
	
	
  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
	  this.color = color;
	  searchDepth = 1;
	  blackleft = 10;
	  whiteleft = 10;
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
	  this.color = color;
	  this.searchDepth = searchDepth;
	  blackleft = 10;
	  whiteleft = 10;

  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
	  //Move m = randomMove();
	  int oppcolor = (color==black?white:black);
	  Best bestMove = bestMove(false,-6,6,searchDepth,color,oppcolor);
	  System.out.println("Score:" + bestMove.score);
	  updateGrid(bestMove.move);
	  
	  int score = evaluateGrid(true);
	  if(score == 6)
	  {
		  result = color;
	  }
	  else if(score == -6)
	  {
		  result = oppcolor;
	  }
	  
	  return bestMove.move;
  } 

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {

	  return true;
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  
  public boolean forceMove(Move m) {
	  if(!islegal(m))
	  {
		  return false;
	  }
	  updateGrid(m);
	  evaluateGrid(true);
	  return true;
  }
  
  
  private boolean islegal(Move m)
  {
	  int[][]GridCopy = new int[8][8];
	  for(int j = 0;j < 8;j++)
	  {
		  for(int i = 0;i < 8;i++)
		  {
			  GridCopy[i][j] = Grid[i][j];
		  }
	  }
	  if(m.moveKind == Move.STEP)
	  {
		  if(m.x1 == m.x2 && m.y1 == m.y2)
		  {
			  return false;
		  }
		  GridCopy[m.x2][m.y2] = isblank;
	  }
	  
	  //Chips are not allowed to be placed on the corner or other's goal area
	  if(color == black)
	  {
		  if(m.x1 == 0 || m.x1 == 7)
		  {
			  return false;
		  }
	  }
	  else if(color == white)
	  {
		  if(m.y1 == 0 || m.y1 == 7)
		  {
			  return false;
		  }
	  }
	  
	  //Chips must be placed on blank area
	  if(GridCopy[m.x1][m.y1] != isblank)
	  {
		  return false;
	  }
	  
	  GridCopy[m.x1][m.y1] = color;
	  
	  for(int j = 0;j < 8;j++)
	  {
		  for(int i = 0;i < 8;i++)
		  {
			  if(GridCopy[i][j] == color)
			  {
				  int clusterCnt = 0;
				  for(int y = j - 1;y <= j + 1;y++)
				  {
					  for(int x = i - 1;x <= i + 1;x++)
					  {
						  if(0<=x && x<=7 && 0<=y && y<=7)
						  {
							  if(GridCopy[x][y] == color)
							  {
								  clusterCnt++;
								  if(clusterCnt == 3)
								  {
									  return false;
								  }
							  }
						  }
					  }
				  }
			  }
		  }
	  }
	  
	  return true;
  }
  
  private Best bestMove(boolean isopponent,int alpha,int beta,int searchDepth,int mycolor,int oppcolor)
  {
	  if(!isopponent)
	  {
		  this.color = mycolor;
	  }
	  else
	  {
		  this.color = oppcolor;
	  }
	  Best myBest = new Best();
	  Best reply = new Best();
	  
	  int score = evaluateGrid(false);
	  SList moveList = allLegalMoves();
	  if(score == 6 || score == -6 || moveList.isEmpty())
	  {
		  myBest.score = score;
		  myBest.move = null;
		  return myBest;
	  }
	  if(isopponent)
	  {
		  if(beta == -6)
		  {
			  return myBest;
		  }
		  myBest.score = beta;
	  }
	  else
	  {
		  if(alpha == 6)
		  {
			  return myBest;
		  }
		  myBest.score = alpha;
	  }
	  try
	  {
		  searchDepth--;
		  myBest.move = (Move) moveList.front().item();
		  for(SListNode i = (SListNode) moveList.front();i.isValidNode();i = (SListNode) i.next())
		  {
			  Move m = (Move)i.item();
			  updateGrid(m);
			  if(evaluateGrid(false) == 6)
			  {
				  if(isopponent)
				  {
					  myBest.move = m;
					  myBest.score = -6;
				  }
				  else
				  {
					  myBest.move = m;
					  myBest.score = 6;
				  }
				  undoMove(m);
				  return myBest; 
			  }
			  else
			  {
				  undoMove(m); 
			  }
		  }
		  for(SListNode i = (SListNode) moveList.front();i.isValidNode();i = (SListNode) i.next())
		  {
			  Move m = (Move)i.item();
			  updateGrid(m);
			  if(searchDepth == 0)
			  {
				  if(isopponent)
				  {
					  score = - evaluateGrid(false);
				  }
				  else
				  {
					  score = evaluateGrid(false);
				  }
			  }
			  else
			  {
				  if(evaluateGrid(false) == 6)
				  {
					  if(isopponent)
					  {
						  myBest.move = m;
						  myBest.score = -6;
					  }
					  else
					  {
						  myBest.move = m;
						  myBest.score = 6;
					  }
					  undoMove(m);
					  return myBest;  
				  }
				  if(evaluateGrid(false) == -6)
				  {
					  undoMove(m);
					  continue;
				  }
				  reply = bestMove(!isopponent,alpha,beta,searchDepth,mycolor,oppcolor);
				  if(!isopponent)
				  {
					  this.color = mycolor;
				  }
				  else
				  {
					  this.color = oppcolor;
				  }
			  }
			  undoMove(m);
			  if(searchDepth != 0)
			  {
				  if(!isopponent && reply.score > myBest.score)
				  {
					  myBest.move = m;
					  myBest.score = reply.score;
					  alpha = reply.score;
				  }
				  else if(isopponent && reply.score < myBest.score)
				  {
					  myBest.move = m;
					  myBest.score = reply.score;
					  beta = reply.score;
				  }
			  }
			  else
			  {
				  if(!isopponent && score > myBest.score)
				  {
					  myBest.move = m;
					  myBest.score = score;
					  alpha = score;
				  }
				  else if(isopponent && score < myBest.score)
				  {
					  myBest.move = m;
					  myBest.score = score;
					  beta = score;
				  }
			  }
			  if(alpha >= beta)
			  {
				  return myBest;
			  }
		  }
		  searchDepth++;
		  return myBest;
	  }
	  catch(InvalidNodeException e)
	  {
		  //System.out.println(ranIdx +" "+ moveList.length());
		  e.printStackTrace();
	  }
	  
	  return myBest;
	  
  }
  private Move randomMove()
  {
	  SList moveList = allLegalMoves();
	  //System.out.println(moveList.length());
	  int ranIdx = Math.round((float)Math.random() * moveList.length());
	  Move ranMov = null;
	  if(!moveList.isEmpty())
	  {
		  try
		  {
			  SListNode node = (SListNode) moveList.front();
			  for(int i = 1;i < ranIdx;i++)
			  {
				  node = (SListNode) node.next();
			  }
			  ranMov = (Move) node.item(); 
		  }
		  catch(InvalidNodeException e)
		  {
			  //System.out.println(ranIdx +" "+ moveList.length());
			  e.printStackTrace();
		  }
	  }
	  return ranMov;
	  
  }
  
  private SList allLegalMoves()
  {
	  SList moveList = new SList();
	  int chipleft = (color==1)?blackleft:whiteleft;
	  if(chipleft > 0)
	  {
		  for(int j = 0;j <= 7;j++)
		  {
			  for(int i = 0;i <= 7;i++)
			  {
				  Move newMove = new Move(i,j);
				  if(islegal(newMove))
				  {
					  moveList.insertBack(newMove);
				  }
			  }
		  }
		  if(chipleft < 10)
		  {
			  for(int j = 0;j <= 7;j++)
			  {
				  for(int i = 0;i <= 7;i++)
				  {
					  for(int k = 1;k <= 10 - chipleft;k++)
					  {
						  int oldi = findChipXCor(color,k);
						  int oldj = findChipYCor(color,k);
						  Move newMove = new Move(i,j,oldi,oldj);
						  if(islegal(newMove))
						  {
							  moveList.insertBack(newMove);
						  }
					  }
				  }
			  }
		  }
	  }
	  else
	  {
		  for(int j = 0;j <= 7;j++)
		  {
			  for(int i = 0;i <= 7;i++)
			  {
				  for(int k = 1;k <= 10;k++)
				  {
					  int oldi = findChipXCor(color,k);
					  int oldj = findChipYCor(color,k);
					  Move newMove = new Move(i,j,oldi,oldj);
					  if(islegal(newMove))
					  {
						  moveList.insertBack(newMove);
					  }
				  }
			  }
		  }
	  }
	  return moveList;
  }
  
  private int findChipXCor(int color,int nth)
  {
	  int k = 0;
	  for(int j = 0;j < 8;j++)
	  {
		  for(int i = 0;i < 8;i++)
		  {
			  if(Grid[i][j] == color)
			  {
				  k++;
				  if(k == nth)
				  {
					  return i;
				  }
			  }
		  }
	  }
	  return 0;
  }
  
  private int findChipYCor(int color,int nth)
  {
	  int k = 0;
	  for(int j = 0;j < 8;j++)
	  {
		  for(int i = 0;i < 8;i++)
		  {
			  if(Grid[i][j] == color)
			  {
				  k++;
				  if(k == nth)
				  {
					  return j;
				  }
			  }
		  }
	  }
	  return 0;
  }
  
  
  //evaluation function
  private int evaluateGrid(boolean print)
  {
	  int point = 0;

	  //find all chips
	  Chip[] WhiteChips = findChips(iswhite);
	  Chip[] BlackChips = findChips(isblack);

	  
	  //find all Links

	  SList WhiteLinks = findLinks(iswhite,WhiteChips);
	  SList BlackLinks = findLinks(isblack,BlackChips);
	  

	  
	  findNetwork(BlackLinks,BlackChips,WhiteLinks,WhiteChips);
	  
	  int whitescore = 0;
	  int blackscore = 0;
	  
	  try
	  {
		  for(SListNode i = (SListNode) whiteNetworks.front();i.isValidNode();i = (SListNode) i.next())
		  {
			  if(((Network)i.item()).getScore() > whitescore)
			  {
				  whitescore = ((Network)i.item()).getScore();
			  }
		  }
		  for(SListNode i = (SListNode) blackNetworks.front();i.isValidNode();i = (SListNode) i.next())
		  {
			  if(((Network)i.item()).getScore() > blackscore)
			  {
				  blackscore = ((Network)i.item()).getScore();
			  }
		  }
	  }
	  catch (InvalidNodeException e) 
	  {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	  
	  if(color == black)
	  {
		  point = blackscore - whitescore;
	  }
	  else
	  {
		  point = whitescore - blackscore;
	  }
	  if(blackscore == 6 && whitescore !=6)
	  {
		  if(color == black)
		  {
			  point = 6;
		  }
		  if(color == white)
		  {
			  point = -6;
		  }
	  }
	  if(blackscore != 6 && whitescore ==6)
	  {
		  if(color == black)
		  {
			  point = -6;
		  }
		  if(color == white)
		  {
			  point = 6;
		  }
	  }
	  if(blackscore == 6 && whitescore == 6)
	  {
		  point = -6;
	  }
	  
	  if(print)
	  {
		  printGrid();
		  //System.out.println(WhiteLinks);
		  //System.out.println(BlackLinks);
		  System.out.println(whiteNetworks);
		  System.out.println(blackNetworks);
		  System.out.println("W Score" + whitescore);
		  System.out.println("B Score" + blackscore);
	  }
	  return point;
  }
  
  private int arelinked(Chip a,Chip b)
  {
	  if(a.getColor() != b.getColor())
	  {
		  return NotLinked;
	  }
	  
	  //linked vertically
	  if(a.getXcor() == b.getXcor() && a.getYcor() != b.getYcor())
	  {
		  int x = a.getXcor();
		  int startIdx = a.getYcor()<b.getYcor() ? a.getYcor():b.getYcor();
		  int endIdx = a.getYcor()>b.getYcor() ? a.getYcor():b.getYcor();
		  for(int i = startIdx + 1;i < endIdx;i++)
		  {
			  if(Grid[x][i] != isblank)
			  {
				  return NotLinked;
			  }
		  }
		  return Vertically;
	  }
	  
	  //linked horizontally
	  if(a.getYcor() == b.getYcor() && a.getXcor() != b.getXcor())
	  {
		  int y = a.getYcor();
		  int startIdx = a.getXcor()<b.getXcor() ? a.getXcor():b.getXcor();
		  int endIdx = a.getXcor()>b.getXcor() ? a.getXcor():b.getXcor();
		  for(int i = startIdx + 1;i < endIdx;i++)
		  {
			  if(Grid[i][y] != isblank)
			  {
				  return NotLinked;
			  }
		  }
		  return Horizontally;
	  }
	  
	  //linked diagonally
	  if(Math.abs(a.getXcor() - b.getXcor()) == Math.abs(a.getYcor() - b.getYcor()) && a.getYcor() != b.getYcor() && a.getXcor() != b.getXcor())
	  {
		  int startIdxX = a.getXcor();
		  int startIdxY = a.getYcor();
		  int endIdxX = b.getXcor();
		  int endIdxY = b.getYcor();
		  int Xincrement = (int) Math.signum(endIdxX - startIdxX);
		  int Yincrement = (int) Math.signum(endIdxY - startIdxY);
		  for(int i = 1;i < Math.abs(a.getXcor() - b.getXcor());i++)
		  {
			  if(Grid[startIdxX+i*Xincrement][startIdxY+i*Yincrement] != isblank)
			  {
				  return NotLinked;
			  }
		  }
		  if(Xincrement * Yincrement == 1)
		  {
			  return DiagonallyP;
		  }
		  return DiagonallyN;
	  }
	  return NotLinked;
  }
  
  private void updateGrid(Move m)
  {
	  if(m.moveKind == Move.ADD)
	  {
		  Grid[m.x1][m.y1] = color;
		  if(color == 1)
		  {
			  blackleft--;
		  }
		  else
		  {
			  whiteleft--;
		  }
	  }
	  if(m.moveKind == Move.STEP)
	  {
		  Grid[m.x2][m.y2] = 0;
		  Grid[m.x1][m.y1] = color;
	  }
  }
  
  private void undoMove(Move m)
  {
	  if(m.moveKind == Move.ADD)
	  {
		  Grid[m.x1][m.y1] = isblank;
		  if(color == 1)
		  {
			  blackleft++;
		  }
		  else
		  {
			  whiteleft++;
		  }
	  }
	  else
	  {
		  if(m.moveKind == Move.STEP)
		  {
			  Grid[m.x1][m.y1] = 0;
			  Grid[m.x2][m.y2] = color;
		  }
	  }
  }
  
  private void printGrid()
  {
	  System.out.println("white:"+(10-whiteleft));
	  System.out.println("black:"+(10-blackleft));
	  for(int j = 0;j < 8;j++)
	  {
		  for(int i = 0;i < 8;i++)
		  {
			  System.out.print(Grid[i][j]+"   ");
		  }
		  System.out.println("\n");
	  } 
	  
  }
  
  private Chip[] findChips(int color)
  {
	  Chip[] Chips = null;
	  if(color == iswhite)
	  {
		  Chips = new Chip[10-whiteleft];
		  for(int i = 0;i < 10 - whiteleft;i++)
		  {
			  Chips[i] = new Chip(iswhite,findChipXCor(iswhite,i+1),findChipYCor(iswhite,i+1));
		  }
	  }
	  if(color == isblack)
	  {
		  Chips = new Chip[10-blackleft];
		  for(int i = 0;i < 10 - blackleft;i++)
		  {
			  Chips[i] = new Chip(isblack,findChipXCor(isblack,i+1),findChipYCor(isblack,i+1));
		  }
	  }
	  return Chips;
  }
  

  
  private SList findLinks(int color,Chip Chips[])
  {
	  int tempway = NotLinked;
	  Link newLink;
	  SList allLinks = new SList();
	  if(color == iswhite)
	  {
		  for(int i = 0;i < 10 - whiteleft - 1;i++)
		  {
			  for(int j = i+1;j < 10 - whiteleft;j++)
			  {
				  tempway = arelinked(Chips[i],Chips[j]);
				  if(tempway != NotLinked)
				  {
					  newLink = new Link(tempway,iswhite,Chips[i],Chips[j]);
					  if(newLink.getLinkType() != Invalid)
					  {
						  allLinks.insertFront(newLink);
					  }
				  }
			  }
		  }
	  }
	  if(color == isblack)
	  {
		  for(int i = 0;i < 10 - blackleft - 1;i++)
		  {
			  for(int j = i+1;j < 10 - blackleft;j++)
			  {
				  tempway = arelinked(Chips[i],Chips[j]);
				  if(tempway != NotLinked)
				  {
					  newLink = new Link(tempway,isblack,Chips[i],Chips[j]);
					  if(newLink.getLinkType() != Invalid)
					  {
						  allLinks.insertFront(newLink);
					  }
				  }
			  }
		  }
	  }
	  return allLinks;
  }
  
  private void findNetwork(SList blackLinks,Chip blackChips[],SList whiteLinks,Chip whiteChips[])
  {
	  blackNetworks = new SList();
	  whiteNetworks = new SList();
	  for(int i = 0;i < blackChips.length;i++)
	  {
		  SList preChips = new SList();
		  findLinkedChip(isblack, blackChips[i],blackChips[i],blackLinks,0,NotLinked,preChips);
	  }
	  
	  for(int i = 0;i < whiteChips.length;i++)
	  {
		  SList preChips = new SList();
		  findLinkedChip(iswhite, whiteChips[i],whiteChips[i],whiteLinks,0,NotLinked,preChips);
	  }
	  if(!blackNetworks.isEmpty())
	  {
		  try 
		  {
			  for(SListNode i = (SListNode) blackNetworks.front();i.isValidNode();i = (SListNode) i.next())
			  {
				  for(SListNode j = (SListNode) i.next();j.isValidNode();)
				  {
					  if(((Network)j.item()).equals(((Network)i.item())))
					  {
						  SListNode removeNode = j;
						  j = (SListNode) j.next();
						  removeNode.remove();
					  }
					  else
					  {
						  j = (SListNode) j.next();
					  }
				  }
			  }
		  }
		  catch (InvalidNodeException e) 
		  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
	  }
	  if(!whiteNetworks.isEmpty())
	  {
		  try 
		  {
			  for(SListNode i = (SListNode) whiteNetworks.front();i.isValidNode();i = (SListNode) i.next())
			  {
				  for(SListNode j = (SListNode) i.next();j.isValidNode();)
				  {
					  if(((Network)j.item()).equals(((Network)i.item())))
					  {
						  SListNode removeNode = j;
						  j = (SListNode) j.next();
						  removeNode.remove();
					  }
					  else
					  {
						  j = (SListNode) j.next();
					  }
				  }
			  }
		  }
		  catch (InvalidNodeException e) 
		  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
	  }
  }
  
  private Chip findLinkedChip(int color, Chip origin,Chip start,SList links,int length,int preLinkWay,SList preChips)
  {
	  Chip end = null;
	  while(start != null)
	  {
		  try
		  {
			  if(!links.isEmpty() && (start.isInMidArea() || length==0))
			  {
				  for(SListNode i = (SListNode) links.front();i.isValidNode();i = (SListNode) i.next())
				  {
					  Link checkLink = (Link) i.item();
					  if(checkLink.getChipA().equals(start) && checkLink.getLinkWay() != preLinkWay)
					  {
						  SListNode checkNode = (SListNode) preChips.front();
						  while(checkNode.isValidNode())
						  {
							  if(((Chip)checkNode.item()).equals(checkLink.getChipB()))
							  {
								  break;
							  }
							  checkNode = (SListNode) checkNode.next();
						  }
						  if(!checkNode.isValidNode())
						  {
							  if(length > 0)
							  {
								  if(color == isblack)
								  {
									  blackNetworks.insertFront(new Network(start,origin,length));
								  }
								  if(color == iswhite)
								  {
									  whiteNetworks.insertFront(new Network(start,origin,length));
								  }
							  }
							  end =  checkLink.getChipB();
							  if(length == 0)
							  { 
								  preChips.insertFront(checkLink.getChipA());
							  }
							  length++; 
							  preChips.insertFront(checkLink.getChipB());
							  findLinkedChip(color,origin,end,links,length,checkLink.getLinkWay(),preChips);
							  length--;
							  preChips.front().remove();
						  }
					  }
					  if(checkLink.getChipB().equals(start) && checkLink.getLinkWay() != preLinkWay)
					  {
						  SListNode checkNode = (SListNode) preChips.front();
						  while(checkNode.isValidNode())
						  {
							  if(((Chip)checkNode.item()).equals(checkLink.getChipA()))
							  {
								  break;
							  }
							  checkNode = (SListNode) checkNode.next();
						  }
						  if(!checkNode.isValidNode())
						  {
							  if(length > 0)
							  {
								  if(color == isblack)
								  {
									  blackNetworks.insertFront(new Network(start,origin,length));
								  }
								  if(color == iswhite)
								  {
									  whiteNetworks.insertFront(new Network(start,origin,length));
								  }
							  }
							  end =  checkLink.getChipA();
							  if(length == 0)
							  {
								  preChips.insertFront(checkLink.getChipB());
							  }
							  length++;
							  preChips.insertFront(checkLink.getChipA());
							  findLinkedChip(color,origin,end,links,length,checkLink.getLinkWay(),preChips);
							  length--;
							  preChips.front().remove();
						  }
					  }
				  }
			  }
			  if(end == null && length > 0)
			  {
				  if(color == isblack)
				  {
					  blackNetworks.insertFront(new Network(start,origin,length));
				  }
				  if(color == iswhite)
				  {
					  whiteNetworks.insertFront(new Network(start,origin,length));
				  }
			  }
		  }
		  catch(InvalidNodeException e)
		  {
			  //System.out.println(ranIdx +" "+ moveList.length());
			  e.printStackTrace();
		  }
		  return end;
	  }
	  return end;
  }
  
  public int getResult()
  {
	  return result;
  }

}
