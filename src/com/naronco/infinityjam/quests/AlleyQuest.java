package com.naronco.infinityjam.quests;

import com.naronco.infinityjam.IQuest;
import com.naronco.infinityjam.Item;

import java.util.Arrays;
import java.util.List;

public class AlleyQuest implements IQuest {
	@Override
	public void activate() {
	}

	@Override
	public List<Item> getRewards() {
		return Arrays.asList(Item.SAW);
	}

	@Override
	public String getDescription() {
		return "Hol die Kettensäge in der Gasse";
	}
}
