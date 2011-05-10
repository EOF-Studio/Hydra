package com.eofstudio.hydra.core.Standard;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import com.eofstudio.hydra.core.*;

/**
 * This class is the center point of Hydra, it handles all the inner workings of 
 * listening for new connections, parsing the Hydra header, creating new instances of plugins
 * or passing the connection along to an existing instance etc.
 * 
 * @author Jesper Fyhr Knudsen
 *
 */
public class HydraManager implements IHydraManager, Runnable, Observer
{
	private boolean         _IsRunning      = false;
	private IPluginManager  _PluginManager  = new PluginManager();
	private ISocketListener _SocketListener = new SocketListener();
	private Thread          _Thread;
	
	public boolean getIsRunning() { return _IsRunning;	}
	public ISocketListener getSocketListener() { return _SocketListener; }
	
	public HydraManager( boolean isAutoStartEnabled ) throws IOException
	{
		_Thread = new Thread( this );
		
		if( isAutoStartEnabled )
			start();
	}

	public void start() throws IOException
	{
		if( getIsRunning() )
			return;
		
		_SocketListener.start();
		_SocketListener.addObserver( this );
		_IsRunning = true;
		_Thread.start();
	}

	public void stop( boolean isBlocking )
	{
		try
		{
			_SocketListener.stop( isBlocking );
			_SocketListener.deleteObserver( this );
			_IsRunning = false;
			
			// TODO ADD blocking logic here
		}
		catch( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run()
	{
		while( getIsRunning() )
		{
			try
			{
				Thread.sleep(25);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update( Observable o, Object arg )
	{
		System.out.println("Packet received");
		
		IHydraPacket packet = (IHydraPacket) arg;
		
		if( packet.getInstanceID() != Long.MIN_VALUE )
			_PluginManager.getPluginInstance( Long.toString( packet.getInstanceID() ) );
		else if( packet.getPluginID() != Long.MIN_VALUE )
			_PluginManager.getPluginSettings( Long.toString( packet.getPluginID() ) );
		else
			System.err.println( "PluginID and InstanceID was invalid" );
	}
}
