package com.eofstudio.hydra.core.Standard;

import com.eofstudio.hydra.core.IPluginSettings;

public class PluginSettings implements IPluginSettings 
{
	private Class<?> _ClassDefinition;
	private String   _PluginID;
	
	@Override
	public Class<?> getClassDefinition() { return _ClassDefinition; }

	@Override
	public String getPluginID() { return _PluginID; }
	
	public PluginSettings( Class<?> classDefinition, String pluginID ) 
	{
		_ClassDefinition = classDefinition;
		_PluginID        = pluginID;
	}
	
	public String toString()
	{
		return String.format( " %60s", getPluginID() ); 
	}
}
