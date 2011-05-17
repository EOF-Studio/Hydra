package com.eofstudio.hydra.core.Standard;

import java.io.IOException;
import java.net.Socket;

import com.eofstudio.hydra.commons.exceptions.InvalidHydraPacketException;
import com.eofstudio.hydra.commons.plugin.IHydraPacket;

public class HydraPacket implements IHydraPacket 
{
	private byte[] _CurrentBuffer = new byte[0];
	private long   _Version       = Long.MIN_VALUE;
	private long   _PluginID      = Long.MIN_VALUE;
	private String _InstanceID    = null;
	private Socket _Socket		  = null;
	
	@Override
	public Socket getSocket() { return _Socket; }
	
	@Override
	public long getVersion(){ return _Version; }
	
	@Override
	public long getPluginID(){ return _PluginID; }
	
	@Override
	public String getInstanceID(){ return _InstanceID; }
	
	@Override
	public void setInstanceID( String id ){ _InstanceID = id; }
	
	public HydraPacket( Socket socket ) throws InvalidHydraPacketException 
	{
		_Socket = socket;

		processInitialData( getCurrentBuffer() );
	}
	
	@Override
	public byte[] getCurrentBuffer() 
	{
		synchronized( _CurrentBuffer )
		{
			_CurrentBuffer = new byte[0];
			
			try 
			{
				int size = 0;
				
				while( (size = _Socket.getInputStream().available()) != 0 )
				{
					byte[] buffer = new byte[size];
					
					_Socket.getInputStream().read( buffer );
	
					appendData( buffer );
				}
			} 
			catch( IOException e ) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return _CurrentBuffer; 
		
	}

	private void processInitialData( byte[] data ) throws InvalidHydraPacketException
	{
		if( _CurrentBuffer.length < 8 )
			throw new InvalidHydraPacketException("Packet is not a valid hydra packet");
		
		_Version  = byteArrayToInt( _CurrentBuffer, 0 );
		_PluginID = byteArrayToInt( _CurrentBuffer, 4 );
			
		if( _CurrentBuffer.length >= 12 )
			_InstanceID = Long.toString( byteArrayToInt( _CurrentBuffer, 8 ) );
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
	
	protected void appendData( byte[] dataToAppend ) 
	{
		byte[] newArray = new byte[ _CurrentBuffer.length + dataToAppend.length ];

		System.arraycopy( _CurrentBuffer, 0, newArray, 0, _CurrentBuffer.length );
		System.arraycopy( dataToAppend, 0, newArray, _CurrentBuffer.length, dataToAppend.length );
		
		_CurrentBuffer = newArray;
	}
}
