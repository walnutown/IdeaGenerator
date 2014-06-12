package com.taohu.ideagenerator.model;

import java.util.ArrayList;
import java.util.List;

public class Combination {
	public String timestamp;
	public List<Item> items;

	public Combination(String timestamp) {
		this.timestamp = timestamp;
		items = new ArrayList<Item>();
	}

	public void addItem(Item item) {
		items.add(item);
	}

	public List<Item> getItems() {
		return items;
	}
	
	public String toString(){
		return items.toString();
	}

}
