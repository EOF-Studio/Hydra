package com.eofstudio.hydra.console.standard;

import java.io.BufferedReader;
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
			ICommand command = WaitForInput();
			
			DrawMenu();
		}
	}

	private void ShowErrorLog()
	{
		// TODO: Start listening for errors
	}

	private ICommand WaitForInput()
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
		System.out.println( "plugins:\t\t\tOutputs a list of the installed plugins." );
		System.out.println( "instances:\t\t\tOutput a list of the current instances" );
		System.out.println( "log (error|info|debug|off):\tOutput logging information based on the log level, default is 'error'" );
		System.out.println( "exit:\t\t\t\tExit Hydra" );
	}
}
