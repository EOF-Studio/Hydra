package com.eofstudio.hydra.core.Standard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.eofstudio.hydra.commons.logging.HydraLog;
import com.eofstudio.hydra.commons.plugin.APlugin;
import com.eofstudio.hydra.commons.plugin.IPlugin;
import com.eofstudio.hydra.commons.plugin.IPluginSettings;
import com.eofstudio.hydra.core.IPluginPool;

public class PluginPool implements IPluginPool, Observer 
{
	private int                         _MaxSimultaniousInstances;
	private Map<String,IPluginSettings> _RegisteredPluginSettings;
	private Map<Long,IPlugin>           _Instances;
	
	public PluginPool( int maxSimultaniousInstances ) 
	{
		_RegisteredPluginSettings = new HashMap<String, IPluginSettings>();
		_Instances                = new HashMap<Long,IPlugin>();
		
		_MaxSimultaniousInstances = maxSimultaniousInstances;
		
		HydraLog.Log.debug("PluginPool instanciated");
	}

	@Override
	public boolean containsPluginDefinition( String classname ) 
	{
		return _RegisteredPluginSettings.containsKey( classname );
	}

	@Override
	public int getMaxSimultaniousInstances() 
	{
		return _MaxSimultaniousInstances;
	}

	@Override
	public Collection<IPluginSettings> getRegisteredDefinition() 
	{
		return _RegisteredPluginSettings.values();
	}
	
	@Override
	public Collection<IPlugin> getInstances() 
	{
		return _Instances.values();
	}

	@Override
	public IPlugin getInstance( long instanceID ) 
	{
		return _Instances.get( instanceID );
	}

	@Override
	public long instanciatePlugin( String classname ) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		IPluginSettings settings = _RegisteredPluginSettings.get( classname );
		APlugin         plugin   = (APlugin) settings.getClassDefinition().newInstance();
		
		plugin.addObserver( this );
		plugin.setSettings( settings );
		
		synchronized( _Instances ) 
		{
			_Instances.put( plugin.getInstanceID() , plugin);
		}
		
		return plugin.getInstanceID();
	}

	@Override
	public void registerPluginDefinition( IPluginSettings settings ) 
	{
		_RegisteredPluginSettings.put( settings.getPluginID(), settings );
	}

	@Override
	public void update( Observable arg0, Object arg1 ) 
	{
		// Remove Plugin once it's done running
		APlugin plugin = (APlugin) arg0;
		
		_Instances.remove( plugin.getInstanceID() );
	}
}
