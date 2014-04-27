package com.arrayprolc.smoothblocks;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.arrayprolc.mb.HeadAdder;

public class Main extends JavaPlugin implements Listener {

	public final Logger logger = Logger.getLogger("Minecraft");
	public static Main plugin;
	public int countdown = 3;
	public static Inventory testInventory;
	public static Inventory crash;
	public final EventListener bl = new EventListener(this);
	public final LightCraftLogger blo = new LightCraftLogger(this);
	public boolean specialjump = true;
	public ArrayList<String> ghost = new ArrayList<String>();
	public ArrayList<String> helper = new ArrayList<String>();
	public ArrayList<String> fall = new ArrayList<String>();
	public ArrayList<String> Wireframe = new ArrayList<String>();
	static String pluginname = "Â§c[CRITICAL ERROR: COULD NOT FIND PLUGIN.YML! PLEASE CONTACT DEVELOPER (ARRAYPRO) IMMEDIATELY! THIS IS A SERIOUS ERROR.]";
	public int steps = 0;
	public static Logger log = Logger.getLogger("Minecraft");
	int num = 1;
	Inventory inv;
	static String version = "pre-release";

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " plugin disabled!");
	}

	@Override
	public void onEnable() {
		/*
		 * getServer().getScheduler().scheduleSyncRepeatingTask(this, new
		 * Runnable() {
		 * 
		 * @Override public void run() { Strings.shouldUpdate(); } }, 0, 10 *
		 * 20);
		 */

		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(bl, this);
		inv = Bukkit.createInventory(null, 9, "§9§l§nSmoothBlocks");

		inv.setItem(
				0,
				createItem(Material.SAND, 1, (short) 0, "§e§lFall Tool",
						"§7Toggle the fall tool."));
		inv.setItem(
				4,
				createItem(Material.SNOW_BLOCK, 1, (short) 0, "§e§lGhost tool",
						"§7Toggle the ghost tool."));
		inv.setItem(
				8,
				createItem(Material.GLASS, 1, (short) 0, "§e§lWireframe Tool",
						"§7Toggle the wireframe tool."));

		if (Grab.shouldLoad()) {
		}

		// inv.setItem(
		// 22,
		// createItem(Material.IRON_DOOR_BLOCK, 1, (short) 0, "§e§lHelp",
		// "§7Alternatively, type /sbh."));

		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		final Player player = (Player) sender;
		if (!Grab.shouldLoad()) {
			return false;
		}
		if (commandLabel.equalsIgnoreCase("fall") && player.isOp()) {
			if (!fall.contains(player.getName())) {
				fall.add(player.getName());
				player.sendMessage("§eFall > §7You have enabled the Fall tool.");
			} else {
				fall.remove(player.getName());
				player.sendMessage("§eFall > §7You have disabled the Fall tool.");
			}
		}

		if (commandLabel.equalsIgnoreCase("ghost") && player.isOp()) {
			if (!ghost.contains(player.getName())) {
				ghost.add(player.getName());
				player.sendMessage("§eGhost > §7You have enabled the Ghost tool.");
			} else {
				ghost.remove(player.getName());
				player.sendMessage("§eGhost > §7You have disabled the Ghost tool.");
			}
		}

		if (commandLabel.equalsIgnoreCase("Wireframe") && player.isOp()) {
			if (!Wireframe.contains(player.getName())) {
				Wireframe.add(player.getName());
				player.sendMessage("§eWireframe > §7You have enabled the Wireframe tool.");
			} else {
				Wireframe.remove(player.getName());
				player.sendMessage("§eWireframe > §7You have disabled the Wireframe tool.");
			}
		}
		if (commandLabel.equalsIgnoreCase("helper") && player.isOp()) {
			if (!helper.contains(player.getName())) {
				helper.add(player.getName());
				player.sendMessage("§eHelper > §7You have enabled the Helper tool.");
			} else {
				helper.remove(player.getName());
				player.sendMessage("§eHelper > §7You have disabled the Helper tool.");
			}
		}

		if (commandLabel.equalsIgnoreCase("sb")) {
			player.openInventory(inv);
		}
		if (commandLabel.equalsIgnoreCase("mblist")
				|| commandLabel.equalsIgnoreCase("mclist")) {
			player.sendMessage("Â§e" + HeadAdder.allNames()
					+ "or a player's name. Â§c(Caps matter!)");
		}
		if (commandLabel.equalsIgnoreCase("mbinfo")
				|| commandLabel.equalsIgnoreCase("mcinfo")) {
			if (args.length == 0) {
				player.sendMessage("Â§eInfo > Â§7" + pluginname
						+ "was coded by ArrayPro.");
				player.sendMessage("Â§eInfo > Â§7For help with " + pluginname
						+ ", please type /mbhelp.");

			} else {
				try {
					String key = args[1].toLowerCase();
					player.sendMessage("Â§e" + pluginname + " > Â§6" + key
							+ "Â§7 is Â§6" + HeadAdder.map.get(key)
							+ "'sÂ§7 head.");
				} catch (Exception e) {
					player.sendMessage("Â§e" + pluginname
							+ " > Â§7That head was not found!");
					player.sendMessage("Â§e" + pluginname
							+ " > Â§7For a list of heads, type /mblist");
				}
			}
		}
		if (commandLabel.equalsIgnoreCase("mb")
				|| commandLabel.equalsIgnoreCase("mini")) {
			if (!(player.getGameMode() == GameMode.CREATIVE)) {
				player.sendMessage("Â§e" + pluginname
						+ " > Â§7You must be in Creative mode!");
				return false;
			}
			if (args.length == 1) {
				try {
					HeadAdder.getHead(args[0], player);
				} catch (Exception e) {
					player.sendMessage("Â§e" + pluginname
							+ " > Â§7That head was not found!");
					player.sendMessage("Â§e" + pluginname
							+ " > Â§7For a list of heads, type /mblist");
				}
			} else {
				player.sendMessage("Â§e" + pluginname + " > Â§7/mb name");
				player.sendMessage("Â§e" + pluginname
						+ " > Â§7For a list of heads, type /mblist");
			}
		}
		return false;
	}

	public ItemStack createItem(Material material, int amount, short shrt,
			String displayname, String lore) {
		ItemStack item = new ItemStack(material, amount, shrt);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayname);
		ArrayList<String> Lore = new ArrayList<String>();
		Lore.add(lore);
		meta.setLore(Lore);

		item.setItemMeta(meta);
		return item;
	}

}
