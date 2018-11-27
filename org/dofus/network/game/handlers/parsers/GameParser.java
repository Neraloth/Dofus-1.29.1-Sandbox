package org.dofus.network.game.handlers.parsers;

import org.dofus.game.actions.RolePlayMovement;
import org.dofus.network.game.GameClient;
import org.dofus.network.game.protocols.GProtocol;
import org.dofus.objects.WorldData;
import org.dofus.objects.actors.Characters;
import org.dofus.objects.characters.Statistic;
import org.dofus.objects.maps.MapTemplate;
import org.apache.mina.core.session.IoSession;
import org.dofus.game.actions.IGameAction.ActionTypeEnum;
import org.dofus.game.actions.IGameAction.GameActionType;

public class GameParser {

	public static void action(IoSession session, GameClient client, String packet) {
    	switch(ActionTypeEnum.valueOf(Integer.parseInt(packet.substring(2, 5)))){
        case MOVEMENT:
            if(client.isBusy())
                session.write("BN");
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
	}

	public static void creation(Characters character, IoSession session) {
		session.write("GCK|1|");
		session.write(Statistic.getStatisticsMessage(character));
    	session.write("GDM|"
    			+ character.getCurrentMap().getId() + "|"
    			+ character.getCurrentMap().getDate() + "|"
    			+ character.getCurrentMap().getKey() + "|");
    	session.write("fC" + 0); //nbr fight
	}

	public static void information(Characters character, IoSession session, GameClient client, MapTemplate map) {
		map.addActor(character);
        
        StringBuilder sb = new StringBuilder(10 + 30 * map.getActors().size()).append("GM");
    	
        for(Characters actor : map.getActors().values()) {
            sb.append("|+");
            GProtocol.getCharacterPattern(sb, actor);
        }
        
        for(Characters actors : map.getActors().values()) {
			IoSession actorSession = WorldData.getSessionByAccount().get(actors.getOwner());
			
			//Packet rollback if equals
			if(!actorSession.equals(client.getSession()))
				actorSession.write(sb.toString());
			else
				session.write(sb.toString());
		}
        
        session.write("GDK");
        session.write("fC" + 0); //nbr fight
	}

	public static void endAction(GameClient client, boolean success, String args) throws Exception {
        if(success)
            client.getActions().pop().end();
        else {
            if(client.getActions().peek().getActionType() != GameActionType.MOVEMENT)
                throw new Exception("invalid action : peeked action isn't a movement");

            ((RolePlayMovement) client.getActions().pop()).cancel(Short.parseShort(args.substring(2)));
        }
		
	}

}
