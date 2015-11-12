package components;

import com.badlogic.ashley.core.Component;

public class OutOfBoundsComponent implements Component {
	
	public AdequateAction action;
	
	public enum AdequateAction{
		NONE, RESPAWN, ALERT, DISPOSE;
	}

}
