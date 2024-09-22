package net.daniel.cmds.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.daniel.cmds.main.Lang;
import net.daniel.cmds.main.Main;

public class setHungerCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // 현재 포만감 지수보다 높게는 불가능

        if (!sender instanceof Player) {
            System.out.println(Lang.INGAME_ONLY.toString());
            return false;
        }

        (new BukkitRunnable() {
            public void run() {
                Player player = (Player) sender;

                double price = Main.getHungerPrice(player);

                if (args.length <= 0) {

                    sender.sendMessage(Lang.HUNGER_HELP.toString().replaceAll("%price%", price + ""));
                    return;
                }
                if (!Main.isNumber(args[0])) {
                    sender.sendMessage(Lang.HUNGER_OVERFLOW.toString());
                    return;
                }

                int level = Integer.parseInt(args[0]);

                if (!(level > 0 && level < 20)) {
                    sender.sendMessage(Lang.HUNGER_OVERFLOW.toString());
                    return;

                }
                int player_level = player.getFoodLevel();

                if (player_level <= level) {
                    sender.sendMessage(Lang.HUNGER_OVER.toString().replaceAll("%food_level%", player_level + ""));
                    return;


                }
                if (Main.Eco.getBalance(player) >= price) {

                    Main.Eco.withdrawPlayer(player, price);


                    player.setFoodLevel(level);
                    sender.sendMessage(Lang.SET_FOOD.toString().replaceAll("%level%", args[0]).replaceAll("%price%",
                            price + ""));

                } else {

                    double NoMoney_ = price - Main.Eco.getBalance(player);

                    sender.sendMessage(Lang.NO_MONEY.toString().replaceAll("%money_need%", NoMoney_ + "")
                            .replaceAll("%price%", price + ""));
                }
            }

        }).runTaskAsynchronously(Main.plugin);
        return true;

    }

}