package it.unibs.dii.pajc.briscola.utility;

public enum SemeCarta 
{
	BASTONI(0),SPADE(1),COPPE(2),DENARI(3);
	
	private int codSeme;
	
	private SemeCarta(int codSeme)
	{
		this.codSeme = codSeme;
	}
	
	public int getCodSeme()
	{
		return codSeme;
	}
}
