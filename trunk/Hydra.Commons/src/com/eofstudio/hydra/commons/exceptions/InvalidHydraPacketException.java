package com.eofstudio.hydra.commons.exceptions;

public class InvalidHydraPacketException extends Exception
{
	private static final long serialVersionUID = 1350791909340035952L;

	public InvalidHydraPacketException()
	{
		super();
	}

	public InvalidHydraPacketException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}

	public InvalidHydraPacketException(String arg0)
	{
		super(arg0);
	}

	public InvalidHydraPacketException(Throwable arg0)
	{
		super(arg0);
	}
}
