package dungeonmap;

import java.awt.image.BufferedImage;
import java.io.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
		writer(level);
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
	
	public static void writer(int level){
		String desc = "";
		int randNum= (int)(Math.floor(Math.random()*(6)));
		String [] intro = new String [6];
			intro[0] = "As you run into the large ominous doors they quickly shut behind you. You are locked in. ";
			intro[1] = "As you cautiously enter the dungeon, the large decrepit doors fall behind you (Catch them 18DC Athletics) -alerting anyone within the dungeon of your presence. "; 
			intro[2] = "You walk in and immediately trigger a trip wire an alarm rings and a volley of arrows falls from the ceiling (15DC Athletics, 6 Damage). ";
			intro[3] = "Rats inside the room shriek when they hear you open the door, then they run in all directions from a putrid corpse lying in the center of the floor. ";
			intro[4] = "You descend the stairs into the dungeon, corpses and pieces of corpses hang from hooks that dangle from chains attached to thick iron rings. ";
			intro[5] = "As you enter the dungeon, your eyes jump to the single occupant of the room you stand in: a statue of a male figure with elven features but the broad, muscular body of a hale human. It kneels on the floor as though fallen to that posture. Both its arms reach upward in supplication, and its face is a mask of grief.";
		desc+=intro[randNum];
		randNum= (int)(Math.floor(Math.random()*(6)));
		String [] enviorment = new String [6];
			enviorment[0] = " The room is filled with thick smoke. You see no fire, but that doesn't mean much when you can only see a few feet in front of you anyway. You start coughing -almost suffocated by the lack of air.";
			enviorment[1] = " Speckles of blood paint the floor and walls, it looks fresh. Light enters through cracks on the walls, but there are no windows.";
			enviorment[2] = " There are no windows, the room is pitch black. The air tastes stale, and the entire place reeks of forgotton corpses";
			enviorment[3] = " As you enter further, you notice odd patterns and images covering the floor and walls, you recognize them, but from where?";
			enviorment[4] = " Stinking smoke wafts up from braziers made of skulls set around the edges of this room. The walls bear scratch marks and lines of soot that form crude pictures and what looks like words in some language [Goblin]. ";
			enviorment[5] = " The room stinks with the wet, pungent scent of mildew.";
		desc+=enviorment[randNum];
		randNum= (int)(Math.floor(Math.random()*(5)));
		String [] structure = new String [5];
			structure[0] = " The air is freezing, you start to shiver. Ice covers the floor, and you fear you might slip with every step you take.";
			structure[1] = " The room is boiling hot. Puddles of what look to be some sort of black bubbling liquid are scattered across the floor.";
			structure[2] = " The walls are black, and the floor is made of marble. The cielings are 20 feet high at the lowest.";
			structure[3] = " The walls are decrepit and peeling. The strongest among you might even be able to bash straight through them.";
			structure[4] = " Black mold grows in tangled veins across the walls and parts of the floor. It looks like it might be safe to travel through. A path of stone clean of mold wends its way through the hallway.";
		desc+=structure[randNum];
		randNum= (int)(Math.floor(Math.random()*(5)));
		String [] tenor = new String [5];
			tenor[0] = " The place reminds you of home... but something is amiss.";
			tenor[1] = " The place buzzes with the strange power of an old arcane art, the likes of which your party has never encountered before.";
			tenor[2] = " You immediately have a bad feeling about this place, this is no ordinary cavern.";
			tenor[3] = " The ground shakes and nearly throws you off your feet, there is something very old and powerful lurking within this dungeon, and it senses your prescence.";
			tenor[4] = " The place seems pretty straightforward, not unlike other dungeons you have seen.";
		desc+=tenor[randNum];	
		randNum= (int)(Math.floor(Math.random()*(2)));
		String [] monsters = new String [3];
			monsters[0] = " [For the DM] Mosters lurk throughout the dungeon, rooms are filled with " + monsterGen(level) + " and "+ monsterGen(level);
			monsters[1] = " [For the DM] There is a small gathering of " + monsterGen(level) + " in the next room. Them and groups of " + monsterGen(level) + " lurk throughout.";
		desc+=monsters[randNum];
		randNum= (int)(Math.floor(Math.random()*(5)));
		String [] endGoal = new String [5];
			endGoal[0] = " The group hopes to reach the end of the dungeoon, for it is rumored to contain a hoard of treasure";
			endGoal[1] = " Your party has been paid by the local town to exterminate whatever lies within this dungeon, as they have become a nuissance to the local residence.";
			endGoal[2] = " Your party has been tracking an evil leprachaun for days, all clues point to this dungeon. Hopefully you will find him.. and his pot of gold.";
			endGoal[3] = " There is rumored to be a strong arcane book somewhere in this dungeon, your group believes if they find it they might be able to sell it for great profit";
			endGoal[4] = " On the other side of the dungeon is the entrance to an ancient dwarven civilaztion, your group believes that there they might be able to gain some reward from the dwarves for clearing out the tunnel.";
		desc+=endGoal[randNum];
		try {
			File file = new File("text.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(desc);
			bw.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
		String [] list = new String [35];
		list[0] = "Direrats";
		list[1] = "Hobgoblins";
		list[2] = "small trolls";
		list[3] = "wolfs";
		list[4] = "Swarms of rats";
		list[5] = "Goblins";
		list[6] = "Mutant Boars";
		list[7] = "Zombies";
		list[8] = "Feral owls";
		list[9] = "Leopards";
		list[10] = "Slimes";
		list[11] = "Swarms of Bats";
		list[12] = "Basilisks";
		list[13] = "Chimeras";
		list[14] = "Demons";
		list[15] = "Cyclopses";
		list[16] = "Drows";
		list[17] = "Angry Midgets";
		list[18] = "Jerrys";
		list[19] = "Ghouls";
		list[20] = "Griffins";
		list[21] = "Halflings";
		list[22] = "Hydras";
		list[23] = "Kobolds";
		list[24] = "Dragons";
		list[25] = "Lycanthropes";
		list[26] = "Wizards";
		list[27] = "Minatours";
		list[28] = "Witchs";
		list[29] = "Orcs";
		list[30] = "OwlBears";
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
		
		for(int i=1; i<rooms; i=i+1){
			int curX=0;
			int xDistanceAmount= Math.abs(prevX[i]-prevX[i-1]);
			int yDistanceAmount= Math.abs(prevY[i]-prevY[i-1]);
			if(prevX[i]<=prevX[i-1]){
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
			if(prevY[i]<=prevY[i-1]){
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
				if(map[x][y]==0){
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



