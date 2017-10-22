package com.naronco.infinityjam.quests;

import com.naronco.infinityjam.IQuest;
import com.naronco.infinityjam.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TripleSevenQuest implements IQuest {
	@Override
	public void activate() {
	}

	@Override
	public List<Item> getRewards() {
		return new ArrayList<>();
	}

	@Override
	public String getDescription() {
		return "Bekomm beim Glücksspiel die 777";
	}
}
