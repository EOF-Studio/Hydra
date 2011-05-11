package com.eofstudio.hydra.plugin.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import com.eofstudio.hydra.commons.exceptions.ClassNotAHydraPlugin;
import com.eofstudio.hydra.core.IHydraManager;
import com.eofstudio.hydra.core.Standard.HydraManager;

import junit.framework.TestCase;

public class TimePluginTest extends TestCase 
{
	public void testShouldLoadTimePlugin()
	{
		IHydraManager manager = null;
		
		try 
		{
			manager = new HydraManager( true );

			manager.loadPluginsFromFile( new URL( "file:/C:/Users/Fyhr/Desktop/hydra/trunk/lib/Hydra.Test.jar" ), "com.eofstudio.hydra.plugin.test.TimePlugin" );
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

			manager.loadPluginsFromFile( new URL( "file:/C:/Users/Fyhr/Desktop/hydra/trunk/lib/Hydra.Test.jar" ), "com.eofstudio.hydra.plugin.test.TimePluginTest" );
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

			manager.loadPluginsFromFile( new URL( "file:/C:/Users/Fyhr/Desktop/hydra/trunk/lib/MISSING.jar" ), "com.eofstudio.hydra.plugin.test.TimePluginTest" );
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
}
