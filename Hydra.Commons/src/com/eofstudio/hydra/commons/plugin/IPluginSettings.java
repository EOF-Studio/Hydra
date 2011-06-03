package com.eofstudio.hydra.commons.plugin;

public interface IPluginSettings
{
	Class<?> getClassDefinition();
	String   getPluginID();
	int      getMaxConnections();
	void     setMaxConnections( int maxConnections );
}
