package com.archyx.aureliumskills.skills.mining;

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
public class MiningLeveler extends SkillLeveler implements Listener {


	public MiningLeveler(AureliumSkills plugin) {
		super(plugin, Ability.MINER);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if (OptionL.isEnabled(Skills.MINING)) {
			Block block = event.getBlock();
			// Check block replace
			if (OptionL.getBoolean(Option.CHECK_BLOCK_REPLACE) && plugin.getRegionManager().isPlacedBlock(block)) {
				return;
			}

			Player player = event.getPlayer();
			if (blockXpGainLocation(block.getLocation(), player)) return;
			if (blockXpGainPlayer(player)) return;
			Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());

			// Search through sources until a match is found for the block broken
			for (MiningSource source : MiningSource.values()) {
				// Add XP to player if matched
				if (!source.isMatch(block)) continue;
				// Check silk touch
				if (source.requiresSilkTouch() && !hasSilkTouch(player)) {
					return;
				}
				event.setCancelled(true);
				double miningFortune = plugin.getPlayerManager().getPlayerData(player.getUniqueId()).getStatLevel(Stats.MINING_FORTUNE);
				//formula 100% + (farmingFortune * 1%)
				double multiplier = 1 + (miningFortune * 0.01);
				plugin.getLeveler().addXp(player, Skills.MINING, getAbilityXp(player, source));
				drops.forEach(itemStack -> {
					itemStack.setAmount((int) (itemStack.getAmount() * multiplier));
					player.getInventory().addItem(itemStack);
				});
				break; // Stop searching if matched
			}
			block.setType(Material.AIR);
			// Check custom blocks
			checkCustomBlocks(player, block, Skills.MINING);
		}
	}
}
