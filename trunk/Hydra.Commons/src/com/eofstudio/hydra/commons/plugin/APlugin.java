package com.eofstudio.hydra.commons.plugin;

import java.util.ArrayList;

public abstract class APlugin implements IPlugin 
{
	private ArrayList<IHydraPacket> _ActiveConnections;
	private int                     _InstanceID;
	
	public APlugin( )
	{
		_ActiveConnections = new ArrayList<IHydraPacket>();
	}
	
	protected ArrayList<IHydraPacket> getActiveConnections()
	{
		return _ActiveConnections;
	}
	
	@Override
	public int getInstanceID() 
	{
		return _InstanceID;
	}

	@Override
	public void addConnection( IHydraPacket packet ) 
	{
		_ActiveConnections.add( packet );
	}
}
