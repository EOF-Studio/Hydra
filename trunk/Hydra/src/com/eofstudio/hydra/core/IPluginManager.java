package com.eofstudio.hydra.core;

import com.eofstudio.hydra.commons.plugin.IPlugin;

public interface IPluginManager
{
	IPluginSettings getPluginSettings( String pluginID );
	IPlugin getPluginInstance( String instanceID );
}
