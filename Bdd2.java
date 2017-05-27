import java.io.*;
import java.util.*;


public class Bdd2 implements Signatures {

	/* DÃ©claration des deux map reprÃ©sentant les bases de donnÃ©es*/
	private  Map<Auteur,List<Auteur>> mapA;
	private  Map<Livre,List<Livre>> mapL;
	private List<Auteur> listA;
	private List<Livre> listeL;
	
	// Le constructeur
	Bdd2() {
		Map<Auteur,List<Livre>> parc = new TreeMap<Auteur, List<Livre>>();
		Map<Livre,List<Auteur>> manege = new TreeMap<Livre,List<Auteur>>();
		List<Auteur> listP = new ArrayList<Auteur>();
		List<Livre> listeM =new ArrayList<Livre>();
	}

	///////////////////////////////////////////////////////////////////////////////////////
	// Suivent ici les mÃ©thodes requises par Signatures ///////////////////////////////////


	public void lireBddAut  (String nomFichier) throws IOException {
		

		ArrayList<String> data = lireFichier(nomFichier);
		StringTokenizer item;

		for (int i=0, max=data.size(); i < max; i++) {
			item = new StringTokenizer(data.get(i),"\t");
			addAuteur(new Auteur(item.nextToken(), item.nextToken(), Integer.parseInt(item.nextToken()) ));
		}
	}

	/* Permet de charger la banque de donnÃ©es des livres Ã  partir d'un fichier dont le nom
	   est passÃ© en argument. Un autre paramètre, booléen, indique si vrai ou faux, il faut
	   remplacer la banque de donnÃ©es existante (s’il y a lieu)  par la nouvelle. Si ce
	   paramètre est faux, on ajoute les nouvelles donnÃ©es Ã  la banque de données existante.
	   Si un livre équivalent est dÃ©jÃ  présent dans la Map, l’ajout sera ignoré. Lors de
	   l’ajout d’un nouveau livre dans la Map, la valeur associé sera une nouvelle
	   collection vide. */
	public void lireBddLivres(String nomFichier) throws IOException {


		ArrayList<String> data = lireFichier(nomFichier);
		StringTokenizer item;

		for (int i=0, max=data.size(); i < max; i++) {
			item = new StringTokenizer(data.get(i),"-");
			addLivre(new Livre(Integer.parseInt(item.nextToken()), item.nextToken(), item.nextToken(), Integer.parseInt(item.nextToken()), Double.parseDouble(item.nextToken()), Integer.parseInt(item.nextToken())));
		}

	}

	/* Lit les donnÃ©es contenues dans le fichier dont le nom est passÃ© en paramÃ¨tre et pour
	   chaque nom de manÃ¨ge et nom de parc, appelle addManegeParc, si une exception
	   correspondant Ã  lÂ’absence du parc ou du manÃ¨ge est levÃ©e, on ignore cet ajout et on
	   passe aux manÃ¨ges et parcs suivants. Comme dans le cas des 2 autres fichiers, cette
	   mÃ©thode recevra aussi un paramÃ¨tre boolÃ©en. 
	public void lireBddManegeParc(String nomFichier) throws IOException {


		ArrayList<String> data = lireFichier(nomFichier);
		StringTokenizer item;

		for (int i=0, max=data.size(); i < max; i++) {
			item = new StringTokenizer(data.get(i),"-");
			try {
				addLivreAuteur(item.nextToken(), item.nextToken() );
			}
			catch (ManegeAbsentException e) {
			}
			catch (ParcAbsentException e) {
			}
		}

	} 
	
	*/

	/* Ajoute un Parc, passÃ© en argument, dans la banque de donnÃ©es (listP) des parcs */
	public void addAuteur(Auteur nouv) {
		if (!mapA.containsKey(nouv))
			mapA.put(nouv, new ArrayList<Auteur>());
	}

	/* Ajoute un Manege, passÃ© en argument, dans la banque de donnÃ©es des manÃ¨ges */
	public void addLivre(Livre nouv) {
		if (!mapL.containsKey(nouv))
			mapL.put(nouv, new ArrayList<Livre>());
	}

	/* ReÃ§oit en arguments un nom de manÃ¨ge et un nom de parc, trouve dans leurs Map
	   respective le Parc et le Manege et, si les 2 sont prÃ©sents, ajoute le Parc Ã  la
	   collection associÃ©e Ã  la clÃ© Manege et ajoute le Manege Ã  la collection associÃ©e
	   au Parc. En cas dÂ’absence du Manege, la mÃ©thode lance une exception
	   ManegeAbsentException et en cas dÂ’absence du Parc, elle lance une exception
	   ParcAbsentException.
	public void addManegeParc(String nomManege,String nomParc)
									throws ParcAbsentException, ManegeAbsentException {
		Manege manege = getManege(nomManege);
		if (manege == null) throw new ManegeAbsentException();
		Parc parc = getParc(nomParc);
		if (parc == null) throw new ParcAbsentException();

		//parc.getManeges().add(manege);
		((Set) mapP.get(parc)).add(manege);
		//manege.getParcs().add(parc);
		((Set) mapM.get(manege)).add(parc);

	}  */

	/* Permet de sauvegarder les informations de la banque de donnÃ©es dans trois fichiers
	   dont les noms sont passÃ©s en argument. Les formats des fichiers sont les mÃªme que
	   pour la lecture */
	public void saveBdd(String nomFichierAuteur,String nomFichierLivre,String nomFichierEmp)
									throws IOException {
		Vector data, items;
		Iterator i, j;
		Object element, element2;

		// Les auteurs
		
		data = new Vector();
		i = mapA.keySet().iterator();

		while (i.hasNext()) {
			element = i.next();
			items = new Vector();
			items.add(((Auteur) element).getCodeAuteur());
			items.add(((Auteur) element).getAuteur());
			items.add(((Auteur) element).getPays());
			data.add(items);
		}

		ecrireFichier(data,nomFichierAuteur);

		// Les livres
		
		data = new Vector();
		i = mapL.keySet().iterator();

		while (i.hasNext()) {
			element = i.next();
			items = new Vector();
			items.add(((Livre) element).getCodeLivre());
			items.add(((Livre) element).getTitre());
			items.add(""+((Livre) element).getCategorie());
			items.add(""+((Livre) element).getCodeAuteur());
			items.add(""+((Livre) element).getPrix());
			data.add(items);
		}

		ecrireFichier(data,nomFichierLivre);

		// Les relations
		data = new Vector();
		i = mapA.keySet().iterator();
		String nomAuteur;

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

		ecrireFichier(data,nomFichierEmp);

	}

	/* Retourne l'auteur correspondant au nom passé en argument. */
	
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
	
	public Livre getLivre(String nomLivre) {
		Livre temp = new Livre(nomLivre);
		if (mapL.containsKey(temp)) {
			return (Livre) ((TreeMap) mapL).tailMap(temp).firstKey();
		} else
			return null;
	}
	
	/* Retourne le livre correspondant au code passé en argument. */
	
	public Livre getLivre(int codeLivre) {
		Livre temp = new Livre(codeLivre);
		if (mapL.containsKey(temp)) {
			return (Livre) ((TreeMap) mapL).tailMap(temp).firstKey();
		} else
			return null;
	}
	

	/* Retourne une collection contenant tous les parcs où le manège, dont le nom est
	   passÃ© en argument, est présent. */
	
	/*public Collection getColLivresAut(String titre) {
		Livre livre = getTitre(titre);
		if (livre == null)
			return null;
		else
			//return manege.getParcs();
			return (Set) mapL.get(manege);
	}*/

	/* Retourne une collection contenant tous les livres de l'auteur dont le nom
	   est passé en argument. */
	
	public Collection colLivresAut(String nom) {
		Auteur auteur = getAuteur(nom);
		if (auteur == null)
			return null;
		else
			//return parc.getManeges();
			return (Set) mapA.get(auteur);
	}

	/* Retourne une collection contenant tous les parcs situÃ©s dans lÂ’Ã©tat (province)
	   passÃ© en argument. 
	   
	public Collection colParcEtat(String etat) {
		TreeSet col = new TreeSet();

		Iterator i = mapA.keySet().iterator();
		Object element;

		while (i.hasNext()) {
			element = i.next();

			if (((Auteur) element).getEtat().equals(etat))
				col.add(element);

		}

		return col;
	}*/

	/* Retourne une Map contenant tous les parcs, et leurs manÃ¨ges, situÃ©s dans lÂ’Ã©tat
	   (province) passÃ© en argument. SÂ’il y a lieu, les parcs situÃ©s dans lÂ’Ã©tat mais
	   nÂ’ayant aucun manÃ¨ge sont aussi inclus. 
	
	public Map MapManegeEtat(String etat) {
		TreeMap map = new TreeMap();

		Iterator i = mapA.keySet().iterator();
		Parc element;

		while (i.hasNext()) {
			element = (Parc) i.next();

			if (((Parc) element).getEtat().equals(etat))
				//map.put(element, element.getManeges());
				map.put(element, mapA.get(element));

		}

		return map;
	} */

		/* Affichage de tous les auteurs présents dans la map ainsi que leur différents livres  */
	
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

	

	/* Méthode permettant de lire un fichier texte et de retourner une
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


	/* Méthode permettant d'écrire un fichier texte Ã  partir d'un Vector de Vector,
	   un Vector par ligne décomposé en champs séparés par des tabulations ('\t')
	   dans un fichier dont le nom est transmis en paramètre. */
	
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

}