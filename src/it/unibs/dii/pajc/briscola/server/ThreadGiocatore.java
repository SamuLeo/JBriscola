package it.unibs.dii.pajc.briscola.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import it.unibs.dii.pajc.briscola.utility.Carta;
import it.unibs.dii.pajc.briscola.utility.Logger;
import it.unibs.dii.pajc.briscola.utility.MessaggiDiServizio;
import it.unibs.dii.pajc.briscola.utility.MessaggioC_S;
import it.unibs.dii.pajc.briscola.utility.MessaggioS_C;

public class ThreadGiocatore extends Thread
{
	public static final String PARTITA_INIZIATA = "S_C COD1" ;
	
	private Socket socket;
	private  ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;

	private boolean partitaInCorso = false;
	private ThreadPartita threadPartita;
	private boolean threadAttivo = true;
	
	private Queue<MessaggioC_S> codaComunicazioni = null;
	private Queue<MessaggioC_S> codaMessaggiDati = null;

	private Logger logger;
	private static final String posizioneFileDiLog = "C:\\Program Files\\JAVA-DevelopmentKit\\workspace\\BriscolaServer\\src\\fileDiLog\\LogServer";
	
	public ThreadGiocatore(Socket socket)
	{
		logger = new Logger(posizioneFileDiLog);
		
		this.socket=socket;		
		codaComunicazioni = new LinkedList<>();
		codaMessaggiDati = new LinkedList<>(); 
	}

	@Override
	public void run() 
	{
		try
		{
			createStreams();
			
			ascoltaMessaggi();
			
			daemonControlloComunicazioni();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		finally
		{
			close();
		}
	}
	
	private void createStreams () throws IOException 
	{
		outputStream = new ObjectOutputStream (socket.getOutputStream());
		outputStream.flush();
		
		inputStream = new ObjectInputStream(socket.getInputStream());
		logger.scriviLog("SERVER -> STREAM CREATI");
	}

	private void ascoltaMessaggi() throws IOException 
	{	

		while(true)
		{
			MessaggioC_S messaggio = leggiMessaggio();
			if(messaggio != null)
			{
				logger.scriviLog("Messaggio arrivato al server" + messaggio.toString());
				smistaMessaggi(messaggio);
			}
		}
	}
	
	private  MessaggioC_S leggiMessaggio()
	{
		MessaggioC_S messaggioC_S = null;
		
		try 
		{
			messaggioC_S =  (MessaggioC_S)inputStream.readObject();
		} 
		catch (ClassNotFoundException | IOException e) 
		{
		}
		return messaggioC_S;
	}
	
	private void smistaMessaggi(MessaggioC_S messaggio) 
	{
		if(messaggio.isFlagComunicazione())	
			codaComunicazioni.add(messaggio);
		else
			aggiungiMessaggioDatiAllaCoda(messaggio);
	}
	
	private void daemonControlloComunicazioni()
	{
		Timer ascolto = new Timer();
		ascolto.schedule(
				new TimerTask()
				{
					public void run()
					{
						MessaggioC_S messaggio = controllaCodaComunicazioni();
						
						if(messaggio != null)
						{
							String codMessaggio = codaComunicazioni.poll().getComunicazione();
							
							if(codMessaggio.equals(MessaggiDiServizio.CHIUSURA_CONNESSIONE))
							{
								threadAttivo = false;
							}
						}		
					}
				}, 0, 1000);		
	}
	
	
	public MessaggioC_S controllaCodaComunicazioni()
	{
		if(codaComunicazioni.isEmpty() == false)
			return codaComunicazioni.peek();
		else
			return null;
	}
	
	protected void inviaMessaggioDati( 
			
			boolean vittoriaPartita,
			boolean proprioTurno,
			boolean vittoriaTurno,
			LinkedList<Carta> carteInMano,
			Carta briscolaSulTavolo,
			Carta ultimaCartaGiocataAvversario,
			int carteRimanentiMazzo
			) 
	{
		
		MessaggioS_C messaggioS_C = new MessaggioS_C(
				partitaInCorso,
				vittoriaPartita,
				proprioTurno,
				vittoriaTurno,
				carteInMano,
				briscolaSulTavolo,
				ultimaCartaGiocataAvversario,
				carteRimanentiMazzo
				);
		
		try 
		{
			outputStream.writeObject(messaggioS_C);
			outputStream.flush();
		} 
		catch 
		(IOException e) 
		{
			logger.scriviLog(MessaggiDiServizio.ERRORE_INVIO_MESSAGGIO_DATI_S_C);
		}
	}
	
	synchronized public void inviaComunicazione(String comunicazione ) 
	{
		
		MessaggioS_C messaggioS_C = new MessaggioS_C(comunicazione);				
		
		try 
		{
			outputStream.writeObject(messaggioS_C);
			outputStream.flush();
		} 
		catch 
		(IOException e) 
		{
			logger.scriviLog(MessaggiDiServizio.ERRORE_INVIO_COMUNICAZIONE_S_C);
		}
	}
	
	public void close()
	{
		try 
		{
			if(outputStream != null && inputStream != null && socket != null) 
			{
				outputStream.close();
				inputStream.close();
				socket.close();
			}
		} 
		catch (IOException e) 
		{
			logger.scriviLog(MessaggiDiServizio.ERRORE_CHIUSURA_STREAM_SERVER);
		}
		logger.scriviLog("SERVER -> CHIUSURA CONNESSIONE SOCKET");

	}
	
	protected void aggiungiMessaggioDatiAllaCoda(MessaggioC_S messaggio)
	{
		logger.scriviLog("Aggiunta alla coda di threadPartita del messaggio:" + messaggio.toString());
		codaMessaggiDati.add(messaggio);
	}
	
	synchronized public MessaggioC_S controllaCodaMessaggiDati()
	{	    
		if(codaMessaggiDati.isEmpty() == false)
		{	
			for(MessaggioC_S messaggio : codaMessaggiDati)
				logger.scriviLog( messaggio.toString() + "\n");
			return codaMessaggiDati.poll();
		}
		else
			return null;
	}
	
	public boolean equals(ThreadGiocatore giocatoreDaConfrontare)
	{
		return this.getName().equals(giocatoreDaConfrontare.getName());		
	}
	
	public boolean isPartitaInCorso() 
	{
		return partitaInCorso;
	}

	public void setPartitaInCorso(boolean partitaInCorso) 
	{
		this.partitaInCorso = partitaInCorso;
	}
	
	public ThreadPartita getThreadPartita() 
	{
		return threadPartita;
	}

	public void setThreadPartita(ThreadPartita threadPartita) 
	{
		this.threadPartita = threadPartita;
	}

	public boolean isThreadAttivo() 
	{
		return threadAttivo;
	}

	public void setThreadAttivo(boolean threadAttivo) 
	{
		this.threadAttivo = threadAttivo;
	}	
}
