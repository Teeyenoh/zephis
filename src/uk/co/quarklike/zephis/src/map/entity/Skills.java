package uk.co.quarklike.zephis.src.map.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Skills {
	public static final int SKILL_COUNT = 46;

	private static final HashMap<Integer, String> _skillNames = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 2983218284579239116L;

		private void addSkill(int id, String name) {
			put(id, name);
		}

		{
			addSkill(0, "<SKILL_ARMOUR_LIGHT>");
			addSkill(1, "<SKILL_ARMOUR_MEDIUM>");
			addSkill(2, "<SKILL_ARMOUR_HEAVY>");

			addSkill(3, "<SKILL_WEAPON_DAGGER>");
			addSkill(4, "<SKILL_WEAPON_SWORD>");
			addSkill(5, "<SKILL_WEAPON_AXE>");
			addSkill(6, "<SKILL_WEAPON_GREATAXE>");
			addSkill(7, "<SKILL_WEAPON_MACE>");
			addSkill(8, "<SKILL_WEAPON_WARHAMMER>");
			addSkill(9, "<SKILL_WEAPON_CLUB>");
			addSkill(10, "<SKILL_WEAPON_STAFF>");
			addSkill(11, "<SKILL_WEAPON_CROSSBOW>");
			addSkill(12, "<SKILL_WEAPON_BOW>");

			addSkill(13, "<SKILL_COMBAT_BLOCK>");
			addSkill(14, "<SKILL_COMBAT_PARRY>");
			addSkill(15, "<SKILL_COMBAT_DODGE>");

			addSkill(16, "<SKILL_SUBVERSION_SNEAKING>");
			addSkill(17, "<SKILL_SUBVERSION_LOCKPICKING>");
			addSkill(18, "<SKILL_SUBVERSION_PICKPOCKETING>");
			addSkill(19, "<SKILL_SUBVERSION_INTIMIDATION>");
			addSkill(20, "<SKILL_SUBVERSION_CHARM>");
			addSkill(21, "<SKILL_SUBVERSION_BRIBERY>");

			addSkill(22, "<SKILL_MAGIC_DAMAGE>");
			addSkill(39, "<SKILL_MAGIC_AFFLICTION>");
			addSkill(23, "<SKILL_MAGIC_ENHANCEMENT>");
			addSkill(24, "<SKILL_MAGIC_HEALING>");
			addSkill(25, "<SKILL_MAGIC_ILLUSION>");
			addSkill(26, "<SKILL_MAGIC_SUMMONING>");

			addSkill(27, "<SKILL_ELEMENT_FIRE>");
			addSkill(28, "<SKILL_ELEMENT_WATER>");
			addSkill(29, "<SKILL_ELEMENT_EARTH>");
			addSkill(30, "<SKILL_ELEMENT_AIR>");
			addSkill(31, "<SKILL_ELEMENT_LIGHT>");
			addSkill(32, "<SKILL_ELEMENT_DARK>");
			addSkill(33, "<SKILL_ELEMENT_ORDER>");
			addSkill(34, "<SKILL_ELEMENT_CHAOS>");

			addSkill(35, "<SKILL_CRAFTING_SMITHING>");
			addSkill(36, "<SKILL_CRAFTING_COOKING>");
			addSkill(37, "<SKILL_CRAFTING_ALCHEMY>");
			addSkill(38, "<SKILL_CRAFTING_BONECRAFTING>");
			addSkill(40, "<SKILL_CRAFTING_LEATHERWORKING>");
			addSkill(41, "<SKILL_CRAFTING_TAILORING>");

			addSkill(42, "<SKILL_GATHERING_MINING>");
			addSkill(43, "<SKILL_GATHERING_HERBALISM>");
			addSkill(44, "<SKILL_GATHERING_SKINNING>");
			addSkill(45, "<SKILL_GATHERING_FISHING>");
		}
	};

	private int[] _abilities;
	private int[] _skills;

	public Skills(int[] abilities, int[] skills) {
		_abilities = abilities;
		_skills = skills;
	}

	public int getAbility(int ability) {
		return _abilities[ability];
	}

	public int getSkill(int skill) {
		return _skills[skill];
	}

	public static String getSkillName(int skill) {
		return _skillNames.get(skill);
	}

	private Comparator<SkillValue> _skillOrder = new Comparator<SkillValue>() {
		public int compare(SkillValue a, SkillValue b) {
			return b.getValue() - a.getValue();
		}
	};

	public int[] getSkillOrder() {
		ArrayList<SkillValue> list = new ArrayList<SkillValue>();
		for (int i = 0; i < SKILL_COUNT; i++) {
			list.add(new SkillValue(i, _skills[i]));
		}

		list.sort(_skillOrder);

		int[] out = new int[SKILL_COUNT];
		for (int i = 0; i < SKILL_COUNT; i++) {
			out[i] = list.get(i).getSkillID();
		}

		return out;
	}

	private class SkillValue {
		private int _skillID;
		private int _value;

		public SkillValue(int skillID, int value) {
			_skillID = skillID;
			_value = value;
		}

		public int getSkillID() {
			return _skillID;
		}

		public int getValue() {
			return _value;
		}
	}
}
