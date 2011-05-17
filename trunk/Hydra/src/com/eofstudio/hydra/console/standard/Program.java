package com.eofstudio.hydra.console.standard;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

import com.eofstudio.hydra.console.AProgram;
import com.eofstudio.hydra.console.IParameter;
import com.eofstudio.hydra.core.IHydraManager;
import com.eofstudio.hydra.core.Standard.HydraManager;

public class Program extends AProgram
{
	private IHydraManager _Hydra;
	
	public static void main( String[] args ) throws IOException 
	{
		Program program = new Program();
		
		program._Hydra = new HydraManager( false );
		
		int port = 12345;
		
		for( IParameter parameter : program.ExtractParameters( args ) ) 
		{
			switch( parameter.getKey() ) 
			{
				case port:
					port = Integer.parseInt( parameter.<String>getValue() );
					break;
				default:
					break;
			}
		}
		
		program._Hydra.start( port, 50 );
		program.menuLoop();
		
		Runtime.getRuntime().exit( 1 );
	}
	
	private void menuLoop()
	{
		while( true )
		{
			DrawMenu();

			switch( WaitForInput() )
			{
				case 0:
					System.out.println( 0 );
					break;
				case 1:
					System.out.println( 1 );
					break;
				case 2:
					ShowErrorLog();
					break;
				case 9:
					System.out.println( "Hydra is shutting down" );
					_Hydra.stop( true );
					return;
				default:
					System.out.println( -1 );
					break;
			}
		}
	}

	private void ShowErrorLog()
	{
		// TODO: Start listening for errors
	}

	private int WaitForInput()
	{
		try
		{
			BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );

			return Integer.parseInt( br.readLine() );
		} 
		catch( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}

	private void DrawMenu()
	{
		ClearConsole();
		
		System.out.println( "*******************************" );
		System.out.println( "*******************************" );
		System.out.println( "**       OCTOPUS AGENT       **" );
		System.out.println( "*******************************" );
		System.out.println( "** 0. Installed Plugins      **" );
		System.out.println( "** 1. Current Instances      **" );
		System.out.println( "** 2. Error Log              **" );
		System.out.println( "**                           **" );
		System.out.println( "** 9. Exit                   **" );
		System.out.println( "*******************************" );
		System.out.println( "*******************************" );
	}

	private void ClearConsole()
	{
		// TODO: Temporary hack to clear the console, should be replaced with a proper console implementation when there is time
		System.out.println( "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
				            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
				            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
				            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" );
	}
}
