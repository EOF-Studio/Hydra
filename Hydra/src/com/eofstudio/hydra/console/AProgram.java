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
			if( !args[i].startsWith( "-" ) )
				OnInputError( "The parameter '%1s' with the value '%2s' is not recognized", args[i], i + 1 >= args.length ? null : args[i+1] );
			
			String key   = args[i];
			String value = null;
			
			if( !args[i + 1].startsWith( "-" ) )
			{
				value = args[i + 1];
				i++;
			}
			
			
			
			parameters.add( new Parameter( InputParameters.valueOf( key.substring( 1 ).toLowerCase() ), value ) );
		}
		
		return parameters;
	}
}
