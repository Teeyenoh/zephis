package uk.co.quarklike.zephis.src.map.item;

import uk.co.quarklike.zephis.src.Translation;

public class Item {
	public static Item[] _items = new Item[256];

	private int _itemID;

	private String _name;
	private String _texture;
	private int _textureSlot;

	public static final Item material_ingot_copper = new Item(0, "<ITEM_MATERIAL_INGOT_COPPER>", "items", 0);
	public static final Item material_ingot_tin = new Item(1, "<ITEM_MATERIAL_INGOT_TIN>", "items", 1);
	public static final Item consumable_drink_tea_green = new Item(2, "<ITEM_CONSUMABLE_DRINK_TEA_GREEN>", "items", 6);
	public static final Item toy_stuffed_rabbit_grey = new Item(3, "<ITEM_TOY_STUFFED_RABBIT_GREY>", "items", 7);
	public static final Item toy_stuffed_pig_rainbow = new Item(4, "<ITEM_TOY_STUFFED_PIG_RAINBOW>", "items", 8);
	public static final Item tool_fork_silver = new Item(5, "<ITEM_TOOL_FORK_SILVER>", "items", 9);
	public static final Item flower_sunflower = new Item(6, "<ITEM_FLOWER_SUNFLOWER>", "items", 10);
	public static final Item consumable_potion_healing_minor = new Item(7, "<ITEM_CONSUMABLE_POTION_HEALING_MINOR>", "items", 11);
	public static final Item tool_tankard_iron = new Item(8, "<ITEM_TOOL_TANKARD_IRON>", "items", 12);
	public static final Item fluid_oil_coconut = new Item(9, "<ITEM_FLUID_OIL_COCONUT>", "items", 13);
	public static final Item tool_wateringcan_tin = new Item(10, "<ITEM_TOOL_WATERINGCAN_TIN>", "items", 14);
	public static final Item tool_instrument_ukulele_maple = new Item(11, "<ITEM_TOOL_INSTRUMENT_UKULELE_MAPLE>", "items", 15);
	public static final Item weapon_longsword_steel = new Item(12, "<ITEM_WEAPON_LONGSWORD_STEEL>", "items", 16);
	public static final Item material_gem_diamond = new Item(13, "<ITEM_MATERIAL_GEM_DIAMOND>", "items", 17);
	public static final Item tool_knife_silver = new Item(14, "<ITEM_TOOL_KNIFE_SILVER>", "items", 18);
	public static final Item tool_whistle_tin = new Item(15, "<ITEM_TOOL_WHISTLE_TIN>", "items", 19);
	public static final Item consumable_drink_wine_red = new Item(16, "<ITEM_CONSUMABLE_DRINK_WINE_RED>", "items", 20);
	public static final Item consumable_drink_ale_dark = new Item(17, "<ITEM_CONSUMABLE_DRINK_ALE_DARK>", "items", 21);
	public static final Item consumable_food_bread_wheat = new Item(18, "<ITEM_CONSUMABLE_FOOD_BREAD_WHEAT>", "items", 22);
	public static final Item material_thread_wool = new Item(19, "<ITEM_MATERIAL_THREAD_WOOL>", "items", 23);
	public static final Item clothing_chest_under_wool = new Item(20, "<ITEM_CLOTHING_CHEST_UNDER_WOOL>", "items", 24);
	public static final Item animal_jar_insect_stick = new Item(21, "<ITEM_ANIMAL_JAR_INSECT_STICK>", "items", 25);
	public static final Item armour_chest_leather = new Item(22, "<ITEM_ARMOUR_CHEST_LEATHER>", "items", 26);
	public static final Item tool_needle_knitting_bronze = new Item(23, "<ITEM_TOOL_NEEDLE_KNITTING_BRONZE>", "items", 27);
	public static final Item clothing_legs_under_linen = new Item(24, "<ITEM_CLOTHING_LEGS_UNDER_LINEN>", "items", 28);
	public static final Item weapon_shortsword_copper = new Item(25, "<ITEM_WEAPON_SHORTSWORD_COPPER>", "items", 29);
	public static final Item shield_buckler_leather = new Item(26, "<ITEM_SHIELD_BUCKLER_LEATHER>", "items", 30);
	public static final Item tool_manure = new Item(27, "<ITEM_TOOL_MANURE>", "items", 31);

	public Item(int itemID, String name, String texture, int textureSlot) {
		_itemID = itemID;
		_items[itemID] = this;
		_name = name;
		_texture = texture;
		_textureSlot = textureSlot;
	}

	public String getName() {
		return _name;
	}

	public String getTranslatedName() {
		return Translation.translate(_name.replaceAll("<", "").replaceAll(">", ""));
	}

	public String getTexture() {
		return _texture;
	}

	public int getTextureSlot() {
		return _textureSlot;
	}

	public static Item getItem(int itemID) {
		return _items[itemID];
	}

	public int getItemID() {
		return _itemID;
	}
}
