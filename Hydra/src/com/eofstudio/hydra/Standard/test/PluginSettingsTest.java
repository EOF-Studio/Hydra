package com.eofstudio.hydra.Standard.test;

import com.eofstudio.hydra.commons.plugin.test.MockPlugin;
import com.eofstudio.hydra.core.IPluginSettings;
import com.eofstudio.hydra.core.Standard.PluginSettings;

import junit.framework.TestCase;

public class PluginSettingsTest extends TestCase 
{
	public void testShould_Get_ToString()
	{
		IPluginSettings settings = new PluginSettings( MockPlugin.class, 999 );
		
		
		assertEquals("                999          com.eofstudio.hydra.commons.plugin.test.MockPlugin", settings.toString() );
	}
}