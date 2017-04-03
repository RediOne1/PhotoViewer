package pl.appsprojekt.photoviewer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import pl.appsprojekt.photoviewer.Photo;
import pl.appsprojekt.photoviewer.R;
import pl.appsprojekt.photoviewer.interfaces.OnItemClickListener;

/**
 * author:  redione1
 * date:    03.04.2017
 */

public class MyPhotosAdapter extends RecyclerView.Adapter<MyPhotosAdapter.ViewHolder> {

	private final List<Photo> photoList;
	private OnItemClickListener<Photo> listener;

	public MyPhotosAdapter(List<Photo> photoList) {
		this.photoList = photoList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		View itemView = inflater.inflate(R.layout.item_photo_list, parent, false);

		ViewHolder holder = new ViewHolder(itemView, this::onItemClick);
		holder.name = (TextView) itemView.findViewById(R.id.item_photo_name);
		holder.rating = (RatingBar) itemView.findViewById(R.id.item_photo_ratingbar);
		return holder;
	}

	private void onItemClick(int position, View itemView) {
		Photo photo = getItem(position);
		if (listener != null)
			listener.onItemClickListener(position, photo);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Photo photo = getItem(position);
		holder.name.setText(photo.getName());
		holder.rating.setRating(photo.getRating());
	}

	private Photo getItem(int position) {
		return photoList.get(position);
	}

	@Override
	public int getItemCount() {
		return photoList.size();
	}

	public void setOnItemClickListener(OnItemClickListener<Photo> listener) {
		this.listener = listener;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView name;
		public RatingBar rating;

		public ViewHolder(View itemView, OnItemClickListener<View> listener) {
			super(itemView);
			itemView.setOnClickListener(view -> listener.onItemClickListener(getAdapterPosition(), view));
		}
	}
}
