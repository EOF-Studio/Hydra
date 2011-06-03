package com.eofstudio.hydra.commons.plugin.standard;

import com.eofstudio.hydra.commons.plugin.IPluginSettings;

public class PluginSettings implements IPluginSettings 
{
	private Class<?> _ClassDefinition;
	private String   _PluginID;
	private int      _MaxConnections;
	
	@Override
	public Class<?> getClassDefinition() { return _ClassDefinition; }

	@Override
	public String getPluginID() { return _PluginID; }
	
	@Override
	public int getMaxConnections() { return _MaxConnections; }

	@Override
	public void setMaxConnections( int maxConnections )	{ _MaxConnections = maxConnections;	}
	
	public PluginSettings( Class<?> classDefinition, String pluginID, int maxConnections ) 
	{
		_ClassDefinition = classDefinition;
		_PluginID        = pluginID;
		_MaxConnections  = maxConnections;
	}
	
	public String toString()
	{
		return String.format( "  %60s   %15d ", getPluginID(), getMaxConnections() ); 
	}
}
