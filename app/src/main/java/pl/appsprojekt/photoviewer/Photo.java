package pl.appsprojekt.photoviewer;

import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * author:  redione1
 * date:    03.04.2017
 */

public class Photo {

	private final String uniqueID;
	private String name;
	private float rating;

	public Photo(@NonNull String name, @FloatRange(from = 0f) float rating) {
		uniqueID = UUID.randomUUID().toString();
		this.name = name;
		this.rating = rating;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	@NonNull
	public String getName() {
		return name;
	}

	public void setName(@NonNull String name) {
		this.name = name;
	}

	@FloatRange(from = 0f)
	public float getRating() {
		return rating;
	}

	public void setRating(@FloatRange(from = 0f) float rating) {
		this.rating = rating;
	}
}
