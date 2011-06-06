package com.eofstudio.hydra.commons.plugin.test;

import java.util.Observable;
import java.util.Observer;

public class APluginObserver implements Observer {

	public boolean wasUpdated = false;
	
	@Override
	public void update(Observable o, Object arg) {
		wasUpdated = true;
	}

}
