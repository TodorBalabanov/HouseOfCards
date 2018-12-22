package eu.veldsoft.house.of.cards;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * General Utility class.
 * 
 * All methods are synchronized to guarantee that there will be no race
 * conditions when a file is being cached and at the same time being requested,
 * thus being cached twice.
 * 
 * @author Panagiotis Peikidis
 * @version 1.0
 * 
 */
class Utility {

	/**
	 * Saves an Serializable Object to the hard disk.
	 * 
	 * @param obj
	 *            the serializable object to be stored
	 * @param url
	 *            the path to be stored
	 */
	public static void saveObject(Serializable obj, String url) {
		try {

			FileOutputStream fOut = new FileOutputStream(url);
			ObjectOutputStream objOut = new ObjectOutputStream(fOut);
			objOut.writeObject(obj);

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Restores an Object from the path given.
	 * 
	 * @param url
	 *            the path of the object to be restored
	 * @return the object that was restored
	 */
	public static Object loadObject(String url) {
		try {

			FileInputStream fIn = new FileInputStream(url);
			ObjectInputStream objIn = new ObjectInputStream(fIn);
			Object obj = objIn.readObject();

			return obj;

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return null;
	}
}
