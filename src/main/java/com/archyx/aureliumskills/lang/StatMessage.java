package com.archyx.aureliumskills.lang;

import com.archyx.aureliumskills.stats.Stat;
import com.archyx.aureliumskills.stats.Stats;

import java.util.Locale;

public enum StatMessage implements MessageKey {
    
    STRENGTH_NAME,
    STRENGTH_DESC,
    STRENGTH_COLOR,
    STRENGTH_SYMBOL,
    HEALTH_NAME,
    HEALTH_DESC,
    HEALTH_COLOR,
    HEALTH_SYMBOL,
    REGENERATION_NAME,
    REGENERATION_DESC,
    REGENERATION_COLOR,
    REGENERATION_SYMBOL,
    LUCK_NAME,
    LUCK_DESC,
    LUCK_COLOR,
    LUCK_SYMBOL,
    WISDOM_NAME,
    WISDOM_DESC,
    WISDOM_COLOR,
    WISDOM_SYMBOL,
    TOUGHNESS_NAME,
    TOUGHNESS_DESC,
    TOUGHNESS_COLOR,
    TOUGHNESS_SYMBOL,
    DAMAGE_NAME,
    DAMAGE_DESC,
    DAMAGE_COLOR,
    DAMAGE_SYMBOL,
    DEFENSE_NAME,
    DEFENSE_DESC,
    DEFENSE_COLOR,
    DEFENSE_SYMBOL,
    SPEED_NAME,
    SPEED_DESC,
    SPEED_COLOR,
    SPEED_SYMBOL,
    ATTACK_SPEED_NAME,
    ATTACK_SPEED_DESC,
    ATTACK_SPEED_COLOR,
    ATTACK_SPEED_SYMBOL,
    CRITICAL_CHANCE_NAME,
    CRITICAL_CHANCE_DESC,
    CRITICAL_CHANCE_COLOR,
    CRITICAL_CHANCE_SYMBOL,
    CRITICAL_DAMAGE_NAME,
    CRITICAL_DAMAGE_DESC,
    CRITICAL_DAMAGE_COLOR,
    CRITICAL_DAMAGE_SYMBOL,
    MAGIC_FIND_NAME,
    MAGIC_FIND_DESC,
    MAGIC_FIND_COLOR,
    MAGIC_FIND_SYMBOL,
    FARMING_FORTUNE_NAME,
    FARMING_FORTUNE_DESC,
    FARMING_FORTUNE_COLOR,
    FARMING_FORTUNE_SYMBOL,
    MINING_FORTUNE_NAME,
    MINING_FORTUNE_DESC,
    MINING_FORTUNE_COLOR,
    MINING_FORTUNE_SYMBOL,
    FORAGING_FORTUNE_NAME,
    FORAGING_FORTUNE_DESC,
    FORAGING_FORTUNE_COLOR,
    FORAGING_FORTUNE_SYMBOL;
    
    private final Stat stat = Stats.valueOf(this.name().substring(0, this.name().lastIndexOf("_")));
    private final String path = "stats." + stat.toString().toLowerCase(Locale.ENGLISH) + "." + this.toString().substring(this.name().lastIndexOf("_") + 1).toLowerCase(Locale.ENGLISH);
    
    @Override
    public String getPath() {
        return path;
    }
}
