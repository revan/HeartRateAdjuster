package com.example.heartrateadjuster;
/**
 * The Record class is used to hold the heart rate, time stamp, artist, song, and genre at a specific point in time.
 * @author Nikhil
 */
public class Record {
	// private variables
	private int heartRate;
	private int timeStamp;
	private String Artist;
	private String Genre;
	private String Song;

	public Record() {
	}

	/**
	 * This constructor initializes the data members of the Record object
	 * @param heartRate : This is the heart rate retrieved from the chest strap
	 * @param timeStamp : This is the time stamp retrieved from the Android phone
	 * @param Artist : The Artist name is provided by the Music Module
	 * @param Genre : The Genre is provided by the Music Module
	 * @param Song : The Song is provided by the Music Module
	 */
	public Record(int heartRate, int timeStamp, String Artist, String Genre, String Song){
		this.heartRate = heartRate;
		this.timeStamp = timeStamp;
		this.Artist = Artist;
		this.Genre = Genre;
		this.Song = Song;
	}
	/**
	 * This sets the heart rate of the Record
	 * @param newRate This is the new rate to be set
	 */
	public void setHeartRate(int newRate) {
		this.heartRate = newRate;
	}
	/**
	 * This returns the heart rate of the Record.
	 * @return The heart rate is returned as an integer
	 */
	public int getHeartRate() {
		return this.heartRate;
	}

	/**
	 * This sets the time stamp of the Record
	 * @param newStamp This is the new time stamp to be set
	 */
	public void setTimeStamp(int newStamp){
		this.timeStamp = newStamp;
	}

	/**
	 * This returns the time stamp of the Record.
	 * @return The time stamp is returned as an integer
	 */
	public int getTimeStamp() {
		return this.timeStamp;
	}

	/**
	 * This sets the Artist of the Record
	 * @param newArtist This is the new Artist to be set
	 */
	public void setArtist(String newArtist) {
		this.Artist = newArtist;
	}

	/**
	 * This returns the Artist of the Record
	 * @return The Artist is returned as a String
	 */
	public String getArtist() {
		return this.Artist;
	}

	/**
	 * This sets the Genre of the Record
	 * @param newGenre This is the new genre to be set
	 */
	public void setGenre(String newGenre) {
		this.Genre = newGenre;
	}

	/**
	 * This returns the Genre of the Record
	 * @return The Genre is returned as a string
	 */
	public String getGenre() {
		return this.Genre;
	}

	/**
	 * This sets the Song of the Record
	 * @param newSong This is the new song to be set
	 */
	public void setSong(String newSong) {
		this.Song = newSong;
	}

	/**
	 * This returns the Song of the Record
	 * @return The Song is returned as a string
	 */
	public String getSong() {
		return this.Song;
	}
}
