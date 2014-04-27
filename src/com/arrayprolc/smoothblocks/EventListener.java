package com.arrayprolc.smoothblocks;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class EventListener implements Listener {

	public static Main plugin;
	public static boolean broadcasted;
	public ArrayList<Block> blocks = new ArrayList<Block>();

	public EventListener(Main instance) {
		plugin = instance;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		event.setCancelled(false);
		for (Block block : event.blockList()) {
			Block b = block;
			blocks.add(b);
			for (Player all : Bukkit.getOnlinePlayers()) {
				if (plugin.getConfig().getBoolean("do-particle-explosions")) {
					sendBlockBreakParticles(all, b.getType(), b.getLocation());
				}

			}
			if (plugin.getConfig().getBoolean("do-fallingsand-explosions")) {
				float x = -2 + (float) (Math.random() * ((2 - -2) + 1));
				float y = -3 + (float) (Math.random() * ((3 - -3) + 1));
				float z = (float) -2.5
						+ (float) (Math.random() * ((2.5 - -2.5) + 1));
				FallingBlock fall = b.getWorld().spawnFallingBlock(
						b.getLocation(), b.getType(), b.getData());
				float drop = 2 + (float) (Math.random() * ((0 - 1) + 1));
				boolean itemDrop = false;
				if ((int) drop == 1) {
					itemDrop = true;
				} else {
					itemDrop = false;
				}
				fall.setDropItem(false);

				fall.setVelocity(new Vector(x - 2, y - 2, z - 2));
			}
		}

	}

	@EventHandler
	public static void onClickEvent(InventoryClickEvent e) {
		try {
			Player p = ((OfflinePlayer) e).getPlayer();
			if (e.getSlot() == 5
					&& e.getCurrentItem().getType() == Material.PUMPKIN) {
				sendBlockBreakParticles(p, Material.PUMPKIN, p.getLocation());
			}
		} catch (Exception ex) {
		}
	}

	@SuppressWarnings("deprecation")
	public static void sendBlockBreakParticles(Player p, Material m,
			Location loc) {
		p.getWorld().playEffect(loc, Effect.STEP_SOUND, m.getId());
	}

	@EventHandler
	public void openMenu(PlayerInteractEvent event) {
		if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			if (event.getPlayer().getItemInHand().equals(Material.CARROT_STICK)) {
				event.getPlayer().performCommand("sb");
				event.getPlayer().sendMessage("debug");
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	@SuppressWarnings("deprecation")
	public void onBlockPlace(BlockPlaceEvent event) {
		Block b = event.getBlock();
		if (event.isCancelled()) {
			sendBlockBreakParticles(event.getPlayer(), b.getType(),
					b.getLocation());
		}
		if (plugin.Wireframe.contains(event.getPlayer().getName())) {
			Block block = event.getPlayer().getWorld()
					.getBlockAt(event.getBlock().getLocation());
			block.setTypeId(36);
		}
		if (event.getBlock().getLocation().subtract(0, 1, 0).getBlock()
				.getType() != Material.AIR) {
			return;
		}
		if (plugin.fall.contains(event.getPlayer().getName())) {
			event.getBlock()
					.getWorld()
					.spawnFallingBlock(event.getBlock().getLocation(),
							event.getBlock().getType(),
							event.getBlock().getData());
			event.getBlock().setType(Material.AIR);
		}

		if (plugin.ghost.contains(event.getPlayer().getName())) {
			if (event.getBlock().getLocation().subtract(0, 1, 0).getBlock()
					.getType() != Material.AIR) {
				event.getBlock().setType(Material.PISTON_MOVING_PIECE);

				event.getBlock()
						.getWorld()
						.spawnFallingBlock(event.getBlock().getLocation(),
								event.getBlock().getType(),
								event.getBlock().getData());
			} else {
				event.setCancelled(true);
				event.getPlayer()
						.sendMessage(
								"§eGhost > §7Ghost blocks cannot have air beneath them.");
			}

		} else {
		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (plugin.ghost.contains(event.getPlayer().getName())) {
			if (event.getBlock().getLocation().subtract(0, 1, 0).getBlock()
					.getType() != Material.AIR) {
				event.getBlock()
						.getWorld()
						.spawnFallingBlock(event.getBlock().getLocation(),
								event.getBlock().getType(),
								event.getBlock().getData());
				event.setCancelled(true);
				event.getBlock().setType(Material.PISTON_MOVING_PIECE);
			} else {
				event.setCancelled(true);
				event.getPlayer()
						.sendMessage(
								"§eGhost > §7Ghost blocks cannot have air beneath them.");
			}

		} else {
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void pickTool(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getInventory().getName().equals(plugin.inv.getName())) {
			event.setCancelled(true);
			player.updateInventory();
			if (event.getCurrentItem() == null) {
				return;
			}
			if (event.getCurrentItem().getType() == Material.SAND) {
				player.performCommand("fall");
				player.closeInventory();
			}
			if (event.getCurrentItem().getType() == Material.SNOW_BLOCK) {
				player.performCommand("ghost");
				player.closeInventory();
			}

			if (event.getCurrentItem().getType() == Material.GLASS) {
				player.performCommand("wireframe");
				player.closeInventory();
			}
		}

	}
}
