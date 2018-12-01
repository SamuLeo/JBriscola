package it.unibs.dii.pajc.briscola.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import it.unibs.dii.pajc.briscola.server.ThreadPartita;
import it.unibs.dii.pajc.briscola.utility.Carta;
import it.unibs.dii.pajc.briscola.utility.Logger;
import it.unibs.dii.pajc.briscola.utility.MessaggiDiServizio;
import it.unibs.dii.pajc.briscola.utility.MessaggioC_S;
import it.unibs.dii.pajc.briscola.utility.MessaggioS_C;
/**
 * Questa classe è la classe di utility per la gestione del client, presenta metodi per instaurare una connessione con il server,
 * per creare la GUI e ascoltare i messaggiS_C provenienti dal Server, oltre a classi per comunicare messaggiC_S
 * * @author Samuele Leone
 *
 */
public class GestoreClient
{
	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	
	private JFrameMenuClient window = null;

	private Logger logger;
	private static final String posizioneFileDiLog = "C:\\Program Files\\JAVA-DevelopmentKit\\workspace\\BriscolaServer\\src\\fileDiLog\\LogClient";
	
	
	/**
	 * Costruttore della classe, inizializza il Logger
	 */
	public GestoreClient()
	{
		logger = new Logger(posizioneFileDiLog);
	}
	
	/**
	 * Questo metodo serve per tentare una connessione con il server
	 * @throws IOException
	 */
	public void provaConnessione() throws IOException
	{
		socket = new Socket ("localhost", 8189);
		logger.scriviLog("CLIENT-LOG-GESTORECLIENT : Connessione avvenuta verso "+ socket.getInetAddress()+" alla porta remota " + socket.getPort() + " e alla porta locale " + socket.getLocalPort());
	}
	
	/**
	 * Questo metodo serve per creare gli stream di oggetti con il server una volta instaurata una connessione con esso
	 * @throws IOException
	 */
	public void creaStreams() throws IOException
	{
		outputStream = new ObjectOutputStream (socket.getOutputStream());
		outputStream.flush();
		
		inputStream = new ObjectInputStream(socket.getInputStream());
				
		logger.scriviLog(MessaggiDiServizio.LOG_GESTORECLIENT_STREAM_CREATI);
	}
	
	/**
	 * Questo metodo crea la finestra in cui verrà elaborata la GUI, aggiunge inoltre una conferma di chiusura alla finestra
	 * @throws IOException
	 */
	public void creaGUI() throws IOException
	{		
		window = new JFrameMenuClient(this);
		if(window != null)
		{
			window.addWindowListener(
					new WindowAdapter()
					{
						public void windowClosing(WindowEvent e)
						{
							JOptionPane msgBoxConfermaChiusura = new JOptionPane();
							msgBoxConfermaChiusura.setBounds(new Rectangle(window.getWidth()/3, window.getHeight()/4));
							int risposta = msgBoxConfermaChiusura.showConfirmDialog(window, "Premere OK per confermare l'uscita dal gioco","",JOptionPane.OK_CANCEL_OPTION);
							if(risposta == JOptionPane.OK_OPTION)
							{
								inviaComunicazione(MessaggiDiServizio.CHIUSURA_CONNESSIONE);
//								logger.scriviLog("CLIENT -> MESSAGGIO DI CHIUSURA CONNESSIONE INVIATO CORRETTAMENTE");
								close();
								window.dispose();
								System.exit(0);
							}
						}
					});	
		
		}
	}
	/**
	 * Questo metodo si mette in ascolto dei messaggi provenienti dal Server fino a che la sessione Client non viene interrotta
	 */
	public void ascoltaMessaggiS_C()
	{
		logger.scriviLog("GestoreClient in ascolto dei messaggi");
		while(true) 
		{
			try
			{
				MessaggioS_C messaggioS_C = (MessaggioS_C)inputStream.readObject();
				logger.scriviLog("Messaggio arrivato al threadClient");
				if(messaggioS_C.isComunicazione() == false)
					window.acquisisciMessaggi(messaggioS_C);
				else
				{
					elaboraMessaggioComunicazione(messaggioS_C);
				}				
			}
			catch (ClassNotFoundException | IOException e) 
			{
			}
		}
	}
	/**
	 * Questo metodo elabora i messaggi di comunicazione acquisiti da ascoltaMessaggiS_C()
	 * @param messaggioS_C messaggio di comunicazione arrivato al Client
	 */
	private void elaboraMessaggioComunicazione(MessaggioS_C messaggioS_C)
	{
		String codMessaggio = messaggioS_C.getComunicazione();
		if(codMessaggio.equals(MessaggiDiServizio.CHIUSURA_CONNESSIONE))
		{
	     	JOptionPane msgBoxConfermaChiusura = new JOptionPane();
			msgBoxConfermaChiusura.setBounds(new Rectangle(window.getWidth()/3, window.getHeight()/4));
			msgBoxConfermaChiusura.showConfirmDialog(window, "La partita è stata interrotta","",JOptionPane.OK_OPTION);
			close();
			System.exit(0);
		}
	}
	
	/**
	 * Questo metodo si occupa di inviare con l'ausilio del try catch un messaggio dati al Server
	 * @param messaggio messaggio dati da inviare al Server
	 */
	public void inviaMessaggioDati(MessaggioC_S messaggio ) 
	{		
		try 
		{
			logger.scriviLog("Il GestoreClient sta inviando il messaggio" + messaggio.toString());
			outputStream.writeObject(messaggio);
			outputStream.flush();
		} 
		catch 
		(IOException e) 
		{
			logger.scriviLog(MessaggiDiServizio.ERRORE_INVIO_MESSAGGIO_DATI_C_S);
		}
	}
	
	/**
	 * Questo metodo si occupa di inviare con l'ausilio del try catch un messaggio comunicazione al Server
	 * @param messaggio messaggio comunicazione da inviare al Server
	 */
	public void inviaComunicazione(String comunicazione ) 
	{
		
		MessaggioC_S messaggio = new MessaggioC_S(comunicazione);				
		
		try 
		{
			outputStream.writeObject(messaggio);
			outputStream.flush();
		} 
		catch 
		(IOException e) 
		{
			logger.scriviLog(MessaggiDiServizio.ERRORE_INVIO_COMUNICAZIONE_C_S);
		}
	}
	
	/**
	 * Questo metodo chiude gli Stream con il server, con l'ausilio del try catch
	 */
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
			logger.scriviLog(MessaggiDiServizio.ERRORE_CHIUSURA_STREAM_CLIENT);
		}
		logger.scriviLog(MessaggiDiServizio.LOG_GESTORECLIENT_CHIUSURA_CONNESSIONE);
	}
}

