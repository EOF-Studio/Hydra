package com.eofstudio.hydra.commons.plugin;

import java.net.Socket;
import java.util.ArrayList;

public abstract class APlugin implements IPlugin 
{
	private ArrayList<Socket> _ActiveConnections;
	private int               _InstanceID;
	
	public APlugin( )
	{
		_ActiveConnections = new ArrayList<Socket>();
	}
	
	@Override
	public int getInstanceID() 
	{
		return _InstanceID;
	}

	@Override
	public void addConnection( Socket socket ) 
	{
		_ActiveConnections.add( socket );
	}
}
