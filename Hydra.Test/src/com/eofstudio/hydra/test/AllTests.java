package com.eofstudio.hydra.test;

import org.apache.log4j.Level;

import junit.framework.Test;
import junit.framework.TestSuite;
import com.eofstudio.hydra.Standard.test.*;
import com.eofstudio.hydra.commons.logging.HydraLog;
import com.eofstudio.hydra.commons.plugin.test.APluginTest;
import com.eofstudio.hydra.plugin.test.TimePluginTest;

public class AllTests {

	public static Test suite() 
	{
		HydraLog.Log.setLevel( Level.DEBUG );
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$

		suite.addTestSuite( HydraManagerTest.class );
		suite.addTestSuite( SocketListenerTest.class );
		suite.addTestSuite( ConnectionHandlerTest.class );
		suite.addTestSuite( PluginSettingsTest.class );
		suite.addTestSuite( TimePluginTest.class );
		suite.addTestSuite( APluginTest.class );
		suite.addTestSuite( PluginPoolTest.class );
		
		//$JUnit-END$
		return suite;
	}

}
