package com.eofstudio.hydra.core.Standard;

import java.io.IOException;
import java.net.Socket;

import com.eofstudio.hydra.commons.exceptions.InvalidHydraPacketException;
import com.eofstudio.hydra.commons.plugin.IHydraPacket;
import com.eofstudio.utils.conversion.byteArray.IntConverter;
import com.eofstudio.utils.conversion.byteArray.LongConverter;

public class HydraPacket implements IHydraPacket 
{
	private byte[] _CurrentBuffer = new byte[0];
	private long   _Version       = Long.MIN_VALUE;
	private String _PluginID      = null;
	private long   _InstanceID    = Long.MIN_VALUE;
	private Socket _Socket		  = null;
	
	@Override
	public Socket getSocket() { return _Socket; }
	
	@Override
	public long getVersion(){ return _Version; }
	
	@Override
	public String getPluginID(){ return _PluginID; }
	
	@Override
	public long getInstanceID(){ return _InstanceID; }
	
	@Override
	public void setInstanceID( long id ){ _InstanceID = id; }
	
	public HydraPacket( Socket socket ) throws InvalidHydraPacketException, IOException 
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

	private void processInitialData( byte[] data ) throws InvalidHydraPacketException, IOException
	{
		if( _CurrentBuffer.length < 8 )
			throw new InvalidHydraPacketException("Packet is not a valid hydra packet");
		
		_Version  = LongConverter.fromByteArray( _CurrentBuffer, 0 );
		
		int length = IntConverter.fromByteArray( _CurrentBuffer, 8 );
		
		_PluginID = new String( data, 12, length );
			
		if( _CurrentBuffer.length > 12 + length )
			_InstanceID = LongConverter.fromByteArray( _CurrentBuffer, 12 + length );
	}

	protected void appendData( byte[] dataToAppend ) 
	{
		byte[] newArray = new byte[ _CurrentBuffer.length + dataToAppend.length ];

		System.arraycopy( _CurrentBuffer, 0, newArray, 0, _CurrentBuffer.length );
		System.arraycopy( dataToAppend, 0, newArray, _CurrentBuffer.length, dataToAppend.length );
		
		_CurrentBuffer = newArray;
	}
}
