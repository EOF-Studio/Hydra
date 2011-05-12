package com.eofstudio.hydra.plugin.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;

import com.eofstudio.hydra.Standard.test.TestObserver;
import com.eofstudio.hydra.commons.exceptions.ClassNotAHydraPlugin;
import com.eofstudio.hydra.core.IHydraManager;
import com.eofstudio.hydra.core.ISocketListener;
import com.eofstudio.hydra.core.Standard.HydraManager;
import com.eofstudio.hydra.core.Standard.SocketListener;

import junit.framework.TestCase;

public class TimePluginTest extends TestCase 
{
	public void testShouldLoadTimePlugin()
	{
		IHydraManager manager = null;
		
		try 
		{
			manager = new HydraManager( true );

			manager.getPluginManager().loadPluginsFromFile( new URL( "file:/C:/Users/Fyhr/Desktop/hydra/trunk/lib/Hydra.Test.jar" ), "com.eofstudio.hydra.plugin.test.TimePlugin", "1" );
		} 
		catch( IOException e ) 
		{
			e.printStackTrace();
			
			assertTrue(false);
		} 
		catch( ClassNotFoundException e ) 
		{
			e.printStackTrace();
			
			assertTrue(false);
		} 
		catch( ClassNotAHydraPlugin e ) 
		{
			e.printStackTrace();
			
			manager.stop(true);
		}
		
		if( manager != null )
			manager.stop(true);
	}
	
	public void testShouldThrowExceptionIfClassIsntAPlugin()
	{
		IHydraManager manager = null;
		
		try 
		{
			manager = new HydraManager( true );

			manager.getPluginManager().loadPluginsFromFile( new URL( "file:/C:/Users/Fyhr/Desktop/hydra/trunk/lib/Hydra.Test.jar" ), "com.eofstudio.hydra.plugin.test.TimePluginTest", "1" );
		} 
		catch( IOException e ) 
		{
			e.printStackTrace();
			
			assertTrue(false);
		} 
		catch( ClassNotFoundException e ) 
		{
			e.printStackTrace();
			
			assertTrue(false);
		} 
		catch( ClassNotAHydraPlugin e ) 
		{
			assertTrue(true);
		}
		
		if( manager != null )
		    manager.stop(true);
	}
	
	public void testShouldThrowExceptionIfPathIsInvalid()
	{
		IHydraManager manager = null;
		
		try 
		{
			manager = new HydraManager( true );

			manager.getPluginManager().loadPluginsFromFile( new URL( "file:/C:/Users/Fyhr/Desktop/hydra/trunk/lib/MISSING.jar" ), "com.eofstudio.hydra.plugin.test.TimePluginTest", "1" );
		} 
		catch( FileNotFoundException e ) 
		{
			assertTrue(true);
		} 
		catch( IOException e ) 
		{
			e.printStackTrace();
			
			assertTrue(false);
		} 
		catch( ClassNotFoundException e ) 
		{
			assertTrue(true);
		} 
		catch( ClassNotAHydraPlugin e ) 
		{
			e.printStackTrace();
			
			assertTrue(false);
		}
		
		if( manager != null )
		    manager.stop(true);
	}
	
	public void testShouldRunCallPlugin()
	{
		IHydraManager manager = null;
		
		try 
		{
			manager = new HydraManager( true );

			manager.getPluginManager().loadPluginsFromFile( new URL( "file:/C:/Users/Fyhr/Desktop/hydra/trunk/lib/Hydra.Test.jar" ), "com.eofstudio.hydra.plugin.test.TimePlugin", "1" );
			
			// send test data
			Socket socket = new Socket( "localhost", 1337 );
			socket.getOutputStream().write( new byte[]{0x00,0x00,0x00,0x01,0x00,0x00,0x00,0x01} );
			socket.getOutputStream().write( new byte[]{0x01} );
			
			String resonse = "";
			int retries = 40;
			
			// wait until date has been received (or 1sec)
			while( true )
			{
				if( socket.getInputStream().available() == 0 )
				{
					Thread.sleep( 25 );

					if( retries-- == 0 )
						break;
				}
			
				int size = 0;
				
				while( (size = socket.getInputStream().available()) != 0 )
				{
					byte[] buffer = new byte[size];
					
					socket.getInputStream().read( buffer );
					
					resonse += new String( buffer );
				}
			}
			
			// validate received data
			assertTrue( resonse.length() != 0 );
		} 
		catch( IOException e ) 
		{
			e.printStackTrace();
			
			assertTrue(false);
		} 
		catch( ClassNotFoundException e ) 
		{
			e.printStackTrace();
			
			assertTrue(false);
		} 
		catch( ClassNotAHydraPlugin e ) 
		{
			e.printStackTrace();
			
			manager.stop(true);
		}
		catch( InterruptedException e ) 
		{
			e.printStackTrace();
			
			manager.stop(true);
		}
		
		if( manager != null )
			manager.stop(true);
	}
}
