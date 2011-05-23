package com.eofstudio.hydra.core;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import com.eofstudio.hydra.commons.exceptions.ClassNotAHydraPlugin;
import com.eofstudio.hydra.commons.plugin.IPlugin;

public interface IPluginManager
{
	IPluginSettings getPluginSettings( long pluginID );
	IPlugin getPluginInstance( long instanceID );
	Iterator<IPluginSettings> getPluginSettings();
	Iterator<IPlugin> getPluginInstance(  );
	
	
	/**
	 * Loads all IPlugins from the Jar file
	 * @param path is path to the Jar file to look through
	 * @throws MalformedURLException 
	 * @throws ClassNotFoundException 
	 * @throws ClassNotAHydraPlugin 
	 * @throws FileNotFoundException 
	 */
	void loadPlugin( URL path, String classname, long pluginID ) throws ClassNotFoundException, ClassNotAHydraPlugin, FileNotFoundException;
	
	void loadPlugin( Class<?> plugin, long pluginID ) throws ClassNotAHydraPlugin;
	long instanciatePlugin( IPluginSettings settings ) throws ClassNotFoundException, InstantiationException, IllegalAccessException;
}
