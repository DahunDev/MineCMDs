
package net.daniel.cmds.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

import net.daniel.cmds.Commands.FeedCommand;
import net.daniel.cmds.Commands.HeadCommand;
import net.daniel.cmds.Commands.broadcastCommand;
import net.daniel.cmds.Commands.checkMuteCommand;
import net.daniel.cmds.Commands.setHungerCommand;
import net.daniel.cmds.Utils.FileUtils;
import net.daniel.cmds.Utils.ThrowsRunnable;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener {
	private final FileConfiguration langConfig = new YamlConfiguration();

	public final Logger logger;
	public static Command cmds;
	public static Economy Eco;
	public static Essentials essentials;

	public static int broad_Cooltime = 20;
	public static double broad_price = 2500.0;

	public static double feed_price = 7500.0;
	public static double set_hunger_price = 4500.0;
	public static boolean usePermPrice = true;

	public static boolean head_check_name = true;
	public static boolean excute_cmd_on_fail_givehead = true;

	public static List<Double> feed_price_group = new ArrayList<Double>();
	public static List<Double> broad_price_group = new ArrayList<Double>();
	public static List<Double> hunger_price_group = new ArrayList<Double>();

	public static List<String> feed_perm_group = new ArrayList<String>();
	public static List<String> broad_perm_group = new ArrayList<String>();
	public static List<String> hunger_perm_group = new ArrayList<String>();

	public static List<String> cmds_on_fail_givehead;

	private static String pluginName;
	private static HashMap<String, PlayerDataHolder> data = new HashMap<String, PlayerDataHolder>();
	public static Main plugin;

	public Main() {
		this.logger = Logger.getLogger("Minecraft");
		plugin = this;

	}

	@Override
	public void onDisable() {
		final PluginDescriptionFile pdFile = this.getDescription();
		data.clear();
		System.out.println(String.valueOf(pdFile.getName()) + pdFile.getVersion() + "이(가) 비활성화 되었습니다.");
	}

	@Override
	public void onEnable() {

		Plugin essentialsPlugin = Bukkit.getPluginManager().getPlugin("Essentials");

		Main.essentials = (Essentials) essentialsPlugin;
		this.reloadConfiguration();

		final PluginDescriptionFile pdFile = this.getDescription();
		Main.pluginName = pdFile.getName();
		if (!this.SetupEconomy()) {
			Bukkit.getConsoleSender().sendMessage("§6§l[ Mine CMD ] §c§lEconomy §f플러그인이 인식되지 않았으므로, 비활성화 됩니다.");
			this.getServer().getPluginManager().disablePlugin((Plugin) this);
			return;
		}

		this.getCommand("밥").setExecutor(new FeedCommand());
		this.getCommand("허기설정").setExecutor(new setHungerCommand());
		this.getCommand("확성기").setExecutor(new broadcastCommand());
		this.getCommand("head").setExecutor(new HeadCommand());
		this.getCommand("채팅금지조회").setExecutor(new checkMuteCommand());
		this.getCommand("채팅금지조회").setAliases(plugin.getCommand("채팅금지조회").getAliases());

		for (Player p : getServer().getOnlinePlayers()) {
			Main.getData().put(p.getUniqueId().toString(), new PlayerDataHolder());
		}

		getServer().getPluginManager().registerEvents(this, this);

		System.out.println(String.valueOf(pdFile.getFullName() + "이(가) 활성화 되었습니다."));

	}

	public static Main get() {
		return (Main) Bukkit.getPluginManager().getPlugin(pluginName);
	}

	public File createLangFile() {
		return new File(getDataFolder(), "lang.yml");
	}

	private void doInputOutput(ThrowsRunnable runnable, String errorMessage) {
		try {
			runnable.run();
		} catch (FileNotFoundException ex) {
			// Ignore
		} catch (Exception ex) {
			getLogger().log(Level.WARNING, errorMessage, ex);
		}
	}

	public FileConfiguration getLangConfig() {
		return langConfig;
	}

	public void loadConfigurations() {
		// saveResource("gui.yml", false);
		doInputOutput(() -> langConfig.load(createLangFile()), "Exception while lang load.");

	}

	public void saveConfigurations() {

		doInputOutput(() -> langConfig.save(FileUtils.writeEnsure(createLangFile())), "Exception when lang save.");
	}

	private boolean SetupEconomy() {
		if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
			Bukkit.getConsoleSender().sendMessage("§6§l[ Mine CMD ] §c§lVault §f플러그인이 인식되지 않았으므로, 서버가 종료 됩니다.");
			Bukkit.shutdown();
			return false;
		}
		Bukkit.getConsoleSender().sendMessage("§6§l[ Mine CMD ] §a§lVault §f플러그인이 인식 되었습니다.");
		RegisteredServiceProvider<Economy> EconomyProvider = this.getServer().getServicesManager()
				.getRegistration(Economy.class);
		if (EconomyProvider != null) {
			Eco = (Economy) EconomyProvider.getProvider();
		}
		if (Eco != null) {
			return true;
		}
		return false;
	}

	public static String getPerm(String arg) {

		String temp = arg.substring(0, arg.lastIndexOf(":"));
		return temp.substring(1, temp.length() - 1);

	}

	public static double getPrice(String arg) {

		String[] temp = arg.split(":");

		return Double.parseDouble(temp[temp.length - 1]);

	}

	public File createConfigFile() {
		return new File(getDataFolder(), "config.yml");
	}

	public void reloadConfiguration() {
		PluginDescriptionFile pdFile = this.getDescription();
		File config = new File("plugins/" + pdFile.getName() + "/config.yml");
		if (config.exists()) {
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(config);
			this.saveDefaultConfig();
			for (String key : cfg.getConfigurationSection("").getKeys(true)) {
				if (!this.getConfig().contains(key)) {
					this.getConfig().set(key, cfg.get(key));
				}
			}
		} else {
			this.saveDefaultConfig();
		}
		this.reloadConfig();

		Main.usePermPrice = this.getConfig().getBoolean("usePermPrice");
		Main.feed_price = this.getConfig().getDouble("feed_price");
		Main.broad_Cooltime = this.getConfig().getInt("broad_Cooltime");
		Main.broad_price = this.getConfig().getDouble("broad_price");
		Main.set_hunger_price = this.getConfig().getDouble("set_hunger_price");
		Main.head_check_name = this.getConfig().getBoolean("head.check_Name_exist");
		Main.excute_cmd_on_fail_givehead = this.getConfig().getBoolean("head.excute_cmd_head.on_fail_givehead");
		List<String> feed_priceConfig = this.getConfig().getStringList("Price-by-Permission.밥");
		List<String> broad_priceConfig = this.getConfig().getStringList("Price-by-Permission.확성기");
		List<String> hunger_priceConfig = this.getConfig().getStringList("Price-by-Permission.허기설정");
		Main.cmds_on_fail_givehead = this.getConfig().getStringList("head.fail_CMD");

		for (String i : feed_priceConfig) {
			Main.feed_perm_group.add(getPerm(i));
			Main.feed_price_group.add(getPrice(i));

		}

		for (String i : broad_priceConfig) {
			Main.broad_perm_group.add(getPerm(i));
			Main.broad_price_group.add(getPrice(i));

		}

		for (String i : hunger_priceConfig) {

			Main.hunger_perm_group.add(getPerm(i));
			Main.hunger_price_group.add(getPrice(i));

		}

		loadConfigurations();
		Lang.init(langConfig);
		saveConfigurations();

	}

	public static boolean isNumber(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	public static void broadcast(String msg, Player sender) {

		Set<Player> outList = new HashSet<>();

		for (Player player : Bukkit.getOnlinePlayers()) {
			final User onlineUser = essentials.getUser(player);
			final User send = essentials.getUser(sender);

			if (!(onlineUser.isIgnoredPlayer(send))) {
				outList.add(player);
			}
		}

		for (Player onlinePlayer : outList) {

			onlinePlayer.sendMessage(msg);
		}
		System.out.println(ChatColor.stripColor(msg));
	}

	// get remaining mute time

	public static int getMuteTime(String arg) throws NullPointerException {
		User check = essentials.getUser(arg);
		long when = 0;

		if (check.getMuted()) {
			when = check.getMuteTimeout();
			if (when == 0) {
				return -1;
			}
		} else {
			return 0;
		}

		long remaining = when - System.currentTimeMillis();
		if (remaining < 0)
			remaining = 0;
		int seconds = (int) TimeUnit.SECONDS.convert(remaining, TimeUnit.MILLISECONDS);

		return seconds;

	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void JoinEvent(PlayerLoginEvent e) {
		Main.getData().put(e.getPlayer().getUniqueId().toString(), new PlayerDataHolder());
	}

	public static double getBroadPrice(Player player) {

		if (usePermPrice) {

			for (int i = broad_perm_group.size() - 1; i >= 0; i--) {

				if (player.hasPermission(broad_perm_group.get(i))) {
					return broad_price_group.get(i);

				}
			}

			return broad_price;

		} else {
			return broad_price;
		}
	}

	public double getFeedPrice(Player player) {

		if (usePermPrice) {

			for (int i = feed_perm_group.size() - 1; i >= 0; i--) {

				if (player.hasPermission(feed_perm_group.get(i))) {
					return feed_price_group.get(i);

				}
			}

			return feed_price;

		} else {
			return feed_price;
		}
	}

	public static double getHungerPrice(Player player) {

		if (usePermPrice) {

			for (int i = hunger_perm_group.size() - 1; i >= 0; i--) {

				if (player.hasPermission(hunger_perm_group.get(i))) {
					return hunger_price_group.get(i);

				}
			}

			return set_hunger_price;

		} else {

			return set_hunger_price;

		}
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String commandLabel,
			final String[] args) {

		if (commandLabel.equalsIgnoreCase("minecmd")) {
			if (sender.hasPermission("MineCMD.reload")) {
				if (args.length == 0) {

					sender.sendMessage("§b§l[ §f§lMine CMD §b§l] §e/minecmd reload §f: 플러그인 설정 리로드");
					return true;

				} else {
					if (args[0].equalsIgnoreCase("reload")) {

						(new BukkitRunnable() {
							public void run() {

								reloadConfiguration();

							}

						}).runTaskLaterAsynchronously(this, 0L);

						sender.sendMessage("§b§l[ §f§lMine CMD §b§l] §f플러그인 설정 리로드 완료");
						return true;

					} else {

						sender.sendMessage("§b§l[ §f§lMine CMD §b§l] §e/minecmd reload §f: 플러그인 설정 리로드");
						return true;

					}

				}

			} else {

				sender.sendMessage("§b§l[ §f§lServer §b§l] §c권한이 없습니다. 필요한 권한: MineCMD.reload");
				return true;

			}
		}

		return false;

	}

	public static HashMap<String, PlayerDataHolder> getData() {
		return data;
	}

	public void setData(HashMap<String, PlayerDataHolder> data) {
		Main.data = data;
	}

	public class PlayerDataHolder {
		public long lastUsedTime = 0L;

		PlayerDataHolder() { // default Constructor
		}

		PlayerDataHolder(long lastUsedTime) {
			this.lastUsedTime = lastUsedTime;
		}

	}

}
