package com.eofstudio.hydra.commons.plugin;

import java.net.Socket;

public interface IPlugin extends Runnable
{
	int getPluginID();
	int getInstanceID();
	void addConnection( Socket socket );
}
