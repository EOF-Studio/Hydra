package com.eofstudio.hydra.core.Standard;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.eofstudio.hydra.commons.exceptions.ClassNotAHydraPluginException;
import com.eofstudio.hydra.commons.plugin.IPlugin;
import com.eofstudio.hydra.commons.plugin.IPluginSettings;
import com.eofstudio.hydra.commons.plugin.standard.PluginSettings;
import com.eofstudio.hydra.core.IPluginManager;
import com.eofstudio.hydra.core.IPluginPool;

public class PluginManager implements IPluginManager
{
	private Map<String,IPluginSettings> _InstalledPlugins = new HashMap<String, IPluginSettings>();
	private ArrayList<IPluginPool>      _PluginPools      = new ArrayList<IPluginPool>();
	
	@Override
	public IPluginSettings getPluginSettings( String pluginID )
	{
		return _InstalledPlugins.get( pluginID );
	}
	
	@Override
	public IPlugin getPluginInstance( long instanceID )
	{
		for( IPluginPool pool : _PluginPools ) 
		{
			IPlugin plugin = pool.getInstance( instanceID );
			
			if( plugin == null )
				continue;
			
			return plugin;
		}
		
		return null;
	}
	
	@Override
	public List<IPluginPool> getPluginPools() 
	{
		return _PluginPools;
	}
	
	@Override
	public Iterator<IPluginSettings> getPluginSettings() 
	{
		return _InstalledPlugins.values().iterator();
	}

	@Override
	public void loadPlugin( URL path, String classname, String pluginID, int maxConnections ) throws ClassNotFoundException, ClassNotAHydraPluginException, FileNotFoundException
	{
		if( !new File( path.getFile() ).exists() )
			throw new FileNotFoundException();
		
		ClassLoader classLoader = URLClassLoader.newInstance( new URL[]{path} );
		Class<?>    clazz       = Class.forName( classname, false, classLoader );
		
		loadPlugin( clazz, pluginID, maxConnections );
	}
	
	@Override
	public void loadPlugin( Class<?> plugin, String pluginID, int maxConnections ) throws ClassNotAHydraPluginException
	{
		if( !classIsPlugin( plugin ) )
			throw new ClassNotAHydraPluginException( String.format( "%1s isn't a valid hydra plugin", plugin.getName() ) );

		_InstalledPlugins.put( pluginID, new PluginSettings( plugin, pluginID, maxConnections ) );
	}
	
	/**
	 * Determines if the class is a Plugin
	 * @param clazz
	 * @return
	 */
	private boolean classIsPlugin( Class<?> clazz ) 
	{
		// TODO: Refactor and see if there is a better way of checking if it implements the Interface or Abstract class
		if( clazz.getGenericSuperclass() == null )
			return false;
		
		for( Class<?> interfaceType : clazz.getInterfaces() )
		{
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
}
