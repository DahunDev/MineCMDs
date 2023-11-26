package net.daniel.cmds.main;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import com.google.common.base.CaseFormat;

public enum Lang {
	// Class ���� ���ϸ�� ���� �ʿ�� ����
	// �̸��� ���Ͽ� �ִ� �޼����� ������, ����� _ �� ��ü (�޼��� X)

	BROADCAST_CHAT("&a&l[ &f&lȮ���� &a&l] &e%player% : &f%msg%"),
	BROADCAST_HELP("&b&l[ &f&lServer &b&l] &e/Ȯ���� <����> &f: %price%���� �Ҹ��Ͽ� Ȯ���⸦"
			+ " ����մϴ�. (��Ÿ��: %cooltime%��) &e��޺��� ������ �ٸ��ϴ�."),

	BROADCAST_MUTED("&b&l[ &f&lServer &b&l] &e����� ���Ұ� ó���Ǿ� �־� Ȯ���� ����� ���ѵ˴ϴ�."),

	COOLTIME_BROAD("&a&l[ &f&lȮ���� &a&l] &e���� ��Ÿ�� �Դϴ�. &f�ܿ� ��Ÿ��: %time_left%��"),
	NO_MONEY("&b&l[ &f&lServer &b&l] &c���� �����մϴ�. �ʿ��� �ݾ�: &e%money_need%��, &e��޺��� ������ �ٸ��ϴ�."),
	DONE_FEED("&b&l[ &f&lServer &b&l] &e%price%���� �����Ͽ� ��⸦ ä�����ϴ�."),
	SET_FOOD("&b&l[ &f&lServer &b&l] &e%price%���� �����Ͽ� ������ ���� %level%(��)�� �����߽��ϴ�."),
	HUNGER_HELP("&b&l[ &f&lServer &b&l] &e/��⼳�� <����> &f: %price%���� �����Ͽ� ���� ������ <����> ������ �����մϴ�."
			+ " 1~19 ���̰���. ������ ���ķ������� ���� ���̳� ���� �����δ� ������ �Ұ����մϴ�. &e��޺��� ������ �ٸ��ϴ�."),
	HUNGER_OVER("&b&l[ &f&lServer &b&l] &c���� ������ ������ ���ķ������� ���� �����δ� ������ �Ұ����մϴ�. "
			+ "��⸦ ä����� &e\"/��\"&c ��ɾ ��� �̿����ּ���. ���� ���� ����: %food_level%"),
	HUNGER_OVERFLOW("&b&l[ &f&lServer &b&l] &c���� ������ 1~19 ������ ������ �����մϴ�. ���� 2�� ������ 1ĭ"),
	INGAME_ONLY("[Kite ����] �ش� ��ɾ�� ���� �� �÷��̾ ����� ������ ��ɾ� �Դϴ�."),
	CONSUMED("&b&l[ &f&lServer &b&l] &e%price%���� ���ҵǾ����ϴ�."),
	NOT_BOOlEAN("&b&l[ &f&lServer &b&l] &c%arg% ��° ������ ���ڿ��� true Ȥ�� false ���� �մϴ�. "),
	HEAD_HELP("&6/head &f: ���� �Ӹ� ��ȯ\n" + "&6/head <�г���> &f: <�г���> �Ӹ� ��ȯ, <�г���>�� \"-default\"�� ��� �⺻�Ӹ� ��ȯ\n"
			+ "&6/head <�г���> <���> &f: <�г���> �Ӹ��� <���> ���� ����, <�г���>�� \"-default\"�� ��� �⺻�Ӹ� ��ȯ\n"
			+ "&6/head <�г���> <���> true &f: <�г���> �Ӹ��� ���ο��� �ߴ� �޼��� ���� <���> ���� ����, <�г���>�� \"-default\"�� ��� �⺻�Ӹ� ��ȯ\n"
			+ "&6/head <�г���> <���> false &f: &6/head <�г���> <���> &f�� ����"),
	NO_PERM("&b&l[ &f&lServer &b&l] &c������ �����ϴ�."), NO_SPACE_INVENTORY("&b&l[ &f&lServer &b&l] &e�κ��丮�� ������ �����մϴ�."),
	NO_SPACE_OTHER_INVENTORY("&b&l[ &f&lServer &b&l] &e%target% ���� �κ��丮 ������ �����մϴ�."),
	GAVE_HEAD("&b&l[ &f&lServer &b&l] &f%target_head% �Ӹ��� ���޵Ǿ����ϴ�."),
	GAVE_HEAD_OTHER("&b&l[ &f&lServer &b&l] &f%target%�� ���� %target_head% �Ӹ��� �����Ͽ����ϴ�."),
	FAILED_TO_GIVE_HEAD("&b&l[ &f&lServer &b&l] &c�Ӹ� ���� ���߿� ����ġ ���� ������ �߻��Ͽ����ϴ�. "),
	NON_EXIST_NICKNAME("&b&l[ &f&lServer &b&l] &c%target_head%��(��) ����ũ����Ʈ�� �������� �ʴ� �÷��̾� �̸����� �⺻ �Ӹ��Դϴ�. "
			+ "�׷����� �ұ� �ϰ� ����ϽǷ��� \"-default\" �� �÷��̾� �̸����� ��ü�Ͽ� ����� �ּ���. &f����: /head -default"),
	PlAYER_NOT_ONLINE("&b&l[ &f&lServer &b&l] &c%target%���� �¶����� �ƴմϴ�."),
	
	MUTEINFO_HELP("&b&l[ &f&lServer &b&l] &e/%cmd% <�г���> &f: ä�� ������ ������ ������ ���� �ð��� Ȯ���մϴ�."),

	PlAYER_MUTE_INFO_INFITE("&b&l[ &f&lServer &b&l] &f%player%���� ������ ä�ñ��� �����Դϴ�."),
	PlAYER_MUTE_INFO("&b&l[ &f&lServer &b&l] &7 ä�ñ��� �ܿ��ð�: &f%day% �� %hour% �ð� %min% �� %sec% ��"),
	PlAYERNOTPLAYED("&b&l[ &f&lServer &b&l] &c%player% ���� ������ ������ ���� �����ϴ�. "),
	PlAYERNOTMUTED("&b&l[ &f&lServer &b&l] &c%player% ���� ä�ñ��� ���°� �ƴմϴ�. "),
	ERROR_ON_GIVEHEAD("&b&l[ &f&lServer &b&l] &c�Ӹ� ��ɾ� ó�� ������ ����ġ ���� ������ �߻��Ͽ����ϴ�. (API ��û Ƚ�� ���� �ʰ� ��)"),
	;

	private final String def;

	Lang(String def) {
		this.def = def;
	}

	public static void init(ConfigurationSection section) {
		for (Lang lang : Lang.values()) {

			String key = lang.key();
			if (!section.contains(key)) {
				section.set(key, lang.def);
			}
		}
	}

	public String toString(ConfigurationSection lang) {
		return ChatColor.translateAlternateColorCodes('&', lang.getString(key(), def));
	}

	@Override
	public String toString() {
		return toString(Main.get().getLangConfig());
	}

	private String key() {
		return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, name());
	}

}
