package com.eofstudio.hydra.core;

import java.util.Collection;

import com.eofstudio.hydra.commons.plugin.IPlugin;
import com.eofstudio.hydra.commons.plugin.IPluginSettings;

public interface IPluginPool 
{
	boolean containsPluginDefinition( String classname );
	void registerPluginDefinition( IPluginSettings settings );
	Collection<IPlugin> getInstances();
	IPlugin getInstance( long instanceID );
	int getMaxSimultaniousInstances();
	
	/**
	 * Instanciate a plugin from plugin settings
	 * @param settings
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	long instanciatePlugin( String classname ) throws ClassNotFoundException, InstantiationException, IllegalAccessException;
}
