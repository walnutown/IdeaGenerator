package com.taohu.ideagenerator;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.taohu.ideagenerator.model.Combination;
import com.taohu.ideagenerator.utils.AppSettings;
import com.taohu.ideagenerator.utils.FileManager;
import com.taohu.ideagenerator.utils.JSONManager;
import com.taohu.ideagenerator.utils.SwipeDismissListViewTouchListener;
import com.taohu.ideagenerator.views.SavedCombinationView;

public class SavedFragment extends Fragment {

	private ListView listView;
	private SavedCombinationsAdapter mAdapter;
	private final static int HISTORY = 2;
	private FileManager fileManager;
	private List<Combination> list;

	public SavedFragment() {
		String json_str = null;
		try {
			fileManager = FileManager.getInstance();
			json_str = fileManager.read(AppSettings.HISTORY_FILE);
			list = JSONManager.getCombinationsFromJSON(new JSONArray(json_str));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = null;
		rootView = inflater.inflate(R.layout.fragment_saved, container, false);
		listView = (ListView) rootView.findViewById(R.id.listview);
		mAdapter = new SavedCombinationsAdapter(list);
		listView.setAdapter(mAdapter);
		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(listView, new SwipeDismissListViewTouchListener.OnDismissCallback() {
			
			@Override
			public void onDismiss(ListView listView, int[] reverseSortedPositions) {
				 for (int position : reverseSortedPositions) {
                     mAdapter.remove(position);
                 }
                 mAdapter.notifyDataSetChanged();
				
			}
		});
		listView.setOnTouchListener(touchListener);
		listView.setOnScrollListener(touchListener.makeScrollListener());
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(HISTORY);
	}

	@Override
	public void onPause() {
		super.onPause();
		try {
			JSONArray json_combinations = new JSONArray();
			for (Combination combination : list)
				json_combinations.put(JSONManager.getJSON(combination));
			fileManager.write(json_combinations.toString(), AppSettings.HISTORY_FILE);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class SavedCombinationsAdapter extends BaseAdapter {
		List<Combination> combinations;

		public SavedCombinationsAdapter(List<Combination> combinations) {
			this.combinations = combinations;
		}

		@Override
		public int getCount() {
			return combinations.size();
		}

		@Override
		public Object getItem(int position) {
			return combinations.get(position);
		}
		
		public void remove(int position){
			combinations.remove(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			SavedCombinationView scview = (SavedCombinationView) convertView;
			if (scview == null) {
				scview = new SavedCombinationView(getActivity());
			}
			scview.resetView((Combination) getItem(position));
			return scview;
		}

	}

}
