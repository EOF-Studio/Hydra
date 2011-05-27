package com.eofstudio.hydra.commons.plugin;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import com.eofstudio.utils.conversion.byteArray.LongConverter;

public abstract class APlugin implements IPlugin
{
	private ArrayList<IHydraPacket> _ActiveConnections;
	private int                     _InstanceID;
	private Thread                  _Thread;
	
	public APlugin( )
	{
		
		_ActiveConnections = new ArrayList<IHydraPacket>();
		_Thread            = new Thread( this );
		
		_Thread.start();
	}
	
	protected ArrayList<IHydraPacket> getActiveConnections()
	{
		return _ActiveConnections;
	}
	
	@Override
	public int getInstanceID() 
	{
		return _InstanceID;
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
			socket.getOutputStream().write( data );
		} 
		catch( IOException e ) 
		{
			// TODO Handle lost connections
			e.printStackTrace();
		}
	}
}
