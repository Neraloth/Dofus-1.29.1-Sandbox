package org.dofus.network.game.handlers;

import java.util.Date;
import java.util.Map;

import org.apache.mina.core.session.IoSession;
import org.dofus.database.objects.CharacterData;
import org.dofus.game.action.GameAction.ActionTypeEnum;
import org.dofus.game.action.GameAction.GameActionType;
import org.dofus.game.action.RolePlayMovement;
import org.dofus.network.game.Game;
import org.dofus.network.game.GameClient;
import org.dofus.network.game.GameClientHandler;
import org.dofus.objects.Characters;
import org.dofus.objects.MapTemplate;
import org.dofus.objects.WorldData;
import org.dofus.utils.StringUtils;

public class RolePlayHandler extends GameClientHandler {

	protected RolePlayHandler(Game game, GameClient client) {
		super(game, client);
		client.getSession().write("cC+i*#$p%!@"); // TODO Channels
		//Guild
		//Subarea
		//Spell
		client.getSession().write("SL"); //Spell list message
		client.getSession().write("AR6bk"); //TODO 6bk base 36
		client.getSession().write("Ow0|1000"); //"Ow" + usedPods + "|" + maxPods;
		client.getSession().write("eL0|"); //Quest?
		client.getSession().write("BD" + StringUtils.CURRENT_DATE_FORMATTER.format(new Date())); // Send actual time
		client.getSession().write("Im153;127.0.0.1"); //Address
		client.getSession().write("Im189"); //Hello message
		//LastCo and last address here TODO
		//Gestion de la nuit
		//Regen life
	}

	@Override
	public void parse(String packet) throws Exception {
        String[] args;
        switch(packet.charAt(0)) {
			case 'B':
				parseBasicsPacket(packet);
			break;/*
			case 'C':
				parseConquestPacket(packet);
			break;*/
			case 'c':
				parseChannelPacket(packet);
			break;
			/*case 'D':
				parseDialogPacket(packet);
			break;		
			case 'E':
				parseExchangePacket(packet);
			break;
			case 'e':
				parseEnvironementPacket(packet);
			break;
			case 'F':
				parseFriendPacket(packet);
			break;
			case 'f':
				parseFightPacket(packet);
			break;*/
			case 'G':
				parseGamePacket(packet);
			break;
			/*case 'g':
				parseGuildPacket(packet);
			break;
			case 'O':
				parseObjectPacket(packet);
			break;
			case 'P':
				parsePartyPacket(packet);
			break;
			case 'Q':
				parseQuestPacket(packet);
			break;
			case 'R':
				parseMountPacket(packet);
			break;
			case 'S':
				parseSpellPacket(packet);
			break;
			case 'W':
				parseWaypointPacket(packet);
			break;*/
        }
	}

	private void parseBasicsPacket(String packet) {
		switch(packet.charAt(1)) {
			case 'a': //Movement by click-map
				switch(packet.charAt(2)) {
					case 'M':
						//FIXME: Restriction game master
						if(!packet.substring(3).equalsIgnoreCase("NaN")) {
							String[] data = packet.substring(3).split(",");
							//FIXME: Load map by X, Y coordinate
							int abscissa = Integer.parseInt(data[0]);
							int ordinate = Integer.parseInt(data[1]);
							//if(!map == null) teleport
						}
						break;
				}
				break;
			case 'A': //Console
				
				break;
			case 'D': //Date
				
				break;
			case 'M': //Message
				/**
				 * TODO: Player muted send BN, correct message etc...
				 * Level 10 if i remember to speak on sell/recruit
				 * XXX: Special packet for fight
				 */
				packet.replace("<", ""); packet.replace(">", "");
				if(packet.length() == 3) return;
				
				StringBuilder message = new StringBuilder().append(packet.split("\\|", 2) [1]);
				
				switch(packet.charAt(2)) {
					case '*': //Default
						//if(!chr.containChannel(pck.charat(2)) return
						for(Characters actor : client.getCharacter().getCurrentMap().getActors().values()) {
				        	IoSession actorSession = WorldData.getSessionByAccount().get(actor.getOwner());
				        	actorSession.write("cMK|" + client.getCharacter().getId() + "|" + client.getCharacter().getName() + "|" + message.toString());
				       	}
						break;
					case '#': //TODO Fight channel
						break;
					case ':': //Sell channel TODO make better no-flood
						for(Characters actor : WorldData.getCharacters().values()) {
							IoSession actorSession = WorldData.getSessionByAccount().get(actor.getOwner());
				        	actorSession.write("cMK:|" + client.getCharacter().getId() + "|" + client.getCharacter().getName() + "|" + message.toString());
						}
						break;
					case '@': //Administration TODO game master
						for(Characters actor : WorldData.getCharacters().values()) {
							IoSession actorSession = WorldData.getSessionByAccount().get(actor.getOwner());
				        	actorSession.write("cMK@|" + client.getCharacter().getId() + "|" + client.getCharacter().getName() + "|" + message.toString());
						}
						break;
					case '?': //Recruit channel
						for(Characters actor : WorldData.getCharacters().values()) {
							IoSession actorSession = WorldData.getSessionByAccount().get(actor.getOwner());
				        	actorSession.write("cMK?|" + client.getCharacter().getId() + "|" + client.getCharacter().getName() + "|" + message.toString());
						}
						break;
					case '!': //Alignement channel TODO
						break;
					// case 'i' pour channel information mais il est en moins cC-i TODO
					default:
						String name = packet.substring(2).split("\\|") [0];
						
						Characters target = WorldData.getCharacterByName().get(name);
						IoSession session = WorldData.getSessionByAccount().get(target.getOwner());
						
						if(!target.isConnected())
							client.getSession().write("cMEf" + name);
						//Im114;target.getName() if away / invi
						
						client.getSession().write(
								"cMK" + "T|" + 
								target.getId() + "|" +
								target.getName() + "|" +
								message.toString());
						
						
						session.write(
								"cMK" + "F|" + 
							    client.getCharacter().getId() + "|" +
							    client.getCharacter().getName() + "|" +
							    message.toString());
				}
				break;
			case 'S': //Emote
				/**
				 * XXX If in fight we have special packet to send
				 */
				int id = Integer.parseInt(packet.substring(2));
		        for(Characters actor : client.getCharacter().getCurrentMap().getActors().values()) {
		        	IoSession actorSession = WorldData.getSessionByAccount().get(actor.getOwner());
		        	actorSession.write("cS" + client.getCharacter().getId() + "|" + id);
		       	}
				break;
			case 'Y': //Character state
				switch(packet.charAt(2)) {
					case 'A': //Away
						/** FIXME No-spam , Set chr away
						 * if away Im038(away = false) else Im037(away = true)
						 */
						break;
					case 'I': //Invisible
						/** FIXME Set chr invisible
						 * if invi Im051(invi = false) else Im050(invi = true)
						 */
						break;
				}
				break;
		}
	}

	private void parseChannelPacket(String packet) {
		switch(packet.charAt(1)) {
			case 'C': //Change channel
				String channel = Integer.toString(packet.charAt(3));
				switch(packet.charAt(2)) {
				case '+': //Add
					//FIXME chr.addChannel
					break;
				case '-': //Remove
					//FIXME chr.removeChannel
					break;
					//TODO: Make chr update
				}
				break;
			default:
				System.out.println("Unknow packet for parseChannelPacket " + packet);
				break;
		}
	}

	private void parseGamePacket(String packet) throws Exception {
        switch(packet.charAt(1)) {
        case 'A': //Action
        	switch(ActionTypeEnum.valueOf(Integer.parseInt(packet.substring(2, 5)))){
                case MOVEMENT:
                    if(client.isBusy())
                        client.getSession().write("BN");
                    else {
                        RolePlayMovement movement = new RolePlayMovement(packet.substring(5), client);
                        client.getActions().push(movement);
                        
                        movement.begin();
                    }
                    break;
    		default:
    			System.out.println("Unknow actionType for parseGamePacket " + packet);
    			break;
        	}
            break;
        case 'C': //GameCreation
        	parseGameCreationMessage(client);
            break;

        case 'I': //GameInformation
        	parseGameInformationMessage(client.getCharacter().getCurrentMap());
            break;

        case 'K': //End Action
            parseGameActionEndRequestMessage(packet.charAt(2) == 'K', packet.substring(3));
            break;
        }
	}

	@Override
	public void onClosed() {
		WorldData.removeCharacterById(client.getCharacter().getId());
		WorldData.removeCharacterByName(client.getCharacter().getName());
		WorldData.removeSessionByAccount(client.getAccount());
		CharacterData.removeCharacter(client.getCharacter());
		
		client.getCharacter().setConnected(false);
		client.getAccount().setConnected(false);
		System.out.println("RolePlayHandler : onClosed()");
	}

	private void parseGameCreationMessage(GameClient client) {
		client.getSession().write("GCK|1|");
    	client.getSession().write(Characters.getStatisticsMessage());
    	client.getSession().write("GDM|"
    			+ client.getCharacter().getCurrentMap().getId() + "|"
    			+ client.getCharacter().getCurrentMap().getDate() + "|"
    			+ client.getCharacter().getCurrentMap().getKey() + "|"
    			);
    	client.getSession().write("fC" + 0); //nbr fight
	}
	
	private void parseGameInformationMessage(MapTemplate map) {
		map.addActor(client.getCharacter());
        
        StringBuilder sb = new StringBuilder(10 + 30 * map.getActors().size()).append("GM");
    	
        for(Characters actor : map.getActors().values()) {
            sb.append("|+");
            getRolePlayCharacterTypePattern(sb, actor);
        }
        
        for(Characters actors : map.getActors().values()) {
			IoSession actorSession = WorldData.getSessionByAccount().get(actors.getOwner());
			
			//Packet rollback if equals
			if(!actorSession.equals(client.getSession()))
				actorSession.write(sb.toString());
			else
				client.getSession().write(sb.toString());
		}
        
        client.getSession().write("GDK");
        client.getSession().write("fC" + 0); //nbr fight
	}
	
    private void parseGameActionEndRequestMessage(boolean success, String args) throws Exception {
        if(success)
            client.getActions().pop().end();
        else {
            if(client.getActions().peek().getActionType() != GameActionType.MOVEMENT)
                throw new Exception("invalid action : peeked action isn't a movement");

            ((RolePlayMovement)client.getActions().pop()).cancel(Short.parseShort(args.substring(2)));
        }
    }
    
    public static String showActorsMessage(Map<Integer, Characters> character) {
        StringBuilder sb = new StringBuilder(10 + 30 * character.size()).append("GM");
        for(Characters actor : character.values()) {
            sb.append("|+");
            getRolePlayCharacterTypePattern(sb, actor);
        }
        return sb.toString();
    }
    
    public static void getRolePlayCharacterTypePattern(StringBuilder sb, Characters player){
        sb.append(player.getCurrentCell()).append(';')
          .append(player.getCurrentOrientation().ordinal()).append(';')
          .append("0;");

        sb.append(player.getId()).append(';')
          .append(player.getName()).append(';')
          .append(player.getBreed()).append(';')
          .append(player.getSkin()).append('^').append(player.getSize()).append(';')
          .append(player.getGender()).append(';');

        sb.append("0,0,0,0").append(';'); //TODO alignment

        sb.append(StringUtils.toHexOrNegative(player.getColor1())).append(';')
          .append(StringUtils.toHexOrNegative(player.getColor2())).append(';')
          .append(StringUtils.toHexOrNegative(player.getColor3())).append(';');

        /*boolean first = true;
        for (int accessory : player.getAccessories()){
            if (first) first = false;
            else sb.append(',');

            sb.append(accessory == 0 ? "" : StringUtil.toHex(accessory));
        }*/
        sb.append(';');

        sb.append(player.getLevel() >= 100 ? (player.getLevel() == 200 ? '2' : '1') : '0');

        sb.append(';')
          .append(';');

        sb.append(';');//Guild name

        sb.append(';')
          .append("0;;");
    }
}
