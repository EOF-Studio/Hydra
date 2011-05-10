package com.eofstudio.hydra.core;

import java.io.IOException;

public interface IHydraManager
{
	void start() throws IOException;
	void stop( boolean isBlocking );
	boolean getIsRunning();
	ISocketListener getSocketListener();
}
