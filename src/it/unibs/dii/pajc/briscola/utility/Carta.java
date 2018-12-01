package it.unibs.dii.pajc.briscola.utility;

import java.io.Serializable;

public class Carta implements Serializable
{

	private SemeCarta semeCarta;
	private int numeroCarta;
	private int valoreCarta;
	
	public Carta(SemeCarta semeCarta, int numeroCarta, int valoreCarta)
	{
		this.semeCarta = semeCarta;
		this.numeroCarta = numeroCarta;
		this.valoreCarta = valoreCarta;
	}

	public boolean equals(Carta cartaDaConfrontare)
	{
		return this.semeCarta == cartaDaConfrontare.semeCarta && this.numeroCarta == cartaDaConfrontare.numeroCarta && this.valoreCarta == cartaDaConfrontare.valoreCarta;
	}
	
	public SemeCarta getSemeCarta() 
	{
		return semeCarta;
	}

	public void setSemeCarta(SemeCarta semeCarta) 
	{
		this.semeCarta = semeCarta;
	}

	public int getNumeroCarta() 
	{
		return numeroCarta;
	}

	public void setNumeroCarta(int numeroCarta) 
	{
		this.numeroCarta = numeroCarta;
	}

	public int getValoreCarta() 
	{
		return valoreCarta;
	}

	public void setValoreCarta(int valoreCarta) 
	{
		this.valoreCarta = valoreCarta;
	}
	
	@Override
	public String toString() {
		return  numeroCarta + " di " + semeCarta;
	}
}
