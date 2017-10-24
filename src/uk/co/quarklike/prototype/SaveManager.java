package uk.co.quarklike.prototype;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector4f;

import uk.co.quarklike.prototype.map.Map;
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

			writeString(out, player.getName(), 32);

			writeInt(out, player.getMapID());
			writeInt(out, player.getX());
			writeInt(out, player.getY());
			writeByte(out, player.getSubX());
			writeByte(out, player.getSubY());
			writeByte(out, player.getDirection());
			writeBool(out, player.isMoving());

			writeShort(out, (short) player.getInventory().getItems().size());

			for (ItemStack i : player.getInventory().getItems()) {
				writeInt(out, i.getItemID());
				writeByte(out, i.getQuantity());
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

			String playerName = readString(buffer, 32);
			int mapID = buffer.getInt();
			int playerX = buffer.getInt();
			int playerY = buffer.getInt();
			byte playerSubX = buffer.get();
			byte playerSubY = buffer.get();
			byte direction = buffer.get();
			boolean moving = buffer.get() == 1;

			player.loadPlayer(playerName, playerX, playerY, playerSubX, playerSubY, direction, moving);
			player.register(map);

			short invItems = buffer.getShort();

			for (int i = 0; i < invItems; i++) {
				int itemID = buffer.getInt();
				byte quantity = buffer.get();
				player.addItem(new ItemStack(itemID, quantity));
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
		player.loadPlayer("Player", 15, 15, (byte) 0, (byte) 0, Map.NORTH, false);
		player.register(map);
		player.addItem(new ItemStack(1, (byte) 5));
		player.addItem(new ItemStack(2, (byte) 5));
	}
}
