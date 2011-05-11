package com.eofstudio.hydra.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public interface IHydraManager
{
	void start() throws IOException;
	void stop( boolean isBlocking );
	boolean getIsRunning();
	ISocketListener getSocketListener();
	
	/**
	 * Loads all IPlugins from the Jar file
	 * @param path is path to the Jar file to look through
	 * @throws MalformedURLException 
	 * @throws ClassNotFoundException 
	 */
	void loadPluginsFromFile( URL path, String classname ) throws MalformedURLException, ClassNotFoundException;
}
