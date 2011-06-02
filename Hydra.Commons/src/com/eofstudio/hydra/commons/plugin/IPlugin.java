package com.eofstudio.hydra.commons.plugin;

import java.io.IOException;

public interface IPlugin extends Runnable
{
	String getPluginID();
	long getInstanceID();
	void addConnection( IHydraPacket packet ) throws IOException;
	Thread getThread();
}
