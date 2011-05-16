package com.eofstudio.hydra.console;


public class Parameter implements IParameter 
{
	private Commands _Key;
	private Object _Value;
	
	public Parameter( String key, Object value )
	{
		_Key   = Commands.valueOf( key );
		_Value = value;
	}
	
	public Parameter( Commands key )
	{
		_Key   = key ;
		_Value = null;
	}
	
	public Parameter( Commands key, Object value )
	{
		_Key   = key ;
		_Value = value;
	}
	
	@Override
	public Commands getKey() 
	{
		return _Key;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getValue()
	{
		return (T) _Value;
	}

}
