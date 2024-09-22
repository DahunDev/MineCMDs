package net.daniel.cmds.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.daniel.cmds.main.Lang;
import net.daniel.cmds.main.Main;

public class FeedCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            System.out.println(Lang.INGAME_ONLY);
            return false;

        }

        (new BukkitRunnable() {
            public void run() {
                double price = Main.plugin.getFeedPrice(player);

                if (Main.Eco.getBalance(player) >= price) {

                    Main.Eco.withdrawPlayer(player, price);

                    player.setFoodLevel(20);

                    sender.sendMessage(Lang.DONE_FEED.toString().replaceAll("%price%", price + ""));

                } else {
                    double NoMoney_ = price - Main.Eco.getBalance(player);

                    sender.sendMessage(Lang.NO_MONEY.toString().replaceAll("%money_need%", NoMoney_ + ""));

                }

            }

        }).runTaskAsynchronously(Main.plugin);

        return true;


    }

}

