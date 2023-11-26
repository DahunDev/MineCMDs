package net.daniel.cmds.Utils;

import java.net.URL;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import net.daniel.cmds.main.Main;

public class MCUtils {

	private static UUID toUUID(String id) {
		return UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-"
				+ id.substring(16, 20) + "-" + id.substring(20, 32));
	}

	public static UUID getUUID(String playerName) {
		try {
			String url = "https://api.mojang.com/users/profiles/minecraft/" + playerName;

			// String UUIDJson = IOUtils.toString(new URL(url), "UTF-8");

			// System.out.println(UUIDJson);

			String UUIDJson = IOUtils.toString(new URL(url), "UTF-8");

			JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);

			String uuid = UUIDObject.get("id").toString();
			return toUUID(uuid);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getFullCommand(String cmd, String[] arg) {
		for (String i : arg) {
			cmd += " " + i;
		}
		return cmd;

	}

	public static boolean hasEnoughSpace(Player player) {

		int slot = player.getInventory().firstEmpty();

		return slot >= 0;

	}

	public static void dispatchCommandSync(final String cmd) {

		new BukkitRunnable() {
			public void run() {

				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd);
			}
		}.runTaskLater(Main.plugin, 0L);

	}

	public static ItemStack setHeadOwner(OfflinePlayer owner, ItemStack item) {

		SkullMeta meta = (SkullMeta) item.getItemMeta();
		if (owner != null) {
			meta.setOwningPlayer(owner);

		} else {

		}

		item.setItemMeta(meta);

		return item;
	}

	public static ItemStack setHeadOwner(ItemStack item, Player owner) {

		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwningPlayer(owner);
		item.setItemMeta(meta);

		return item;

	}

}
