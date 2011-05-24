package com.eofstudio.hydra.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import com.eofstudio.hydra.Standard.test.*;
import com.eofstudio.hydra.plugin.test.TimePluginTest;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$

		suite.addTestSuite( HydraManagerTest.class );
		suite.addTestSuite( SocketListenerTest.class );
		suite.addTestSuite( ConnectionHandlerTest.class );
		suite.addTestSuite( TimePluginTest.class );
		
		//$JUnit-END$
		return suite;
	}

}