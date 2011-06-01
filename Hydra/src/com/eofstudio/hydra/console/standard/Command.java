package com.eofstudio.hydra.console.standard;

import com.eofstudio.hydra.console.IKeyValuesPair;

public class Command implements IKeyValuesPair<Commands,String> 
{
	private Commands _Key;
	private String[]   _Value;

	public Command( String commandLine ) 
	{		
		commandLine = commandLine.toLowerCase();
		
		// TODO: Error handling, for incorrect commands
		if( commandLine.contains( " " ) )
		{
			String[] inputArguments = commandLine.split( " " );
			
			_Key   = Commands.valueOf( inputArguments[0] );
			_Value = new String[ inputArguments.length -1 ];
			
			System.arraycopy( inputArguments, 1, _Value, 0, _Value.length );
		}
		else
			_Key = Commands.valueOf( commandLine );
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
