package me.grundyboy34.deeds.savedata;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.grundyboy34.deeds.Reference;
import me.grundyboy34.deeds.config.Config;

public class DeedSaveHandler {
	private SaveData currentSave = null;
	private static DeedSaveHandler INSTANCE = null;

	private DeedSaveHandler() {
	}

	public static DeedSaveHandler instance() {
		if (INSTANCE == null) {
			synchronized (DeedSaveHandler.class) {
				if (INSTANCE == null) {
					INSTANCE = new DeedSaveHandler();
				}
			}
		}
		return INSTANCE;
	}
	
	public SaveData getCurrentSave() {
		return currentSave;
	}

	public void saveDeeds() {
		if (currentSave == null) {
			return;
		}

		try {

			File saveFile = new File(Config.configFolder, Reference.WORLD_DIR + ".dat");
			FileOutputStream saveFileStream = new FileOutputStream(saveFile); // Saving to the current profile
			ObjectOutputStream saveHandle = new ObjectOutputStream(saveFileStream); // Creating the file handle

			saveHandle.writeObject(currentSave); // Saving

			saveHandle.close();
			saveFileStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}

	public void loadDeeds() {
		try {
			File loadFile = new File(Config.configFolder, Reference.WORLD_DIR + ".dat");

			if (loadFile.isFile()) // Exists
			{
				FileInputStream loadFileStream = new FileInputStream(loadFile);
				ObjectInputStream loadHandle = new ObjectInputStream(loadFileStream);

				Object loadedObj = loadHandle.readObject();

				if (loadedObj instanceof SaveData) {
					currentSave = (SaveData) loadedObj; // Here ya go
				}

				loadHandle.close();
				loadFileStream.close();
			} else {
				currentSave = new SaveData();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
		}
	}
}
