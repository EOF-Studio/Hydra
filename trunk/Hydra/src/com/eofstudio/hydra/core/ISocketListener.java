package com.eofstudio.hydra.core;

import java.io.IOException;
import java.util.Observer;

public interface ISocketListener
{
	boolean start() throws IOException;
	void stop( boolean isBlocking ) throws IOException;
	void addObserver( Observer o );
	void deleteObserver( Observer o );
	boolean getIsRunning();
	int getPort();
	int getTimeout();
}
