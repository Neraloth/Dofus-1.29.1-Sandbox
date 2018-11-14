package org.dofus.objects;

import java.util.HashMap;
import java.util.Map;

public class Characters {

	private int id;
	private Accounts owner;
	
	private String name;
	private short level;
	private byte breed, gender;
	private int color1, color2, color3;
	private short skin, size;
	
	private MapTemplate currentMap;
	private short currentCell;
	private OrientationEnum currentOrientation;
	
	private boolean connected = false;
	
	public Characters(int id, Accounts owner, String name, short level, byte breed, byte gender,
			int color1, int color2, int color3, short skin, short size, MapTemplate currentMap, short currentCell,
			OrientationEnum currentOrientation) {
		this.setId(id);
        this.setOwner(owner);
        this.setName(name);
        this.setLevel(level);
        this.setBreed(breed);
        this.setGender(gender);
        this.setColor1(color1);
        this.setColor2(color2);
        this.setColor3(color3);
        this.setSkin(skin);
        this.setSize(size);
        this.setCurrentMap(currentMap);
        this.setCurrentCell(currentCell);
        this.setCurrentOrientation(currentOrientation);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Accounts getOwner() {
		return owner;
	}

	public void setOwner(Accounts owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getLevel() {
		return level;
	}

	public void setLevel(short level) {
		this.level = level;
	}

	public byte getBreed() {
		return breed;
	}

	public void setBreed(byte breed) {
		this.breed = breed;
	}

	public byte getGender() {
		return gender;
	}

	public void setGender(byte gender) {
		this.gender = gender;
	}

	public int getColor1() {
		return color1;
	}

	public void setColor1(int color1) {
		this.color1 = color1;
	}

	public int getColor2() {
		return color2;
	}

	public void setColor2(int color2) {
		this.color2 = color2;
	}

	public int getColor3() {
		return color3;
	}

	public void setColor3(int color3) {
		this.color3 = color3;
	}

	public short getSkin() {
		return skin;
	}

	public void setSkin(short skin) {
		this.skin = skin;
	}

	public short getSize() {
		return size;
	}

	public void setSize(short size) {
		this.size = size;
	}

	public MapTemplate getCurrentMap() {
		return currentMap;
	}

	public void setCurrentMap(MapTemplate currentMap) {
		this.currentMap = currentMap;
	}

	public short getCurrentCell() {
		return currentCell;
	}

	public void setCurrentCell(short currentCell) {
		this.currentCell = currentCell;
	}

	public OrientationEnum getCurrentOrientation() {
		return currentOrientation;
	}

	public void setCurrentOrientation(OrientationEnum currentOrientation) {
		this.currentOrientation = currentOrientation;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	
	public enum OrientationEnum {
	    EAST,
	    SOUTH_EAST,
	    SOUTH,
	    SOUTH_WEST,
	    WEST,
	    NORTH_WEST,
	    NORTH,
	    NORTH_EAST;

	    private static final Map<Integer, OrientationEnum> values = new HashMap<>();

	    static {
	        for (OrientationEnum e : values()) {
	            values.put(e.ordinal(), e);
	        }
	    }

	    public static OrientationEnum valueOf(int ordinal) {
	        return values.get(ordinal);
	    }
	}

	//TODO: à mettre dans CharactersStats plus tard
    public static String statisticsMessage
    (long currentExperience, long minExp, long maxExp,
     int kamas,
     short statsPoints, short spellsPoints,
     int alignId, short alignLevel, short alignGrade, int honor, int dishonor, boolean pvpEnabled,
     short lifePoints, short maxLifePoints,
     short energy, short maxEnergy,
     short initiative,
     short prospection) {
    	
		StringBuilder sb = new StringBuilder(500).append("As");
		
		sb.append(currentExperience).append(',').append(minExp).append(',').append(maxExp).append('|');
		sb.append(kamas).append('|');
		sb.append(statsPoints).append('|').append(spellsPoints).append('|');
		sb.append(alignId).append('~').append(alignId).append(',')
		  .append(alignLevel).append(',')
		  .append(alignGrade).append(',')
		  .append(honor).append(',')
		  .append(dishonor).append(',')
		  .append(pvpEnabled ? "1|" : "0|");
		sb.append(lifePoints).append(',').append(maxLifePoints).append('|');
		sb.append(energy).append(',').append(maxEnergy).append('|');
		sb.append(initiative).append('|')
		  .append(prospection).append('|');
		
		sb.append(0).append(',') //get base
		  .append(0).append(',') //get equip
		  .append(0).append(',') //get gift
		  .append(0).append(',') // get context
		  .append(0).append('|'); //get safe total
		
		sb.append(0).append(',') //get base
		  .append(0).append(',') //get equip
		  .append(0).append(',') //get gift
		  .append(0).append(',') // get context
		  .append(0).append('|'); //get safe total
		
		/*for (int i = 4; i < characs.length; ++i){
		    ICharacteristic charac = statistics.get(characs[i]);
		
		    sb.append('|');
		    sb.append(charac.getBase()).append(',');
		    sb.append(charac.getEquipments()).append(',');
		    sb.append(charac.getGifts()).append(',');
		    sb.append(charac.getContext()).append(',');
		}*/
		
		return sb.toString();
	}
    
    public static String getStatisticsMessage(){
        return statisticsMessage(
                (long) 1, // Current exp
                (long) 1, //Mix exp
                (long) 1000, //Max exp
                1, //Kamas
                (short) 0,//stats point
                (short) 0, //spell point
                0, (short) 0, (short) 0, 0, 0, false, //todo alignment
                (short) 50,//get life
                (short) 50, //max life
                (short) 5000, //energy
                (short) 5000, //max energy
                (short) 100, //iniative
                (short) 0 //prospection
        );
    }
}
