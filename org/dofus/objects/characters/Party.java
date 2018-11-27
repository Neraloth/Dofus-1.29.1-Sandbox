package org.dofus.objects.characters;

import java.util.ArrayList;

import org.dofus.objects.actors.Characters;

public class Party {

	private Characters chief;
	//By id
	private ArrayList<Characters> members = new ArrayList<Characters>();
	
	public Party(Characters chief, Characters member) {
		setChief(chief);
		getMembers().add(chief);
		getMembers().add(member);
	}

	public Characters getChief() {
		return chief;
	}
	
	public void setChief(Characters chief) {
		this.chief = chief;
	}

	public boolean isChief(Characters character) {
		return chief == character;
	}
	
	public ArrayList<Characters> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<Characters> members) {
		this.members = members;
	}
	
	public void addMember(Characters character) {
		members.add(character.getId(), character);
	}
	
	public int getMembersSize() {
		return getMembers().size();
	}
	
	public int getMembersLevel() {
		int level = 0;
		for(Characters character : members)
			level += character.getExperience().getLevel();
		return level;
	}
	
	public void leave(Characters character) {
		character.setParty(null);
		members.remove(character);
		if(members.size() == 1) {
			members.get(0).setParty(null);
			members.get(0).getOwner().getSession().write("PV");
		} else 
			character.getOwner().getSession().write("PM-" + character.getId());
		
	}
}
