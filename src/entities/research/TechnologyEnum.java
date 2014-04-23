package entities.research;

public enum TechnologyEnum {
	INFANTRYDAMAGE(InfantryDamage.class), DISABLEDTECHNOLOGY(
			DisabledTechnology.class), RANGEDDAMAGE(RangedDamage.class), RANGEDLOS(
			RangedLOS.class), INFANTRYARMOR(InfantryArmor.class);

	private Class<? extends Technology> value;

	/**
	 * 
	 * @param tech
	 *            - Which class we would like to instantiate in the
	 *            TechnologyTree class. We cannot instantiate the class here,
	 *            because then we end up with only 1 instance of each
	 *            Technology, but would still like to define them in this class.
	 */
	private TechnologyEnum(Class<? extends Technology> tech) {
		value = tech;
	}

	public Class<? extends Technology> getValue() {
		return value;
	}
}
