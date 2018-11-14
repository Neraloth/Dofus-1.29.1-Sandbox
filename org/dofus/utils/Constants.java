package org.dofus.utils;

import java.util.HashMap;
import java.util.Map;

public enum Constants {

	APPLICATION_VERSION("2.9.24-a"),
	DOFUS_VERSION("1.29.1"),
	SUBSCRIPTION_DURATION(3153600000000L);
	
	public String sValue;
	public Long lValue;
	
	Constants(String value) {
		this.sValue = value;
	}
	
	public String getsValue() {
		return sValue;
	}
	
	Constants(Long value) {
		this.lValue = value;
	}
	
	public Long getlValue() {
		return lValue;
	}
	
	public enum Community {

		FRENCH(0),
		ENGLISH(1),
		INTERNATIONALE(2),
		GERMAN(3),
		SPANISH(4),
		RUSSIAN(5),
		BRAZILIAN(6),
		DUTCH(7),
		ITALIAN(8),
		JAPANESE(10),
		DEBUG(99);
		
		private int value;
	    
	    Community(int value) {
	        this.value = value;
	    }
	    
	    public int value(){
	        return value;
	    }
		
		private static Map<Integer, Community> values = new HashMap<Integer, Community>();

	    static {
	        for(Community community : values())
	            values.put(community.value, community);
	    }

	    public static Community valueOf(Integer ordinal) {
	        return values.get(ordinal);
	    }
	}
	
	public enum Permission {

		BANNED(-1),
		NORMAL(0),
		SERVER(1);
		
		private int value;
	    
	    Permission(int value) {
	        this.value = value;
	    }
	    
	    public int value(){
	        return value;
	    }
	    
	    public boolean superior(Permission permissions) {
	        return this.value > permissions.value;
	    }
	    
	    public boolean inferior(Permission permissions) {
	        return this.value < permissions.value;
	    }
	    
	    public boolean equals(Permission permissions) {
	        return this.value == permissions.value;
	    }
	    
	    public boolean superiorOrEquals(Permission permissions) {
	        return this.value >= permissions.value;
	    }
	    
	    public boolean inferiorOrEquals(Permission permissions) {
	        return this.value <= permissions.value;
	    }

	    private static Map<Integer, Permission> values = new HashMap<Integer, Permission>();

	    static {
	        for(Permission permissions : values())
	            values.put(permissions.value, permissions);
	    }

	    public static Permission valueOf(Integer ordinal) {
	        return values.get(ordinal);
	    }
	}
	
	public static class ServerInformation {

		private int id = 1;
		private int requireSubscribe = 0;
		
		public enum Server {
			JIVA(1);
			
			Server(int value) {
				
			}
		}
		
		public enum States {
			OFFLINE(0),
			ONLINE(1),
			SAVING(2);
			
			States(int value) {
				
			}
		}
		
		public enum Population {
			RECOMMENDED(0),
			AVERAGE(1),
			HIGH(2),
			LOW(3),
			FULL(4);
			
			Population(int value) {
				
			}
		}
		
		public String toString() {
			return new StringBuilder()
					.append("AH")
					.append(getId()).append(";")
					.append(States.ONLINE.ordinal()).append(";")
					.append(Population.RECOMMENDED.ordinal()).append(";")
					.append(getRequireSubscribe())
						.toString();
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getRequireSubscribe() {
			return requireSubscribe;
		}

		public void setRequireSubscribe(int requireSubscribe) {
			this.requireSubscribe = requireSubscribe;
		}
	}
}
