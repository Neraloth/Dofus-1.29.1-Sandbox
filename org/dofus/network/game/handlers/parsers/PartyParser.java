package org.dofus.network.game.handlers.parsers;

import org.apache.mina.core.session.IoSession;
import org.dofus.objects.WorldData;
import org.dofus.objects.actors.Characters;
import org.dofus.objects.characters.Party;

public class PartyParser {

	public static void accept(Characters character, IoSession session) {
		Characters chief = WorldData.getCharacterByName().get(character.getInvitation());
		IoSession chiefSession = WorldData.getSessionByAccount().get(chief.getOwner());
		Party party = chief.getParty();
		
		if(party == null) { //Si le groupe n'existe pas (première invitation)
			party = new Party(chief, character);
			
			//On send les invitations
			session.write("PCK" + party.getChief().getName());
			session.write("PL" + party.getChief().getId());
			
			chiefSession.write("PCK" + party.getChief().getName());
			chiefSession.write("PL" + party.getChief().getId());
						
			StringBuilder sb = new StringBuilder(3 + 15 * party.getMembersSize());
			sb.append("PM+");
			
			boolean first = true;
			for(Characters player : party.getMembers()) {
				if(!first)
					sb.append("|");
				sb.append(player.parseParty());
				first = false;
			}
			
			character.setParty(party);
			session.write(sb.toString());
			
			chief.setParty(party);
			chiefSession.write(sb.toString());
		} else { //Il y a déjà des joueurs dans le groupe
			session.write("PCK" + party.getChief().getName());
			session.write("PL" + party.getChief().getId());
			
			System.out.println("Character = " + character.getName());
			System.out.println("Chief = " + chief.getName());
			
			character.setParty(party);
			party.addMember(character);
			
			for(Characters player : party.getMembers()) {
				IoSession players = WorldData.getSessionByAccount().get(player.getOwner());
				players.write("PM+" + player.parseParty());
			}
		}
		
		character.setParty(party);
		
		/*StringBuilder sb = new StringBuilder();
		sb.append("PM+");
		
		boolean first = true;
		for(Characters player : party.getMembers()) {
			if(!first)
				sb.append("|");
			sb.append(player.parseParty());
			first = false;
		}
		session.write(sb.toString());*/
		session.write("PR");
		chiefSession.write("PR");
	}

	public static void invitation(Characters character, IoSession session, String name) {
		Characters target = WorldData.getCharacterByName().get(name);
		
		if(!target.isConnected()) {
			session.write("PIEn" + name);
			return;
		} else if(target.getParty() != null) {
			session.write("PIEa" + name);
			return;
		} else if(character.getParty() != null && character.getParty().getMembersSize() == 8) {
			session.write("PIEf");
		}
		
		target.setInvitation(character.getName());
		character.setInvitation(target.getName());
		
		IoSession targetSession = WorldData.getSessionByAccount().get(target.getOwner());
    	
		targetSession.write("PIK" + character.getName() + "|" + target.getName());
		session.write("PIK" + character.getName() + "|" + target.getName());

	}

	public static void refuse(Characters character, IoSession session) {
		session.write("BN");
		Characters target2 = WorldData.getCharacterByName().get(character.getInvitation());
		character.setInvitation(null);
		target2.setInvitation(null);
		IoSession targetSession2 = WorldData.getSessionByAccount().get(target2.getOwner());
    	targetSession2.write("PR");
	}

	public static void leave(Characters character, IoSession session, String packet) {
		Party party2 = character.getParty();
		if(packet.length() == 2) { //Leave alone
			party2.leave(character); 
			session.write("PV");
		} else if(party2.isChief(character)) { //Kick
			Characters kick = WorldData.getCharacters().get(Integer.parseInt(packet.substring(2)));
			party2.leave(kick);
			IoSession targetSession4 = WorldData.getSessionByAccount().get(kick.getOwner());
			targetSession4.write("PV" + character.getId());
		}
	}

}
