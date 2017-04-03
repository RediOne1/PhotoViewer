package pl.appsprojekt.photoviewer.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import pl.appsprojekt.photoviewer.Photo;
import pl.appsprojekt.photoviewer.R;
import pl.appsprojekt.photoviewer.event.EventNewRate;

/**
 * author:  redione1
 * date:    03.04.2017
 */

public class PhotoFragment extends Fragment implements RatingBar.OnRatingBarChangeListener {

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String RATING = "rating";

	private String photoId;

	public static Fragment newInstance(Photo photo) {
		Bundle arg = new Bundle();
		arg.putString(ID, photo.getUniqueID());
		arg.putString(NAME, photo.getName());
		arg.putFloat(RATING, photo.getRating());

		Fragment fragment = new PhotoFragment();
		fragment.setArguments(arg);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_photo, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		Bundle bundle = getArguments();

		photoId = bundle.getString(ID);
		String name = bundle.getString(NAME, "Pieseł XXX");
		float rating = bundle.getFloat(RATING, 0f);

		TextView nameTextView = (TextView) view.findViewById(R.id.photo_name);
		ImageView imageView = (ImageView) view.findViewById(R.id.photo_image);
		RatingBar ratingBar = (RatingBar) view.findViewById(R.id.photo_rating);
		ratingBar.setOnRatingBarChangeListener(this);

		nameTextView.setText(name);
		ratingBar.setRating(rating);
		Picasso.with(getContext())
				.load("https://i.ytimg.com/vi/bVzpweTJvhc/hqdefault.jpg")
				.into(imageView);
	}

	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
		if (fromUser) {
			EventNewRate event = new EventNewRate(photoId, rating);
			EventBus.getDefault().postSticky(event);
		}
	}
}
