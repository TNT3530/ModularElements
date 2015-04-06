package tnt3530.mod.ModularElements.Networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import tnt3530.mod.ModularElements.Common.Constants;
import tnt3530.mod.ModularElements.Items.CoreElement;
import tnt3530.mod.ModularElements.TileEntity.TileEntityAtomWorkbench;

public class ElementStorageManager
{
	public static final int maxStoredElements = ConfigurationHandler.maxElements;
	public static String[] loadedElementsAndInfo = null;
	public static int elementId = -1;
	private static boolean reasons = false;
	public static int nextStore = 0;
	public static Item[] elements = new Item[maxStoredElements];
	public static Item[] swords = new Item[maxStoredElements];
	public static Item[] picks = new Item[maxStoredElements];
	public static Item[] spades = new Item[maxStoredElements];
	public static Item[] hoes = new Item[maxStoredElements];
	public static Item[] axes = new Item[maxStoredElements];
	public static Item[] ingots = new Item[maxStoredElements];
	public static Block[] blocks = new Block[maxStoredElements];
	
	/**
	 * 	@param pro
	 *  Amount of protons in the element being named
	 *  
	 *  @param neu
	 *  Amount of neutrons in the element being named
	 *  
	 *  @return
	 *  Returns the full name of the element, including the element_ part
	 */
	public static String getFullName(int pro, int neu)
	{
		return "element_" + getElementName(pro) + "ium " + "§§(§§" + (pro + neu) + "§§)";
	}
	
	/**
	 * 	@param pro
	 *  Amount of protons in the element being named
	 *  
	 *  @param neu
	 *  Amount of neutrons in the element being named
	 *  
	 *  @return
	 *  Returns the displayable name, the one without the element_ part
	 */
	public static String getDisplayName(int pro, int neu)
	{
		return getElementName(pro) + "ium " + "§§(§§" + (pro + neu) + "§§)";
	}
	
	/**
	 * 	@param name
	 *  Amount of protons in the element being named
	 *  
	 *  @return
	 *  True if the element wont exceed the max amount of elements and the id is free
	 */
	public static boolean canCreateElement(String name)
	{
		if(loadedElementsAndInfo.length > 0 && !reasons)
		{
			elementId = elementId + loadedElementsAndInfo.length;
			reasons = true;
		}
		//System.out.println(name);
		//System.out.println(elementId);
		//System.out.println(ConfigurationHandler.maxElements);
		if(elementId < ConfigurationHandler.maxElements)
		{
			return true;
		}
		else return false;
	}

	/**
	 * 	@param name
	 *  Name of the element (This is just for debug purposes)
	 *  
	 *  @param var1
	 *  Amount of protons in the element being stored
	 *  
	 *  @param var2
	 *  Amount of neutrons in the element being stored
	 *  
	 *  @param var3
	 *  Amount of electrons in the element being stored
	 */
	public static void storeElement(String name, int var1, int var2, int var3)
	{
		elementId++;
		System.out.println("Element " + name + " has been saved. (We Hope)");
		System.out.println("Element " + name + " has been registered as element #" + elementId);
		System.out.println(ConfigurationHandler.maxElements - elementId + " element(s) left to create.");
		try {

			File file = new File("CreatedElements.txt");

			if (!file.exists()) {
				file.createNewFile();
				System.out.println("Oh dear, it appears you killed Modular Elements. That wasn't very nice.");
				System.out.println("Next time, don't delete CreatedElements.txt please, for your own safety. :)");
			}
			
			//State (Solid[1], Liquid[2], Gas[3])
			//this.elementInformation[4] = 1;
			//Stability/Radioactivity (High[1] - Low[100])
			//this.elementInformation[5] = 80;
			//Valence E- (1-8)
			//this.elementInformation[6] = 1;
			//Charge for Ions
			//this.elementInformation[7] = 0;
			//Weight (Light[1] - Heavy[10])
			//this.elementInformation[8] = 3;
			//Hardness (Soft[1] - Hard[10]
			//this.elementInformation[9] = 1;
			//Brittleness (Mallable[1] - Shatter[10]
			//this.elementInformation[10] = 1;
			//Conductivity (Not[0] - Very[10])
			//this.elementInformation[11] = 1;
			//Flamibility (Water[0] - Explosive[100])
			//this.elementInformation[12] = 65;
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("/" + elementId + ":" + var1 + ":" + var2 + ":" + var3);		
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * @param name
	 * The name of the element
	 * 
	 * @return
	 * Returns an Int array of the amount of protons for the element and the mass
	 */
	public static int[] getProtonsFromName(String name)
	{
		String[] x = name.split("§§");
		int o = 0, t = 0, h = 0, th = 0;
		for(int i = 0; i < x.length; i++)
		{
			x[i] = x[i].toLowerCase();
			if(x[i].contains("ium")) x[i] = "";
			if(x[i].contains("(")) x[i] = "";
			if(x[i].contains(")")) x[i] = "";
		}
		if(x.length == 6)
		{
			if(x[1].contains("nil")) o = 0;
			if(x[1].contains("un")) o = 1;
			if(x[1].contains("b")) o = 2;
			if(x[1].contains("tr")) o = 3;
			if(x[1].contains("quad")) o = 4;
			if(x[1].contains("pent")) o = 5;
			if(x[1].contains("hex")) o = 6;
			if(x[1].contains("sept")) o = 7;
			if(x[1].contains("oct")) o = 8;
			if(x[1].contains("enn")) o = 9;
		}
		if(x.length == 7)
		{
			if(x[1].contains("nil")) t = 0;
			if(x[1].contains("un")) t = 10;
			if(x[1].contains("b")) t = 20;
			if(x[1].contains("tr")) t = 30;
			if(x[1].contains("quad")) t = 40;
			if(x[1].contains("pent")) t = 50;
			if(x[1].contains("hex")) t = 60;
			if(x[1].contains("sept")) t = 70;
			if(x[1].contains("oct")) t = 80;
			if(x[1].contains("enn")) t = 90;
		
			if(x[2].contains("nil")) o = 0;
			if(x[2].contains("un")) o = 1;
			if(x[2].contains("b")) o = 2;
			if(x[2].contains("tr")) o = 3;
			if(x[2].contains("quad")) o = 4;
			if(x[2].contains("pent")) o = 5;
			if(x[2].contains("hex")) o = 6;
			if(x[2].contains("sept")) o = 7;
			if(x[2].contains("oct")) o = 8;
			if(x[2].contains("enn")) o = 9;
		}	
		
		if(x.length == 8)
		{
			if(x[1].contains("nil")) h = 0;
			if(x[1].contains("un")) h = 100;
			if(x[1].contains("b")) h = 200;
			if(x[1].contains("tr")) h = 300;
			if(x[1].contains("quad")) h = 400;
			if(x[1].contains("pent")) h = 500;
			if(x[1].contains("hex")) h = 600;
			if(x[1].contains("sept")) h = 700;
			if(x[1].contains("oct")) h = 800;
			if(x[1].contains("enn")) h = 900;
			
			if(x[2].contains("nil")) t = 00;
			if(x[2].contains("un")) t = 10;
			if(x[2].contains("b")) t = 20;
			if(x[2].contains("tr")) t = 30;
			if(x[2].contains("quad")) t = 40;
			if(x[2].contains("pent")) t = 50;
			if(x[2].contains("hex")) t = 60;
			if(x[2].contains("sept")) t = 70;
			if(x[2].contains("oct")) t = 80;
			if(x[2].contains("enn")) t = 90;
			
			if(x[3].contains("nil")) o = 0;
			if(x[3].contains("un")) o = 1;
			if(x[3].contains("b")) o = 2;
			if(x[3].contains("tr")) o = 3;
			if(x[3].contains("quad")) o = 4;
			if(x[3].contains("pent")) o = 5;
			if(x[3].contains("hex")) o = 6;
			if(x[3].contains("sept")) o = 7;
			if(x[3].contains("oct")) o = 8;
			if(x[3].contains("enn")) o = 9;
		}	
		
		if(x.length == 9)
		{
			if(x[1].contains("nil")) th = 0;
			if(x[1].contains("un")) th = 1000;
			if(x[1].contains("b")) th = 2000;
			if(x[1].contains("tr")) th = 3000;
			if(x[1].contains("quad")) th = 4000;
			if(x[1].contains("pent")) th = 5000;
			if(x[1].contains("hex")) th = 6000;
			if(x[1].contains("sept")) th = 7000;
			if(x[1].contains("oct")) th = 8000;
			if(x[1].contains("enn")) th = 9000;
			
			if(x[2].contains("nil")) h = 000;
			if(x[2].contains("un")) h = 100;
			if(x[2].contains("b")) h = 200;
			if(x[2].contains("tr")) h = 300;
			if(x[2].contains("quad")) h = 400;
			if(x[2].contains("pent")) h = 500;
			if(x[2].contains("hex")) h = 600;
			if(x[2].contains("sept")) h = 700;
			if(x[2].contains("hct")) h = 800;
			if(x[2].contains("enn")) h = 900;
			
			if(x[3].contains("nil")) t = 00;
			if(x[3].contains("un")) t = 10;
			if(x[3].contains("b")) t = 20;
			if(x[3].contains("tr")) t = 30;
			if(x[3].contains("quad")) t = 40;
			if(x[3].contains("pent")) t = 50;
			if(x[3].contains("hex")) t = 60;
			if(x[3].contains("sept")) t = 70;
			if(x[3].contains("tct")) t = 80;
			if(x[3].contains("enn")) t = 90;
			
			if(x[4].contains("nil")) o = 0;
			if(x[4].contains("un")) o = 1;
			if(x[4].contains("b")) o = 2;
			if(x[4].contains("tr")) o = 3;
			if(x[4].contains("quad")) o = 4;
			if(x[4].contains("pent")) o = 5;
			if(x[4].contains("hex")) o = 6;
			if(x[4].contains("sept")) o = 7;
			if(x[4].contains("oct")) o = 8;
			if(x[4].contains("enn")) o = 9;
		}	
		int a = o + t + h + th;
		int b = 0;
		if(x.length > 2)
		{
			b = Integer.parseInt(x[x.length - 2]);
		}
		//Protons, Mass
		int[] ar = {a, b};
		return ar;
	}
	
	/**
	 * @param ton
	 * Amount of protons
	 * 
	 * @param mass
	 * Mass of the element
	 * 
	 * @return
	 * Returns the element ID
	 */
	public static int getIdFromProtonsAndMass(int ton, int mass)
	{
		int ret = 0;
		for(int x = 0; x < ElementStorageManager.loadedElementsAndInfo.length; x++)
		{
			String[] data = ElementStorageManager.getElementAndInfo(x);
			
			int pro = Integer.parseInt(data[1]);
			int mass1 = Integer.parseInt(data[1]) + Integer.parseInt(data[2]);
			if(pro == ton && mass1 == mass) ret = Integer.parseInt(data[0]);
		}	
		return ret;
	}
	
	public static void readElements()
	{
		String[] ret = null;
		try{
			// Open file to read from, named SavedObj.sav.
			FileInputStream saveFile = new FileInputStream("CreatedElements.txt");

			// Create an ObjectInputStream to get objects from save file.
			//ObjectInputStream save = new ObjectInputStream(saveFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(saveFile));
			String line = null;

			while((line = reader.readLine()) != null) 
			{
				System.out.println(line);
				String[] data = line.split("/");
				ret = data;
			}
			// Close the file.
			reader.close(); // This also closes saveFile.
		}
		catch(Exception exc){
			exc.printStackTrace(); // If there was an error, print the info.
			System.out.println("No CreatedElements.txt found!");
			
			try
			{
				PrintWriter writer = new PrintWriter("CreatedElements.txt");
				writer.println("0:0:0:0");
				writer.close();
				System.out.println("Created a new CreatedElements.txt for you. You're welcome.");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("Elements read from file");
		loadedElementsAndInfo = ret;
	}
	
	/**
	 * @param id
	 * ID of the element to get information about
	 * 
	 * @return
	 * Returns a STRING array of the Protons, Neutrons and Electrons retrieved from CreatedElements.txt
	 */
	public static String[] getElementAndInfo(int id)
	{
		String ret = loadedElementsAndInfo[id];
		String[] data = ret.split(":");
		return data;
	}
	

	private static String checkFirstOnes(int x)
	{
		if(x <= 0) return "§§Nil§§";
		if(x >= 1 && x < 2) return "§§Un§§";
		if(x >= 2 && x < 3) return "§§B§§";
		if(x >= 3 && x < 4) return "§§Tr§§";
		if(x >= 4 && x < 5) return "§§Quad§§";
		if(x >= 5 && x < 6) return "§§Pent§§";
		if(x >= 6 && x < 7) return "§§Hex§§";
		if(x >= 7 && x < 8) return "§§Sept§§";
		if(x >= 8 && x < 9) return "§§Oct§§";
		if(x >= 9 && x < 10) return "§§Enn§§";
		else return "";
	}

	private static String checkAfterOnes(int x)
	{
		if(x < 0) return "Broken";
		if(x >= 0 && x < 1) return "nil§§";
		if(x >= 1 && x < 2) return "un§§";
		if(x >= 2 && x < 3) return "b§§";
		if(x >= 3 && x < 4) return "tr§§";
		if(x >= 4 && x < 5) return "quad§§";
		if(x >= 5 && x < 6) return "pent§§";
		if(x >= 6 && x < 7) return "hex§§";
		if(x >= 7 && x < 8) return "sept§§";
		if(x >= 8 && x < 9) return "oct§§";
		if(x >= 9 && x < 10) return "enn§§";
		else return "";
	}

	private static String checkAfterOthers(int x)
	{
		if(x < 0) return "Broken";
		if(x >= 0 && x < 10) return "nil§§";
		if(x >= 10 && x < 20) return "un§§";
		if(x >= 20 && x < 30) return "bi§§";
		if(x >= 30 && x < 40) return "tri§§";
		if(x >= 40 && x < 50) return "quad§§";
		if(x >= 50 && x < 60) return "pent§§";
		if(x >= 60 && x < 70) return "hex§§";
		if(x >= 70 && x < 80) return "sept§§";
		if(x >= 80 && x < 90) return "oct§§";
		if(x >= 90 && x < 100) return "enn§§";
		else return "";
	}

	private static String checkFirstOthers(int x)
	{
		if(x < 10) return "Broken";
		if(x >= 10 && x < 20) return "§§Un§§";
		if(x >= 20 && x < 30) return "§§Bi§§";
		if(x >= 30 && x < 40) return "§§Tri§§";
		if(x >= 40 && x < 50) return "§§Quad§§";
		if(x >= 50 && x < 60) return "§§Pent§§";
		if(x >= 60 && x < 70) return "§§Hex§§";
		if(x >= 70 && x < 80) return "§§Sept§§";
		if(x >= 80 && x < 90) return "§§Oct§§";
		if(x >= 90 && x < 100) return "§§Enn§§";
		else return "";
	}

	private static String checkSymbolLetter(int x)
	{
		if(x < 0) return "Broken";
		if(x >= 0 && x < 1) return "N";
		if(x >= 1 && x < 2) return "U";
		if(x >= 2 && x < 3) return "B";
		if(x >= 3 && x < 4) return "T";
		if(x >= 4 && x < 5) return "Q";
		if(x >= 5 && x < 6) return "P";
		if(x >= 6 && x < 7) return "H";
		if(x >= 7 && x < 8) return "S";
		if(x >= 8 && x < 9) return "O";
		if(x >= 9 && x < 10) return "E";
		else return "";
	}

	/**
	 * @param x
	 * Amount of protons of the element to name
	 * 
	 * @return
	 * Returns name of the element _-_-_ium
	 */
	public static String getElementName(int x)
	{		
		int trimThou = (x / 1000) * 1000;
		int trimHund = ((x - trimThou) / 100) * 100;
		int trimTens = ((x - trimThou - trimHund) / 10) * 10;		

		String ones = checkFirstOnes(x);
		String tens = checkFirstOthers(trimTens) + checkAfterOnes(x - trimTens);
		String hunds = checkFirstOthers(trimHund / 10) + checkAfterOthers(x - trimHund) + checkAfterOnes((x - trimHund - trimTens));
		String thous = checkFirstOthers(trimThou / 100) + checkAfterOthers((x - trimThou) / 10) + checkAfterOthers((x - trimThou - trimHund)) + checkAfterOnes((x - trimThou - trimHund - trimTens));

		if(x >= 0 && x < 10) return ones;
		if(x >= 10 && x < 100) return tens;
		if(x >= 100 && x < 1000) return hunds;
		if(x >= 1000 && x < 10000) return thous;

		else return "";
	}

	/**
	 * @param x
	 * Amount of protons of the element to get the symbol of
	 * 
	 * @return
	 * Returns 1-3 Letter symbol of the element
	 */
	public static String getElementSymbol(int x)
	{		
		int trimThou = (x / 1000) * 1000;
		int trimHund = ((x - trimThou) / 100) * 100;
		int trimTens = ((x - trimThou - trimHund) / 10) * 10;		

		String ones = checkSymbolLetter(x);
		String tens = checkSymbolLetter(trimTens / 10) + checkSymbolLetter(x - trimTens);
		String hunds = checkSymbolLetter(trimHund / 100) + checkSymbolLetter((x - trimHund) / 10) + checkSymbolLetter((x - trimHund - trimTens));

		if(x >= 1 && x < 10) return ones;
		if(x >= 10 && x < 100) return tens;
		if(x >= 100 && x < 1000) return hunds;
		if(x >= 1000 && x < 10000) return checkSymbolLetter(x / 1000) + checkSymbolLetter((x - trimThou) / 100) + checkSymbolLetter((x - trimThou - trimHund) / 10) + checkSymbolLetter((x - trimThou - trimHund - trimTens));

		else return "";
	}
	
	public static int getElementGroup(int x)
	{
		int tempProtonAmount = 0;

		for(int i = 1; i < (ConfigurationHandler.maxElements/118); i += 1)
		{
			if (x > (118 * i) && x < (118 * (1 + i)))
			{
				tempProtonAmount = (x - (118 * (int) i));
			}
			else tempProtonAmount = x;
		}

		//Alkali Metals
		if (tempProtonAmount == 3 || tempProtonAmount == 11 || tempProtonAmount == 19
				|| tempProtonAmount == 37 || tempProtonAmount == 55 || tempProtonAmount == 87)
		{
			return 1;
		}

		//Alkaline Metals
		if (tempProtonAmount == 4 || tempProtonAmount == 12 || tempProtonAmount == 20 || tempProtonAmount == 38
				|| tempProtonAmount == 56 || tempProtonAmount == 88)
		{
			return 2;
		}

		//F orbitals are broke, too lazy
		//Transition Metals
		if (tempProtonAmount == 21 || tempProtonAmount == 39)
		{
			return 3;
		}

		if (tempProtonAmount == 22 || tempProtonAmount == 40 || tempProtonAmount == 72 || tempProtonAmount == 104)
		{
			return 4;
		}

		if (tempProtonAmount == 23 || tempProtonAmount == 41 || tempProtonAmount == 73 || tempProtonAmount == 105)
		{
			return 5;
		}

		if (tempProtonAmount == 24 || tempProtonAmount == 42 || tempProtonAmount == 74 || tempProtonAmount == 106)
		{
			return 6;
		}

		if (tempProtonAmount == 25 || tempProtonAmount == 43 || tempProtonAmount == 75 || tempProtonAmount == 107)
		{
			return 7;
		}

		if (tempProtonAmount == 26 || tempProtonAmount == 44 || tempProtonAmount == 76 || tempProtonAmount == 108)
		{
			return 8;
		}

		if (tempProtonAmount == 27 || tempProtonAmount == 45 || tempProtonAmount == 77 || tempProtonAmount == 109)
		{
			return 9;
		}

		if (tempProtonAmount == 28 || tempProtonAmount == 46 || tempProtonAmount == 78 || tempProtonAmount == 110)
		{
			return 10;
		}

		if (tempProtonAmount == 29 || tempProtonAmount == 47 || tempProtonAmount == 79 || tempProtonAmount == 111)
		{
			return 11;
		}

		//Poor Metals (Wut) and Nonmetals
		if (tempProtonAmount == 30 || tempProtonAmount == 48 || tempProtonAmount == 80 || tempProtonAmount == 112)
		{
			return 12;
		}

		if (tempProtonAmount == 5 || tempProtonAmount == 14 || tempProtonAmount == 31 || tempProtonAmount == 49
				|| tempProtonAmount == 81 || tempProtonAmount == 113)
		{
			return 13;
		}

		if (tempProtonAmount == 6 || tempProtonAmount == 15 || tempProtonAmount == 32 || tempProtonAmount == 50
				|| tempProtonAmount == 82 || tempProtonAmount == 114)
		{
			return 14;
		}

		if (tempProtonAmount == 7 || tempProtonAmount == 16 || tempProtonAmount == 33 || tempProtonAmount == 51
				|| tempProtonAmount == 83 || tempProtonAmount == 115)
		{
			return 15;
		}

		if (tempProtonAmount == 8 || tempProtonAmount == 17 || tempProtonAmount == 34 || tempProtonAmount == 52
				|| tempProtonAmount == 84 || tempProtonAmount == 116)
		{
			return 16;
		}

		if (tempProtonAmount == 9 || tempProtonAmount == 18 || tempProtonAmount == 35 || tempProtonAmount == 53
				|| tempProtonAmount == 85 || tempProtonAmount == 117)
		{
			return 17;
		}

		//Noble gasses
		if (tempProtonAmount == 2 || tempProtonAmount == 10 || tempProtonAmount == 18 || tempProtonAmount == 36
				|| tempProtonAmount == 54 || tempProtonAmount == 86 || tempProtonAmount == 118)
		{
			return 18;
		}

		//Lanthanoids
		if(tempProtonAmount <= 71 || tempProtonAmount >= 57) return 3;

		//Actinoids
		if(tempProtonAmount <= 103 || tempProtonAmount >= 89) return 3;

		else return 0;
	}
	
	/**
	 * @param group
	 * Group number of the element, retrieved from {@link getElementGroup}
	 * @param p
	 * Protons
	 * @param n
	 * Neutrons
	 * @param e
	 * Electrons
	 * @return
	 * Returns a int array of the properties
	 */
	public static int[] getElementProperties(int group, int p, int n, int e)
	{
		////////////0, 1, 2, 3, 4, 5, 6,    7,   8, 9, 10, 11, 12
		int[] g1 = {0, p, n, e, 1, 80, 1, p - e, 3, 1, 1, 1, 65};
		if(group == 1) return g1;
		int[] g2 = {0, p, n, e, 1, 85, 2, p - e, 4, 2, 2, 2, 55};
		if(group == 2) return g2;
		int[] g3 = {0, p, n, e, 1, 95, 3, p - e, 6, 5, 4, 4, 20};
		if(group == 3) return g3;
		int[] g4 = {0, p, n, e, 1, 95, 4, p - e, 6, 5, 4, 4, 20};
		if(group == 4) return g4;
		int[] g5 = {0, p, n, e, 1, 95, -3, p - e, 6, 5, 4, 4, 20};
		if(group == 5) return g5;
		int[] g6 = {0, p, n, e, 1, 95, -2, p - e, 6, 5, 4, 4, 20};
		if(group == 6) return g6;
		int[] g7 = {0, p, n, e, 1, 95, -1, p - e, 6, 5, 4, 4, 20};
		if(group == 7) return g7;
		int[] g8 = {0, p, n, e, 1, 100, 8, p - e, 8, 8, 5, 7, 0};
		if(group == 8|| group == 9 || group == 10) return g8;
		int[] g11 = {0, p, n, e, 1, 100, 1, p - e, 10, 10, 2, 10, 0};
		if(group == 11) return g11;
		int[] g12 = {0, p, n, e, 1, 75, 2, p - e, 7, 5, 4 ,3, 30};
		if(group == 12) return g12;
		int[] g13 = {0, p, n, e, 1, 70, 3, p - e, 6, 7, 6, 4, 20};
		if(group == 13) return g13;
		int[] g14 = {0, p, n, e, 1, 80, 4, p - e, 4, 3, 9, 0, 10};
		if(group == 14) return g14;
		int[] g15 = {0, p, n, e, 1, 80, -3, p - e, 3, 3, 1, 0, 30};
		if(group == 15) return g15;
		int[] g16 = {0, p, n, e, 1, 80, -2, p - e, 1, 1, 1, 0, 40};
		if(group == 16) return g16;
		int[] g17 = {0, p, n, e, 3, 90, -1, p - e, 2, 1, 1, 0, 90};
		if(group == 17) return g17;
		int[] g18 = {0, p, n, e, 3, 100, 8, p - e, 0, 0, 0, 1, 1};
		if(group == 18) return g18;
		else return null;
	}
}


