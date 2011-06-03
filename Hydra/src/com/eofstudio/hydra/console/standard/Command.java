package com.eofstudio.hydra.console.standard;

import com.eofstudio.hydra.console.IKeyValuesPair;

public class Command implements IKeyValuesPair<Commands,String> 
{
	private Commands _Key;
	private String[] _Value;

	public Command( String commandLine ) 
	{		
		commandLine = commandLine.toLowerCase();
		
		if( commandLine.contains( " " ) )
		{
			String[] inputArguments = commandLine.split( " " );
			
			_Key   = ParseKey( inputArguments[0] );
			_Value = new String[ inputArguments.length -1 ];
			
			System.arraycopy( inputArguments, 1, _Value, 0, _Value.length );
		}
		else
			_Key = ParseKey( commandLine );
	}

	private Commands ParseKey( String key ) 
	{
		try
		{
			return Commands.valueOf( key );
		}
		catch( IllegalArgumentException e )
		{
			return Commands.unknown;
		}
	}

	@Override
	public Commands getKey() 
	{
		return _Key;
	}

	@Override
	public String[] getValue() 
	{
		return _Value;
	}

}
