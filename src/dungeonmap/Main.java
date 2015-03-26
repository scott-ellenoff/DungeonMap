package dungeonmap;

import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
public class Main {
	//GABE CAN EDIT
	
	public static void main(String[] args) throws IOException {
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
		createImage(map, xAmount, yAmount);
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
		genCorridors(map, roomDensity);
		
		return map;
	}
	
	public static void writer(int[] rooms, int level){
		PrintWriter writer;
		try {
			writer= new PrintWriter("description.txt", "UTF-8");
		}
		catch (IOException e){
			writer =null;
		}
		for (int i =0; i <= rooms.length; i++) {
		writer.println(roomDescript(evalSize(rooms[i]),level));
		}
		writer.close();
	
	}
	public static String roomDescript(int size, int level){
		String desc= "Encounter: ";
		for (int i =0; i < size; i++) desc += monsterGen(level);
		desc += room.randDescribe();
		return desc;
	}
	
	public int evalSize(int x1, int y1, int x2, int y2){
		int hold = (x2-x1)*(y2-y1);
		if (hold <=12) return 0;
		if (hold <=25) return 1;
		if (hold <=45) return 2;
		if (hold <=75) return 3;
		else return 4;
	}
	
	public static String monsterGen(int level){
		int num= (int)(Math.random()*(level*5));
		String [] list = new String [30];
		list[0] = "Direrat";
		list[1] = "Hobgoblin";
		list[2] = "small troll";
		list[3] = "wolf";
		list[4] = "Swarm of rats";
		list[5] = "Goblin";
		list[6] = "Mutant Boar";
		list[7] = "Zombie";
		list[8] = "Feral owl";
		list[9] = "Leopard";
		list[10] = "Slime";
		list[11] = "Swarm of Bats";
		list[12] = "Basilisk";
		list[13] = "Chimera";
		list[14] = "Demon";
		list[15] = "Cyclops";
		list[16] = "Drow";
		list[17] = "Angry Midget";
		list[18] = "Jerry";
		list[19] = "Ghoul";
		list[20] = "Griffin";
		list[21] = "Halfling";
		list[22] = "Hydra";
		list[23] = "Kobold";
		list[24] = "Dragon";
		list[25] = "Lycanthrope";
		list[26] = "Wizard";
		list[27] = "Minatour";
		list[28] = "Witch";
		list[29] = "Orc";
		list[30] = "OwlBear";
		return list[num];
		
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
		
		int size= (int)(Math.random()*6)+3;
		
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
	
	public static int[][] genCorridors(int[][]map, int rooms){
		
		for(int i=1; i<rooms; i=i+2){
			int curX=0;
			int xDistanceAmount= Math.abs(prevX[i]-prevX[i-1]);
			int yDistanceAmount= Math.abs(prevY[i]-prevY[i-1]);
			if(prevX[i]<prevX[i-1]){
				for(int j=prevX[i]; j<=prevX[i]+xDistanceAmount; j++){
					if(j<map[0].length){
						map[prevY[i]][j]=1;
						curX=j;
					}
				}
			}
			else{
				for(int j=prevX[i]; j>=prevX[i]-xDistanceAmount; j--){
					if(j<map[0].length){
						map[prevY[i]][j]=1;
						curX=j;
					}
				}
			}
			if(prevY[i]<prevY[i-1]){
				for(int k=prevY[i]; k<prevY[i]+yDistanceAmount; k++){
					if(k<map.length){
						map[k][curX]=1;
					}
				}
			}
			else{
				for(int k=prevY[i]; k>prevY[i]-yDistanceAmount; k--){
					if(k<map.length){
						map[k][curX]=1;
					}
				}
			}
		}
		
		return map;
	}
	
	public static void createImage(int map[][], int xTotal, int yTotal) throws IOException{
		
		File file1= new File("data/wall.jpg");
		File file2= new File("data/tile.jpg");
		
		BufferedImage wall= ImageIO.read(file1);
		BufferedImage tile= ImageIO.read(file2);
		
		int widthImg1= wall.getWidth();
		int heightImg1= wall.getHeight();
		BufferedImage mapF= new BufferedImage(
				widthImg1*xTotal,
				widthImg1*yTotal,
				 BufferedImage.TYPE_INT_RGB
				);
		
		for(int y=0; y<yTotal-1; y++){
			for( int x=0; x<xTotal-1; x++){
				boolean imageDrawn;
				if(map[y][x]==0){
					imageDrawn= mapF.createGraphics().drawImage(wall,y*heightImg1, x*widthImg1, null);
				}
				else{
					imageDrawn= mapF.createGraphics().drawImage(tile,y*heightImg1, x*widthImg1, null);
				}
				if(!imageDrawn){
					System.out.println("ERROR ERROR");
				}
			}
		}
		File final_image= new File("Dungeon.jpg");
		try {
			boolean finalImageDrawing= ImageIO.write(mapF, "jpg", final_image);
			if(!finalImageDrawing){
				System.out.println("error: write");
			}
			else{
				System.out.println("sucess");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error: write2");
		}
	}
}



