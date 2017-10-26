package uk.co.quarklike.prototype;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;

import org.lwjgl.util.vector.Vector4f;

import uk.co.quarklike.prototype.map.Map;
import uk.co.quarklike.prototype.map.entity.Entity;
import uk.co.quarklike.prototype.map.entity.EntityLiving;
import uk.co.quarklike.prototype.map.item.ItemStack;

public class SaveManager {
	public static void saveFile(Map map, EntityLiving player, String fileName) {
		try {
			File dir = new File("save/");
			dir.mkdirs();

			File f = new File("save/" + fileName);
			if (!f.exists()) {
				f.createNewFile();
			}

			FileOutputStream out = new FileOutputStream(f);

			Collection<Entity> entities = map.getEntities();

			writeShort(out, (short) entities.size());

			for (Entity e : entities) {
				writeEntity(out, map, player, e);
			}

			ArrayList<Vector4f> items = map.getItems();
			writeShort(out, (short) items.size());

			for (Vector4f item : items) {
				writeInt(out, (int) item.getX());
				writeInt(out, (int) item.getY());
				writeInt(out, (int) item.getZ());
				writeByte(out, (byte) item.getW());
			}

			out.close();
		} catch (IOException e) {
			Log.err("Failed to save game", e);
		}
	}

	public static final byte DEFAULT = 0;
	public static final byte LIVING = 1;
	public static final byte PROJECTILE = 2;
	public static final byte ITEM = 3;

	public static void readFile(Map map, EntityLiving player, String fileName) {
		try {
			File f = new File("save/" + fileName);
			if (!f.exists()) {
				newGame(map, player);
				return;
			}

			FileInputStream in = new FileInputStream(f);

			byte[] data = new byte[in.available()];
			in.read(data);

			ByteBuffer buffer = ByteBuffer.allocate(data.length);
			buffer.put(data);
			buffer.flip();

			short entities = buffer.getShort();

			for (int entity = 0; entity < entities; entity++) {
				readEntity(buffer, player, map);
			}

			short mapItems = buffer.getShort();

			for (int i = 0; i < mapItems; i++) {
				int x = buffer.getInt();
				int y = buffer.getInt();
				int itemID = buffer.getInt();
				byte quant = buffer.get();
				map.addItem(x, y, itemID, quant);
			}

			in.close();
		} catch (IOException e) {
			Log.err("Failed to read save game", e);
		}
	}

	private static void writeByteArray(FileOutputStream stream, byte[] array) throws IOException {
		stream.write(array);
	}

	private static void writeCharArray(FileOutputStream stream, char[] array, int size) throws IOException {
		for (int i = 0; i < array.length; i++) {
			ByteBuffer buffer = ByteBuffer.allocate(Character.BYTES);
			buffer.putChar(array[i]);
			stream.write(buffer.array());
		}

		for (int i = array.length; i < size; i++) {
			stream.write(0);
			stream.write(0);
		}
	}

	private static void writeBool(FileOutputStream stream, boolean toWrite) throws IOException {
		writeByte(stream, toWrite ? (byte) 1 : (byte) 0);
	}

	private static void writeByte(FileOutputStream stream, byte toWrite) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(Byte.BYTES);
		buffer.put(toWrite);
		stream.write(buffer.array());
	}

	private static void writeShort(FileOutputStream stream, short toWrite) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
		buffer.putShort(toWrite);
		stream.write(buffer.array());
	}

	private static void writeInt(FileOutputStream stream, int toWrite) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
		buffer.putInt(toWrite);
		stream.write(buffer.array());
	}

	private static void writeLong(FileOutputStream stream, long toWrite) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(toWrite);
		stream.write(buffer.array());
	}

	private static void writeString(FileOutputStream stream, String toWrite, int length) throws IOException {
		char[] a = toWrite.toCharArray();
		writeCharArray(stream, a, length);
	}

	private static String readString(ByteBuffer buffer, int length) throws IOException {
		char[] out = new char[length];
		for (int i = 0; i < length; i++) {
			out[i] = buffer.getChar();
		}
		return String.valueOf(out);
	}

	private static void newGame(Map map, EntityLiving player) {
		player.loadEntity("Player", 15, 15, (byte) 0, (byte) 0, Map.NORTH, false, (short) 100, (short) 100, (short) 100, (short) 100, (short) 100, (short) 100, (byte) 100, (byte) 100, (byte) 100);
		player.register(map);
		player.addItem(new ItemStack(1, (byte) 5));
		player.addItem(new ItemStack(2, (byte) 5));
	}

	private static void writeEntity(FileOutputStream out, Map map, EntityLiving player, Entity e) throws IOException {
		byte type = e.getType();

		writeByte(out, e.equals(player) ? (byte) 1 : (byte) 0);
		writeString(out, e.getName(), 32);
		writeByte(out, type);
		writeInt(out, e.getMapID());
		writeLong(out, e.getEntityID());
		writeInt(out, e.getX());
		writeInt(out, e.getY());
		writeByte(out, e.getSubX());
		writeByte(out, e.getSubY());

		switch (type) {
		case DEFAULT:
			break;
		case LIVING:
			EntityLiving e_living = (EntityLiving) e;
			writeByte(out, e_living.getDirection());
			writeBool(out, e_living.isMoving());
			writeShort(out, e_living.getStats().getHardMaxHealth());
			writeShort(out, e_living.getStats().getHealth());
			writeShort(out, e_living.getStats().getHardMaxMana());
			writeShort(out, e_living.getStats().getMana());
			writeShort(out, e_living.getStats().getHardMaxStamina());
			writeShort(out, e_living.getStats().getStamina());
			writeByte(out, e_living.getStats().getHunger());
			writeByte(out, e_living.getStats().getTiredness());
			writeByte(out, e_living.getStats().getWarmth());

			writeShort(out, (short) e_living.getInventory().getItems().size());

			for (ItemStack i : e_living.getInventory().getItems()) {
				writeInt(out, i.getItemID());
				writeByte(out, i.getQuantity());
			}

			break;
		}
	}

	private static void readEntity(ByteBuffer buffer, EntityLiving player, Map map) throws IOException {
		Entity e = null;

		boolean isPlayer = buffer.get() == 1;
		String name = readString(buffer, 32);
		byte type = buffer.get();
		int mapID = buffer.getInt();
		long entityID = buffer.getLong();
		int x = buffer.getInt();
		int y = buffer.getInt();
		byte subX = buffer.get();
		byte subY = buffer.get();

		switch (type) {
		case DEFAULT:
			e = new Entity("UNKNOWN", "blank.png");
			e.loadEntity(name, x, y, subX, subY);
			break;
		case LIVING:
			byte direction = buffer.get();
			boolean moving = buffer.get() == 1;
			short maxHealth = buffer.getShort();
			short health = buffer.getShort();
			short maxMana = buffer.getShort();
			short mana = buffer.getShort();
			short maxStamina = buffer.getShort();
			short stamina = buffer.getShort();
			byte hunger = buffer.get();
			byte tiredness = buffer.get();
			byte warmth = buffer.get();

			e = isPlayer ? player : new EntityLiving("UNKNOWN", "tiles/grass.png");
			((EntityLiving) e).loadEntity(name, x, y, subX, subY, direction, moving, maxHealth, health, maxMana, mana, maxStamina, stamina, hunger, tiredness, warmth);

			short invItems = buffer.getShort();

			for (int i = 0; i < invItems; i++) {
				int itemID = buffer.getInt();
				byte quantity = buffer.get();
				((EntityLiving) e).addItem(new ItemStack(itemID, quantity));
			}

			break;
		case PROJECTILE:
			break;
		case ITEM:
			break;
		}

		e.register(map, entityID);
	}
}
