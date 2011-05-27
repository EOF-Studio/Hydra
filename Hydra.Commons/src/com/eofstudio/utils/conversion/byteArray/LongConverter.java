package com.eofstudio.utils.conversion.byteArray;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LongConverter 
{
	public static byte[] toByteArray( long value ) throws IOException 
	{
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();  
	    DataOutputStream      dos = new DataOutputStream(bos); 
	    
	    dos.writeLong( value ); 
	    
	    return  bos.toByteArray();  
	}

	public static long fromByteArray( byte[] value ) throws IOException 
	{
		return fromByteArray( new ByteArrayInputStream( value ) );
	}
	
	public static long fromByteArray( byte[] value, int offset ) throws IOException 
	{
	     return fromByteArray( new ByteArrayInputStream( value, offset, 8 ) );
	}
	
	private static long fromByteArray( ByteArrayInputStream byteArrayInputStream ) throws IOException
	{
	     DataInputStream dis = new DataInputStream( byteArrayInputStream );
	     
	     return dis.readLong();
	}
}
