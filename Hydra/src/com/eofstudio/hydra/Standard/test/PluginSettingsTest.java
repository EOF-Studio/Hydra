package com.eofstudio.hydra.Standard.test;

import com.eofstudio.hydra.commons.plugin.IPluginSettings;
import com.eofstudio.hydra.commons.plugin.standard.PluginSettings;
import com.eofstudio.hydra.commons.plugin.test.MockPlugin;

import junit.framework.TestCase;

public class PluginSettingsTest extends TestCase 
{
	public void testShould_Get_ToString()
	{
		IPluginSettings settings = new PluginSettings( MockPlugin.class, "com.eofstudio.hydra.commons.plugin.test.MockPlugin", 1 );

		assertEquals("            com.eofstudio.hydra.commons.plugin.test.MockPlugin                 1 ", settings.toString() );
	}
}