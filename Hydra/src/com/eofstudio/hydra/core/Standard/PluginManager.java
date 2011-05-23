package com.eofstudio.hydra.core.Standard;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import com.eofstudio.hydra.commons.exceptions.ClassNotAHydraPlugin;
import com.eofstudio.hydra.commons.plugin.IPlugin;
import com.eofstudio.hydra.core.IPluginManager;
import com.eofstudio.hydra.core.IPluginSettings;

public class PluginManager implements IPluginManager
{
	private Map<Long,IPluginSettings> _InstalledPlugins = new HashMap<Long, IPluginSettings>();
	private Map<Long,IPlugin>         _PluginInstances  = new HashMap<Long, IPlugin>();
	
	@Override
	public IPluginSettings getPluginSettings( long pluginID )
	{
		return _InstalledPlugins.get( pluginID );
	}
	
	@Override
	public IPlugin getPluginInstance( long instanceID )
	{
		return _PluginInstances.get( instanceID );
	}
	
	@Override
	public Iterator<IPluginSettings> getPluginSettings() 
	{
		return _InstalledPlugins.values().iterator();
	}

	@Override
	public Iterator<IPlugin> getPluginInstance() 
	{
		return _PluginInstances.values().iterator();
	}
	
	@Override
	public void loadPlugin( URL path, String classname, long pluginID ) throws ClassNotFoundException, ClassNotAHydraPlugin, FileNotFoundException
	{
		if( !new File( path.getFile() ).exists() )
			throw new FileNotFoundException();
		
		ClassLoader classLoader = URLClassLoader.newInstance( new URL[]{path} );
		Class<?>    clazz       = Class.forName( classname, false, classLoader );
		
		loadPlugin( clazz, pluginID );
	}
	
	@Override
	public void loadPlugin( Class<?> plugin, long pluginID ) throws ClassNotAHydraPlugin
	{
		if( !classIsPlugin( plugin ) )
			throw new ClassNotAHydraPlugin( String.format( "%1s isn't a valid hydra plugin", plugin.getName() ) );
		
		// TODO: Add more settings to the plugin, how many are allowed to run at one time, etc.
		_InstalledPlugins.put( pluginID, new PluginSettings( plugin, pluginID ) );
	}
	
	/**
	 * Determines if the class is a Plugin
	 * @param clazz
	 * @return
	 */
	private boolean classIsPlugin( Class<?> clazz ) 
	{
		if( clazz.getGenericSuperclass() == null )
			return false;
		
		for( Class<?> interfaceType : clazz.getInterfaces() )
		{
			// TODO: See if there is a better way of checking if it implements the Interface or Abstract class
			if( interfaceType.getClass().getName() == "com.eofstudio.hydra.commons.plugin.IPlugin" )
				return true;
			
			if( classIsPlugin( interfaceType ) )
				return true;
		}
		
		if( ( (Class<?>) clazz.getGenericSuperclass()).getName() == "com.eofstudio.hydra.commons.plugin.APlugin" )
			return true;
		else
			return classIsPlugin( (Class<?>) clazz.getGenericSuperclass() );
	}

	@Override
	public long instanciatePlugin( IPluginSettings settings ) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		IPlugin plugin     = (IPlugin) settings.getClassDefinition().newInstance();
		Random  random     = new Random();
		long    instanceID = random.nextLong();
		
		synchronized( _PluginInstances ) 
		{
			_PluginInstances.put( instanceID, plugin );
		}
		
		return instanceID;
	}
}
