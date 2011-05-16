package com.eofstudio.hydra.core.Standard;

import java.io.IOException;
import java.net.*;
import java.util.Observable;
import java.util.Observer;

import com.eofstudio.hydra.core.IConnectionHandler;
import com.eofstudio.hydra.core.ISocketListener;

public class SocketListener extends Observable implements ISocketListener, Runnable, Observer
{
	protected int timeout;
	protected int port;
	
	private ServerSocket       socket;
	private Thread             thread;
	private boolean            isRunning = false;
	private IConnectionHandler connectionHandler;
	
	public int     getPort()      { return port;	  }
	public int     getTimeout()   { return timeout;   }
	public boolean getIsRunning() { return isRunning; }
	
	public SocketListener( )
	{
		this( 1337, 10 );
	}
	
	public SocketListener( int port, int delay )
	{
		this.port              = port;
		this.timeout           = delay;
		this.connectionHandler = new ConnectionHandler( this );
	}
	
	@Override 
	public boolean start( int port, int timeout ) throws IOException
	{
		this.port    = port;
		this.timeout = timeout;
		
		return start();
	}
	
	@Override 
	public boolean start() throws IOException
	{
		socket    = new ServerSocket( port );
		thread    = new Thread( this );
		isRunning = true;
	
		connectionHandler.start();
		socket.setSoTimeout( timeout );
		thread.start();
		
		return true;
	}

	@Override 
	public void stop( boolean isBlocking ) throws IOException
	{
		isRunning = false;
		
		while( isBlocking )
		{
			if( socket.isClosed() )
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
		
		connectionHandler.stop( isBlocking );
	}

	@Override
	public void run()
	{
		while( isRunning )
		{
			try
			{
				System.out.println("waiting for connection");
				connectionHandler.newConnection( socket.accept() );
			} 
			catch( SocketTimeoutException e )
			{
				// this exception is acceptable and expected
			}
			catch( IOException e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.close();
		} 
		catch( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void update( Observable arg0, Object arg1 )
	{
		setChanged();
		notifyObservers( arg1 );
	}
	
	@Override
	public void addObserver( Observer o )
	{
		super.addObserver( o );
	}

	@Override
	public void deleteObserver( Observer o )
	{
		super.deleteObserver( o );
	}
}
