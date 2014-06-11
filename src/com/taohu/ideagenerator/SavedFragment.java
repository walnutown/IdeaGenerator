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
import com.taohu.ideagenerator.views.SavedCombinationView;

public class SavedFragment extends Fragment {

	private ListView savedCombinations;
	private final static int HISTORY = 2;
	private FileManager fileManager;

	public SavedFragment() {
		fileManager = FileManager.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = null;
		try {
			rootView = inflater.inflate(R.layout.fragment_saved, container, false);
			savedCombinations = (ListView) rootView.findViewById(R.id.listview);
			String json_str = fileManager.read(AppSettings.HISTORY_FILE);
			List<Combination> combinations = JSONManager.getCombinationsFromJSON(new JSONArray(json_str));
			savedCombinations.setAdapter(new SavedCombinationsAdapter(combinations));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(HISTORY);
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

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			SavedCombinationView view = (SavedCombinationView) convertView;
			if (view == null)
				view = new SavedCombinationView(getActivity());
			view.resetView((Combination) getItem(position));
			return view;
		}

	}

}
