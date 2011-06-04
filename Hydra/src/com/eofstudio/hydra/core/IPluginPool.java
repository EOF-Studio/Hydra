package com.eofstudio.hydra.core;

import java.util.ArrayList;
import java.util.Collection;

import com.eofstudio.hydra.commons.plugin.IPlugin;

public interface IPluginPool 
{
	ArrayList<String> getDefinitions();
	Collection<IPlugin> getInstances();
	IPlugin getInstance( long instanceID );
	int getMaxSimultaniousInstances();
}
