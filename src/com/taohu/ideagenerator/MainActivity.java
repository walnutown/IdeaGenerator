package com.taohu.ideagenerator;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.taohu.ideagenerator.model.Combination;
import com.taohu.ideagenerator.model.Item;
import com.taohu.ideagenerator.utils.AppSettings;
import com.taohu.ideagenerator.utils.FileManager;
import com.taohu.ideagenerator.utils.JSONManager;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private Fragment currentFragment;
	private FileManager fileManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(
				R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

		fileManager = FileManager.createInstance(getApplicationContext());
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		switch (position) {
		case 0:
			currentFragment = new MainFragment();
			break;
		case 1:
			currentFragment = new SavedFragment();
		}
		fragmentManager.beginTransaction().replace(R.id.container, currentFragment).commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			if (currentFragment instanceof MainFragment){
				getMenuInflater().inflate(R.menu.main, menu);
				MenuItem shareItem = menu.findItem(R.id.action_share);
				ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
				mShareActionProvider.setShareIntent(getDefaultIntent());
			}else
				getMenuInflater().inflate(R.menu.saved, menu);
			restoreActionBar();	
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	private Intent getDefaultIntent() {
	    Intent intent = new Intent(Intent.ACTION_SEND);
	    intent.setType("image/*");
	    return intent;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		try {
			int id = item.getItemId();
			if (id == R.id.action_save) {
				saveCombination();
				Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return super.onOptionsItemSelected(item);
	}

	private void saveCombination() throws JSONException, IOException {
		Time currTime = new Time();
		currTime.setToNow();
		Combination combination = new Combination(currTime.format3339(true));
		List<Item> items = ((MainFragment) currentFragment).getCurrentItems();
		for (Item item : items)
			combination.addItem(item);
		JSONArray json_combinations = null;
		if (!fileManager.has(AppSettings.HISTORY_FILE)) {
			json_combinations = new JSONArray();
		} else
			json_combinations = new JSONArray(fileManager.read(AppSettings.HISTORY_FILE));
		json_combinations.put(JSONManager.getJSON(combination));
		fileManager.write(json_combinations.toString(), AppSettings.HISTORY_FILE);
	}
}
