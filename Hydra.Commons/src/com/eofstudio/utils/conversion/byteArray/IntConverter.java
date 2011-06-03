package com.eofstudio.utils.conversion.byteArray;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IntConverter 
{
	public static byte[] toByteArray( int value ) throws IOException 
	{
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();  
	    DataOutputStream      dos = new DataOutputStream(bos); 
	    
	    dos.writeInt( value ); 
	    
	    return  bos.toByteArray();  
	}

	public static int fromByteArray( byte[] value ) throws IOException 
	{
		return fromByteArray( new ByteArrayInputStream( value ) );
	}
	
	public static int fromByteArray( byte[] value, int offset ) throws IOException 
	{
	     return fromByteArray( new ByteArrayInputStream( value, offset, 4 ) );
	}
	
	private static int fromByteArray( ByteArrayInputStream byteArrayInputStream ) throws IOException
	{
	     DataInputStream dis = new DataInputStream( byteArrayInputStream );
	     
	     return dis.readInt();
	}
}
