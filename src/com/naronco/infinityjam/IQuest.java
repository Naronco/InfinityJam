package com.naronco.infinityjam;

import java.util.List;

public interface IQuest {
	void activate();
	List<Item> getRewards();
	String getDescription();
}
