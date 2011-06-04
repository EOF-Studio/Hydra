package com.eofstudio.hydra.core.Standard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.eofstudio.hydra.commons.plugin.IPlugin;
import com.eofstudio.hydra.core.IPluginPool;

public class PluginPool implements IPluginPool 
{
	private int               _MaxSimultaniousInstances;
	private ArrayList<String> _Definitions;
	private Map<Long,IPlugin> _Instances;
	
	public PluginPool( int maxSimultaniousInstances ) 
	{
		_Definitions = new ArrayList<String>();
		_Instances   = new HashMap<Long,IPlugin>();
		
		_MaxSimultaniousInstances = maxSimultaniousInstances;
	}

	@Override
	public ArrayList<String> getDefinitions() 
	{
		return _Definitions;
	}

	@Override
	public int getMaxSimultaniousInstances() 
	{
		return _MaxSimultaniousInstances;
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

}
