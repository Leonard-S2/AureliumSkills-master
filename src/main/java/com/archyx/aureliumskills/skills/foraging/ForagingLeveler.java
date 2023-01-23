package com.archyx.aureliumskills.skills.foraging;

import com.archyx.aureliumskills.AureliumSkills;
import com.archyx.aureliumskills.ability.Ability;
import com.archyx.aureliumskills.configuration.Option;
import com.archyx.aureliumskills.configuration.OptionL;
import com.archyx.aureliumskills.leveler.SkillLeveler;
import com.archyx.aureliumskills.skills.Skills;
import com.archyx.aureliumskills.stats.Stats;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

@SuppressWarnings({"DataFlowIssue", "DuplicatedCode"})
public class ForagingLeveler extends SkillLeveler implements Listener{


	public ForagingLeveler(AureliumSkills plugin) {
		super(plugin, Ability.FORAGER);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if (!OptionL.isEnabled(Skills.FORAGING)) return;
		Block block = event.getBlock();
		// Check block replace
		if (OptionL.getBoolean(Option.CHECK_BLOCK_REPLACE) && plugin.getRegionManager().isPlacedBlock(block)) {
			return;
		}
		Player player = event.getPlayer();
		if (blockXpGainLocation(event.getBlock().getLocation(), player)) return;
		if (blockXpGainPlayer(player)) return;
		Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());
		// Search through sources until a match is found for the block broken
		for (ForagingSource source : ForagingSource.values()) {
			// Add XP to player if matched
			if (source.isMatch(block)) {
				event.setCancelled(true);
				double forgingFortune = plugin.getPlayerManager().getPlayerData(player.getUniqueId()).getStatLevel(Stats.FORAGING_FORTUNE);
				//formula 100% + (farmingFortune * 1%)
				double multiplier = 1 + (forgingFortune * 0.01);
				plugin.getLeveler().addXp(player, Skills.FORAGING, getAbilityXp(player, source));
				drops.forEach(itemStack -> {
					itemStack.setAmount((int) (itemStack.getAmount() * multiplier));
					player.getInventory().addItem(itemStack);
				});
				break; // Stop searching if matched
			}
		}
		block.setType(Material.AIR);
		// Check custom blocks
		checkCustomBlocks(player, block, Skills.FORAGING);
	}
}
