package com.eofstudio.hydra.console;


public class Parameter <T extends Enum<?>> implements IParameter 
{
	private T _Key;
	private Object _Value;
	
	public Parameter( T key )
	{
		_Key   = key ;
		_Value = null;
	}
	
	public Parameter( T key, Object value )
	{
		_Key   = key ;
		_Value = value;
	}



}
