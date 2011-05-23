package com.eofstudio.hydra.core;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

import com.eofstudio.hydra.commons.exceptions.ClassNotAHydraPlugin;
import com.eofstudio.hydra.commons.plugin.IPlugin;

public interface IPluginManager
{
	IPluginSettings getPluginSettings( String pluginID );
	IPlugin getPluginInstance( String instanceID );
	
	
	/**
	 * Loads all IPlugins from the Jar file
	 * @param path is path to the Jar file to look through
	 * @throws MalformedURLException 
	 * @throws ClassNotFoundException 
	 * @throws ClassNotAHydraPlugin 
	 * @throws FileNotFoundException 
	 */
	void loadPlugin( URL path, String classname, String pluginID ) throws ClassNotFoundException, ClassNotAHydraPlugin, FileNotFoundException;
	
	void loadPlugin( Class<?> plugin, String pluginID ) throws ClassNotAHydraPlugin;
	String instanciatePlugin( IPluginSettings settings ) throws ClassNotFoundException, InstantiationException, IllegalAccessException;
}
