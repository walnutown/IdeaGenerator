package com.taohu.ideagenerator.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.taohu.ideagenerator.model.Combination;
import com.taohu.ideagenerator.model.Item;

/*
 * Manage the conversion between JSON and other data structure 
 */
public class JSONManager {

	/**
	 * Build json from Combination object
	 */
	public static JSONObject getJSON(Combination combination) throws JSONException{
		JSONObject json_comb = new JSONObject();
		json_comb.put("timestamp", combination.timestamp);
		JSONArray json_items = new JSONArray();
		for (Item item : combination.getItems()){
			JSONObject json_item = new JSONObject();
			json_item.put("value", item.value);
			json_item.put("color", item.color);
			json_items.put(json_item);
		}
		json_comb.put("items", json_items);
		return json_comb;
	}
	
	public static List<Combination> getCombinationsFromJSON(JSONArray json_combinations) throws JSONException{
		List<Combination> combinations = new ArrayList<Combination>();
		for (int i=0; i<json_combinations.length(); i++){
			combinations.add(getCombinationFromJSON(json_combinations.getJSONObject(i)));
		}
		return combinations;
	}
	
	private static Combination getCombinationFromJSON(JSONObject json_combination) throws JSONException{
		Combination combination = new Combination(json_combination.getString("timestamp"));
		JSONArray json_items = json_combination.getJSONArray("items");
		for (int i=0; i<json_items.length(); i++){
			JSONObject json_item = json_items.getJSONObject(i);
			combination.addItem(new Item(json_item.getInt("color"), json_item.getString("value")));
		}
		return combination;
	}
	
}
