package com.eofstudio.hydra.commons.plugin.test;

import com.eofstudio.hydra.commons.plugin.APlugin;

public class MockPlugin extends APlugin 
{
	@Override
	public long getPluginID() 
	{
		return 999;
	}

	@Override
	public void run() 
	{
	}

}
