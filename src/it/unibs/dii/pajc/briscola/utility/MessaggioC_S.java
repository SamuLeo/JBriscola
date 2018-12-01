package it.unibs.dii.pajc.briscola.utility;

import java.io.Serializable;

public class MessaggioC_S implements Serializable
{
	private boolean resa = false;
	private boolean flagComunicazione = false;
	
	private Carta cartaGiocata = null;
	
	private String comunicazione = null;
	

	public MessaggioC_S( Carta cartaGiocata) 
	{
		this.cartaGiocata = cartaGiocata;
	}
	
	public MessaggioC_S(boolean resa, Carta cartaGiocata) 
	{
		this(cartaGiocata);
		this.resa = resa;
	}

	public MessaggioC_S(String comunicazione) 
	{
		flagComunicazione = true;
		this.comunicazione = comunicazione;
	}

	public boolean isFlagComunicazione() 
	{
		return flagComunicazione;
	}

	public void setFlagComunicazione(boolean flagComunicazione) 
	{
		this.flagComunicazione = flagComunicazione;
	}

	public String getComunicazione() 
	{
		return comunicazione;
	}

	public void setComunicazione(String comunicazione) 
	{
		this.comunicazione = comunicazione;
	}

	public boolean isResa() 
	{
		return resa;
	}

	public void setResa(boolean resa) 
	{
		this.resa = resa;
	}

	public Carta getCartaGiocata() 
	{
		return cartaGiocata;
	}

	public void setCartaGiocata(Carta cartaGiocata) 
	{
		this.cartaGiocata = cartaGiocata;
	}
	
	@Override
	public String toString() 
	{
		return "MessaggioC_S [resa=" + resa + ", flagComunicazione=" + flagComunicazione + ", cartaGiocata="
				+ cartaGiocata + ", comunicazione=" + comunicazione + "]";
	}
}
