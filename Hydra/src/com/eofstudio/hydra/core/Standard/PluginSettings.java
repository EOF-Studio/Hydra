package com.eofstudio.hydra.core.Standard;

import com.eofstudio.hydra.core.IPluginSettings;

public class PluginSettings implements IPluginSettings 
{
	private Class<?> _ClassDefinition;
	private long   _PluginID;
	
	@Override
	public Class<?> getClassDefinition() { return _ClassDefinition; }

	@Override
	public long getPluginID() { return _PluginID; }
	
	public PluginSettings( Class<?> classDefinition, long pluginID ) 
	{
		_ClassDefinition = classDefinition;
		_PluginID        = pluginID;
	}
}
