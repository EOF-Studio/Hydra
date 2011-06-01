package com.eofstudio.hydra.console;


public class Parameter implements IKeyValuesPair<InputParameters,String>
{
	private InputParameters _Key;
	private String[] _Value;
	
	public Parameter( InputParameters key )
	{
		_Key   = key ;
		_Value = null;
	}
	
	public Parameter( InputParameters key, String... value )
	{
		_Key   = key ;
		_Value = value;
	}

	@Override
	public InputParameters getKey() 
	{
		return _Key;
	}

	@Override
	public String[] getValue() 
	{
		return _Value;
	}
}
