package com.eofstudio.hydra.console;

public interface IParameter 
{
	public Commands getKey();
	public <T> T    getValue();
}
