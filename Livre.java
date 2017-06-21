
public class Livre implements Comparable<Livre>
{
	public String titre, categorie;
	private  int codeAuteur, codeLivre, nbPages;
	private double prix;
	
	public Livre( int codeLivre, String titre, String categorie, int codeAuteur, double prix, int nbPages)
	{
		this.titre = titre;
		this.categorie = categorie;
		this.codeAuteur = codeAuteur;
		this.codeLivre = codeLivre;
		this.prix = prix;
		this.nbPages = nbPages;
	}
	
	Livre(int codeLivre) {
		this(codeLivre, "", "", 0, 0.0, 0);
	}
	
	Livre(String titre) {
		this(0, titre, "", 0, 0.0, 0);
	}
	
	public void setTitre(String titre)
	{
		this.titre = titre;
	}
	
	public void setCategorie(String categorie)
	{
		this.categorie = categorie;
	}
	
	public void setCodeAuteur(int codeAuteur)
	{
		this.codeAuteur = codeAuteur;
	}
	
	public void setCodeLivre(int codeLivre)
	{
		this.codeLivre = codeLivre;
	}
	
	public void setNbPages(int nbPages)
	{
		this.nbPages = nbPages;
	}
	
	public void setPrix(double prix)
	{
		this.prix = prix;
	}
	
	public String getTitre()
	{
		return  titre;
		}
	
	public String getCategorie()
	{
		return categorie ;
	}
	
	public int getCodeAuteur()
	{
		return codeAuteur;
	}
	
	public int getCodeLivre()
	{
		return codeLivre;
	}
	
	public int getPages()
	{
		return nbPages;
	}
	
	public double getPrix()
	{
		return prix;
	}
	
	public int compareTo(Livre autre)
	{
		return titre.compareToIgnoreCase(autre.titre);
	}
	
	public boolean equals(Object autre) {
		if (this == autre)						// Lui-même
			return true;
		else
			if ( ! (autre instanceof Livre) )	// Incomparable
					  return false;
			else
				return titre == ((Livre) autre).getTitre();
		}
	
	public String toString()
	{
		return "Livre [Code du livre" + codeLivre+ "Titre:"+ titre+ "Categorie:" + categorie + "Code de l'auteur"
				+ codeAuteur + "Prix:"+ prix+ "pages:"+ nbPages+ "]";
	}
	

}
