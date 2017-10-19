package uk.co.quarklike.prototype;

import java.io.File;
import java.io.IOException;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

import uk.co.quarklike.prototype.map.item.Item;
import uk.co.quarklike.prototype.map.item.Material;

public class DatabaseParser {
	public static void parseDatabse(String fileName) {
		try {
			Database database = DatabaseBuilder.open(new File("res/" + fileName));

			Table items = database.getTable("items");
			for (Row r : items) {
				new Item(r.getInt("itemID"), r.getString("itemName"), "items/" + r.getString("itemTexture"), Short.parseShort(Integer.toString(r.getInt("textureSlot"))));
			}

			Table materials = database.getTable("materials");
			for (Row r : materials) {
				new Material(r.getInt("matID"), r.getString("matName"));
			}
		} catch (IOException e) {
			Log.err("Failed to load database: " + fileName, e);
		}
	}
}
