package com.eofstudio.hydra.commons.plugin.test;

import com.eofstudio.hydra.commons.plugin.APlugin;
import com.eofstudio.hydra.commons.plugin.IPlugin;

import junit.framework.TestCase;

public class APluginTest extends TestCase 
{
	public void testShouldGetToString()
	{
		APlugin plugin = new MockPlugin();
		
		assertEquals( String.format( " %12s", Long.toHexString( plugin.getInstanceID() ) ) + "           com.eofstudio.hydra.commons.plugin.test.MockPlugin                0", plugin.toString() );
	}
	
	public void testShould_Get_PluginID()
	{
		IPlugin plugin = new MockPlugin();
		
		assertEquals( "com.eofstudio.hydra.commons.plugin.test.MockPlugin", plugin.getPluginID() );
	}
}

