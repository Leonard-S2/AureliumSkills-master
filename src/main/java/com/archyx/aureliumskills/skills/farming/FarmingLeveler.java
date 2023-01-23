package com.archyx.aureliumskills.skills.farming;

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

@SuppressWarnings("DataFlowIssue")
public class FarmingLeveler extends SkillLeveler implements Listener {

	public FarmingLeveler(AureliumSkills plugin) {
		super(plugin, Ability.FARMER);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if (!OptionL.isEnabled(Skills.FARMING)) return;
		// Check cancelled
		Player player = event.getPlayer();
		if (blockXpGainLocation(event.getBlock().getLocation(), player)) return;
		Block block = event.getBlock();
		if (blockXpGainPlayer(player)) return;

		for (FarmingSource source : FarmingSource.values()) {
			if (!source.isMatch(block)) continue;
			// Check block replace
			if (source.shouldCheckBlockReplace() && OptionL.getBoolean(Option.CHECK_BLOCK_REPLACE) && plugin.getRegionManager().isPlacedBlock(block)){
				return;
			}
			event.setCancelled(true);

			double farmingFortune = plugin.getPlayerManager().getPlayerData(player.getUniqueId()).getStatLevel(Stats.FARMING_FORTUNE);
			//formula 100% + (farmingFortune * 1%)
			double multiplier = 1 + (farmingFortune * 0.01);
			plugin.getLeveler().addXp(player, Skills.FARMING, getAbilityXp(player, source));
			Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());
			drops.forEach(itemStack -> {
				itemStack.setAmount((int) (itemStack.getAmount() * multiplier));
				player.getInventory().addItem(itemStack);
			});
			break;
		}
		block.setType(Material.AIR);
		// Check custom blocks
		checkCustomBlocks(player, block, Skills.FARMING);
	}
}
