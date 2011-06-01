package com.eofstudio.hydra.commons.logging;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class HydraLog
{
	public static Logger Log;
	
	static
	{
		Log = Logger.getLogger( "Hydra" );
		
		Log.addAppender( new ConsoleAppender() );
		Log.addAppender( new FileAppender() );
		
		Log.setLevel( Level.ERROR );
	}
}
