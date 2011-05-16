package com.eofstudio.hydra.console;

import java.util.ArrayList;
import java.util.List;

public abstract class AProgram 
{
	protected void OnInputError( String message, Object... input )
	{
		throw new IllegalArgumentException( String.format( message, input ) );
	}
	
	protected List<IParameter> ExtractParameters( String[] args )
	{
		List<IParameter> parameters = new ArrayList<IParameter>();
		
		for( int i = 0; i < args.length; i++ )
		{
			try
			{
				switch( Commands.valueOf( args[i] ) ) 
				{
					case Port:
						if( i + 1 >= args.length )
							throw new IllegalArgumentException();
							
						parameters.add( new Parameter( Commands.Port, Integer.parseInt( args[ i+1 ] ) ) );
						i++;
						break;
				
					default:
						OnInputError( "The parameter '%1s' with the value '%2s' is not recognized", args[i], i + 1 >= args.length ? null : args[i+1] );
						break;
				}
				
			}
			catch( IllegalArgumentException e )
			{
				OnInputError( "The parameter '%1s' with the value '%2s' is not recognized", args[i], i + 1 >= args.length ? null : args[i+1] );
			}
		}
		
		return parameters;
	}
}
