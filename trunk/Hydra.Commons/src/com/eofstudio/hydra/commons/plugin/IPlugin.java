package com.eofstudio.hydra.commons.plugin;

public interface IPlugin extends Runnable
{
	int getPluginID();
	int getInstanceID();
	void addConnection( IHydraPacket packet );
}
