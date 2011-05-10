package com.eofstudio.hydra.Standard.test;

import java.util.Observable;
import java.util.Observer;

import com.eofstudio.hydra.core.IHydraPacket;

public class TestObserver implements Observer {

	private byte[] data = null;
	
	public byte[] getData() { return data; }
	
	@Override
	public void update( Observable o, Object arg )
	{
		IHydraPacket packet = ( IHydraPacket ) arg;
		
		data = packet.getCurrentBuffer();
	}
}
