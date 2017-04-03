package pl.appsprojekt.photoviewer.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import pl.appsprojekt.photoviewer.Photo;
import pl.appsprojekt.photoviewer.R;
import pl.appsprojekt.photoviewer.view.fragment.PhotoFragment;
import pl.appsprojekt.photoviewer.view.fragment.PhotoListFragment;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.container, PhotoListFragment.newInstance())
				.commit();

	}

	@Override
	protected void onStart() {
		super.onStart();
		EventBus.getDefault().register(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EventBus.getDefault().unregister(this);
	}

	@Subscribe
	public void onPhotoItemClick(Photo photo) {
		getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.container, PhotoFragment.newInstance(photo), "PHOTO")
				.commit();
	}

	@Override
	public void onBackPressed() {
		Fragment fragment = getSupportFragmentManager()
				.findFragmentByTag("PHOTO");
		if (fragment != null)
			getSupportFragmentManager()
					.beginTransaction()
					.remove(fragment)
					.commit();
		else
			super.onBackPressed();

	}
}
