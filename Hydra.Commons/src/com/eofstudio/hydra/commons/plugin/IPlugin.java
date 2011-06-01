package com.eofstudio.hydra.commons.plugin;

import java.io.IOException;

public interface IPlugin extends Runnable
{
	long getPluginID();
	long getInstanceID();
	void addConnection( IHydraPacket packet ) throws IOException;
	Thread getThread();
}
