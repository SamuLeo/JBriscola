package it.unibs.dii.pajc.briscola.client;


import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unibs.dii.pajc.briscola.utility.Carta;
import it.unibs.dii.pajc.briscola.utility.GBC;
import it.unibs.dii.pajc.briscola.utility.Logger;
import it.unibs.dii.pajc.briscola.utility.MessaggiDiServizio;
import it.unibs.dii.pajc.briscola.utility.MessaggioC_S;
import it.unibs.dii.pajc.briscola.utility.MessaggioS_C;

public class JPanelPartita extends JPanel
{
	private GestoreClient gestoreClient;
	
	private boolean partitaInCorso = false;
	private boolean vittoriaPartita = false;
	private boolean proprioTurno = false;
	private boolean vittoriaTurno = false;
	//flag utilizzato per impedire al giocatore di giocare pi˘ carte
	private boolean gi‡Giocato = false;
	//flag impostato se si Ë i primi a iniziare la partita
	private boolean giocatoreInizializzatore = false;
	
	private LinkedList<Carta> carteInMano;
	private Carta briscolaSulTavolo = null;
	private Carta cartaGiocata = null;
	private Carta ultimaCartaGiocataAvversario = null;
	private int retroCarte = 0;	
	private int carteRimanentiMazzo = 0;
	
	private JLabel propriaCartaSx ;
	private JButton bottonePropriaCartaSx;
	private JLabel propriaCartaCentrale ;
	private JButton bottonePropriaCartaCentrale;
	private JLabel propriaCartaDx ;
	private JButton bottonePropriaCartaDx;
	
	private JLabel cartaGiocataAvversario ;
	private JLabel propriaCartaGiocata ;
	
	private JLabel briscola ;
	private JLabel mazzo ;
	
	private JLabel CartaSxAvversario ;
	private JLabel CartaCentraleAvversario ;
	private JLabel CartaDxAvversario ;
	
	private GestoreImmaginiCarte gestoreImmaginiCarte = null;
	private int contatoreTurni = 0;

	private Logger logger;
	private static final String posizioneFileDiLog = "C:\\Program Files\\JAVA-DevelopmentKit\\workspace\\BriscolaServer\\src\\fileDiLog\\LogClient";
	
	
	public JPanelPartita
	(
			GestoreClient gestoreClient,
			boolean partitaInCorso,
			boolean vittoriaPartita,
			boolean proprioTurno,
			boolean vittoriaTurno,
			LinkedList<Carta> carteInMano,
			Carta briscolaSulTavolo,
			Carta ultimaCartaGiocataAvversario, 
			int carteRimanentiMazzo
			) 
	{
		this.carteInMano = new LinkedList<>();
		this.gestoreClient = gestoreClient;
		this.partitaInCorso = partitaInCorso;
		this.vittoriaPartita = vittoriaPartita;
		this.proprioTurno = proprioTurno;
		this.vittoriaTurno = vittoriaTurno;

		LinkedList<Carta> nuovaMano = new LinkedList<>();
		nuovaMano = carteInMano;
		for(Carta carta : carteInMano)
			this.carteInMano.add(carta);
//		for(int i = 0; i < 3;i++)
//			this.carteInMano.set(i, nuovaMano.get(i));
//		this.carteInMano = carteInMano;
		this.briscolaSulTavolo = briscolaSulTavolo;
		this.ultimaCartaGiocataAvversario = ultimaCartaGiocataAvversario;
		this.carteRimanentiMazzo = carteRimanentiMazzo;
	
		logger = new Logger(posizioneFileDiLog);
		
		gestoreImmaginiCarte = new GestoreImmaginiCarte();
		//condizione creata per uniformare il conteggio turni tra i giocatori
		if(proprioTurno == true)
			giocatoreInizializzatore = true;
		
		setLayout(new GridBagLayout());
		inizializzaComponenti();
		aggiornaComponentiGrafici();		
	}
	

	public void inizializzaComponenti()
	{
		  propriaCartaSx = new JLabel();
		  bottonePropriaCartaSx = new JButton("GIOCA");
		  propriaCartaCentrale = new JLabel();
		  bottonePropriaCartaCentrale = new JButton("GIOCA");
		  propriaCartaDx = new JLabel();
		  bottonePropriaCartaDx = new JButton("GIOCA");
		
		  cartaGiocataAvversario = new JLabel();
		  propriaCartaGiocata = new JLabel();
		
		  briscola = new JLabel(new ImageIcon(gestoreImmaginiCarte.getImmagineCarta(calcolaCodiceCarta(briscolaSulTavolo))));
		  mazzo = new JLabel(new ImageIcon(gestoreImmaginiCarte.getImmagineCarta(retroCarte)));
		
		  CartaSxAvversario = new JLabel(new ImageIcon(gestoreImmaginiCarte.getImmagineCarta(retroCarte)));
		  CartaCentraleAvversario = new JLabel(new ImageIcon(gestoreImmaginiCarte.getImmagineCarta(retroCarte)));
		  CartaDxAvversario = new JLabel(new ImageIcon(gestoreImmaginiCarte.getImmagineCarta(retroCarte)));
	}

	public void paintComponent(Graphics g)
	{		
		super.paintComponent(g);
		
		add(CartaSxAvversario, 				new GBC(0,0).setWeight(10, 10).setInsets(10));
		add(CartaCentraleAvversario,		new GBC(1,0).setWeight(10, 10).setInsets(10));
		add(CartaDxAvversario, 				new GBC(2,0).setWeight(10, 10).setInsets(10));
		
		add(cartaGiocataAvversario,		    new GBC(0,1).setWeight(10, 10).setInsets(10));
		add(propriaCartaGiocata, 			new GBC(1,1).setWeight(10, 10).setInsets(10));
		
		add(briscola, 						new GBC(3,1).setWeight(10, 10).setInsets(10));
		add(mazzo, 							new GBC(4,1).setWeight(10, 10).setInsets(10));
		
		add(propriaCartaSx, 				new GBC(0,2).setWeight(10, 10).setInsets(10));
		add(bottonePropriaCartaSx,   		new GBC(0,3).setWeight(10, 10).setFill(GBC.CENTER).setInsets(10));
		add(propriaCartaCentrale, 	        new GBC(1,2).setWeight(10, 10).setInsets(10));
		add(bottonePropriaCartaCentrale,    new GBC(1,3).setWeight(10, 10).setFill(GBC.CENTER).setInsets(10));
		add(propriaCartaDx, 				new GBC(2,2).setWeight(10, 10).setInsets(10));
		add(bottonePropriaCartaDx,  	    new GBC(2,3).setWeight(10, 10).setFill(GBC.CENTER).setInsets(10));	
		
	}

	public void aggiornaCampoDaGioco(MessaggioS_C messaggio) 
	{
		contatoreTurni++;
		aggiornaAttributi(messaggio);
		aggiornaComponentiGrafici();
		repaint();
		revalidate();
	}

	private void aggiornaAttributi(MessaggioS_C messaggio)
	{
		this.partitaInCorso = messaggio.isPartitaInCorso();
//		if(partitaInCorso == false)
//		{
//			JOptionPane msgBoxConfermaChiusura = new JOptionPane();
//			msgBoxConfermaChiusura.setBounds(new Rectangle(getWidth()/3, getHeight()/4));
//			int risposta = msgBoxConfermaChiusura.showConfirmDialog(this, "La partita Ë stata interrotta","",JOptionPane.OK_OPTION);
//			gestoreClient.close();
//			System.exit(0);
//		}
			
		this.vittoriaPartita = messaggio.isVittoriaPartita();
		this.proprioTurno = messaggio.isProprioTurno();
		this.vittoriaTurno = messaggio.isVittoriaTurno();
		
		this.carteInMano = new LinkedList<>();
		Carta[] nuovaMano = new Carta[3];
		nuovaMano = messaggio.getCarteInMano().toArray(new Carta[3]);
		for(int i = 0; i < nuovaMano.length; i++)
		{
			this.carteInMano.add(nuovaMano[i]);
		}
		
		this.ultimaCartaGiocataAvversario = messaggio.getUltimaCartaGiocataAvversario();
		this.carteRimanentiMazzo = messaggio.getCarteRimanentiMazzo();
		
		creaJOptionPane();
	}

	private void creaJOptionPane()
	{
		if(vittoriaTurno == true && proprioTurno == true)
		{
			JOptionPane msgVittoria = new JOptionPane();
			msgVittoria.setBounds(new Rectangle(getWidth()/3, getHeight()/4));
			msgVittoria.showMessageDialog(this, "Vittoria nel turno precedente \n La Carta giocata dall'avversario Ë stata : " + ultimaCartaGiocataAvversario.toString());
		}
		
		if(vittoriaTurno == false && proprioTurno == false)
		{
			JOptionPane msgSconfitta = new JOptionPane();
			msgSconfitta.setBounds(new Rectangle(getWidth()/3, getHeight()/4));
			msgSconfitta.showMessageDialog(this, "Sconfitta nel turno precedente \n La Carta giocata dall'avversario Ë stata : " + ultimaCartaGiocataAvversario.toString(),"",JOptionPane.OK_OPTION);
		}
		if(vittoriaPartita == true)
		{
			JOptionPane msgVittoria = new JOptionPane();
			msgVittoria.setBounds(new Rectangle(getWidth()/3, getHeight()/4));
			msgVittoria.showMessageDialog(this, "VITTORIA!!!" );
		}		
		if(vittoriaPartita == false && carteInMano.isEmpty())
		{
			JOptionPane msgVittoria = new JOptionPane();
			msgVittoria.setBounds(new Rectangle(getWidth()/3, getHeight()/4));
			msgVittoria.showMessageDialog(this, "BETTER LUCK NEXT TIME" );
		}
		
	}
	
	private void aggiornaComponentiGrafici() 
	{
		if(contatoreTurni == 0)
		{
			if(proprioTurno == false)
			{
				if(giocatoreInizializzatore == true)
					impostaTurnoAvversarioSecondoDiMano();
				else
					impostaTurnoAvversarioPrimoDiMano();
			}
			else if(proprioTurno == true)
			{
			if(giocatoreInizializzatore == true)
				impostaProprioTurnoPrimoDiMano();
			else
				impostaProprioTurnoSecondoDiMano();
			}
		}				
		if(contatoreTurni >= 1 && contatoreTurni <= 40)
		{
			if(vittoriaTurno == true)
			{
				if(proprioTurno == true)
				{
					impostaProprioTurnoPrimoDiMano();
					controlloGraficaPerTurniFinali();
				}
					
				else
				{	
					impostaTurnoAvversarioSecondoDiMano();
					controlloGraficaPerTurniFinali();
				}
			}
			
			if(vittoriaTurno == false)
			{
				if(proprioTurno == true)
				{
					impostaProprioTurnoSecondoDiMano();					
					controlloGraficaPerTurniFinali();
				}
				else
				{
					if(contatoreTurni == 1)
						impostaProprioTurnoSecondoDiMano();					
					else
						impostaTurnoAvversarioPrimoDiMano();
					
					controlloGraficaPerTurniFinali();
				}
			}
		}
		
		if(contatoreTurni >= 40)
			logger.scriviLog(MessaggiDiServizio.ERRORE_OVERFLOW_TURNI);

	}
	
	private void controlloGraficaPerTurniFinali()
	{
		if(contatoreTurni >= 37)
		{
			mazzo.setVisible(false);
			briscola.setVisible(false);
		}
	}
	
	private void impostaProprioTurnoPrimoDiMano()
	{
		gi‡Giocato = false;
		
		aggiornaLabelCarteInManoConControllo();

		cartaGiocataAvversario .setVisible(false);
		propriaCartaGiocata    .setVisible(false);
		
		CartaDxAvversario      .setVisible(true);

		aggiornaBottoniConControllo();
	}
	
	private void impostaProprioTurnoSecondoDiMano()
	{
		gi‡Giocato = false;
		
		aggiornaLabelCarteInManoConControllo();

		cartaGiocataAvversario .setVisible(true);
		cambiaLabelConControllo(ultimaCartaGiocataAvversario, cartaGiocataAvversario);
		propriaCartaGiocata    .setVisible(false);
			
		CartaDxAvversario      .setVisible(false);
		   
		aggiornaBottoniConControllo();
	}
	
	private void impostaTurnoAvversarioPrimoDiMano()
	{
		
		aggiornaLabelCarteInManoConControllo();
		
		cartaGiocataAvversario.setVisible(false);
		propriaCartaGiocata   .setVisible(false);
		
		CartaDxAvversario      .setVisible(true);
		
		bottonePropriaCartaSx      .setVisible(false);
		bottonePropriaCartaCentrale.setVisible(false);
		bottonePropriaCartaDx      .setVisible(false);
	}
	
	private void impostaTurnoAvversarioSecondoDiMano()
	{			
		aggiornaLabelCarteInManoConControllo();
		
		cartaGiocataAvversario.setVisible(false);
		propriaCartaGiocata.setVisible(true);
		cambiaLabelConControllo(cartaGiocata, propriaCartaGiocata);
		
		
		bottonePropriaCartaSx      .setVisible(false);
		bottonePropriaCartaCentrale.setVisible(false);
		bottonePropriaCartaDx      .setVisible(false);
	}
	
	private void aggiornaLabelCarteInManoConControllo()
	{
		for(int i = 0 ; i < carteInMano.size() ; i++)
		{
			if(carteInMano.get(i) != null)
			{
				if(i == 0)
					cambiaLabelConControllo(carteInMano.get(i), propriaCartaSx);
				if(i == 1)
					cambiaLabelConControllo(carteInMano.get(i), propriaCartaCentrale);
				if(i == 2)
					cambiaLabelConControllo(carteInMano.get(i), propriaCartaDx);
			}
			else if(i == 0)
				propriaCartaSx.setVisible(false);
			else if(i == 1)
				propriaCartaCentrale.setVisible(false);
			else if(i == 2)
				propriaCartaDx.setVisible(false);
		}
	}
	
	private void aggiornaBottoniConControllo()
	{
		for(int i = 0 ; i < carteInMano.size() ; i++)
		{
			if(carteInMano.get(i) != null)
			{
				if(i == 0)
				{
					bottonePropriaCartaSx      .setVisible(true);
					bottonePropriaCartaSx      .addActionListener(new ButtonActionListener(carteInMano.get(i),propriaCartaSx));
				}
				if(i == 1)
				{
					bottonePropriaCartaCentrale      .setVisible(true);
					bottonePropriaCartaCentrale      .addActionListener(new ButtonActionListener(carteInMano.get(i),propriaCartaCentrale));
				}
				
				if(i == 2)
				{
					bottonePropriaCartaDx      .setVisible(true);
					bottonePropriaCartaDx      .addActionListener(new ButtonActionListener(carteInMano.get(i),propriaCartaDx));
				}
			}
			else if(i == 0)
				bottonePropriaCartaSx.setVisible(false);
			else if(i == 1)
				bottonePropriaCartaCentrale.setVisible(false);
			else if(i == 2)
				bottonePropriaCartaDx.setVisible(false);
		}
	}
	
	private void cambiaLabelConControllo(Carta carta, JLabel label)
	{
		if(label == null)
			return;
		
		if(carta == null)
			label.setVisible(false);
		else
		{
			label.setVisible(true);
			label.setIcon(new ImageIcon(gestoreImmaginiCarte.getImmagineCarta(calcolaCodiceCarta(carta))));
		}
	}
	
	private int calcolaCodiceCarta(Carta carta) 
	{				
		return carta.getSemeCarta().getCodSeme()*10 + carta.getNumeroCarta();
	}

	
    private class ButtonActionListener implements ActionListener
    {
    	Carta cartaGiocata ;
    	JLabel labelDaNascondere ;
    	
    	public ButtonActionListener(Carta carta, JLabel labelDaNascondere)
    	{
    		this.cartaGiocata = carta;
    		this.labelDaNascondere = labelDaNascondere;
    	}
    	
    	public void actionPerformed(ActionEvent e)
    	{
    		if(proprioTurno == true && gi‡Giocato == false)
    		{
    			cambiaLabelConControllo(cartaGiocata, propriaCartaGiocata);
    			cambiaLabelConControllo(null, labelDaNascondere);
    			bottonePropriaCartaSx      .setVisible(false);
    			bottonePropriaCartaCentrale.setVisible(false);
    			bottonePropriaCartaDx      .setVisible(false);

    			repaint();
    			gi‡Giocato = true;
    			
    			if(proprioTurno == true && vittoriaTurno == true)
    				contatoreTurni++;
    			if(proprioTurno == true && giocatoreInizializzatore == true)
    				contatoreTurni++;
    			
    			gestoreClient.inviaMessaggioDati(new MessaggioC_S(false, cartaGiocata));
    		}
    	}

    }
}