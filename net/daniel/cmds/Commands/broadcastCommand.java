package net.daniel.cmds.Commands;

import java.util.Date;
import java.util.regex.Matcher;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.earth2me.essentials.User;

import net.daniel.cmds.main.Lang;
import net.daniel.cmds.main.Main;
import net.daniel.cmds.main.Main.PlayerDataHolder;

public class broadcastCommand implements CommandExecutor {
	


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
			if (sender instanceof Player) {
				Player player = (Player) sender;
				(new BukkitRunnable() {
					public void run() {

				if (args.length > 0) {
							double price = Main.getBroadPrice(player);

							if (sender.hasPermission("MineCMD.broadcast")) {

								User excutor = Main.essentials.getUser(player);

								if (excutor.isMuted()) {

									sender.sendMessage(Lang.BROADCAST_MUTED.toString());

									return;
								} else { 

									if (Main.Eco.getBalance(player) >= price) {

										Long current = Long.valueOf(new Date().getTime());
										current = Long.valueOf(current.longValue() / 1000L);

										// ÄðÅ¸ÀÓ
										
			
										PlayerDataHolder pdata = (PlayerDataHolder) Main.getData().get(player.getUniqueId()+"");

										if (current.longValue() - pdata.lastUsedTime < Main.broad_Cooltime
												&& (!(sender.hasPermission("MineCMD.broad.ignorelimit")))) {

											long lefttime = Main.broad_Cooltime - (current.longValue() - pdata.lastUsedTime);

											sender.sendMessage(Lang.COOLTIME_BROAD.toString().replaceAll("%time_left%", lefttime + ""));

											return;
										} else {

											pdata.lastUsedTime = current.longValue();

											Main.Eco.withdrawPlayer(player, price);

											sender.sendMessage(Lang.CONSUMED.toString().replaceAll("%price%", price + ""));

											StringBuilder str = new StringBuilder();
											for (int i = 0; i < args.length; i++) {
												str.append(args[i] + " ");
											}

											String msg = ChatColor.translateAlternateColorCodes('&', str.toString());

											msg = Matcher.quoteReplacement(msg);
											String name = player.getName();

											name = Matcher.quoteReplacement(name);

											Main.broadcast(Lang.BROADCAST_CHAT.toString().replaceAll("%msg%", msg).
													replaceAll("%player%",
													name), player);
											return;

										}

									}

									else {

										double NoMoney_ = price - Main.Eco.getBalance(player);


										sender.sendMessage(Lang.NO_MONEY.toString().replaceAll("%money_need%", NoMoney_ + "")
												.replaceAll("%cooltime%", Main.broad_Cooltime + "")
												.replaceAll("%price%", price + ""));
										return;

									}

								}

							} else {

								sender.sendMessage(Lang.NO_PERM.toString());

							}
							
							
				} else {

					sender.sendMessage(Lang.BROADCAST_HELP.toString().replaceAll("%cooltime%", Main.broad_Cooltime + "")
							.replaceAll("%price%", Main.getBroadPrice(player) + ""));
					return;

				}

					}
				}).runTaskAsynchronously(Main.plugin);
				return true;
				
			} else {
				System.out.println(Lang.INGAME_ONLY.toString());

				return false;
			}
	}

}
