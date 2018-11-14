package org.dofus.network.game.handlers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentMap;

import org.dofus.database.objects.*;
import org.dofus.network.game.Game;
import org.dofus.network.game.GameClient;
import org.dofus.network.game.GameClientHandler;
import org.dofus.objects.Accounts;
import org.dofus.objects.Characters;
import org.dofus.objects.Characters.OrientationEnum;
import org.dofus.objects.WorldData;
import org.dofus.utils.Constants;
import org.dofus.utils.StringUtils;

public class GameScreenHandler extends GameClientHandler {

	public boolean authenticated;
	
	public GameScreenHandler(Game game, GameClient client) {
		super(game, client);
	}

	@Override
	public void parse(String packet) throws Exception {
		if(packet.charAt(0) != 'A') // we want A... we have *...
            throw new Exception("bad data received !");
		
		if(authenticated) {
            String[] args;
            switch(packet.charAt(1)) {
	            case 'A': //Create player
	            	args = packet.substring(2).split("\\|");
	            	
	                if (args.length != 6)
	                    throw new Exception("Bad data received.");
	
	                parseCharacterCreationRequestMessage(
	                        args[0],
	                        Byte.parseByte(args[1]),
	                        Byte.parseByte(args[2]),
	                        Integer.parseInt(args[3]),
	                        Integer.parseInt(args[4]),
	                        Integer.parseInt(args[5])
	                );
	                break;
	              
	            case 'B': //TODO: Boost Stats player 
	            	
	                break;
	
	            case 'D': //Delete player
	            	args = packet.substring(2).split("\\|");
	            	
                    if(args.length != 1 && args.length != 2)
                        throw new Exception("Bad data received.");

                    parseCharacterDeletionRequestMessage(
                            Integer.parseInt(args[0]),
                            args.length > 1 ? args[1] : ""
                    );
	                break;
	                
	            case 'G': //TODO: Attribute gift .split("\\|")
	            	
	                break;
	                
	            case 'g': //TODO: Apply gift
	            	
	                break;
	                
	            case 'i': //FIXME: client id
	            	break;
	            	
	            case 'L': //Player list
	            	client.getSession().write(
	                		charactersListMessage(
	                				1, 
	                				Constants.SUBSCRIPTION_DURATION.lValue, 
	                				client.getAccount().getCharacters(),
	                				client.getAccount()));
	
	            	break;
	            	
	            case 'k': //FIXME: Set client key substring(2)
	            	break;
	            	
	            case 'P': //FIXME: Send random nickname make better
	            	client.getSession().write("APK" + StringUtils.randomPseudo());
	            	//Impossible to random a name : APE
	                break;
	                
	            case 'R': //FIXME Heroic : Revive character
	            	break;
	            	
	            case 'S': //Player selection
	                Characters character = client.getAccount().getCharacterById(Integer.parseInt(packet.substring(2)));
	                characterSelectionSucessMessage(character, client, game);
	                break;
	                
	            case 'V': //Community
	            	client.getSession().write("AV" + Constants.Community.FRENCH.value());
	                break;
	                
	        default:
	        	if(!packet.equals("Af"))
	        		System.out.println("Unknow paquet <" + packet + "> in ServerScreenHandler");
	        	client.getSession().write("BN");
            }
		} else { //If not connected !
			Accounts account = AccountData.getAccountByKey(packet.substring(2));
			
            if(account != null) {
                client.setAccount(account);
                client.getSession().write("ATK0");//Community
                authenticated = true;
            } else
                client.getSession().write("ATE");
		}
	}

	@Override
	public void onClosed() {
		System.out.println("GameScreenHandler : onClosed()");
	}

	private void parseCharacterCreationRequestMessage(String name, Byte breed, Byte gender,
            int color1, int color2, int color3) throws Exception {
		
		if(client.getAccount().getCharacters().size() >= 5) //Player per account
			client.getSession().write("AAEf");//Account full
		else if(CharacterData.nicknameIsExist(name))
			client.getSession().write("AAEa"); //name already exist
		else {
			client.getSession().write("TB");
			
			Characters character = 
					new Characters(
							(int) uniqueId(),
							client.getAccount(),
							name,
							(short) 1, //Level
							breed, 
							gender, 
							color1, 
							color2, 
							color3, 
							(short) ((breed * 10) + gender), //Skin
							(short) 100, //Size 
							MapsData.findById(7411), //map id 
							(short) 250,
							OrientationEnum.SOUTH_EAST);
			
			CharacterData.create(character, client.getAccount().getId());
			
			WorldData.addSessionByAccount(client.getAccount(), client.getSession());
			
			client.getAccount().addCharacter(character);
			client.setCharacters(character);
			
			client.getSession().write("AAK"); //creation success
			
			client.getSession().write( //FIXME: Really need it? 
            		charactersListMessage(
            				1, 
            				Constants.SUBSCRIPTION_DURATION.lValue, 
            				client.getAccount().getCharacters(),
            				client.getAccount()));
		}
	}
	
	private void parseCharacterDeletionRequestMessage(int id, String answer) {
		Characters character = CharacterData.getCharacterById(id);
		
		if(character == null)
			client.getSession().write("ADE");
		else if(!client.getAccount().getAnswer().equalsIgnoreCase(answer) && character.getLevel() > 49)
			client.getSession().write("ADE");
		else {
			CharacterData.delete(character);
			client.getAccount().removeCharacter(character);
			
			client.getSession().write(
            		charactersListMessage(
            				1, 
            				Constants.SUBSCRIPTION_DURATION.lValue, 
            				client.getAccount().getCharacters(),
            				client.getAccount()));
		}
	}
	
	public static String charactersListMessage(int serverId, long remainingSubscriptionTime, ConcurrentMap<Integer, Characters> characters, Accounts account) {
        StringBuilder sb = new StringBuilder(50).append("ALK");

        sb.append(remainingSubscriptionTime).append("|");
        sb.append(characters.size());

        for(Characters character : characters.values()) {
        	if(character.getOwner().getId() != account.getId())
        		continue;
        	
            sb.append("|");

            sb.append(character.getId()).append(";")
              .append(character.getName()).append(";")
              .append(character.getLevel()).append(";")
              .append(character.getSkin()).append(";")
              .append(StringUtils.toHexOrNegative(character.getColor1())).append(";")
              .append(StringUtils.toHexOrNegative(character.getColor2())).append(";")
              .append(StringUtils.toHexOrNegative(character.getColor3())).append(";");

            /*boolean first = true;
            for(int accessory : character.getAccessories()){
                if (first) first = false;
                else sb.append(',');
                sb.append(accessory == -1 ? "" : StringUtils.toHex(accessory));
            }*/
            sb.append(';');
            
            sb.append("0").append(';')//sb.append(character.isStoreActive() ? '1' : '0').append(';')
            
              .append(serverId).append(';')
              .append(';') // is dead ?  (heroic)
              .append(';') // nb deathes (heroic)
              .append(""); // level max
        }
        return sb.toString();
    }
	
	public static void characterSelectionSucessMessage(Characters character, GameClient client, Game game) {
		if(character == null)
        	client.getSession().write("ASE");
        else {
            
            client.getSession().write(characterSelectionSucessMessage(
            		character.getId(),
            		character.getName(),
                    character.getLevel(),
                    character.getBreed(),
                    character.getGender(),
                    character.getSkin(),
                    character.getColor1(),
                    character.getColor2(),
                    character.getColor3()
            ));

            client.getAccount().setConnected(true);
            
            client.setCharacters(character);
            client.getCharacter().setConnected(true);
            
            WorldData.addCharacterById(character, character.getId());
            WorldData.addCharacterByName(character, character.getName());
            WorldData.addSessionByAccount(client.getAccount(), client.getSession());
            
            client.setHandler(new RolePlayHandler(game, client));
        }
	}
	
    public static String characterSelectionSucessMessage(long id, String name, int level, int breedId, byte breed,
            short skin, int color1, int color2, int color3) {
    	StringBuilder sb = new StringBuilder().append("ASK|");

        sb.append(id).append('|');
        sb.append(name).append('|');
        sb.append(level).append('|');
        sb.append(breedId).append('|');
        sb.append(breed).append('|');
        sb.append(skin).append('|');
        sb.append(StringUtils.toHexOrNegative(color1)).append('|');
        sb.append(StringUtils.toHexOrNegative(color2)).append('|');
        sb.append(StringUtils.toHexOrNegative(color3)).append('|');
        //ItemGameMessageFormatter.formatItems(sb, items);

        return sb.toString();
    }
    
    public static long uniqueId() {
        return Long.parseLong(new SimpleDateFormat("ddhhmmssMs").format(new Date()));
    }
}
