package com.eofstudio.hydra.console.standard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.eofstudio.hydra.console.AProgram;
import com.eofstudio.hydra.console.IKeyValuesPair;
import com.eofstudio.hydra.console.InputParameters;
import com.eofstudio.hydra.core.IHydraManager;
import com.eofstudio.hydra.core.IPluginSettings;
import com.eofstudio.hydra.core.Standard.HydraManager;

public class Program extends AProgram
{
	private IHydraManager _Hydra;
	private boolean 	  _IsRunning = true;
	
	public static void main( String[] args ) throws IOException 
	{
		Program program = new Program();
		
		program._Hydra = new HydraManager( false );
		
		int port = 12345;
		
		for( IKeyValuesPair<InputParameters,String> parameter : program.ExtractParameters( args ) ) 
		{
			switch( parameter.getKey() ) 
			{
				case port:
					port = Integer.parseInt( parameter.getValue()[0] );
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
		drawMenu();
		
		while( _IsRunning )
		{
			IKeyValuesPair<Commands,String> command = waitForInput();
			
			// TODO: Based on input, execute command
			switch( command.getKey() )
			{
				case menu:
					drawMenu();
					break;
				case plugins:
					plugins();
					break;
				case exit:
					exit();
					break;
				default:
					break;
			}
			
		}
	}

	private void plugins() 
	{
		System.out.println( "Plugin ID\tName" ); 
		
		while( _Hydra.getPluginManager().getPluginSettings().hasNext() )
		{
			IPluginSettings settings = _Hydra.getPluginManager().getPluginSettings().next();
			
			System.out.println( settings.toString() );
		}
	}

	private void exit() 
	{
		System.out.println("Shutting down...");
		long start = System.currentTimeMillis();
		
		_Hydra.stop( true );
		_IsRunning = false;
		
		System.out.println("Hydra has shut down (" + (System.currentTimeMillis() - start) + "ms)");
	}

	private void showErrorLog()
	{
		// TODO: Start listening for errors
	}

	private IKeyValuesPair<Commands,String> waitForInput()
	{
		try
		{
			BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );

			return new Command( br.readLine() );
		} 
		catch( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	private void drawMenu()
	{
		System.out.println( "menu:\t\t\t\tPrints the menu." );
		System.out.println( "plugins:\t\t\tOutputs a list of the installed plugins." );
		System.out.println( "instances:\t\t\tOutput a list of the current instances" );
		System.out.println( "log (error|info|debug|off):\tOutput logging information based on the log level, default is 'error'" );
		System.out.println( "exit:\t\t\t\tExit Hydra" );
	}
}
