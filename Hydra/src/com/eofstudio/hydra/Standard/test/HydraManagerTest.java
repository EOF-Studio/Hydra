package com.eofstudio.hydra.Standard.test;

import java.io.IOException;
import java.net.Socket;

import com.eofstudio.hydra.core.Standard.HydraManager;
import com.eofstudio.hydra.core.IHydraManager;
import com.eofstudio.utils.conversion.byteArray.IntConverter;

import junit.framework.TestCase;

public class HydraManagerTest extends TestCase
{
	public void testShouldInitilizeDefaultHydraManager()
	{
		try
		{
			IHydraManager manager = new HydraManager( false );
			
			assertEquals( false, manager.getIsRunning() );
		}
		catch( Exception e ) 
		{
			assertFalse(true);
			e.printStackTrace();
		}
	}
	
	public void testShouldInitilizeDefaultHydraManagerWithCustomSocketListenerSettings()
	{
		try
		{
			IHydraManager manager = new HydraManager( false, 5000, 123 );
			
			assertEquals( 5000, manager.getSocketListener().getPort() );
			assertEquals( 123, manager.getSocketListener().getTimeout() );
		}
		catch( Exception e ) 
		{
			assertFalse(true);
			e.printStackTrace();
		}
	}
	
	public void testShouldSetPortAndTimeoutOnStart()
	{
		try
		{
			IHydraManager manager = new HydraManager( false );
			
			manager.start( 5000, 123 );  
			
			assertEquals( 5000, manager.getSocketListener().getPort() );
			assertEquals( 123, manager.getSocketListener().getTimeout() );
			
			manager.stop(true);
		}
		catch( Exception e ) 
		{
			assertFalse(true);
			e.printStackTrace();
		}
	}
	
	public void testShouldInitilizeAutoStartHydraManager()
	{
		try
		{
			IHydraManager manager = new HydraManager( true );
		
			assertTrue( manager.getIsRunning() );
			
			manager.stop( true );
		}
		catch( Exception e ) 
		{
			assertFalse(true);
			e.printStackTrace();
		}
	}
	
	public void testShouldBeRunningAfterStart()
	{
		try
		{
			IHydraManager manager = new HydraManager( false );
			
			manager.start();
			
			assertTrue( manager.getIsRunning() );
			
			manager.stop( true );
		}
		catch( Exception e ) 
		{
			assertFalse(true);
			e.printStackTrace();
		}
	}
	
	public void testShouldGetAnInstanceOfISocketListener()
	{
		try
		{
			IHydraManager manager = new HydraManager( false );
			
			assertNotNull( manager.getSocketListener() );
		}
		catch( Exception e ) 
		{
			assertFalse(true);
			e.printStackTrace();
		}
	}
	
	public void testShouldBeNotifiedWhenDataIsRecieved()
	{
		try 
		{
			IHydraManager m1 = new HydraManager( true );
			
			m1.start();
			
			// Register test observer
			TestObserver obs = new TestObserver();
			m1.getSocketListener().addObserver( obs );
			
			// send test data
			Socket socket = new Socket( "localhost", 1337 );
			socket.getOutputStream().write( getHydraPacketData() );
			
			int retries = 40;
			
			while( true )
			{
				if( socket.getInputStream().available() != 0 )
				{
					socket.getInputStream().read();
					break;
				}
				
				Thread.sleep( 5 );
				
				if( retries-- == 0 )
					break;
			}
			
			socket.getOutputStream().write( new byte[]{0x0f,0x0f,0x0f} );
			
			       retries = 40;
			byte[] data    = null;
			
			// wait until date has been received (or 1sec)
			while( ( data = obs.packet.getCurrentBuffer() ).length == 0 )
			{
				Thread.sleep(25);
				
				if( retries-- == 0 )
				{
					assertTrue(false);
					break;
				}
			}
			
			m1.stop(true);
			
			// validate received data
			assertEquals(0x0f, data[2] );
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			assertFalse(true);
		} 
		catch (InterruptedException e) 
		{
			assertFalse(true);
			e.printStackTrace();
		}
	}

	private byte[] getHydraPacketData() throws IOException 
	{
		byte[] pluginID = "some.test.plugin.id".getBytes();
		
		byte[] data = new byte[8 + 4 + pluginID.length + 8];
		
		System.arraycopy( new byte[]{0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x01}, 0, data, 0, 8);
		System.arraycopy( IntConverter.toByteArray( pluginID.length ), 0, data, 8, 4);
		System.arraycopy( pluginID, 0, data, 8 + 4, pluginID.length);
		System.arraycopy( new byte[]{0x7f,0x7f,0x7f,0x7f,0x7f,0x7f,0x7f,0x7f}, 0, data, 8 + 4 + pluginID.length, 8);
		
		return data;
	}
}
