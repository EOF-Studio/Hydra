package com.eofstudio.hydra.console;

public interface IKeyValuesPair <T extends Enum<?>,E>
{
	public T getKey();
	public E[] getValue();
}
