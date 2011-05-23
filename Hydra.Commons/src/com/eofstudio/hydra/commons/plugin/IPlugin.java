package com.eofstudio.hydra.commons.plugin;

import java.io.IOException;

public interface IPlugin extends Runnable
{
	int getPluginID();
	int getInstanceID();
	void addConnection( IHydraPacket packet ) throws IOException;
	Thread getThread();
}
