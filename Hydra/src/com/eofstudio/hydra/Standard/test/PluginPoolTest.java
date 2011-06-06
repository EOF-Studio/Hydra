package com.eofstudio.hydra.Standard.test;

import com.eofstudio.hydra.commons.plugin.APlugin;
import com.eofstudio.hydra.commons.plugin.IPluginSettings;
import com.eofstudio.hydra.commons.plugin.standard.PluginSettings;
import com.eofstudio.hydra.commons.plugin.test.MockPlugin;
import com.eofstudio.hydra.core.IPluginPool;
import com.eofstudio.hydra.core.Standard.PluginPool;

import junit.framework.TestCase;

public class PluginPoolTest extends TestCase
{
	public void testShould_Initialize_PluginPool()
	{
		IPluginPool pool = new PluginPool( 25 );
		
		assertTrue( pool.getInstances() != null );
		assertEquals( 25, pool.getMaxSimultaniousInstances() );
	}
	
	public void testShould_Remove_Plugin_Instance_Once_Done()
	{
		IPluginPool     pool     = new PluginPool( 25 );
		IPluginSettings settings = new PluginSettings(MockPlugin.class,"com.eofstudio.hydra.commons.plugin.APlugin.MockPlugin", 1 );
		
		pool.registerPluginDefinition( settings );
		try 
		{
			long instanceID = pool.instanciatePlugin( "com.eofstudio.hydra.commons.plugin.APlugin.MockPlugin" );
			
			assertTrue( pool.getInstances().size() == 1 );
			
			APlugin plugin = (APlugin) pool.getInstance( instanceID );
			
			plugin.run();
			
			assertTrue( pool.getInstances().size() == 0 );
		} 
		catch (ClassNotFoundException e) {
			assertTrue(false);
		} 
		catch (InstantiationException e) {
			assertTrue(false);
		} 
		catch (IllegalAccessException e) {
			assertTrue(false);
		}
		
		
		
	}
}
