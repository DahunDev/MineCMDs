package net.daniel.cmds.Commands;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.daniel.cmds.Utils.MCUtils;
import net.daniel.cmds.main.Lang;
import net.daniel.cmds.main.Main;

public class HeadCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	public OfflinePlayer getOfflinePlayer(String name, Player target) {

		if (name != null) {
			OfflinePlayer offPlayer = target;

			if (name.equalsIgnoreCase(target.getName())) {

				return offPlayer;
			}

			try {

				offPlayer = Bukkit.getOfflinePlayer(name); // deprecated 됬지만 사용

				return offPlayer;

			} catch (Exception e) {

				e.printStackTrace();
				Main.plugin.getLogger().log(Level.INFO,
						"method parameters info.  String: " + name + " Player: " + target.getName());
				return null;

			}

		} else {
			return null;
		}

	}

	private void excute_fail_cmd(Player target, String cmd) {

		if (Main.excute_cmd_on_fail_givehead) {

			for (String CMD : Main.cmds_on_fail_givehead) {
				MCUtils.dispatchCommandSync(CMD.replace("/", "").replaceAll("%player%", target.getName())
						.replaceAll("%uuid%", target.getUniqueId().toString()).replaceAll("%cmd%", cmd));
			}

		}

	}

	private boolean isNameExist(String name, Player target) {
		if (!Main.head_check_name || name.equalsIgnoreCase(target.getName()) || name.equalsIgnoreCase("-default")) {
			return true;

		} else if (MCUtils.getUUID(name) != null) {
			return true;
		} else {

			return false;
		}

	}

	private void giveHead(String target_head, Player target, CommandSender sender, boolean silent, boolean giveToOther,
			String[] args) {

		if (silent) {
			if (MCUtils.hasEnoughSpace(target)) {

				try {
					if (isNameExist(target_head, target)) {

						OfflinePlayer head = getOfflinePlayer(target_head, target);

						if (head != null) {

							if (!target_head.equalsIgnoreCase("-default")) {
								target.getInventory().setItem(target.getInventory().firstEmpty(),
										MCUtils.setHeadOwner(head, new ItemStack(Material.SKULL_ITEM, 1, (short) 3)));
							} else {
								target.getInventory().setItem(target.getInventory().firstEmpty(),
										new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
							}

							target.sendMessage(Lang.GAVE_HEAD.toString().replaceAll("%target_head%", target_head));
						} else {

							target.sendMessage(Lang.FAILED_TO_GIVE_HEAD.toString());

							excute_fail_cmd(target, MCUtils.getFullCommand("/minecmd:head", args));

						}

					} else {

						target.sendMessage(Lang.NON_EXIST_NICKNAME.toString().replaceAll("%target_head%", target_head));
						excute_fail_cmd(target, MCUtils.getFullCommand("/minecmd:head", args));
					}
				} catch (Exception e) {

					if (Main.plugin.getConfig().getBoolean("head.stacktrace", false)) {

						e.printStackTrace();

					}

					sender.sendMessage(Lang.ERROR_ON_GIVEHEAD.toString());
					excute_fail_cmd(target, MCUtils.getFullCommand("/minecmd:head", args));

				}

			} else {
				target.sendMessage(Lang.NO_SPACE_INVENTORY.toString());
				excute_fail_cmd(target, MCUtils.getFullCommand("/minecmd:head", args));

			}

		} else {

			if (MCUtils.hasEnoughSpace(target)) {

				try {
					if (isNameExist(target_head, target)) {

						OfflinePlayer head = getOfflinePlayer(target_head, target);

						if (head != null) {
							if (!target_head.equalsIgnoreCase("-default")) {
								target.getInventory().setItem(target.getInventory().firstEmpty(),
										MCUtils.setHeadOwner(head, new ItemStack(Material.SKULL_ITEM, 1, (short) 3)));
							} else {
								target.getInventory().setItem(target.getInventory().firstEmpty(),
										new ItemStack(Material.SKULL_ITEM, 1, (short) 3));

							}
							target.sendMessage(Lang.GAVE_HEAD.toString().replaceAll("%target_head%", target_head));

							if (giveToOther) {
								sender.sendMessage(
										Lang.GAVE_HEAD_OTHER.toString().replaceAll("%target_head%", target_head)
												.replaceAll("%target%", target.getName()));
							}

						} else {
							target.sendMessage(Lang.FAILED_TO_GIVE_HEAD.toString());

							if (giveToOther) {
								sender.sendMessage(Lang.FAILED_TO_GIVE_HEAD.toString());
							}

							excute_fail_cmd(target, MCUtils.getFullCommand("/minecmd:head", args));
						}

					} else {

						target.sendMessage(Lang.NON_EXIST_NICKNAME.toString().replaceAll("%target_head%", target_head));
						if (giveToOther) {
							sender.sendMessage(
									Lang.NON_EXIST_NICKNAME.toString().replaceAll("%target_head%", target_head));
						}
						excute_fail_cmd(target, MCUtils.getFullCommand("/minecmd:head", args));
					}
				} catch (Exception e) {
					if (Main.plugin.getConfig().getBoolean("head.stacktrace", false)) {

						e.printStackTrace();

					}
					sender.sendMessage(Lang.ERROR_ON_GIVEHEAD.toString());

					excute_fail_cmd(target, MCUtils.getFullCommand("/minecmd:head", args));

					// TODO: handle exception
				}

			} else {
				target.sendMessage(Lang.NO_SPACE_INVENTORY.toString());
				if (giveToOther) {
					sender.sendMessage(
							Lang.NO_SPACE_OTHER_INVENTORY.toString().replaceAll("%target%", target.getName()));
					excute_fail_cmd(target, MCUtils.getFullCommand("/minecmd:head", args));
				}
			}

		}

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("MineCMD.head") || sender.isOp()) {

			if (args.length >= 2) {
				if (Bukkit.getPlayer(args[1]) != null) {
					if (args.length == 2) {

						Player target = Bukkit.getPlayer(args[1]);

						(new BukkitRunnable() {
							public void run() {
								giveHead(args[0], target, sender, false, true, args);

							}

						}).runTaskAsynchronously(Main.plugin);

					} else {
						if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
							boolean silent = args[2].equalsIgnoreCase("true");
							Player target = Bukkit.getPlayer(args[1]);
							(new BukkitRunnable() {
								public void run() {
									giveHead(args[0], target, sender, silent, true, args);

								}

							}).runTaskAsynchronously(Main.plugin);

						} else {
							sender.sendMessage(Lang.NOT_BOOlEAN.toString().replaceAll("%arg%", "3"));
							return true;

						}

					}

				} else {
					sender.sendMessage(Lang.PlAYER_NOT_ONLINE.toString().replaceAll("%target%", args[1]));
					return true;

				}

			} else if (sender instanceof Player) {
				Player player = (Player) sender;

				if (args.length == 0) {

					(new BukkitRunnable() {
						public void run() {
							giveHead(player.getName(), player, sender, false, false, args);

						}

					}).runTaskAsynchronously(Main.plugin);

				} else {

					(new BukkitRunnable() {
						public void run() {
							giveHead(args[0], player, sender, false, false, args);

						}

					}).runTaskAsynchronously(Main.plugin);

				}

			} else {

				sender.sendMessage(Lang.INGAME_ONLY.toString());
				sender.sendMessage(Lang.HEAD_HELP.toString());
				return true;

			}

		} else {
			sender.sendMessage(Lang.NO_PERM.toString());
			return false;
		}
		return false;
	}
}
