package com.eofstudio.hydra.commons.plugin;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import com.eofstudio.hydra.commons.logging.HydraLog;
import com.eofstudio.utils.conversion.byteArray.LongConverter;

public abstract class APlugin implements IPlugin
{
	private ArrayList<IHydraPacket> _ActiveConnections;
	private long                    _InstanceID;
	private Thread                  _Thread;
	private IPluginSettings         _Settings;
	
	public APlugin( )
	{
		
		_ActiveConnections = new ArrayList<IHydraPacket>();
		_Thread            = new Thread( this );
		_InstanceID        = new Random().nextLong();
		
		_Thread.start();
	}
	
	protected ArrayList<IHydraPacket> getActiveConnections()
	{
		return _ActiveConnections;
	}
	
	@Override
	public int getCurrentConnections() 
	{
		return _ActiveConnections.size();
	}
	
	int getNoOfCurrentConnections()
	{
		return _ActiveConnections.size();
	}
	
	@Override
	public IPluginSettings getSettings() 
	{
		return _Settings;
	}
	
	public void setSettings( IPluginSettings settings ) 
	{
		_Settings = settings;
	}
	
	@Override
	public long getInstanceID() 
	{
		return _InstanceID;
	}
	
	@Override
	public String getPluginID() 
	{
		return getClass().getName();
	}
	
	@Override
	public Thread getThread() 
	{
		return _Thread;
	}

	@Override
	public void addConnection( IHydraPacket packet ) throws IOException 
	{
		_ActiveConnections.add( packet );
		SendResponse( packet.getSocket(), LongConverter.toByteArray( packet.getInstanceID() ) );
	}

	protected void SendResponse( Socket socket, byte[] data ) 
	{
		// TODO: Implement the protocol header format, with commandID
		try 
		{
			HydraLog.Log.debug(String.format("SendResponse - %s", new String(data)));
			socket.getOutputStream().write( data );
		} 
		catch( IOException e ) 
		{
			HydraLog.Log.error(String.format("Couldn't send response, exception: %s", e.getMessage()));
		}
	}

	public String toString()
	{
		return String.format( "  %16s %60s %15d %15d", Long.toHexString( getInstanceID() ), getClass().getName(), getActiveConnections().size(), getSettings().getMaxConnections() ); 
	}
}
