package it.unibs.dii.pajc.briscola.client;

import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import it.unibs.dii.pajc.briscola.utility.Logger;
import it.unibs.dii.pajc.briscola.utility.MessaggiDiServizio;
import it.unibs.dii.pajc.briscola.utility.MessaggioS_C;

import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;



/**
 * Questa classe serve per gestire la GUI 
 * @author Samuele Leone
 *
 */
public class JFrameMenuClient extends JFrame
{
	public static final String ERRORE_CREAZIONE_GUI = "ERRORE-CLIENT-GUI 1: Fallita creazione GUI Client";
	public static final String ERRORE_TIMER = "ERRORE-CLIENT-GUI : Errore nel bloccare il TimerTask";
	
	private int screenHeight;
	private int screenWidth;
	
	private JPanelSchermataIniziale jPanelSchermataIniziale = null;
	private JPanelPartita pannelloPartita = null;
	
	private GestoreClient gestoreClient = null;
	
	private Queue<MessaggioS_C> codaMessaggiS_C = null;
	
	int contatoreMessaggi = 0;
	
	private Logger logger;
	private static final String posizioneFileDiLog = "C:\\Program Files\\JAVA-DevelopmentKit\\workspace\\BriscolaServer\\src\\fileDiLog\\LogClient";
	
	
	/**
	 * Il costruttore della classe crea la finestra, inserisce la schermata iniziale e successivamente controlla
	 * @param gestoreClient
	 */
    public JFrameMenuClient(GestoreClient gestoreClient)
    {	
		logger = new Logger(posizioneFileDiLog);
		
		codaMessaggiS_C = new LinkedList<>();
		this.gestoreClient = gestoreClient;
		
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					disegnaFinestra();
					
					disegnaSchermataIniziale();
					
					controlloMessaggiInCoda();
				}
				catch (Exception e) 
				{
					logger.scriviLog(ERRORE_CREAZIONE_GUI);
				}
			}
		});
    }

	private void disegnaFinestra() 
	{
    	calcolaMisureSchermo();
    	
		setTitle("JBriscola");
		getContentPane().setForeground(new Color(34, 139, 34));
		setBounds((screenWidth-screenWidth*4/5)/2, (screenHeight-screenHeight*4/5)/2, screenWidth*4/5, screenHeight*4/5);
//		setBounds(0, 0, screenWidth, screenHeight);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		getContentPane().setLayout(new BorderLayout());
	}
	
	private void calcolaMisureSchermo()
    {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		screenHeight = screenSize.height;
		screenWidth = screenSize.width;
    }
	
	private void disegnaSchermataIniziale() 
	{
		jPanelSchermataIniziale = new JPanelSchermataIniziale();		
		jPanelSchermataIniziale.setBounds(0, 0, getWidth(), getHeight());
		getContentPane().add(jPanelSchermataIniziale);
//		jPanelSchermataIniziale.setLayout(null);	
	}
	
	public void acquisisciMessaggi(MessaggioS_C messaggio)
	{
		codaMessaggiS_C.add(messaggio);
		logger.scriviLog("Messaggio arrivato al JFrame");
		for(MessaggioS_C mex : codaMessaggiS_C)
			logger.scriviLog(mex.toString());
	}
	
	private void controlloMessaggiInCoda() 
	{
		Timer ascolto = new Timer();
		ascolto.schedule(
				new TimerTask()
				{
					public void run()
					{
						if(codaMessaggiS_C.isEmpty() == false)
						{
							logger.scriviLog("I messaggi stanno venendo processati nel JFrame");
							processaMessaggiS_C();
						}
					}
				}, 0, 1000);
	}
	//synchronized
	private void processaMessaggiS_C() 
	{
		MessaggioS_C messaggio = null;
		
		if(codaMessaggiS_C.isEmpty() == false)
		{
			messaggio = codaMessaggiS_C.poll();
			contatoreMessaggi++;

			if(contatoreMessaggi == 1)
			{
				disegnaCampoDaGioco(messaggio);			
			}

			else
			{
//				getContentPane().remove(pannelloPartita);
				pannelloPartita.aggiornaCampoDaGioco(messaggio);
				getContentPane().add(pannelloPartita);
				repaint();
			}
		}
	}

	private void disegnaCampoDaGioco(MessaggioS_C messaggio) 
	{	
		getContentPane().remove(jPanelSchermataIniziale);
		pannelloPartita = new JPanelPartita
				(
				gestoreClient,
				messaggio.isPartitaInCorso(),
				messaggio.isVittoriaPartita(),
				messaggio.isProprioTurno(),
				messaggio.isVittoriaTurno(),
				messaggio.getCarteInMano(),
				messaggio.getBriscolaSulTavolo(), 
				messaggio.getUltimaCartaGiocataAvversario(),
				messaggio.getCarteRimanentiMazzo()
				);
		pannelloPartita.setBounds(0, 0, getWidth(), getHeight());
		pannelloPartita.setForeground(Color.WHITE);
		getContentPane().add(pannelloPartita);
		pannelloPartita.setVisible(true);
		pannelloPartita.repaint();
		pannelloPartita.revalidate();
		repaint();

		logger.scriviLog("repaint() chiamato");

	}
}


