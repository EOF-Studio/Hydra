package com.eofstudio.hydra.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import com.eofstudio.hydra.Standard.test.*;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$

		suite.addTestSuite( HydraManagerTest.class );
		suite.addTestSuite( SocketListenerTest.class );
		suite.addTestSuite( ConnectionHandlerTest.class );
		
		//$JUnit-END$
		return suite;
	}

}
