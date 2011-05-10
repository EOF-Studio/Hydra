package com.eofstudio.hydra.core.Standard;

//import com.geckon.conversion.standard.ArrayExpert;
//
//import dk.fyhr.hydra.commons.packet.*;
//import dk.fyhr.hydra.commons.packet.standard.*;
//import dk.fyhr.hydra.core.IPacketAnalyser;
//
//public class PacketAnalyser implements IPacketAnalyser
//{
//	private static final int HEADER_LENGTH = 6;
//
//	@Override
//	public IRequestPacket parsePacket( byte[] packetData )
//	{
//		if( packetData == null )
//			throw new NullPointerException( "packetData cannot be null" );
//		
//		if( packetData.length < HEADER_LENGTH )
//			throw new ArrayIndexOutOfBoundsException( "The input data doesn't meet the minimum length requirement" );
//		
//		IRequestHeader  header  = new Header( ArrayExpert.slice( packetData, 0, 6 ) );
//		IRequestContent content = new RequestContent( header.getContentLength(), ArrayExpert.slice( packetData, 6, packetData.length - 6 ) );
//		
//		return new Packet( header, content );
//	}
//
//	@Override
//	public void parsePacket( IRequestPacket packet, byte[] packetData )
//	{
//		packet.getContent().append( packetData );
//	}
//}