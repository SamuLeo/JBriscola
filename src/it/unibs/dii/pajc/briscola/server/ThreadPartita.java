package it.unibs.dii.pajc.briscola.server;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JOptionPane;

import it.unibs.dii.pajc.briscola.utility.Carta;
import it.unibs.dii.pajc.briscola.utility.Logger;
import it.unibs.dii.pajc.briscola.utility.MessaggiDiServizio;
import it.unibs.dii.pajc.briscola.utility.MessaggioC_S;

public class ThreadPartita extends Thread
{
	private Mazzo mazzo;
	private Carta briscola;
	private int carteRimanentiMazzo = 40;
	private boolean confrontaCarte = false;
	private int vincitorePartita = 0;
	private int giocatoreVincitoreTurno = 0;
		
	private ThreadGiocatore giocatore1;
	private LinkedList<Carta> manoG1;
	private int puntiG1;
	private Carta ultimaCartaGiocataG1;
	private boolean resaG1 = false;
	
	private ThreadGiocatore giocatore2;
	private LinkedList<Carta> manoG2;
	private int puntiG2;
	private Carta ultimaCartaGiocataG2;
	private boolean resaG2 = false;

	private LinkedList<ThreadGiocatore> ordineGiocatori;

	private Logger logger;
	private static final String posizioneFileDiLog = "C:\\Program Files\\JAVA-DevelopmentKit\\workspace\\BriscolaServer\\src\\fileDiLog\\LogServer";

	
	
	public ThreadPartita(ThreadGiocatore giocatore1, ThreadGiocatore giocatore2)
	{
		this.giocatore1 = giocatore1;
		this.giocatore2 = giocatore2;
		
		if(giocatore1 == null || giocatore2 == null)
			logger.scriviLog("giocatori null");
		
		giocatore1.setPartitaInCorso(true);
		giocatore2.setPartitaInCorso(true);
		
		manoG1 = new LinkedList<>();
		manoG2 = new LinkedList<>();
		mazzo = new Mazzo();
		
		ordineGiocatori = new LinkedList<>();
		
		
		logger = new Logger(posizioneFileDiLog);
		
		logger.scriviLog("LOG-PARTITA : " + getName() + "creato");
	}
	
	public void run()
	{
		inizializzaPartita();
		
		gestionePartita();
		
		terminaPartita();
	}
	
	private void inizializzaPartita() 
	{		
		mazzo.inizializzaMazzo();
		
		briscola = mazzo.pescaDalMazzo();
				
		for(int i=0 ; i<3 ; i++)
		{
			manoG1.add(mazzo.pescaDalMazzo());
			carteRimanentiMazzo--;
		}
			
		
		for(int i=0 ; i<3 ; i++)
		{
			manoG2.add(mazzo.pescaDalMazzo());
			carteRimanentiMazzo--;
		}

		estraiChiInizia();
					
		if(turnoG1())
		{
			inviaDati(giocatore1, manoG1, briscola, null, carteRimanentiMazzo);
			inviaDati(giocatore2, manoG2, briscola, null, carteRimanentiMazzo);
		}
		else
		{
			inviaDati(giocatore1,  manoG1, briscola, null, carteRimanentiMazzo);
			inviaDati(giocatore2 , manoG2, briscola, null, carteRimanentiMazzo);
		}
		logger.scriviLog("LOG-PARTITA : Partita inizializzata");
	}
	
	private void estraiChiInizia()
	{
		if(Math.random() < 0.5)
		{
			ordineGiocatori.add(giocatore1);
			ordineGiocatori.add(giocatore2);
		}
		else
		{
			ordineGiocatori.add(giocatore2);
			ordineGiocatori.add(giocatore1);
		}
	}
	
	private boolean turnoG1()
	{
		if(ordineGiocatori.get(0).equals(giocatore1))
			return true;
		else
			return false;
	}
	
	private void inviaDati(	ThreadGiocatore giocatore, LinkedList<Carta> carteInMano, Carta briscolaSulTavolo, 
							Carta ultimaCartaGiocataAvversario, int carteRimanentiMazzo)
	{
		try
		{
			if(giocatore.equals(giocatore1))
			{
				LinkedList<Carta> icarteInMano = new LinkedList<>();
				for(Carta carta : carteInMano)
					icarteInMano.add(carta);
//				Carta iBriscolaSulTavolo = 	briscolaSulTavolo;
//				Carta iUltimaCartaGiocataAvversario = ultimaCartaGiocataAvversario;

				giocatore1.inviaMessaggioDati(	vincitorePartita == 1, turnoG1(), giocatoreVincitoreTurno == 1, icarteInMano,
						briscolaSulTavolo, ultimaCartaGiocataAvversario, carteRimanentiMazzo);
				logger.scriviLog("LOG-PARTITA : Messaggio inviato al giocatore 1");
			}
			else if(giocatore.equals(giocatore2))
			{
				LinkedList<Carta> icarteInMano = new LinkedList<>();
				for(Carta carta : carteInMano)
					icarteInMano.add(carta);
				
				giocatore2.inviaMessaggioDati(	vincitorePartita == 2, !turnoG1(), giocatoreVincitoreTurno == 2, icarteInMano, 
						briscolaSulTavolo, ultimaCartaGiocataAvversario, carteRimanentiMazzo);			
				logger.scriviLog("LOG-PARTITA : Messaggio inviato al giocatore 2");
			}
		}
		catch(NullPointerException e)
		{
			logger.scriviLog(MessaggiDiServizio.ERRORE_INVIO_MESSAGGI); 
		}
	}
	
	private void gestionePartita() 
	{
		MessaggioC_S messaggioDatiC_S = null;

		while(manoG1.size() > 0 || manoG2.size() > 0)
		{
			if(turnoG1())
			{
				do
				{
					try 
					{
						messaggioDatiC_S = giocatore1.controllaCodaMessaggiDati();
						if(messaggioDatiC_S != null)
							{logger.scriviLog(MessaggiDiServizio.LOG_MESSAGGIO_RICEVUTO_DA_G1 + messaggioDatiC_S.toString());
						logger.scriviLog(MessaggiDiServizio.LOG_MESSAGGIO_RICEVUTO_DA_G1 + messaggioDatiC_S.toString());}
						elaboraComunicazioniGiocatori(giocatore1);
						elaboraComunicazioniGiocatori(giocatore2);
					} 

					catch (NullPointerException e)
					{
					}
				}
				while(messaggioDatiC_S == null);	
			}
			else
			{
				do
				{
					try 
					{
						messaggioDatiC_S = giocatore2.controllaCodaMessaggiDati();
						if(messaggioDatiC_S != null)
						{	logger.scriviLog(MessaggiDiServizio.LOG_MESSAGGIO_RICEVUTO_DA_G2 + messaggioDatiC_S.toString());
						logger.scriviLog(MessaggiDiServizio.LOG_MESSAGGIO_RICEVUTO_DA_G1 + messaggioDatiC_S.toString());}

						elaboraComunicazioniGiocatori(giocatore1);
						elaboraComunicazioniGiocatori(giocatore2);
					} 
					catch (NullPointerException e)
					{
					}
				}
				while(messaggioDatiC_S == null);				
			}
			
			elaboraMessaggio(messaggioDatiC_S);
						
			elaboraTurno();
			
			confrontaCarte = !confrontaCarte;		
		}
		
	}
	
	private void elaboraMessaggio(MessaggioC_S messaggioC_S) 
	{	
		if(turnoG1())
		{
			if(controllaCartaMexSiaInMano(messaggioC_S.getCartaGiocata(), giocatore1))
			{
				ultimaCartaGiocataG1 = messaggioC_S.getCartaGiocata();
				resaG1 = messaggioC_S.isResa();
				manoG1.remove(calcolaIndiceCartaInLista(messaggioC_S.getCartaGiocata(),giocatore1));
			}
		}
		else
		{
			if(controllaCartaMexSiaInMano(messaggioC_S.getCartaGiocata(), giocatore2))
			{
				ultimaCartaGiocataG2 = messaggioC_S.getCartaGiocata();
				resaG2 = messaggioC_S.isResa();
				try
				{
					manoG2.remove(calcolaIndiceCartaInLista(messaggioC_S.getCartaGiocata(),giocatore2));
				}
				catch(IndexOutOfBoundsException e)
				{
				}
			}
		}
		
		if(resaG1 || resaG2) terminaPartita();
		logger.scriviLog("LOG-PARTITA : Messaggio elaborato");
	}
	
	private int calcolaIndiceCartaInLista(Carta carta, ThreadGiocatore giocatore)
	{
		int indice = 0;
		if(giocatore.equals(giocatore1))
		{
			if( carta.equals(manoG1.get(0)))
				indice = 0;
			if(carta.equals(manoG1.get(1)))
				indice = 1;
			if(carta.equals(manoG1.get(2)))
				indice = 2;		
		}
		else
		{
			if(carta.equals(manoG2.get(0)))
				indice = 0;
			if(carta.equals(manoG2.get(1)))
				indice = 1;
			if(carta.equals(manoG2.get(2)))
				indice = 2;	
		}
		return indice;
	}
	
	private boolean controllaCartaMexSiaInMano(Carta cartaMessaggio,ThreadGiocatore giocatore)
	{
		if(giocatore.equals(giocatore1))
		{
			for(Carta carta : manoG1)
			{
				if(carta.equals(cartaMessaggio))
					return true;
			}
		}
		else
			for(Carta carta : manoG2)
			{
				if(carta.equals(cartaMessaggio))
					return true;
			}
		return false;
	}
	
	private void elaboraComunicazioniGiocatori(ThreadGiocatore giocatore)
	{
		MessaggioC_S messaggioCom = null;
		
		messaggioCom = giocatore.controllaCodaComunicazioni();
		
		if(messaggioCom != null)
		{
			logger.scriviLog("LOG-PARTITA : Comunicazione " + messaggioCom.toString() + " arrivata dal client " + giocatore.getName());
			String codMessaggio = messaggioCom.getComunicazione();
			if(codMessaggio.equals(MessaggiDiServizio.CHIUSURA_CONNESSIONE))
			{
				if(giocatore.equals(giocatore1))
				{
					giocatore2.inviaComunicazione(MessaggiDiServizio.CHIUSURA_CONNESSIONE);
					giocatore2.close();
					giocatore2.interrupt();
				}
				else
				{
					giocatore1.inviaComunicazione(MessaggiDiServizio.CHIUSURA_CONNESSIONE);
					giocatore1.close();
					giocatore1.interrupt();
				}
			}
		}
	}
	
	private void elaboraTurno()
	{		
		//se entrambi i giocatori hanno giocato la carta per quel turno determina il vincitore, assegna i punti e invia i messaggi, altimenti fa passare il turno
		if(confrontaCarte)
		{
			determinaVincitoreTurno();
			
			svolgiTerminazioneTurno();
			
			giocatoreVincitoreTurno = 0;
		}
		else
		{
			continuaTurno();
		}
	}
	
	private void faiPescareIGiocatori()
	{
		if(giocatoreVincitoreTurno == 1)
		{
			manoG1.add(mazzo.pescaDalMazzo());
			carteRimanentiMazzo--;
			
			if(carteRimanentiMazzo == 1)
			{
				logger.scriviLog("LOG-PARTITA : Ultima Mano");
				manoG2.add(briscola);
				carteRimanentiMazzo--;
			}
			else
			{
				manoG2.add(mazzo.pescaDalMazzo());
				carteRimanentiMazzo--;
			}				
		}
		else
		{
			manoG2.add(mazzo.pescaDalMazzo());
			carteRimanentiMazzo--;
			
			if(carteRimanentiMazzo == 1)
			{
				logger.scriviLog("LOG-PARTITA : Ultima Mano");
				manoG1.add(briscola);
				carteRimanentiMazzo--;
			}
			else
			{
				manoG1.add(mazzo.pescaDalMazzo());
				carteRimanentiMazzo--;
			}		
		}	
	}
	/**
	 * Questo metodo serve a determinare in base alle carte giocte chi ha vinto la mano
	 */
	private void determinaVincitoreTurno() 
	{
		int codSemeCartaG1 = ultimaCartaGiocataG1.getSemeCarta().getCodSeme();
		int valoreCartaG1 = ultimaCartaGiocataG1.getValoreCarta();
		
		int codSemeCartaG2 = ultimaCartaGiocataG2.getSemeCarta().getCodSeme();
		int valoreCartaG2 = ultimaCartaGiocataG2.getValoreCarta();
		
		int codSemeBriscola = briscola.getSemeCarta().getCodSeme();
 
		
		if(codSemeCartaG1 == codSemeBriscola && codSemeCartaG2 == codSemeBriscola)
		{
			if(valoreCartaG1 > valoreCartaG2)
				giocatoreVincitoreTurno = 1;
			else
				giocatoreVincitoreTurno = 2;
		}
		else 
		{
			if(codSemeCartaG1 == codSemeBriscola)
				giocatoreVincitoreTurno = 1;
			else if(codSemeCartaG2 == codSemeBriscola)
				giocatoreVincitoreTurno = 2;
			else if(codSemeCartaG1 == codSemeCartaG2)
			{
				if(valoreCartaG1 > valoreCartaG2)
					giocatoreVincitoreTurno = 1;
				else
					giocatoreVincitoreTurno = 2;				
			}
			else if(codSemeCartaG1 != codSemeCartaG2)
			{
				if(turnoG1())
					giocatoreVincitoreTurno = 1;
				else
					giocatoreVincitoreTurno = 2;				
			}
		}
		logger.scriviLog("LOG-PARTITA : Giocatore vincitore del turno : " + giocatoreVincitoreTurno);
	}
	/**
	 * Metodo che permette di far pescare i due giocatori, assegnare i punti al vincitore e inviare i messaggi
	 */
	private void svolgiTerminazioneTurno()
	{
		//controlla che il mazzo non sia vuoto, poi fa pescare i due giocatori, prima giocatore1 poi giocatore2
		if(confrontaCarte && mazzo.pescaDalMazzo() != null)
		{	
			faiPescareIGiocatori();
		}
		
		if(giocatoreVincitoreTurno == 1)
		{
			puntiG1 += ultimaCartaGiocataG1.getValoreCarta() + ultimaCartaGiocataG2.getValoreCarta();

			ordineGiocatori.set(0, giocatore1);
			ordineGiocatori.set(1, giocatore2);

			inviaDati(giocatore1, manoG1, briscola, ultimaCartaGiocataG2, carteRimanentiMazzo);
			inviaDati(giocatore2 , manoG2, briscola, ultimaCartaGiocataG1, carteRimanentiMazzo);
		}
		else if(giocatoreVincitoreTurno == 2)
		{
			puntiG2 += ultimaCartaGiocataG1.getValoreCarta() + ultimaCartaGiocataG2.getValoreCarta();

			ordineGiocatori.set(0, giocatore2);
			ordineGiocatori.set(1, giocatore1);

			inviaDati( giocatore1, manoG1, briscola, ultimaCartaGiocataG2, carteRimanentiMazzo);
			inviaDati( giocatore2, manoG2, briscola, ultimaCartaGiocataG1, carteRimanentiMazzo);
		}
		
	}
	
	private void continuaTurno()
	{
		if(turnoG1())
		{
			ordineGiocatori.set(0, giocatore2);
			ordineGiocatori.set(1, giocatore1);
			inviaDati(giocatore2, manoG2, briscola, ultimaCartaGiocataG1, carteRimanentiMazzo);
		}
		else
		{
			ordineGiocatori.set(0, giocatore1);
			ordineGiocatori.set(1, giocatore2);
			inviaDati(giocatore1 , manoG1, briscola, ultimaCartaGiocataG2, carteRimanentiMazzo);
		}
	}
	
	private void terminaPartita() 
	{
		
		if(puntiG1 > puntiG2 || resaG2)
		{	
			vincitorePartita = 1;
			inviaDati(giocatore1 , manoG1, briscola, ultimaCartaGiocataG2, carteRimanentiMazzo);
			inviaDati(giocatore2 , manoG2, briscola, ultimaCartaGiocataG1, carteRimanentiMazzo);
		}
		else if(puntiG1 < puntiG2 || resaG1)			
		{
			vincitorePartita = 2;
			inviaDati(giocatore1 , manoG1, briscola, ultimaCartaGiocataG2, carteRimanentiMazzo);
			inviaDati(giocatore2 , manoG2, briscola, ultimaCartaGiocataG1, carteRimanentiMazzo);
		}
		else
		{
			inviaDati(giocatore1 , manoG1, briscola, ultimaCartaGiocataG2, carteRimanentiMazzo);
			inviaDati(giocatore2 , manoG2, briscola, ultimaCartaGiocataG1, carteRimanentiMazzo);
		}
		logger.scriviLog("LOG-PARTITA : Giocatore " + vincitorePartita + " vincitore della partita");
	}
}
