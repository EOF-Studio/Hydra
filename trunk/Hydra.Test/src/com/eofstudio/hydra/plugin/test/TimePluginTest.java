package com.eofstudio.hydra.plugin.test;

import java.io.IOException;
import java.net.URL;

import com.eofstudio.hydra.core.IHydraManager;
import com.eofstudio.hydra.core.Standard.HydraManager;

import junit.framework.TestCase;

public class TimePluginTest extends TestCase 
{
	public void testShouldLoadTimePlugin()
	{
		try 
		{
			IHydraManager manager = new HydraManager( true );

			manager.loadPluginsFromFile( new URL( "file:/C:/Users/Fyhr/Desktop/hydra/trunk/lib/Hydra.Test.jar" ), "com.eofstudio.hydra.plugin.test.TimePlugin" );
		} 
		catch( IOException e ) 
		{
			e.printStackTrace();
		} 
		catch( ClassNotFoundException e ) 
		{
			e.printStackTrace();
		}
	}
}
