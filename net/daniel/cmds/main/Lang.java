package net.daniel.cmds.main;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import com.google.common.base.CaseFormat;

public enum Lang {
	// Class 명이 파일명과 같을 필요는 없음
	// 이름이 파일에 있는 메세지와 같을것, 띄어쓰기는 _ 로 대체 (메세지 X)

	BROADCAST_CHAT("&a&l[ &f&l확성기 &a&l] &e%player% : &f%msg%"),
	BROADCAST_HELP("&b&l[ &f&lServer &b&l] &e/확성기 <내용> &f: %price%원을 소모하여 확성기를"
			+ " 사용합니다. (쿨타임: %cooltime%초) &e계급별로 가격이 다릅니다."),

	BROADCAST_MUTED("&b&l[ &f&lServer &b&l] &e당신은 음소거 처리되어 있어 확성기 사용이 제한됩니다."),

	COOLTIME_BROAD("&a&l[ &f&l확성기 &a&l] &e현재 쿨타임 입니다. &f잔여 쿨타임: %time_left%초"),
	NO_MONEY("&b&l[ &f&lServer &b&l] &c돈이 부족합니다. 필요한 금액: &e%money_need%원, &e계급별로 가격이 다릅니다."),
	DONE_FEED("&b&l[ &f&lServer &b&l] &e%price%원을 지불하여 허기를 채웠습니다."),
	SET_FOOD("&b&l[ &f&lServer &b&l] &e%price%원을 지불하여 포만감 값을 %level%(으)로 설정했습니다."),
	HUNGER_HELP("&b&l[ &f&lServer &b&l] &e/허기설정 <정수> &f: %price%원이 지불하여 음식 레벨을 <정수> 값으로 설정합니다."
			+ " 1~19 사이가능. 현재의 음식레벨보다 높은 값이나 같은 값으로는 설정이 불가능합니다. &e계급별로 가격이 다릅니다."),
	HUNGER_OVER("&b&l[ &f&lServer &b&l] &c음식 레벨은 현재의 음식레벨보다 높은 값으로는 설정이 불가능합니다. "
			+ "허기를 채울려면 &e\"/밥\"&c 명령어를 대신 이용해주세요. 현재 음식 레벨: %food_level%"),
	HUNGER_OVERFLOW("&b&l[ &f&lServer &b&l] &c음식 레벨은 1~19 사이의 정수만 가능합니다. 레벨 2당 포만감 1칸"),
	INGAME_ONLY("[Kite 서버] 해당 명령어는 게임 내 플레이어만 사용이 가능한 명령어 입니다."),
	CONSUMED("&b&l[ &f&lServer &b&l] &e%price%원이 지불되었습니다."),
	NOT_BOOlEAN("&b&l[ &f&lServer &b&l] &c%arg% 번째 인자의 문자열은 true 혹은 false 여야 합니다. "),
	HEAD_HELP("&6/head &f: 본인 머리 소환\n" + "&6/head <닉네임> &f: <닉네임> 머리 소환, <닉네임>이 \"-default\"일 경우 기본머리 소환\n"
			+ "&6/head <닉네임> <대상> &f: <닉네임> 머리를 <대상> 에게 지급, <닉네임>이 \"-default\"일 경우 기본머리 소환\n"
			+ "&6/head <닉네임> <대상> true &f: <닉네임> 머리를 본인에게 뜨는 메세지 없이 <대상> 에게 지급, <닉네임>이 \"-default\"일 경우 기본머리 소환\n"
			+ "&6/head <닉네임> <대상> false &f: &6/head <닉네임> <대상> &f과 동일"),
	NO_PERM("&b&l[ &f&lServer &b&l] &c권한이 없습니다."), NO_SPACE_INVENTORY("&b&l[ &f&lServer &b&l] &e인벤토리의 공간이 부족합니다."),
	NO_SPACE_OTHER_INVENTORY("&b&l[ &f&lServer &b&l] &e%target% 님의 인벤토리 공간이 부족합니다."),
	GAVE_HEAD("&b&l[ &f&lServer &b&l] &f%target_head% 머리가 지급되었습니다."),
	GAVE_HEAD_OTHER("&b&l[ &f&lServer &b&l] &f%target%님 에게 %target_head% 머리를 지급하였습니다."),
	FAILED_TO_GIVE_HEAD("&b&l[ &f&lServer &b&l] &c머리 지급 도중에 예기치 못한 문제가 발생하였습니다. "),
	NON_EXIST_NICKNAME("&b&l[ &f&lServer &b&l] &c%target_head%는(은) 마인크래프트에 존재하지 않는 플레이어 이름으로 기본 머리입니다. "
			+ "그럼에도 불구 하고 사용하실려면 \"-default\" 를 플레이어 이름으로 대체하여 사용해 주세요. &f예시: /head -default"),
	PlAYER_NOT_ONLINE("&b&l[ &f&lServer &b&l] &c%target%님은 온라인이 아닙니다."),
	
	MUTEINFO_HELP("&b&l[ &f&lServer &b&l] &e/%cmd% <닉네임> &f: 채팅 금지가 해제될 때까지 남은 시간을 확인합니다."),

	PlAYER_MUTE_INFO_INFITE("&b&l[ &f&lServer &b&l] &f%player%님은 무기한 채팅금지 상태입니다."),
	PlAYER_MUTE_INFO("&b&l[ &f&lServer &b&l] &7 채팅금지 잔여시간: &f%day% 일 %hour% 시간 %min% 분 %sec% 초"),
	PlAYERNOTPLAYED("&b&l[ &f&lServer &b&l] &c%player% 님은 서버에 접속한 적이 없습니다. "),
	PlAYERNOTMUTED("&b&l[ &f&lServer &b&l] &c%player% 님은 채팅금지 상태가 아닙니다. "),
	ERROR_ON_GIVEHEAD("&b&l[ &f&lServer &b&l] &c머리 명령어 처리 과정에 예기치 못한 오류가 발생하였습니다. (API 요청 횟수 제한 초과 등)"),
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
