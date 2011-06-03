package com.eofstudio.hydra.plugin.test;

import java.io.IOException;
import java.util.Calendar;

import com.eofstudio.hydra.commons.logging.HydraLog;
import com.eofstudio.hydra.commons.plugin.APlugin;

public class TimePlugin extends APlugin 
{	
	@Override
	public void run() 
	{
		while( true )
		{
			try 
			{
				if( getActiveConnections().size() == 0 )
				{
					Thread.sleep( 25 );
					continue;
				}
				
				for( int i = 0; i < getActiveConnections().size(); i++ )
				{
					if( getActiveConnections().get( i ).getSocket().getInputStream().available() == 0 )
						continue;

					int size = 0;
					
					while( (size = getActiveConnections().get( i ).getSocket().getInputStream().available()) != 0 )
					{
						byte[] buffer = new byte[size];
						
						getActiveConnections().get( i ).getSocket().getInputStream().read( buffer );
						
						if( buffer[0] != 0x01 )
						{
							HydraLog.Log.debug(String.format("buffer length", buffer.length));
							
							return;
						}
						
						SendResponse( getActiveConnections().get( i ).getSocket(), Calendar.getInstance().getTime().toString().getBytes() );
						return;
					}
				}
			} 
			catch( InterruptedException e ) 
			{
				// This Interrupt is allowed
			} 
			catch( IOException e) 
			{
				// TODO Add code to handle lost connections
				e.printStackTrace();
			}
		}
	}
}
