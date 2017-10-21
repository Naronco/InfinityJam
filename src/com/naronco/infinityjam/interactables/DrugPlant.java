package com.naronco.infinityjam.interactables;

import com.deviotion.ld.eggine.math.Polygon2d;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.Interactable;
import com.naronco.infinityjam.Item;

public class DrugPlant implements Interactable {
    private Polygon2d outline;
	private boolean harvested = false;
	private int harvesting = 0;

	public int save() {
		if (harvested)
			return -1;
		else
			return harvesting;
	}

	public void load(int s) {
		harvested = false;
		if (s == -1)
			harvested = true;
		else
			harvesting = s;
	}

    public DrugPlant(Polygon2d outline) {
        this.outline = outline;
    }

    @Override
    public String getName() {
        return "Dekopflanze";
    }

    @Override
    public String getNameWithArticle() {
        return "eine dekorative Pflanze";
    }

    @Override
    public boolean intersects(int x, int y) {
        return outline.intersects(x, y);
    }

    @Override
    public boolean hasLook() {
        return false;
    }

    @Override
    public boolean hasUse() {
        return true;
    }

    @Override
    public boolean hasTake() {
        return true;
    }

    @Override
    public boolean hasPunch() {
        return true;
    }

    @Override
    public void look(int x, int y) {

    }

    @Override
    public void use(int x, int y) { Game.instance.showMessage("Nein Danke, ich bin schon ausgeschlafen."); }

    @Override
    public void take(int x, int y) {
	    if (harvesting == 0 && Game.instance.random.nextBoolean()) {
		    harvested = true;
		    Game.instance.showMessage("Hier gibt es nichts");
		    return;
	    }
	    if (harvested) {
		    Game.instance.showMessage("Mehr ist hier nicht.");
		    return;
	    }
	    harvesting++;
	    if (harvesting == 1)
	        Game.instance.showMessage("*Raschel Raschel*");
	    else if (harvesting ==  2)
		    Game.instance.showMessage("Die Pflanze wehrt sich");
	    else if (harvesting ==  3)
		    Game.instance.showMessage("Fast...");
	    else if (harvesting ==  4) {
		    Game.instance.showMessage("Ah! Endlich");
		    Game.instance.giveItems(Item.LEAF, 1, 5);
	    }
    }

    @Override
    public void punch(int x, int y) {
    	if (harvested) {
		    Game.instance.showMessage("Mehr ist hier nicht.");
    		return;
	    }
	    harvested = true;
        Game.instance.showMessage("Die Pflanze wurde ausgerissen.");
        Game.instance.giveItems(Item.LEAF, 3, 5);
    }

    @Override
    public void interact(int x, int y, Item item) {
        switch (item) {
            case KEY:
                Game.instance.showMessage("Schlüssel unter Planzentöpfen zu verstecken ist zu offensichtlich.");
                break;
        }
    }
}
