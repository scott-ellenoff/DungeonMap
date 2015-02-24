package dungeonmap;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
public class Main {

	public static void main(String[] args) {
		// string args spots, [0] is the x demension of the picture by tiles, [1] is the y demension of the picture
		//[2] is the level of the player, [3] is the number of players
		int xAmount= Integer.parseInt(args[0]);
		int yAmount= Integer.parseInt(args[1]);
		int level= Integer.parseInt(args[2]);
		int playerNum= Integer.parseInt(args[3]);
		
		System.out.println(totalXp(20,4));
	}

	public static BufferedImage readImage(String name){
		BufferedImage bufferedImage;
		try
		{
			bufferedImage= ImageIO.read(new File("/Users/al/some-picture.jpg"));
		}
		catch(IOException e)
		{
			bufferedImage=null;
		}
		return bufferedImage;
	}
	public static int totalXp(int level, int playerNum){
		int totalXp= -1;
		int[] xpByLevel= {400,500,600,700,800,1000,1200,1400,1600,2000,2400,2800,3200,4000,4800,5600, 6400, 8000, 9600, 11200, 12800, 16600, 20400,24200,2800,36000,44000,52000,60000,76000}; 
		totalXp= xpByLevel[level-1]/4;
		totalXp=totalXp*playerNum;
		return totalXp;
	}
}

