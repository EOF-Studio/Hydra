package com.eofstudio.hydra.core.Standard;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.eofstudio.hydra.commons.exceptions.InvalidHydraPacketException;
import com.eofstudio.hydra.commons.logging.HydraLog;
import com.eofstudio.hydra.core.IConnectionHandler;

/**
 * This Class handles connections asynchronously using the FIFO principle
 * @author Jesper Fyhr Knudsen
 *
 */
public class ConnectionHandler extends Observable implements IConnectionHandler, Runnable
{
	private Thread            thread;
	private boolean           isRunning = false;
	private ArrayList<Socket> sockets;
	
	public ConnectionHandler( Observer observer )
	{
		sockets = new ArrayList<Socket>();
		
		addObserver( observer );
	}
	
	@Override
	public boolean start() throws IOException
	{
		thread    = new Thread( this );
		isRunning = true;

		thread.start();
		
		return true;
	}

	@Override
	public void stop( boolean isBlocking ) throws IOException
	{
		isRunning = false;
		
		while( isBlocking )
		{
			if( !thread.isAlive() )
				return;
			
			try
			{
				Thread.sleep( 25 );
			}
			catch( InterruptedException e )
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void newConnection( Socket socket )
	{
		HydraLog.Log.debug( "New Connection attempt" );
		
		synchronized( sockets )
		{
			sockets.add( socket );
		}
	}	

	@Override
	public void run()
	{
		while( isRunning )
		{
			while( sockets.size() > 0 )
			{
				Socket socket = sockets.get( 0 );
				
				synchronized( sockets )
				{
					sockets.remove( 0 );
				}
				
				try
				{
					setChanged();
					notifyObservers( new HydraPacket( socket ) );

				} 
				catch( IOException e ) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch( InvalidHydraPacketException e )
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try
			{
				Thread.sleep( 5 );
			} 
			catch( InterruptedException e )
			{
				e.printStackTrace();
			}
		}
	}
}
