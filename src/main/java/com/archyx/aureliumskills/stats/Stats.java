package com.archyx.aureliumskills.stats;

import com.archyx.aureliumskills.lang.Lang;
import com.archyx.aureliumskills.lang.StatMessage;

import java.util.Locale;

public enum Stats implements Stat {

	STRENGTH,
	HEALTH,
	REGENERATION,
	LUCK,
	WISDOM,
	TOUGHNESS,
	DAMAGE,
	DEFENSE,
	SPEED,
	ATTACK_SPEED,
	CRITICAL_CHANCE,
	CRITICAL_DAMAGE,
	MAGIC_FIND,
	FARMING_FORTUNE,
	MINING_FORTUNE,
	FORAGING_FORTUNE;

	@Override
	public String getDisplayName(Locale locale) {
		return Lang.getMessage(StatMessage.valueOf(this.name() + "_NAME"), locale);
	}

	@Override
	public String getColor(Locale locale) {
		return Lang.getMessage(StatMessage.valueOf(this.name() + "_COLOR"), locale);
	}

	@Override
	public String getSymbol(Locale locale) {
		return Lang.getMessage(StatMessage.valueOf(this.name() + "_SYMBOL"), locale);
	}

	@Override
	public String getDescription(Locale locale) {
		return Lang.getMessage(StatMessage.valueOf(this.name() + "_DESC"), locale);
	}

}
