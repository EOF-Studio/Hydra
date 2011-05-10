package com.eofstudio.hydra.core;

import java.io.IOException;
import java.net.Socket;

public interface IConnectionHandler
{
	boolean start() throws IOException;
	void stop( boolean isBlocking ) throws IOException;
	void newConnection( Socket socket );
}
