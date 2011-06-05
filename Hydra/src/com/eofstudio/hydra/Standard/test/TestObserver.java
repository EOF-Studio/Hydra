package com.eofstudio.hydra.Standard.test;

import java.util.Observable;
import java.util.Observer;

import com.eofstudio.hydra.commons.logging.HydraLog;
import com.eofstudio.hydra.commons.plugin.IHydraPacket;

public class TestObserver implements Observer 
{
	public IHydraPacket packet;
	
	@Override
	public void update( Observable o, Object arg )
	{
		HydraLog.Log.debug("TestObserver recieved notification");
		packet = ( IHydraPacket ) arg;
	}
}
