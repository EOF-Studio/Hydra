package com.eofstudio.hydra.core.Standard;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import com.eofstudio.hydra.commons.logging.HydraLog;
import com.eofstudio.hydra.commons.plugin.IHydraPacket;
import com.eofstudio.hydra.core.*;

/**
 * This class is the center point of Hydra, it handles all the inner workings of 
 * listening for new connections, parsing the Hydra header, creating new instances of plugins
 * or passing the connection along to an existing instance etc.
 * 
 * @author Jesper Fyhr Knudsen
 *
 */
public class HydraManager implements IHydraManager, Observer
{
	private IPluginManager  _PluginManager  = new PluginManager();
	private ISocketListener _SocketListener = new SocketListener();
	
	public boolean getIsRunning() { return _SocketListener.getIsRunning();	}
	public ISocketListener getSocketListener() { return _SocketListener; }
	public IPluginManager  getPluginManager() { return _PluginManager; }
	
	public HydraManager( boolean isAutoStartEnabled ) throws IOException
	{
		if( isAutoStartEnabled )
			start();
	}

	public HydraManager( boolean isAutoStartEnabled, int port, int timeout ) throws IOException
	{
		this( isAutoStartEnabled );
		
		_SocketListener = new SocketListener( port, timeout );
	}
	
	@Override
	public void start() throws IOException
	{
		if( getIsRunning() )
			return;
		
		_SocketListener.start();
		_SocketListener.addObserver( this );
	}
	
	@Override
	public void start( int port, int timeout ) throws IOException
	{
		if( getIsRunning() )
			return;
		
		_SocketListener.start( port, timeout );
		_SocketListener.addObserver( this );
	}

	@Override
	public void stop( boolean isBlocking )
	{
		try
		{
			_SocketListener.stop( isBlocking );
			_SocketListener.deleteObserver( this );
		}
		catch( IOException e )
		{
			// TODO Auto-generated catch block
			HydraLog.Log.error( e.getMessage() );
		}
	}

	@Override
	public void update( Observable o, Object arg )
	{
		try 
		{
			IHydraPacket packet = (IHydraPacket) arg;
			
			if( packet.getInstanceID() != Long.MIN_VALUE )
				PassConnectionToInstance( packet );
			else if( packet.getPluginID() != null )
				PassPacketToNewPluginInstance( packet );
			else
			{
				HydraLog.Log.error( String.format( "PluginID and InstanceID was invalid! PluginID: %s, InstanceID: %s", packet.getPluginID(), packet.getInstanceID() ));

				// TODO: Send message back to client, with an error code
			}
		} 
		catch( ClassNotFoundException e ) 
		{
			// TODO Actual exception handling
			HydraLog.Log.error( e.getMessage() );
		} 
		catch( InstantiationException e ) 
		{
			// TODO Actual exception handling
			HydraLog.Log.error( e.getMessage() );
		} 
		catch( IllegalAccessException e ) 
		{
			// TODO Actual exception handling
			HydraLog.Log.error( e.getMessage() );
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			HydraLog.Log.error( e.getMessage() );
		}
	}
	
	private void PassPacketToNewPluginInstance( IHydraPacket packet ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException 
	{
		IPluginSettings settings = _PluginManager.getPluginSettings( packet.getPluginID() );
		
		// TODO: Make sure the settings are obeyed if the plugin instance is created
		// TODO: Implement Execution slots akin those in Octopus.Net
		
		packet.setInstanceID( getPluginManager().instanciatePlugin( settings ) );
		PassConnectionToInstance( packet );

	}
	
	private void PassConnectionToInstance( IHydraPacket packet ) throws IOException 
	{
		// TODO: Proper exception handling
		if( _PluginManager.getPluginInstance( packet.getInstanceID() ) == null )
			return;
		
		_PluginManager.getPluginInstance( packet.getInstanceID() ).addConnection( packet );
		
		HydraLog.Log.info( String.format( "Connection established to instance %s", Long.toHexString( packet.getInstanceID() ) ));
	}
}
