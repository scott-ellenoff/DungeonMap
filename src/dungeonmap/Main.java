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
		int roomDensity= Integer.parseInt(args[2]);
		int level= Integer.parseInt(args[3]);
		int playerNum= Integer.parseInt(args[4]);
		
		System.out.println(totalXp(level,playerNum));
		System.out.println("/*********************************\\");
		int[][] map= makeMap(xAmount, yAmount);
		printMap(map);
		System.out.println("/*********************************\\");
		map= genFullMap(map, roomDensity);
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
	static int[] prevX= new int[10];
	static int[] prevY= new int[10];
	static int[] prevSize=new int[10];
	public static int[][] genFullMap(int[][] map, int roomDensity){
		//0 = wall 1= free tile
		//rooms are always started in the top right corner
		for(int x=0; x<roomDensity; x++){
			genRoom(map,x);
		}
		
		return map;
	}
	
	public static int[][] genRoom(int[][]map, int number){
		
		int yCorRoom= (int) (Math.random()*(map.length-5))+1;
		int xCorRoom= (int)(Math.random()*(map[yCorRoom].length-5))+1;
		for(int i=0; i<10; i++){
			for(int xSize=0; xSize<prevSize[i]; xSize++){
				if(xCorRoom==prevX[i]+xSize){
					xCorRoom= (int) (xCorRoom+Math.random()*5+prevSize[i]);
				}
				if(yCorRoom==prevY[i]+xSize){
					yCorRoom=(int)(yCorRoom+Math.random()*5+prevSize[i]);
				}
				if(yCorRoom>map.length || xCorRoom>map[0].length){
					return map;
				}
			}
		}
		
		int size= (int)(Math.random()*6)+2;
		
		for(int i= yCorRoom; i<yCorRoom+size; i++){
			for(int j= xCorRoom; j<xCorRoom+size; j++){
				if(i<map.length){
					if(j<map[0].length){
						map[i][j]=1;
					}
				}
			}
		}
		prevX[number]=xCorRoom;
		prevY[number]= yCorRoom;
		prevSize[number]=size;
		return map;
	}
}



