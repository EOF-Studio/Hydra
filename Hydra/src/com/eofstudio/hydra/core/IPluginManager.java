package com.eofstudio.hydra.core;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import com.eofstudio.hydra.commons.exceptions.ClassNotAHydraPluginException;
import com.eofstudio.hydra.commons.plugin.IPlugin;
import com.eofstudio.hydra.commons.plugin.IPluginSettings;

public interface IPluginManager
{
	IPluginSettings getPluginSettings( String pluginID );
	IPlugin getPluginInstance( long instanceID );
	Iterator<IPluginSettings> getPluginSettings();
	Iterator<IPluginPool> getPluginPools();

	/**
	 * Loads all IPlugins from the Jar file
	 * @param path is path to the Jar file to look through
	 * @throws MalformedURLException 
	 * @throws ClassNotFoundException 
	 * @throws ClassNotAHydraPluginException 
	 * @throws FileNotFoundException 
	 */
	void loadPlugin( URL path, String classname, String pluginID, int maxConnections ) throws ClassNotFoundException, ClassNotAHydraPluginException, FileNotFoundException;
	
	/**
	 * Load a particular plugin
	 * @param plugin, the type of the plugin to load
	 * @param pluginID, the ID of the plugin to load
	 * @throws ClassNotAHydraPluginException
	 */
	void loadPlugin( Class<?> plugin, String pluginID, int maxConnections ) throws ClassNotAHydraPluginException;
	
	/**
	 * Instanciate a plugin from plugin settings
	 * @param settings
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	long instanciatePlugin( IPluginSettings settings ) throws ClassNotFoundException, InstantiationException, IllegalAccessException;
}
