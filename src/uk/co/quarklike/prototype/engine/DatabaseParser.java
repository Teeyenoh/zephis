package uk.co.quarklike.prototype.engine;

import java.io.File;
import java.io.IOException;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

import uk.co.quarklike.prototype.Log;
import uk.co.quarklike.prototype.map.entity.CharClass;
import uk.co.quarklike.prototype.map.entity.Race;
import uk.co.quarklike.prototype.map.item.Item;
import uk.co.quarklike.prototype.map.item.Material;
import uk.co.quarklike.prototype.map.item.type.ItemType;
import uk.co.quarklike.prototype.map.item.type.ItemTypeDrink;
import uk.co.quarklike.prototype.map.item.type.ItemTypeFood;

public class DatabaseParser {
	public static void parseDatabse(String fileName) {
		try {
			Database database = DatabaseBuilder.open(new File("res/" + fileName));

			Table items = database.getTable("items");
			for (Row r : items) {
				String itemTypeS = r.getString("itemType");
				ItemType itemType = null;

				switch (itemTypeS) {
				case "DEFAULT":
					itemType = new ItemType();
					break;
				case "FOOD":
					itemType = new ItemTypeFood(r.getShort("health"), r.getShort("stamina"), r.getByte("hunger"), r.getByte("warmth"));
					break;
				case "DRINK":
					itemType = new ItemTypeDrink(r.getShort("mana"), r.getShort("stamina"), r.getByte("hunger"), r.getByte("warmth"));
					break;
				}

				new Item(r.getInt("itemID"), r.getString("itemName"), itemType, "items/" + r.getString("itemTexture"), Short.parseShort(Integer.toString(r.getInt("textureSlot"))));
			}

			Table materials = database.getTable("materials");
			for (Row r : materials) {
				new Material(r.getInt("matID"), r.getString("matName"));
			}

			Table races = database.getTable("races");
			for (Row r : races) {
				new Race(r.getInt("raceID"), r.getString("raceName"));
			}

			Table classes = database.getTable("classes");
			for (Row r : classes) {
				new CharClass(r.getInt("classID"), r.getString("className"));
			}
		} catch (IOException e) {
			Log.err("Failed to load database: " + fileName, e);
		}
	}
}
