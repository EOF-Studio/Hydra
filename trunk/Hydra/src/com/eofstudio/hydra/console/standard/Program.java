package com.eofstudio.hydra.console.standard;

import java.io.IOException;

import com.eofstudio.hydra.console.AProgram;
import com.eofstudio.hydra.console.IParameter;
import com.eofstudio.hydra.core.IHydraManager;
import com.eofstudio.hydra.core.Standard.HydraManager;

public class Program extends AProgram
{
	IHydraManager hydra; 
	
	public static void main( String[] args ) throws IOException 
	{
		Program program = new Program();
		
		program.hydra = new HydraManager( false );
		
		int port = 12345;
		
		for( IParameter parameter : program.ExtractParameters( args ) ) 
		{
			switch( parameter.getKey() ) 
			{
				case Port:
					port = parameter.getValue();
					break;
				default:
					break;
			}
		}
		
		program.hydra.start( port, 50 );
	}
}
