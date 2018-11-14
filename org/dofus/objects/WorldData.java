package org.dofus.objects;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.mina.core.session.IoSession;

public class WorldData {

	/**
	 * XXX: Cette classe sert à stocké les connectés dans le game
	 */
	
	//Session By Account
	private static final ConcurrentMap<Accounts, IoSession> accountsBySession = new ConcurrentHashMap<Accounts, IoSession>();
	//By id
	private static final ConcurrentMap<Integer, Characters> characters = new ConcurrentHashMap<Integer, Characters>();
	//By name
	private static final ConcurrentMap<String, Characters> charactersByName = new ConcurrentHashMap<String, Characters>();
	
	public static ConcurrentMap<Accounts, IoSession> getSessionByAccount() {
		return accountsBySession;
	}
	
	public static void addSessionByAccount(Accounts account, IoSession session) {
		if(!accountsBySession.containsKey(account))
			accountsBySession.put(account, session);
	}
	
	public static void removeSessionByAccount(Accounts account) {
		if(accountsBySession.containsKey(account))
			accountsBySession.remove(account);
	}
	
	public static ConcurrentMap<Integer, Characters> getCharacters() {
		return characters;
	}
	
	public static void addCharacterById(Characters character, int id) {
		if(!characters.containsKey(id))
			characters.put(id, character);
	}
	
	public static void removeCharacterById(int id) {
		if(characters.containsKey(id))
			characters.remove(id);
	}
	
	public static ConcurrentMap<String, Characters> getCharacterByName() {
		return charactersByName;
	}
	
	public static void addCharacterByName(Characters character, String name) {
		if(!charactersByName.containsKey(name))
			charactersByName.put(name, character);
	}
	
	public static void removeCharacterByName(String name) {
		if(charactersByName.containsKey(name))
			charactersByName.remove(name);
	}
}
