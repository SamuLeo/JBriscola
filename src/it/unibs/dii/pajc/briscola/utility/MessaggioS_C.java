package it.unibs.dii.pajc.briscola.utility;

import java.io.Serializable;
import java.util.LinkedList;

public class MessaggioS_C implements Serializable
{
	private boolean partitaInCorso = false;
	private boolean vittoriaPartita = false;
	private boolean proprioTurno = false;
	private boolean vittoriaTurno = false;
	private boolean flagComunicazione = false;

	private LinkedList<Carta> carteInMano = null;
	private Carta briscolaSulTavolo = null;
	private Carta ultimaCartaGiocataAvversario = null;
	
	private int carteRimanentiMazzo = 0;

	private String comunicazione = null;
	
	public MessaggioS_C
			(
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
		this.partitaInCorso = partitaInCorso;
		this.vittoriaPartita = vittoriaPartita;
		this.proprioTurno = proprioTurno;
		this.vittoriaTurno = vittoriaTurno;
		this.carteInMano = carteInMano;
		this.briscolaSulTavolo = briscolaSulTavolo;
		this.ultimaCartaGiocataAvversario = ultimaCartaGiocataAvversario;
		this.carteRimanentiMazzo = carteRimanentiMazzo;
	}	

	public MessaggioS_C( String comunicazione)
	{
		this.flagComunicazione = true;
		this.comunicazione = comunicazione;
	}
		
	public String getComunicazione() 
	{
		return comunicazione;
	}

	public void setComunicazioni(String comunicazione) 
	{
		this.comunicazione = comunicazione;
	}

	public boolean isPartitaInCorso() 
	{
		return partitaInCorso;
	}

	public void setPartitaInCorso(boolean partitaInCorso) 
	{
		this.partitaInCorso = partitaInCorso;
	}

	public boolean isVittoriaPartita() 
	{
		return vittoriaPartita;
	}

	public void setVittoriaPartita(boolean vittoriaPartita) 
	{
		this.vittoriaPartita = vittoriaPartita;
	}

	public boolean isProprioTurno() 
	{
		return proprioTurno;
	}

	public void setProprioTurno(boolean proprioTurno) 
	{
		this.proprioTurno = proprioTurno;
	}

	public boolean isVittoriaTurno() {
		return vittoriaTurno;
	}

	public void setVittoriaTurno(boolean vittoriaTurno) 
	{
		this.vittoriaTurno = vittoriaTurno;
	}

	public LinkedList<Carta> getCarteInMano() 
	{
		return carteInMano;
	}

	public void setCarteInMano(LinkedList<Carta> carteInMano) 
	{
		this.carteInMano = carteInMano;
	}

	public Carta getBriscolaSulTavolo() 
	{
		return briscolaSulTavolo;
	}

	public void setBriscolaSulTavolo(Carta briscolaSulTavolo) 
	{
		this.briscolaSulTavolo = briscolaSulTavolo;
	}

	public Carta getUltimaCartaGiocataAvversario() 
	{
		return ultimaCartaGiocataAvversario;
	}

	public void setUltimaCartaGiocataAvversario(Carta ultimaCartaGiocataAvversario) 
	{
		this.ultimaCartaGiocataAvversario = ultimaCartaGiocataAvversario;
	}

	public int getCarteRimanentiMazzo() 
	{
		return carteRimanentiMazzo;
	}

	public void setCarteRimanentiMazzo(int carteRimanentiMazzo) 
	{
		this.carteRimanentiMazzo = carteRimanentiMazzo;
	}	
	
	public boolean isComunicazione() 
	{
		return flagComunicazione;
	}

	public void setComunicazione(boolean flagComunicazione)
	{
		this.flagComunicazione = flagComunicazione;
	}
	
	@Override
	public String toString() 
	{
		return "MessaggioS_C [partitaInCorso=" + partitaInCorso + ", vittoriaPartita=" + vittoriaPartita
				+ ", proprioTurno=" + proprioTurno + ", vittoriaTurno=" + vittoriaTurno + ", flagComunicazione="
				+ flagComunicazione + ", carteInMano=" + carteInMano + ", briscolaSulTavolo=" + briscolaSulTavolo
				+ ", ultimaCartaGiocataAvversario=" + ultimaCartaGiocataAvversario + ", carteRimanentiMazzo="
				+ carteRimanentiMazzo + ", comunicazioni=" + flagComunicazione + "]";
	}
}
