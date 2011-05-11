package com.eofstudio.hydra.commons.plugin;

import java.net.Socket;

public interface IPlugin 
{
	int getInstanceID();
	void AddConnection( Socket socket );
}
