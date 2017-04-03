package pl.appsprojekt.photoviewer.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.appsprojekt.photoviewer.Photo;
import pl.appsprojekt.photoviewer.R;
import pl.appsprojekt.photoviewer.adapter.MyPhotosAdapter;
import pl.appsprojekt.photoviewer.event.EventNewRate;

/**
 * author:  redione1
 * date:    03.04.2017
 */

public class PhotoListFragment extends Fragment {

	private static final int MAX_LENGTH = 10;
	private List<Photo> photoList;
	private RecyclerView recyclerView;
	private MyPhotosAdapter adapter;

	public static Fragment newInstance() {
		return new PhotoListFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_photo_list, container, false);
	}

	/**
	 * <p>
	 * W tej metodzie szukamy widoków w layoucie, za pomoca {@link View#findViewById(int)}, Lub używając ButterKnife'a;
	 * </p>
	 * <p>
	 * <p>
	 * Przykład uzycia {@link View#findViewById(int)}:<br>
	 * {@code TextView textView = (TextView) view.findViewById(R.id.my_textview);}
	 * </p>
	 *
	 * @param view               To widok utworzony w metodzie {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
	 * @param savedInstanceState
	 */
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		photoList = new ArrayList<>();

		recyclerView = (RecyclerView) view.findViewById(R.id.photo_recyclerView);
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		adapter = new MyPhotosAdapter(photoList);
		adapter.setOnItemClickListener((position, photo) -> EventBus.getDefault().post(photo));

		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);

		populateList();
		adapter.notifyDataSetChanged();
	}

	private void populateList() {
		Random generator = new Random();

		for (int i = 0; i < 100; i++) {
			String name = "Pieseł " + (i + 1);
			float rating = generator.nextInt(5);
			Photo photo = new Photo(name, rating);
			photoList.add(photo);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		EventBus.getDefault().register(this);
	}

	@Override
	public void onStop() {
		EventBus.getDefault().unregister(this);
		super.onStop();
	}

	@Subscribe(sticky = true)
	public void onNewRate(EventNewRate event) {
		int size = photoList.size();
		for (int i = 0; i < size; i++) {
			Photo photo = photoList.get(i);
			if (photo.getUniqueID().equals(event.getPhotoId())) {
				photo.setRating(event.getNewRating());
				adapter.notifyItemChanged(i);
			}
		}
	}
}
