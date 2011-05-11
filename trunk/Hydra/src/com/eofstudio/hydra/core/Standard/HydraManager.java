package com.eofstudio.hydra.core.Standard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Observable;
import java.util.Observer;

import sun.net.www.protocol.jar.URLJarFile;

import com.eofstudio.hydra.commons.exceptions.ClassNotAHydraPlugin;
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
	
	@Override
	public void loadPluginsFromFile( URL path, String classname ) throws ClassNotFoundException, ClassNotAHydraPlugin, FileNotFoundException
	{
		if( !new File( path.getFile() ).exists() )
			throw new FileNotFoundException();
		
		ClassLoader classLoader = URLClassLoader.newInstance( new URL[]{path} );
		Class<?>    clazz       = Class.forName( classname, false, classLoader );
		
		if( !classIsPlugin( clazz ) )
			throw new ClassNotAHydraPlugin( String.format( "%1s isn't a valid hydra plugin", classname ) );
	}
	
	/**
	 * Determines if the class is a Plugin
	 * @param clazz
	 * @return
	 */
	private boolean classIsPlugin( Class<?> clazz ) 
	{
		for( Class<?> interfaceType : clazz.getInterfaces() )
		{
			// TODO: See if there is a better way of checking if it implements the Interface or Abstract class
			if( interfaceType.getClass().getName() == "com.eofstudio.hydra.commons.plugin.IPlugin" )
				return true;
		}

		return ( (Class<?>) clazz.getGenericSuperclass()).getName() == "com.eofstudio.hydra.commons.plugin.APlugin";
	}
}
