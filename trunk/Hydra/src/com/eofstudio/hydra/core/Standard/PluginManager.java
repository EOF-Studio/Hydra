package com.eofstudio.hydra.core.Standard;

import java.util.HashMap;
import java.util.Map;

import com.eofstudio.hydra.commons.plugin.IPlugin;
import com.eofstudio.hydra.core.IPluginManager;
import com.eofstudio.hydra.core.IPluginSettings;

public class PluginManager implements IPluginManager
{
	private Map<String,IPluginSettings> _InstalledPlugins = new HashMap<String, IPluginSettings>();
	private Map<String,IPlugin>         _PluginInstances  = new HashMap<String, IPlugin>();
	
	@Override
	public IPluginSettings getPluginSettings( String pluginID )
	{
		return _InstalledPlugins.get( pluginID );
	}
	@Override
	public IPlugin getPluginInstance( String instanceID )
	{
		return _PluginInstances.get( instanceID );
	}
	
	
}
