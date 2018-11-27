package org.dofus.constants;

public enum EConstants {

	/**
	 * Dofus constants
	 */
	
	//XXX: Default constants
	DEFAULT_LEVEL(1),
	DEFAULT_SIZE(100),
	
	//XXX: Alignement
	ALIGNEMENT_NEUTRAL(-1),
	ALIGNEMENT_ANGEL(1),
	ALIGNEMENT_DEMON(2),
	ALIGNEMENT_MERCENARY(3),
	
	//XXX: Max length and delay
	MAX_MESSAGE_LENGTH(200),
	SMILEY_DELAY(3000),
	
	//XXX: Server constants
	DOFUS_VERSION("1.29.1"),
	MAX_PLAYER_ON_SERVER(5),
	
	//XXX: Game constants
	NOVICE_LEVEL(5),
	ENERGY_MAX(10000),
	PLAYER_LEVEL_FOR_BOOST_SPELL_LEVEL_6(100),
	SPELL_BOOST_MAX_LEVEL(6),
	SPELL_BOOST_BONUS(5),
	MAX_PLAYERS_IN_TEAM(8),
	MAX_PLAYERS_IN_CHALLENGE(16),
	MEMBERS_COUNT_IN_PARTY(8),
	
	//XXX: Effects (A revoir je crois pas que tout est bon)
	ELEMENT_NULL(-1),
	ELEMENT_NEUTRAL(0),
	ELEMENT_STRENGTH(1),
	ELEMENT_CHANCE(2),
	ELEMENT_INTELLIGENCE(3),
	ELEMENT_AGILITY(4),
	
	ADD_AP(111),
	REMOVE_AP_DODGE(101),
	
	ADD_LIFE(110),
	
	ADD_DAMAGE(112),
	MULTIPLY_DAMAGE(114),
	
	ADD_CRITICAL_HIT(115),
	REDUCE_RANGE(116),
	ADD_RANGE(117),
	ADD_STRENGTH(118),
	ADD_AGILITY(119),
	ADD_AP_BIS(120),
	ADD_CRITICAL_FAILURE(122),
	ADD_CHANCE(123),
	ADD_WISDOM(124),
	ADD_VITALITY(125),
	ADD_INTELLIGENCE(126),
	REMOVE_MP_DODGE(127),
	ADD_MP(128),
	
	ADD_DAMAGE_PERCENT(138),
	
	ADD_PHYSICAL_DAMAGE(142),
	REMOVE_PHYSICAL_DAMAGE(145),
	
	REMOVE_CHANCE(152),
	REMOVE_VITALITY(153),
	REMOVE_AGILITY(154),
	REMOVE_INTELLIGENCE(155),
	REMOVE_WISDOM(156),
	REMOVE_STRENGTH(157),
	ADD_WEIGHT(158),
	REMOVE_WEIGHT(159),
	ADD_DODGE_AP(160),
	ADD_DODGE_MP(161),
	REMOVE_DODGE_AP(162),
	REMOVE_DODGE_MP(163),

	REMOVE_AP(168),
	REMOVE_MP(169),

	REMOVE_CRITICAL_HIT(171),

	ADD_INITIATIVE(174),
	REMOVE_INITIATIVE(175),
	ADD_PROSPECTION(176),
	REMOVE_PROSPECTION(177),
	ADD_HEAL(178),
	REMOVE_HEAL(179),

	ADD_SUMMONS(182),

	ADD_REDUCE_DAMAGE_PER_STRENGTH(210),
	ADD_REDUCE_DAMAGE_PER_CHANCE(211),
	ADD_REDUCE_DAMAGE_PER_AGILITY(212),
	ADD_REDUCE_DAMAGE_PER_INTELLIGENCE(213),
	ADD_REDUCE_DAMAGE_PER_NEUTRAL(214),
	
	REMOVE_REDUCE_DAMAGE_PER_STRENGTH(215),
	REMOVE_REDUCE_DAMAGE_PER_WATER(216),
	REMOVE_REDUCE_DAMAGE_PER_AGILITY(217),
	REMOVE_REDUCE_DAMAGE_PER_INTELLIGENCE(218),
	REMOVE_REDUCE_DAMAGE_PER_NEUTRAL(219),
	
	STATS_RETDOM(220), //?

	ADD_TRAP_DAMAGE_PERCENT(225),
	STATS_TRAPPER(226), //?

	ADD_REDUCE_DAMAGE_STRENGTH(240),
	ADD_REDUCE_DAMAGE_CHANCE(241),
	ADD_REDUCE_DAMAGE_AGILITY(242),
	ADD_REDUCE_DAMAGE_INTELLIGENCE(243),
	ADD_REDUCE_DAMAGE_NEUTRAL(244),
	
	REMOVE_REDUCE_DAMAGE_STRENGTH(245),
	REMOVE_REDUCE_DAMAGE_CHANCE(246),
	REMOVE_REDUCE_DAMAGE_AGILITY(247),
	REMOVE_REDUCE_DAMAGE_INTELLIGENCE(248),
	REMOVE_REDUCE_DAMAGE_NEUTRAL(249),
	
	ADD_REDUCE_DAMAGE_PER_PVP_STRENGTH(250),
	ADD_REDUCE_DAMAGE_PER_PVP_CHANCE(251),
	ADD_REDUCE_DAMAGE_PER_PVP_AGILITY(252),
	ADD_REDUCE_DAMAGE_PER_PVP_INTELLIGENCE(253),
	ADD_REDUCE_DAMAGE_PER_PVP_NEUTRAL(254),
	
	REMOVE_REDUCE_DAMAGE_PER_PVP_CHANCE(255),
	REMOVE_REDUCE_DAMAGE_PER_PVP_STRENGTH(256),
	REMOVE_REDUCE_DAMAGE_PER_PVP_AGILITY(257),
	REMOVE_REDUCE_DAMAGE_PER_PVP_INTELLIGENCE(258),
	REMOVE_REDUCE_DAMAGE_PER_PVP_NEUTRAL(259),
	
	ADD_REDUCE_DAMAGE_PVP_STRENGTH(260),
	ADD_REDUCE_DAMAGE_PVP_CHANCE(261),
	ADD_REDUCE_DAMAGE_PVP_AGILITY(262),
	ADD_REDUCE_DAMAGE_PVP_INTELLIGENCE(263),
	ADD_REDUCE_DAMAGE_PVP_NEUTRAL(264);
	
	public static short[][] AMAKNA_ZAAPS ={{935,295},{528,156},{9454,268},{951,126},{1242,323},{164,193},{1158,340},{8037,249},
		{8437,310},{8088,223},{8125,358},{8163,207},{10643,269},{11170,326},{1841,150},{844,212},{11210,401},{4263,170},{3022,186},
		{6855,253},{6137,104},{3250,165},{4739,354},{5295,579},{8785,253},{7411,311}, {6954,238},{2191,200}};
	public static short[][] INCARNAM_ZAAPS	= {{10297,199},{10349,282},{10304,138},{10317,195,},{10114,254}};

	public static short[][] BONTA_ZAAPI = {{4271,420},{4174,348},{8758,657},{4299,599},{4180,672},{8759,527},{4183,398},{2221,247},
		{4308,457},{4217,473},{4098,528},{8757,540},{4223,279},{8760,360},{2214,548},{4179,297},{4229,217},{4232,506},{8478,413},{4238,354},
		{4263,134},{4216,668},{6159,253},{4172,448},{4247,251},{4272,641},{4250,168},{4178,267},{4106,304},{4181,723},{4259,136},{4090,694},
		{4262,346},{4287,131},{4300,455},{4240,449},{4218,230},{4074,142}};
	public static short[][] BRAKMAR_ZAAPI = {{6167,183},{4930,214},{4620,639},{4604,483},{4639,489},{4627,208},{4579,594},{8756,406},{5277,506},
		{5304,551},{5334,484},{4612,641},{4549,549},{4607,467},{8753,345},{4622,644},{4565,134},{4639,489},{4627,208},{5112,754},{4562,173},
		{8756,406},{8754,484},{5317,310},{5304,551},{4615,582},{5334,486},{4618,344},{4588,559},{8756,406},{8493,342},{4646,297},{8493,342},
		{5332,191},{8754,484},{8755,513},{5116,435},{4601,507},{4604,483},{4579,594},{4612,641},{4637,728},{4623,443},{4551,254},{5295,468}};

	public short shortv;
	EConstants(short value) {
		this.shortv = value;
	}
	
	public int integer;
	EConstants(int value) {
		this.integer = value;
	}
	
	public String string;
	EConstants(String value) {
		this.string = value;
	}
	
	public short getShort() {
		return shortv;
	}
	
	public int getInt() {
		return integer;
	}
	
	public int getString() {
		return integer;
	}
}
