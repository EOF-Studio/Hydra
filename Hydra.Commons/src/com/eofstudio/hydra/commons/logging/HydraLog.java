package com.eofstudio.hydra.commons.logging;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class HydraLog
{
	public static Logger Log;
	
	static
	{
		try 
		{
			Log = Logger.getLogger( "Hydra" );
			
			Log.addAppender( new ConsoleAppender( new PatternLayout( "%d{yyyy.MM.dd HH:mm:ss,SSS} [%p] %m" ) ) );
			Log.addAppender( new FileAppender(    new PatternLayout( "%d{yyyy.MM.dd HH:mm:ss,SSS} [%p] %m" ), "hydra.log" ) );
			
			Log.setLevel( Level.ERROR );
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
