
public class Auteur implements Comparable <Auteur>
{
	private String nom, pays;
	private  int code;
	
	public Auteur(int code, String nom, String pays)
	{
		this.nom = nom;
		this.code = code;
		this.pays = pays;
	}
	
	Auteur(String nom) {
		this(0, nom,"");
	}
	
	Auteur(int code){
		this(code, "", "");
	}
	
	public void setAuteur(String nom)
	{
		this.nom = nom;
	}
	
	public void setPays(String pays)
	{
		this.pays = pays;
	}
	
	public void setAuteur(int code)
	{
		this.code = code;
	}
	
	public String getAuteur()
	{
		return  nom ;
		}
	
	public String getPays()
	{
		return pays ;
	}
	
	public int getCodeAuteur()
	{
		return code;
	}
	
	public String toString()
	{
		return "Auteur [Code" + code + "Nom:" + nom + "Pays:" + pays + "]";
	}

	@Override
	// Implémentation de l'interface Comparable
	
		 public int compareTo(Auteur autre)
	{
			    return nom.compareToIgnoreCase(autre.nom);
		}
	
	public boolean equals(Object autre) {
		if (this == autre)						
			return true;
		else
			if ( ! (autre instanceof Auteur) )	
				return false;
		else
			return nom == ((Auteur) autre).getAuteur();
		}

}
