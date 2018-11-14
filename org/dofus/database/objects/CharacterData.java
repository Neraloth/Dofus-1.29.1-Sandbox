package org.dofus.database.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.dofus.database.Connector;
import org.dofus.objects.Accounts;
import org.dofus.objects.Characters;
import org.dofus.objects.Characters.OrientationEnum;
import org.dofus.objects.MapTemplate;
import org.dofus.objects.WorldData;

public class CharacterData {

	private static final Connection connection = Connector.getConnection();
	
	//By id
	private static final ConcurrentMap<Integer, Characters> characters = new ConcurrentHashMap<Integer, Characters>();

	public static void load(Accounts account) {
		try {
			ResultSet reader = connection
            		.createStatement()
            		.executeQuery("SELECT * FROM `characters` WHERE `owner` = '" + account.getId() + "';");
			
			Characters character;
			MapTemplate map;
			
			while(reader.next()) {
				map = MapsData.load(reader.getShort("currentMap"));
				
				character = new Characters(
						reader.getInt("id"), 
						account, 
						reader.getString("name"), 
						reader.getShort("level"),
						reader.getByte("breed"), 
						reader.getByte("gender"),
						reader.getInt("color1"), 
						reader.getInt("color2"), 
						reader.getInt("color3"), 
						reader.getShort("skin"), 
						reader.getShort("size"), 
						map, 
						reader.getShort("currentCell"), 
						OrientationEnum.SOUTH_EAST);
				
				
				
				characters.put(character.getId(), character);
				account.addCharacter(character);
				
				System.out.println("Character owner " + character.getOwner().getId() + " loaded with success");
			}
			
		} catch(SQLException e) {
			System.out.println("Impossible to load character : " + e.getMessage());
		}
	}
	
	public static void create(Characters character, int owner) throws SQLException {
		String query = "INSERT INTO `characters`(`id`,`owner`,`name`,`level`,`breed`,`gender`,`color1`,`color2`,`color3`," +
                "`skin`,`size`,`currentMap`,`currentCell`) " +
                "VALUES(?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?);";
		
		PreparedStatement statement = connection.prepareStatement(query);
		
		statement.setInt(1, character.getId());
		statement.setInt(2, owner);
		statement.setString(3, character.getName());
		statement.setShort(4, character.getLevel());
		statement.setByte(5, character.getBreed());
		statement.setByte(6, character.getGender());
		statement.setInt(7, character.getColor1());
		statement.setInt(8, character.getColor2());
		statement.setInt(9, character.getColor3());
		statement.setShort(10, character.getSkin());
		statement.setShort(11, character.getSize());
		statement.setInt(12, character.getCurrentMap().getId());
		statement.setShort(13, character.getCurrentCell());
		
		statement.execute();
		
		MapsData.load(character.getCurrentMap().getId());
		
		WorldData.addCharacterById(character, character.getId());
		
		characters.put(character.getId(), character);
		
		statement.clearParameters();
        statement.close();
	}
	
	public static void delete(Characters character) {
		String query = "DELETE FROM characters WHERE `id` = ?";
		
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, character.getId());
			//Items, mounts, guilds, house ?
			
			statement.execute();
			
			removeCharacter(character);
			
			statement.clearParameters();
	        statement.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean nicknameIsExist(String nickname) {
		try {
			ResultSet reader = connection
            		.createStatement()
            		.executeQuery("SELECT `name` FROM `characters` WHERE `name` LIKE '" + nickname + "';");
			
			while(reader.next()) {
				if(reader.getString("name").toLowerCase().equals(nickname.toLowerCase()))
					return true;
				else
					return false;
			}
		} catch(SQLException e) {
			System.out.println("Impossible to load accounts " + nickname + " : " + e.getMessage());
			return true;
		}
		return false;
	}
	
	public static Characters getCharacterById(int id) {
		if(!characters.containsKey(id))
			return null;
		return characters.get(id);
	}

	public static void removeCharacter(Characters character) {
		if(characters.containsKey(character.getId()))
			characters.remove(character.getId());
	}
}
