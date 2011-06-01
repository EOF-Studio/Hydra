package com.eofstudio.hydra.core;

import java.io.IOException;

public interface IHydraManager
{
	void start() throws IOException;
	void start( int port, int timeout ) throws IOException;
	void stop( boolean isBlocking );
	boolean getIsRunning();
	ISocketListener getSocketListener();
	IPluginManager getPluginManager();
}
