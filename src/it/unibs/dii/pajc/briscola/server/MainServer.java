package it.unibs.dii.pajc.briscola.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ForkJoinPool;

import javax.swing.JButton;
import javax.swing.JFrame;

import it.unibs.dii.pajc.briscola.utility.Logger;
import it.unibs.dii.pajc.briscola.utility.MessaggiDiServizio;

public class MainServer 
{
	
	private static LinkedList<ThreadGiocatore> giocatoriInCoda;
	
	private static ForkJoinPool threadsGiocatori = new ForkJoinPool();
	private static ForkJoinPool threadsPartite = new ForkJoinPool();
	
	private static	int numeroGiocatori = 0;
	private static int numeroPartite = 0;
	
	private static Logger logger;
	private static final String posizioneFileDiLog = "C:\\Program Files\\JAVA-DevelopmentKit\\workspace\\BriscolaServer\\src\\fileDiLog\\LogServer";
	
	public static void main(String[] args) 
	{
		logger = new Logger(posizioneFileDiLog);
		
		creaGUI();
		
		accettaClients();		
	}

	public static void creaGUI()
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					JFrame frame = new JFrame();
					frame.setTitle("BriscolaServer");
					frame.setBackground(Color.LIGHT_GRAY);
					frame.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width/5, Toolkit.getDefaultToolkit().getScreenSize().height/5);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
					frame.getContentPane().setLayout(new BorderLayout(0,0));	
					
					JButton bottoneTermina = new JButton("TERMINA IL SERVER");
					bottoneTermina.setForeground(Color.RED);
					bottoneTermina.addActionListener(event -> System.exit(0));
					frame.add(bottoneTermina, BorderLayout.CENTER);
				}
				catch (Exception e) 
				{
					logger.scriviLog(MessaggiDiServizio.ERRORE_CREAZIONE_GUI);
				}
			}
		});
	}

	public static void accettaClients()
	{
		giocatoriInCoda = new LinkedList<>();
		
		Timer ascolto = new Timer();
		ascolto.schedule(
				new TimerTask()
				{
					public void run()
					{
						controllaCodaGiocatori();
						inizializzaPossibiliPartite();
					}
				}, 0, 2000);
		
		while(true)
		{
			try(ServerSocket s = new ServerSocket(8189))
			{
				creaConnessione(s);
			}
			catch(IOException e)
			{
				System.out.println(MessaggiDiServizio.ERRORE_SOCKET);
			}
		}
		


	}

	public static void creaConnessione(ServerSocket s) throws IOException
	{
		Socket incoming = s.accept();
		ThreadGiocatore giocatore = new ThreadGiocatore(incoming);
		//giocatore.start();
		threadsGiocatori.execute(giocatore);
		giocatoriInCoda.add(giocatore);
		numeroGiocatori++;
		System.out.println("SERVER -> CREAZIONE DELLA CONNESSIONE CON IL GIOCATORE " + numeroGiocatori);
	}
	
	private static void controllaCodaGiocatori() 
	{
		if(!giocatoriInCoda.isEmpty())
		{
			for(int i = 0 ; i < giocatoriInCoda.size() ; i++)
			{
				if(giocatoriInCoda.get(i) == null )
				{
					giocatoriInCoda.remove(i);
					System.out.println("SERVER -> UN GIOCATORE HA LASCIATO LA CODA");
					numeroGiocatori--;
				}	
				else if(!giocatoriInCoda.get(i).isThreadAttivo())
				{
					giocatoriInCoda.remove(i);
					System.out.println("SERVER -> UN GIOCATORE HA LASCIATO LA CODA");
					numeroGiocatori--;
				}
			}
		}
	}
	
	private static void inizializzaPossibiliPartite() 
	{
		while(giocatoriInCoda.size() >= 2)
		{
			ThreadGiocatore giocatore1 = giocatoriInCoda.poll();
			ThreadGiocatore giocatore2 = giocatoriInCoda.poll();
			ThreadPartita threadPartita = new ThreadPartita(giocatore1, giocatore2);
			//threadPartita.start();
			threadsPartite.execute(threadPartita);
			giocatore1.setThreadPartita(threadPartita);
			giocatore2.setThreadPartita(threadPartita);
			numeroPartite++;
			System.out.println("SERVER -> PARTITA NUMERO " + numeroPartite + " CREATA");
		}	
	}
}


