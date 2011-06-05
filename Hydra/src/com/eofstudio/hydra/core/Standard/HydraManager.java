package com.eofstudio.hydra.core.Standard;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import com.eofstudio.hydra.commons.logging.HydraLog;
import com.eofstudio.hydra.commons.plugin.IHydraPacket;
import com.eofstudio.hydra.commons.plugin.IPlugin;
import com.eofstudio.hydra.commons.plugin.IPluginSettings;
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
	private IPluginManager         _PluginManager  = new PluginManager();
	private ISocketListener        _SocketListener = new SocketListener();
	//private String          _PluginFolder   = "plugins/";
	
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
				PassPacketToNewOrAvailablePluginInstance( packet );
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
		catch( IOException e ) 
		{
			HydraLog.Log.error( e.getMessage() );
		}
	}
	
	private void PassPacketToNewOrAvailablePluginInstance( IHydraPacket packet ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException 
	{
		IPluginSettings       settings = _PluginManager.getPluginSettings( packet.getPluginID() );

		for( IPluginPool pool : getPluginManager().getPluginPools() )
		{
			if( !pool.containsPluginDefinition( packet.getPluginID() ) )
				continue;
			
			if( pool.getMaxSimultaniousInstances() <= pool.getInstances().size() )
				continue;
			
			packet.setInstanceID( pool.instanciatePlugin( settings.getClassDefinition().getName() ) );
			PassConnectionToInstance( packet );
		}
		
		// TODO: Notify client if there are no available plugins slots in any plugin pools
	}
	
	private void PassConnectionToInstance( IHydraPacket packet ) throws IOException 
	{
		IPlugin plugin = _PluginManager.getPluginInstance( packet.getInstanceID() );
		
		// TODO: Proper exception handling
		if( plugin == null )
			return; // TODO: Notify client if the instance doesn't exist
		
		if( !plugin.getPluginID().equals( packet.getPluginID() ) )
			return; // TODO: Notify client if the instance doesn't match the provided plugin ID
		
		if( plugin.getSettings().getMaxConnections() <= plugin.getCurrentConnections() )
			return; // TODO: Notify client that the max connections has been reached
			
		plugin.addConnection( packet );
		
		HydraLog.Log.info( String.format( "Connection established to instance %s", Long.toHexString( packet.getInstanceID() ) ));
	}
}
