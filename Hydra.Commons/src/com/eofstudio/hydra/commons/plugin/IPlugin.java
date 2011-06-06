package com.eofstudio.hydra.commons.plugin;

import java.io.IOException;

public interface IPlugin extends Runnable
{
	IPluginSettings getSettings();
	String getPluginID();
	long getInstanceID();
	void addConnection( IHydraPacket packet ) throws IOException;
	int getCurrentConnections();
	Thread getThread();
	void doWork() throws Exception;
	void start();
}
