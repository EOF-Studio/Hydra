package com.eofstudio.hydra.Standard.test;

import java.util.Observable;
import java.util.Observer;

import com.eofstudio.hydra.core.IConnectionHandler;
import com.eofstudio.hydra.core.Standard.ConnectionHandler;

import junit.framework.TestCase;

public class ConnectionHandlerTest extends TestCase implements Observer
{
	public void testShouldInitializeConnectionHandler()
	{
		IConnectionHandler handler = new ConnectionHandler( this );
	
		assertNotNull( handler );
	}

	@Override
	public void update( Observable o, Object arg )
	{
	}
}
