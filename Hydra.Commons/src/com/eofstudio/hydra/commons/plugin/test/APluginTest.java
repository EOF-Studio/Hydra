package com.eofstudio.hydra.commons.plugin.test;

import com.eofstudio.hydra.commons.plugin.APlugin;

import junit.framework.TestCase;

public class APluginTest extends TestCase 
{
	public void testShouldGetToString()
	{
		APlugin plugin = new MockPlugin();
		
		assertEquals( String.format( " %019d", plugin.getInstanceID() ) + "           com.eofstudio.hydra.commons.plugin.test.MockPlugin             0", plugin.toString() );
	}
}

