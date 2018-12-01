package it.unibs.dii.pajc.briscola.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import it.unibs.dii.pajc.briscola.utility.Logger;
import it.unibs.dii.pajc.briscola.utility.MessaggiDiServizio;

public class MainClient 
{	
	private static Logger logger;
	private static final String posizioneFileDiLog = "C:\\Program Files\\JAVA-DevelopmentKit\\workspace\\BriscolaServer\\src\\fileDiLog\\LogClient";
	
	public static void main(String[] args) 
	{
		GestoreClient client = null;
		
		try 
		{
			client = new GestoreClient();
			client.provaConnessione();
			client.creaStreams();
			client.creaGUI();
			client.ascoltaMessaggiS_C();
			
		} 
		catch (IOException e) 
		{
			logger.scriviLog(MessaggiDiServizio.ERRORE_MAIN_CLIENT);
		}
		finally
		{
			client.close();
		}
	}
}
