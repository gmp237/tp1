import java.io.*;
import java.util.*;


public class Bdd implements Signatures 
{

	private  Map<Auteur,List<Auteur>> mapA;
	private  Map<Livre,List<Livre>> mapL;
	private List<Auteur> listA;
	private List<Livre> listeL;
	
	// Le constructeur
	Bdd() {
		Map<Auteur,List<Livre>> parc = new TreeMap<Auteur, List<Livre>>();
		Map<Livre,List<Auteur>> manege = new TreeMap<Livre,List<Auteur>>();
		List<Auteur> listA = new ArrayList<Auteur>();
		List<Livre> listeL =new ArrayList<Livre>();
	}


	public void lireBddAut  (String nomFichier) throws IOException {
		

		ArrayList<String> data = lireFichier(nomFichier);
		StringTokenizer item;

		for (int i=0, max=data.size(); i < max; i++) {
			item = new StringTokenizer(data.get(i),"\t");
			addAuteur(new Auteur( Integer.parseInt(item.nextToken()), item.nextToken(), item.nextToken() ));
		}
	}


	@Override
	public void lireBddLivre(String nomFichier) throws IOException {


		ArrayList<String> data = lireFichier(nomFichier);
		StringTokenizer item;

		for (int i=0, max=data.size(); i < max; i++) {
			item = new StringTokenizer(data.get(i),"-");
			addLivre(new Livre(Integer.parseInt(item.nextToken()), item.nextToken(), item.nextToken(), Integer.parseInt(item.nextToken()), Double.parseDouble(item.nextToken()), Integer.parseInt(item.nextToken())));
		}

	}

	
	public void addAuteur(Auteur nouv) 
	{
		if (!mapA.containsKey(nouv))
			mapA.put(nouv, new ArrayList<Auteur>());
	}

	public void addLivre(Livre nouv) {
		if (!mapL.containsKey(nouv))
			mapL.put(nouv, new ArrayList<Livre>());
	}


	/* Retourne l'auteur correspondant au nom passé en argument. */
	@Override
	public Auteur getAuteur(String nomAuteur)
	{
		Auteur temp = new Auteur(nomAuteur);
		if (mapA.containsKey(temp)) 
		{
			return (Auteur) ((TreeMap) mapA).tailMap(temp).firstKey();
		} else
			return null;
	}
	
	/* Retourne l'auteur correspondant au code passé en argument. */
	
	@Override
	public Auteur getAuteur(int codeAuteur)
	{
		Auteur temp = new Auteur(codeAuteur);
		if (mapA.containsKey(temp)) 
		{
			return (Auteur)((TreeMap) mapA).tailMap(temp).firstKey();
		} else
			return null;
	}

	/* Retourne le livre correspondant au titre passé en argument. */
	
	@Override
	public Livre getLivre(String nomLivre) {
		Livre temp = new Livre(nomLivre);
		if (mapL.containsKey(temp)) {
			return (Livre) ((TreeMap) mapL).tailMap(temp).firstKey();
		} else
			return null;
	}
	
	/* Retourne le livre correspondant au code passé en argument. */
	
	@Override
	public Livre getLivre(int codeLivre) {
		Livre temp = new Livre(codeLivre);
		if (mapL.containsKey(temp)) {
			return (Livre) ((TreeMap) mapL).tailMap(temp).firstKey();
		} else
			return null;
	}
	


	/* Retourne une collection contenant tous les livres de l'auteur dont le nom
	   est passé en argument. */
	
	@Override
	public Collection getColLivresAut(Auteur unAuteur)
	{
		Auteur auteur = unAuteur;
		if (unAuteur == null)
			return null;
		else
			return (Set) mapA.get(auteur);
	}



		/* Affichage de tous les auteurs présents dans la map ainsi que leur différents livres  */
	@Override
	public String toString() {
		String data = "";
		Object element;

		Iterator i = mapA.keySet().iterator();

		while (i.hasNext()) {
			element = i.next();
			data += ((Auteur) element) + "\n";

			//Iterator j = ((Auteur) element).getLivres().iterator();
			
			Iterator j = ((Set) mapA.get((Auteur) element)).iterator();

			while (j.hasNext())
				data += "\t" + ((Livre) j.next()) + "\n";

			data += "\n";
		}

		return data;
	}

	

	/* lire un fichier texte et de retourner une
	   liste des éléments lus */
	
	public ArrayList<String> lireFichier(String nomFichier) throws IOException 
	{
		FileReader fr = null;
		try {
			fr = new FileReader ("/Bibliographie/src/"+nomFichier);
		}
	catch (java.io.FileNotFoundException e) {
			System.out.println("Incapable d'ouvrir le fichier"+nomFichier+"\n");
			throw e;
		}

		BufferedReader entree = new BufferedReader(fr);
		boolean finFichier = false ;

		ArrayList<String> data = new ArrayList<String>();
		StringTokenizer items;
		String uneLigne;

		while ( !finFichier ) {
			uneLigne = entree.readLine();
			if (uneLigne == null)
				finFichier = true ;
			else {
				items = new StringTokenizer(uneLigne, "\t");
				data.add(uneLigne);
			}
		}

		entree.close();
		return data;
	}


	
	static private void ecrireFichier(Vector data, String nomFichier) throws IOException
 {
		FileWriter fw = null;
		try {
			String nomFichier2="/Bibliographie/src/"+nomFichier;
			fw = new FileWriter(nomFichier2);
		}
		catch (java.io.FileNotFoundException e) {
			System.out.println("Incapable d'écrire sur le fichier " + nomFichier + " \n");
			throw e;
		}

		PrintWriter sortie = new PrintWriter(fw);
		Vector temp;

		for (int i = 0, maxI = data.size(); i < maxI; i++) {
			temp = (Vector) data.get(i);
			for (int j = 0, maxJ = temp.size(); j < maxJ - 1; j++)
				sortie.print((String) temp.get(j) + "\t");
			sortie.println((String) temp.get(temp.size() - 1));
		}

		sortie.close();

	}

	


	@Override
	public void rapportParAuteurs() throws IOException 
	{	
		
		Vector data, items;
		Iterator i, j;
		Object element, element2;
		
		data = new Vector();
		String nomFichierAuteur= "parAuteurs.txt";
		i = mapA.keySet().iterator();
		String nomAuteur;

		while (i.hasNext()) {
			element = i.next();
			items = new Vector();
			items.add(((Auteur) element).getCodeAuteur());
			items.add(((Auteur) element).getAuteur());
			items.add(((Auteur) element).getPays());
			data.add(items);
		}
		
		while (i.hasNext()) {
			element = i.next();

			j = ((Set) mapA.get((Auteur) element)).iterator();

			nomAuteur = ((Auteur) element).getAuteur();

			while (j.hasNext()) {
				element2 = j.next();

				items = new Vector();

				items.add(nomAuteur);
				items.add(((Livre) element2).getTitre());

				data.add(items);
			}

		}


		ecrireFichier(data, nomFichierAuteur);
		
	}

	@Override
	public void rapportParLivres() throws IOException 
	{
		Vector data, items;
		Iterator i, j;
		Object element, element2;
		
		data = new Vector();
		String nomFichierLivre= "parLivres.txt";
		i = mapL.keySet().iterator();
		String nomAuteur;

		while (i.hasNext())
		{
			element = i.next();
			items = new Vector();
			items.add(((Livre) element).getCodeLivre());
			items.add(((Livre) element).getTitre());
			items.add(""+((Livre) element).getCategorie());
			items.add(""+((Livre) element).getCodeAuteur());
			items.add(""+((Livre) element).getPrix());
			data.add(items);
		}
		
		while (i.hasNext()) {
			element = i.next();

			j = ((Set) mapA.get((Auteur) element)).iterator();

			nomAuteur = ((Auteur) element).getAuteur();

			while (j.hasNext()) {
				element2 = j.next();

				items = new Vector();

				items.add(nomAuteur);
				items.add(((Livre) element2).getTitre());

				data.add(items);
			}

		}


		ecrireFichier(data, nomFichierLivre);
		
		
	}

}
