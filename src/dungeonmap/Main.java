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
		
		System.out.println(totalXp(level,playerNum));
		System.out.println("/*********************************\\");
		int[][] map= makeMap(xAmount, yAmount);
		printMap(map);
		System.out.println("/*********************************\\");
		map= genFullMap(map);
		printMap(map);
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
	public static int[][] makeMap(int x, int y){
		int[][] map = new int[y][x];
		for(int i=0; i<y; i++){
			for(int j=0; j<x; j++){
				map[i][j]=0;
			}
		}
		return map;
	}
	public static void printMap(int[][]map){
		for(int i=0; i<map.length; i++){
			for(int j=0; j<map[i].length; j++){
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}
	
	public static int[][] genFullMap(int[][] map){
		//0 = wall 1= free tile
		//rooms are always started in the top right corner
		int yCorRoom= (int) (Math.random()*(map.length-5))+1;
		int xCorRoom= (int)(Math.random()*(map[yCorRoom].length-5))+1;
		
		for(int i= yCorRoom; i<yCorRoom+4; i++){
			for(int j= xCorRoom; j<xCorRoom+4; j++){
				map[i][j]=1;
			}
		}
		
		return map;
	}
}

