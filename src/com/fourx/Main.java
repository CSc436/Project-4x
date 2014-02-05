package com.fourx;

import com.fourx.research.TechnologyEnum;

public class Main {
	public static void main(String[] args) {
		Player p = new Player("BOB");
		String tech = TechnologyEnum.INFANTRYDAMAGE1.getValue().getName();
		p.research(tech);
		System.out.println(p.upgrades.mapping.get("INFANTRY").damage);
	}
}
