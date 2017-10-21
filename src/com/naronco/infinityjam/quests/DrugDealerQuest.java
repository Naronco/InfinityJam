package com.naronco.infinityjam.quests;

import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.IQuest;
import com.naronco.infinityjam.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrugDealerQuest implements IQuest {
	@Override
	public void activate() {
	}

	@Override
	public List<Item> getRewards() {
		return Arrays.asList(Item.DRUG);
	}

	@Override
	public String getDescription() {
		return "DrugDealerQuest";
	}
}
