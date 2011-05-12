package com.eofstudio.hydra.core.Standard;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.eofstudio.hydra.commons.exceptions.ClassNotAHydraPlugin;
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
	
	@Override
	public void loadPluginsFromFile( URL path, String classname, String pluginID ) throws ClassNotFoundException, ClassNotAHydraPlugin, FileNotFoundException
	{
		if( !new File( path.getFile() ).exists() )
			throw new FileNotFoundException();
		
		ClassLoader classLoader = URLClassLoader.newInstance( new URL[]{path} );
		Class<?>    clazz       = Class.forName( classname, false, classLoader );
		
		if( !classIsPlugin( clazz ) )
			throw new ClassNotAHydraPlugin( String.format( "%1s isn't a valid hydra plugin", classname ) );
		
		// TODO: Add more settings to the plugin, how many are allowed to run at one time, etc.
		_InstalledPlugins.put( pluginID, new PluginSettings( clazz, pluginID ) );
	}
	
	/**
	 * Determines if the class is a Plugin
	 * @param clazz
	 * @return
	 */
	private boolean classIsPlugin( Class<?> clazz ) 
	{
		for( Class<?> interfaceType : clazz.getInterfaces() )
		{
			// TODO: See if there is a better way of checking if it implements the Interface or Abstract class
			if( interfaceType.getClass().getName() == "com.eofstudio.hydra.commons.plugin.IPlugin" )
				return true;
		}

		return ( (Class<?>) clazz.getGenericSuperclass()).getName() == "com.eofstudio.hydra.commons.plugin.APlugin";
	}

	@Override
	public String InstanciatePlugin( IPluginSettings settings ) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		IPlugin plugin     = (IPlugin) settings.getClassDefinition().newInstance();
		Random  random     = new Random();
		String  instanceID = Long.toString( random.nextLong() );
		
		synchronized( _PluginInstances ) 
		{
			_PluginInstances.put( instanceID, plugin );
		}
		
		return instanceID;
	}
}
