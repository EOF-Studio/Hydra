package com.eofstudio.hydra.commons.plugin.test;

import com.eofstudio.hydra.commons.plugin.APlugin;
import com.eofstudio.hydra.commons.plugin.IPlugin;
import com.eofstudio.hydra.commons.plugin.standard.PluginSettings;

import junit.framework.TestCase;

public class APluginTest extends TestCase
{
	public void testShouldGetToString()
	{
		APlugin plugin = new MockPlugin();
		plugin.setSettings( new PluginSettings(plugin.getClass(), plugin.getPluginID(), 1) );

		assertEquals( String.format( "  %16s", Long.toHexString( plugin.getInstanceID() ) ) + "           com.eofstudio.hydra.commons.plugin.test.MockPlugin               0                 1", plugin.toString() );
	}
	
	public void testShould_Get_PluginID()
	{
		IPlugin plugin = new MockPlugin();
		
		assertEquals( "com.eofstudio.hydra.commons.plugin.test.MockPlugin", plugin.getPluginID() );
	}
	
	public void testShould_Notify_Observers_When_There_Are_No_More_Work_To_Be_Done()
	{
		APlugin         plugin = new MockPlugin();
		APluginObserver obs    = new APluginObserver();
		
		plugin.addObserver( obs );
		plugin.run();
		
		assertTrue( obs.wasUpdated );
	}
}
