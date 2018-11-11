package org.dofus.database.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.dofus.database.Connector;
import org.dofus.objects.Accounts;

public class AccountData {

	/**
	 * XXX: Tous les lengths de la table accounts sont fixé sur 16
	 */
	
	private static final Connection connection = Connector.getConnection();
	
	//By id
	private static final ConcurrentMap<Integer, Accounts> accounts = new ConcurrentHashMap<Integer, Accounts>();
	//By username
	private static final ConcurrentMap<String, Accounts> accountsByUsername = new ConcurrentHashMap<String, Accounts>();

	public static Accounts load(String username) {
		if(!accountsByUsername.containsKey(username.toLowerCase())) { //Si le compte n'est pas dans le hashMap
			try {
				ResultSet reader = connection
	            		.createStatement()
	            		.executeQuery("SELECT * FROM `accounts` WHERE `username` = '" + username + "';");
				
				while(reader.next()) {
					Accounts account = new Accounts(
							reader.getInt("id"), 
							reader.getString("username").toLowerCase(), 
							reader.getString("password"), 
							reader.getString("question"), 
							reader.getString("answer"), 
							reader.getString("nickname"), 
							(reader.getInt("banned") == 1));
					
					accounts.put(account.getId(), account);
					accountsByUsername.put(account.getUsername(), account);
					
					System.out.println("Account " + username + " loaded with success");
				}
				
			} catch(SQLException e) {
				System.out.println("Impossible to load accounts " + username + " : " + e.getMessage());
				return null;
			}
		}
		return accountsByUsername.get(username);
	}
	
	public static void updateNickname(Accounts account) throws SQLException {
		if(account != null) {
			String query = "UPDATE `accounts` SET `nickname` = '" + account.getNickname() + "' WHERE `id` = " + account.getId() + ";";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.execute();
			
			statement.clearParameters();
	        statement.close();
		}
	}
	
	public static boolean nicknameIsExist(String nickname) {
		try {
			ResultSet reader = connection
            		.createStatement()
            		.executeQuery("SELECT `nickname` FROM `accounts` WHERE `nickname` LIKE '" + nickname + "';");
			
			while(reader.next()) {
				if(reader.getString("nickname").toLowerCase().equals(nickname.toLowerCase()))
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
	
	public static Accounts getAccountById(int id) {
		if(!accounts.containsKey(id))
			return null;
		return accounts.get(id);
	}
	
	public static Accounts getAccountByName(String username) {
		if(!accountsByUsername.containsKey(username))
			return null;
		return accountsByUsername.get(username);
	}
}
