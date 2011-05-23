package com.eofstudio.hydra.Standard.test;

import java.io.IOException;
import java.net.Socket;

import com.eofstudio.hydra.core.ISocketListener;
import com.eofstudio.hydra.core.Standard.SocketListener;

import junit.framework.TestCase;

public class SocketListenerTest extends TestCase 
{
	public void testShouldInstantiateSocketListener()
	{
		ISocketListener sl = new SocketListener( 1337, 1000 ); 
		
		assertEquals( 1337, sl.getPort() );
		assertEquals( 1000, sl.getTimeout() );
	}
	
	public void testIsRunningShouldBeTrueWhenStartHasBeenCalled()
	{
		ISocketListener sl = new SocketListener( 1337, 1000 ); 
		
		try 
		{
			sl.start();
			assertTrue( sl.getIsRunning() );
			sl.stop(true);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void testShouldBeAbleToSetPortAndTimeoutOnStart()
	{
		ISocketListener sl = new SocketListener( ); 
		
		try 
		{
			sl.start( 5000, 123 );
			assertEquals(5000, sl.getPort());
			assertEquals(123, sl.getTimeout());
			sl.stop(true);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void testIsRunningShouldBeFalseWhenStopHasBeenCalled()
	{
		ISocketListener sl = new SocketListener( 1337, 1000 ); 
		
		try 
		{
			sl.start();
			sl.stop(true);
			assertFalse( sl.getIsRunning() );
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void testShouldBeNotifiedWhenDataIsRecieved()
	{
		ISocketListener sl = new SocketListener( 1337, 100 ); 
		
		try 
		{
			sl.start();
			
			// Register test observer
			TestObserver obs = new TestObserver();
			sl.addObserver( obs );
			
			// send test data
			Socket socket = new Socket( "localhost", 1337 );
			socket.getOutputStream().write( new byte[]{0x00,0x00,0x0f,0x00,0x00,0x00,0x0f,0x01,0x00,0x00,0x0f,0x00,0x00,0x00,0x00,0x01,0x7f,0x7f,0x7f,0x7f,0x7f,0x7f,0x7f,0x7f} );
			
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
			
			// validate received data
			assertEquals(0x0f, data[2] );
			
			sl.stop(true);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void testShouldBeNotifiedWhenDataIsRecievedWithoutInstanceID()
	{
		ISocketListener sl = new SocketListener( 1337, 100 ); 
		
		try 
		{
			sl.start();
			
			// Register test observer
			TestObserver obs = new TestObserver();
			sl.addObserver( obs );
			
			// send test data
			Socket socket = new Socket( "localhost", 1337 );
			socket.getOutputStream().write( new byte[]{0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x01,0x7f,0x7f,0x7f,0x7f,0x7f,0x7f,0x7f,0x7f} );
			
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
			
			assertEquals( 1, obs.packet.getVersion() );
			assertEquals( 9187201950435737471L, obs.packet.getInstanceID() );
			assertEquals( 1, obs.packet.getPluginID() );
			
			sl.stop(true);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
}
