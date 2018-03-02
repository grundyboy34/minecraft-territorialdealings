package me.grundyboy34.deeds;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.grundyboy34.deeds.savedata.SaveData;

public class SaveHandler 
{
	public static void saveFactionsToFile(String configDir, String worldDir, SaveData save)
	{
		// Step 1, ensure that the folder exists
		String folder = configDir + "/territorialdealings";
		File folderFile = new File(folder);
		
		folderFile.mkdirs();	// Making sure that exists
		
		// Step 2, save the faction config under the world name
		String path = folder + "/" + worldDir + ".gen";
		File configFile = new File(path);
		
		try
		{
			FileOutputStream saveFile = new FileOutputStream(configFile);			// Saving to the current profile
			ObjectOutputStream saveHandle = new ObjectOutputStream(saveFile);		// Creating the file handle

			saveHandle.writeObject(save);	// Saving the whole shebang

			saveHandle.close();	// Done saving all factions
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
		finally 
		{ 
			//Deeds.console("Saved faction data for world '" + worldDir + "' to disk."); 
		}
	}

	
	public static SaveData loadFactionsFromFile(String configDir, String worldDir) 
	{
		SaveData save = null;

		try
		{
			String path = configDir + "/territorialdealings/" + worldDir + ".gen";
			File file = new File(path);

			if(file.isFile())	// Exists
			{
				FileInputStream loadFile = new FileInputStream(file); 
				ObjectInputStream loadHandle = new ObjectInputStream(loadFile);

				Object loadedObj = loadHandle.readObject();

				if (loadedObj instanceof SaveData)	
				{
					save = (SaveData) loadedObj;	// Here ya go
				}

				loadHandle.close();
				loadFile.close();
			}
			else
			{
				// Doesn't exist. Now what? Should be fine to load no factions on first start
			}
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
		catch (EOFException e) {  }
		catch (IOException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		finally
		{
			//Deeds.console("Loaded factions data for world '" + worldDir + "' from disk.");
		}
		
		return save;
	}
}
