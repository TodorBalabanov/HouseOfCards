package eu.veldsoft.house.of.cards;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

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
	 * Caches images so they don't need to be loaded every time
	 */
	private static Map<String, ImageIcon> imgCache = new ConcurrentHashMap<String, ImageIcon>();

	/**
	 * Returns an image depending on the URL.
	 * 
	 * @param url
	 *            the image to fetch
	 * @return the image found
	 */
	public static synchronized ImageIcon getImage(String url) {
		if (imgCache.containsKey(url))
			return imgCache.get(url);

		URL imgURL = Main.class.getResource("/" + url);
		if (imgURL != null) {
			imgCache.put(url, new ImageIcon(imgURL));
			return imgCache.get(url);
		} else {
			System.err.println("Couldn't find file: " + url);
			return null;
		}
	}

	/**
	 * Returns an image of the appropriate card.
	 * 
	 * This is a specialized getImage method for convenience. It is again
	 * synchronized for the same reasons.
	 * 
	 * @param card
	 *            the card that will be used to determine which image to return
	 * @return the card's image
	 */
	public static synchronized ImageIcon getCardImage(Card card) {
		return getImage("" + card.getShortName() + ".png");
	}

	/**
	 * Plays a sound.
	 * 
	 * @param url
	 *            the URL of the sound
	 */
	public static synchronized void playSound(final String url) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(Main.class
									.getResourceAsStream("/res/snd/" + url));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}

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
