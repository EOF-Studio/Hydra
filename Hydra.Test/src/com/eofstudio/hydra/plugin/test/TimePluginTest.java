package com.eofstudio.hydra.plugin.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;

import com.eofstudio.hydra.commons.exceptions.ClassNotAHydraPluginException;
import com.eofstudio.hydra.core.IHydraManager;
import com.eofstudio.hydra.core.Standard.HydraManager;
import com.eofstudio.utils.conversion.byteArray.IntConverter;
import com.eofstudio.utils.conversion.byteArray.LongConverter;

import junit.framework.TestCase;

public class TimePluginTest extends TestCase 
{
	public void testShouldLoadTimePlugin()
	{
		IHydraManager manager = null;
		
		try 
		{
			manager = new HydraManager( true );

			manager.getPluginManager().loadPlugin( new URL( "file:../lib/Hydra.Test.jar" ), "com.eofstudio.hydra.plugin.test.TimePlugin", "com.eofstudio.hydra.plugin.test.TimePlugin" );
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
		catch( ClassNotAHydraPluginException e ) 
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

			manager.getPluginManager().loadPlugin( new URL( "file:../lib/Hydra.Test.jar" ), "com.eofstudio.hydra.plugin.test.TimePluginTest", "com.eofstudio.hydra.plugin.test.TimePluginTest" );
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
		catch( ClassNotAHydraPluginException e ) 
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

			manager.getPluginManager().loadPlugin( new URL( "file:../lib/MISSING.jar" ), "com.eofstudio.hydra.plugin.test.TimePluginTest", "com.eofstudio.hydra.plugin.test.TimePluginTest" );
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
		catch( ClassNotAHydraPluginException e ) 
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
			//manager = new HydraManager( true );

			//manager.getPluginManager().loadPlugin( new URL( "file:../lib/Hydra.Test.jar" ), "com.eofstudio.hydra.plugin.test.TimePlugin", "com.eofstudio.hydra.plugin.test.TimePlugin" );
			
			// send test data
			Socket socket = new Socket( "localhost", 12345 );
			socket.getOutputStream().write( getHydraPacketData() );
			
			int retries = 40;
			
			while( true )
			{
				if( socket.getInputStream().available() != 0 )
					break;
				
				Thread.sleep( 5 );
				
				if( retries-- == 0 )
					break;
			}

			socket.getOutputStream().write( new byte[]{0x01} );
			
			        retries    = 40;
			long    instanceID = Long.MIN_VALUE;
			byte[]  data       = new byte[0];
			
			// wait until date has been received (or 1sec)
			while( true )
			{
				int available = socket.getInputStream().available();
				
				if( available == 0 )
				{
					Thread.sleep( 5 );

					if( retries-- == 0 )
						break;
				}
			
				if( available != 0 )
				{
					byte[] buffer = new byte[ data.length + available ];
					
					System.arraycopy( data, 0, buffer, 0, data.length );
					socket.getInputStream().read( buffer, data.length, available );

					data = buffer;
				}
			}
			
			instanceID = LongConverter.fromByteArray( data, 0 );		

			assertTrue( data.length > 8 );
			assertTrue( instanceID != Long.MIN_VALUE );
		} 
		catch( IOException e ) 
		{
			e.printStackTrace();
			
			assertTrue(false);
		} 
//		catch( ClassNotFoundException e ) 
//		{
//			e.printStackTrace();
//			
//			assertTrue(false);
//		} 
//		catch( ClassNotAHydraPluginException e ) 
//		{
//			e.printStackTrace();
//			
//			manager.stop(true);
//		}
		catch( InterruptedException e ) 
		{
			e.printStackTrace();
			
			manager.stop(true);
		}
		
		if( manager != null )
			manager.stop(true);
	}
	
	private byte[] getHydraPacketData() throws IOException 
	{
		byte[] pluginID = "com.eofstudio.hydra.plugin.test.TimePlugin".getBytes();
		
		byte[] data = new byte[8 + 4 + pluginID.length];
		
		System.arraycopy( new byte[]{0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x01}, 0, data, 0, 8);
		System.arraycopy( IntConverter.toByteArray( pluginID.length ), 0, data, 8, 4);
		System.arraycopy( pluginID, 0, data, 8 + 4, pluginID.length);
		
		return data;
	}
}
