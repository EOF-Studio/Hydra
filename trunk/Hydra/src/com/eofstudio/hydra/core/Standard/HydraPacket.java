package com.eofstudio.hydra.core.Standard;

import java.io.IOException;
import java.net.Socket;

import com.eofstudio.hydra.core.IHydraPacket;

public class HydraPacket implements IHydraPacket 
{
	private byte[] currentBuffer = new byte[0];
	private long   version       = Long.MIN_VALUE;
	private long   pluginID      = Long.MIN_VALUE;
	private long   instanceID    = Long.MIN_VALUE;
	
	@Override
	public byte[] getCurrentBuffer() { return currentBuffer; }
	
	@Override
	public long getVersion(){ return version; }
	
	@Override
	public long getPluginID(){ return pluginID; }
	
	@Override
	public long getInstanceID(){ return instanceID; }
	
	public HydraPacket( Socket socket ) throws InvalidHydraPacketException 
	{
		try 
		{
			int size = 0;
			
			while( (size = socket.getInputStream().available()) != 0 )
			{
				byte[] buffer = new byte[size];
				
				socket.getInputStream().read( buffer );

				appendData( buffer );
			}
		} 
		catch( IOException e ) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		processInitialData();
	}

	private void processInitialData() throws InvalidHydraPacketException
	{
		if( currentBuffer.length < 8 )
			throw new InvalidHydraPacketException("Packet is not a valid hydra packet");
		
		version  = byteArrayToInt( currentBuffer, 0 );
		pluginID = byteArrayToInt( currentBuffer, 4 );
			
		if( currentBuffer.length >= 12 )
			instanceID = byteArrayToInt( currentBuffer, 8 );
	}

	private long byteArrayToInt( byte[] b, int offset ) 
	{
		long value = 0;
        for( int i = 0; i < 4; i++) 
        {
            int shift = (4 - 1 - i) * 8;
            value += ( b[i + offset] & 0x000000FF) << shift;
        }
        return value;
    }
	
	private void appendData( byte[] dataToAppend ) 
	{
		byte[] newArray = new byte[ currentBuffer.length + dataToAppend.length ];

		System.arraycopy( currentBuffer, 0, newArray, 0, currentBuffer.length );
		System.arraycopy( dataToAppend, 0, newArray, currentBuffer.length, dataToAppend.length );
		
		currentBuffer = newArray;
	}
}
