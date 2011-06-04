package com.eofstudio.hydra.console.standard;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.apache.log4j.Level;

import com.eofstudio.hydra.commons.exceptions.ClassNotAHydraPluginException;
import com.eofstudio.hydra.commons.logging.HydraLog;
import com.eofstudio.hydra.commons.plugin.IPlugin;
import com.eofstudio.hydra.commons.plugin.IPluginSettings;
import com.eofstudio.hydra.console.AProgram;
import com.eofstudio.hydra.console.IKeyValuesPair;
import com.eofstudio.hydra.console.InputParameters;
import com.eofstudio.hydra.core.IHydraManager;
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
//		try {
//			program._Hydra.getPluginManager().loadPlugin( new URL("file:../lib/Hydra.Test.jar"), "com.eofstudio.hydra.plugin.test.TimePlugin", "com.eofstudio.hydra.plugin.test.TimePlugin" );
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotAHydraPluginException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		program.menuLoop();
		
		Runtime.getRuntime().exit( 1 );
	}
	
	private void menuLoop()
	{
		drawMenu();
		
		while( _IsRunning )
		{
			Command command = waitForInput();
			
			switch( command.getKey() )
			{
				case menu:
					drawMenu();
					break;
				case plugin:
					handlePlugin( command );
					break;
				case instance:
					showInstances();
					break;
				case log:
					changeLogLevel( command );
					break;
				case exit:
					exit();
					break;
				case unknown:
					unknown();
					break;
				default:
					break;
			}
			
		}
	}

	private void handlePlugin( Command command ) 
	{
		if( command.getValue()  == null || command.getValue().length == 0 )
			showPluginCommands();
		else
		if( command.getValue()[0].equals( "-ls" ) )
			showPlugins();
		else
		if( command.getValue()[0].equals( "-p" ) )
			loadPlugin( command );
		else
			showPluginCommands();
	}

	private void showPluginCommands() 
	{
		System.out.println( "The load command take the forllowing parameters" );
		System.out.println( "-ls: will list all the loaded plugins " );
		System.out.println( "-p \"path/to/file\" \"classpath\" max_number_of_connections : this will load a plugin" );
	}

	private void loadPlugin( Command command ) 
	{
		if( command.getValue().length != 4 )
			showPluginCommands();
		else
		{
			try 
			{
				long start = System.currentTimeMillis();
				
				_Hydra.getPluginManager().loadPlugin( new URL( "file:" + command.getValue()[1].replace( "\"", "" ) ), command.getValue()[2].replace( "\"", "" ), command.getValue()[2].replace( "\"", "" ), Integer.parseInt( command.getValue()[3] ) );
				
				System.out.printf( "Plugin Loaded (%sms)%n", System.currentTimeMillis() - start );
			} 
			catch( FileNotFoundException e ) 
			{
				HydraLog.Log.error( "FileNotFoundException while loading plugin", e );
			} 
			catch( MalformedURLException e ) 
			{
				HydraLog.Log.error( "MalformedURLException while loading plugin", e );
			} 
			catch( ClassNotFoundException e ) 
			{
				HydraLog.Log.error( "ClassNotFoundException while loading plugin", e );
			} 
			catch( ClassNotAHydraPluginException e )
			{
				HydraLog.Log.error( "ClassNotAHydraPluginException while loading plugin", e );
			} 
		}
	}

	private void unknown() 
	{
		System.out.println( "The commands wasn't recognized, type menu for a list of all available commands" );
	}

	private void changeLogLevel( IKeyValuesPair<Commands,String> command ) 
	{
		Level logLevel = convertStringToLevel( command.getValue()[0] );
		
		System.out.printf( "Changing log level from %1s to %2s\n", HydraLog.Log.getLevel(), logLevel );
		
		HydraLog.Log.setLevel( logLevel );
	}

	private Level convertStringToLevel( String level ) 
	{
		if( level.equals( "info" ) )
			return Level.INFO;
		
		if( level.equals( "debug" ) )
			return Level.DEBUG;
		
		if( level.equals( "error" ) )
			return Level.ERROR;
		
		return Level.OFF;
	}

	private void showInstances() 
	{
		System.out.println( "[   Instance ID    |                             Name                           | # Connections | Max Connections ]" );

		Iterator<IPlugin> ite = _Hydra.getPluginManager().getPluginInstance();
		
		while( ite.hasNext() )
		{
			IPlugin instance = ite.next();
			
			System.out.println( instance.toString() );
		}
	}

	private void showPlugins() 
	{
		System.out.println( "[                         Plugin ID                            | Max Connections ]" ); 
		Iterator<IPluginSettings> ite = _Hydra.getPluginManager().getPluginSettings();
		
		while( ite.hasNext() )
		{
			IPluginSettings settings = ite.next();
			
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

	private Command waitForInput()
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
		System.out.println( "plugin:\t\t\t\tOutputs a list of the installed plugins." );
		System.out.println( "instance:\t\t\tOutput a list of the current instances" );
		System.out.println( "log (error|info|debug|off):\tOutput logging information based on the log level, default is 'error'" );
		System.out.println( "exit:\t\t\t\tExit Hydra" );
	}
}
