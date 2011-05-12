package com.eofstudio.hydra.commons.plugin;

import java.net.Socket;

public interface IHydraPacket 
{
	/**
	 * 
	 * @return the currently buffered data
	 */
	byte[] getCurrentBuffer();
	
	/**
	 *  
	 * @return default value is Long.MIN_VALUE
	 */
	long getVersion();
	
	/**
	 * 
	 * @return default value is Long.MIN_VALUE
	 */
	long getPluginID();
	
	/**
	 * The instance ID the current packet is mean for.
	 * @return default value is Long.MIN_VALUE
	 */
	String getInstanceID();
	
	void setInstanceID( String id );

	Socket getSocket();
}
