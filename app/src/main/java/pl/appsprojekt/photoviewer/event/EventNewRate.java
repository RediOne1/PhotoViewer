package pl.appsprojekt.photoviewer.event;

/**
 * author:  redione1
 * date:    03.04.2017
 */

public class EventNewRate {

	private String photoId;
	private float newRating;

	public EventNewRate(String photoId, float newRating) {
		this.photoId = photoId;
		this.newRating = newRating;
	}

	public String getPhotoId() {
		return photoId;
	}

	public float getNewRating() {
		return newRating;
	}
}
