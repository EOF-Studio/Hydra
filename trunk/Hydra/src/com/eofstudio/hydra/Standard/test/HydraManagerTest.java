package com.eofstudio.hydra.Standard.test;

import java.io.IOException;
import java.net.Socket;

import com.eofstudio.hydra.core.Standard.HydraManager;
import com.eofstudio.hydra.core.Standard.SocketListener;
import com.eofstudio.hydra.core.IHydraManager;
import com.eofstudio.hydra.core.ISocketListener;

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
			socket.getOutputStream().write( new byte[]{0x00,0x00,0x0f,0x01,0x00,0x00,0x00,0x01,0x7f,0x7f,0x7f,0x7f} );
			
			int retries = 40;
			
			// wait until date has been received (or 1sec)
			while( obs.getData() == null )
			{
				Thread.sleep(25);
				
				if( retries-- == 0 )
				{
					assertTrue(false);
					break;
				}
			}
			
			// validate received data
			assertEquals(0x0f, obs.getData()[2] );
			
			m1.stop(true);
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
}
