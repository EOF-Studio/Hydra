package com.eofstudio.hydra.Standard.test;

import com.eofstudio.hydra.core.IPluginPool;
import com.eofstudio.hydra.core.Standard.PluginPool;

import junit.framework.TestCase;

public class PluginPoolTest extends TestCase
{
	public void testShould_Initialize_PluginPool()
	{
		IPluginPool pool = new PluginPool( 25 );
		
		assertTrue( pool.getDefinitions() != null );
		assertEquals( 25, pool.getMaxSimultaniousInstances() );
	}
}
