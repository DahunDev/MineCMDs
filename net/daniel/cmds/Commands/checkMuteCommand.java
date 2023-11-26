package net.daniel.cmds.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.daniel.cmds.main.Lang;
import net.daniel.cmds.main.Main;

public class checkMuteCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender.hasPermission("MineCMD.muteinfo")) {

			if (args.length != 1) {
				sender.sendMessage( Lang.MUTEINFO_HELP.toString().replaceAll("%cmd%", label)  );
				return true;
			} else {

				try {
					int sec = Main.getMuteTime(args[0]); // NPE 오류 가능

					if (sec == 0) {
						sender.sendMessage(Lang.PlAYERNOTMUTED.toString().replaceAll("%player%", args[0]));
						
						return true;
					}

					if (sec == -1) {
						sender.sendMessage(Lang.PlAYER_MUTE_INFO_INFITE.toString().replaceAll("%player%", args[0]));

						return true;
					}

					int min = sec / 60;
					sec = sec % 60;

					int hour = min / 60;
					min = min % 60;
					int day = hour / 24;
					hour = hour % 24;
					sender.sendMessage(Lang.PlAYER_MUTE_INFO.toString().replaceAll("%player%", args[0]).
							replaceAll("%day%", day+"").replaceAll("%hour%", hour+"").replaceAll("%min%", min+"").replaceAll("%sec%", sec+"")                   );

					
					return true;

				} catch (Exception e) {
					sender.sendMessage(Lang.PlAYERNOTPLAYED.toString().replaceAll("%player%", args[0]));
					return true;
				}

			}

		}
			
			sender.sendMessage(Lang.NO_PERM.toString());
			return true;
		
	
		


	}

}
