package uk.co.quarklike.zephis.old.map.item;

public class Material {
	private int materialID;
	private String materialName;

	public Material(int id, String name) {
		this.materialID = id;
		this.materialName = name;
	}

	public int getID() {
		return materialID;
	}

	public String getName() {
		return materialName;
	}
}
