package com.arrayprolc.mb;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	public final Logger logger = Logger.getLogger("Minecraft");
	public static Main plugin;
	public final EventListener bl = new EventListener(this);
	Boolean b = false;
	static String pluginname = "§c[CRITICAL ERROR: COULD NOT FIND PLUGIN.YML! PLEASE CONTACT DEVELOPER (ARRAYPRO) IMMEDIATELY! THIS IS A SERIOUS ERROR.]";

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " plugin disabled!");
	}

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(bl, this);
		HeadAdder.add();
		PluginDescriptionFile pdf = this.getDescription(); // Gets plugin.yml
		pluginname = pdf.getName(); // Gets the version
	}

	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		final Player player = (Player) sender;
		if (commandLabel.equalsIgnoreCase("mblist")
				|| commandLabel.equalsIgnoreCase("mclist")) {
			player.sendMessage("§e" + HeadAdder.allNames()
					+ "or a player's name. §c(Caps matter!)");
		}
		if (commandLabel.equalsIgnoreCase("mbinfo")
				|| commandLabel.equalsIgnoreCase("mcinfo")) {
			if (args.length == 0) {
				player.sendMessage("§eInfo > §7" + pluginname
						+ "was coded by ArrayPro.");
				player.sendMessage("§eInfo > §7For help with " + pluginname
						+ ", please type /mbhelp.");

			} else {
				try {
					String key = args[1].toLowerCase();
					player.sendMessage("§e" + pluginname + " > §6" + key
							+ "§7 is §6" + HeadAdder.map.get(key)
							+ "'s§7 head.");
				} catch (Exception e) {
					player.sendMessage("§e" + pluginname
							+ " > §7That head was not found!");
					player.sendMessage("§e" + pluginname
							+ " > §7For a list of heads, type /mblist");
				}
			}
		}
		if (commandLabel.equalsIgnoreCase("mb")
				|| commandLabel.equalsIgnoreCase("mini")) {
			if (!(player.getGameMode() == GameMode.CREATIVE)) {
				player.sendMessage("§e" + pluginname
						+ " > §7You must be in Creative mode!");
				return false;
			}
			if (args.length == 1) {
				try {
					HeadAdder.getHead(args[0], player);
				} catch (Exception e) {
					player.sendMessage("§e" + pluginname
							+ " > §7That head was not found!");
					player.sendMessage("§e" + pluginname
							+ " > §7For a list of heads, type /mblist");
				}
			} else {
				player.sendMessage("§e" + pluginname + " > §7/mb name");
				player.sendMessage("§e" + pluginname
						+ " > §7For a list of heads, type /mblist");
			}
		}
		return false;
	}

}
