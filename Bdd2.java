import java.io.*;
import java.util.*;


public class Bdd2 implements Signatures {

	/* Déclaration des deux map représentant les bases de données*/
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
	// Suivent ici les méthodes requises par Signatures ///////////////////////////////////


	public void lireBddAut  (String nomFichier) throws IOException {
		

		ArrayList<String> data = lireFichier(nomFichier);
		StringTokenizer item;

		for (int i=0, max=data.size(); i < max; i++) {
			item = new StringTokenizer(data.get(i),"\t");
			addAuteur(new Auteur(item.nextToken(), item.nextToken(), Integer.parseInt(item.nextToken()) ));
		}
	}

	/* Permet de charger la banque de données des livres à partir d'un fichier dont le nom
	   est passé en argument. Un autre param�tre, bool�en, indique si vrai ou faux, il faut
	   remplacer la banque de données existante (s�il y a lieu)  par la nouvelle. Si ce
	   param�tre est faux, on ajoute les nouvelles données à la banque de donn�es existante.
	   Si un livre �quivalent est déjà pr�sent dans la Map, l�ajout sera ignor�. Lors de
	   l�ajout d�un nouveau livre dans la Map, la valeur associ� sera une nouvelle
	   collection vide. */
	public void lireBddLivres(String nomFichier) throws IOException {


		ArrayList<String> data = lireFichier(nomFichier);
		StringTokenizer item;

		for (int i=0, max=data.size(); i < max; i++) {
			item = new StringTokenizer(data.get(i),"-");
			addLivre(new Livre(Integer.parseInt(item.nextToken()), item.nextToken(), item.nextToken(), Integer.parseInt(item.nextToken()), Double.parseDouble(item.nextToken()), Integer.parseInt(item.nextToken())));
		}

	}

	/* Lit les données contenues dans le fichier dont le nom est passé en paramètre et pour
	   chaque nom de manège et nom de parc, appelle addManegeParc, si une exception
	   correspondant à labsence du parc ou du manège est levée, on ignore cet ajout et on
	   passe aux manèges et parcs suivants. Comme dans le cas des 2 autres fichiers, cette
	   méthode recevra aussi un paramètre booléen. 
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

	/* Ajoute un Parc, passé en argument, dans la banque de données (listP) des parcs */
	public void addAuteur(Auteur nouv) {
		if (!mapA.containsKey(nouv))
			mapA.put(nouv, new ArrayList<Auteur>());
	}

	/* Ajoute un Manege, passé en argument, dans la banque de données des manèges */
	public void addLivre(Livre nouv) {
		if (!mapL.containsKey(nouv))
			mapL.put(nouv, new ArrayList<Livre>());
	}

	/* Reçoit en arguments un nom de manège et un nom de parc, trouve dans leurs Map
	   respective le Parc et le Manege et, si les 2 sont présents, ajoute le Parc à la
	   collection associée à la clé Manege et ajoute le Manege à la collection associée
	   au Parc. En cas dabsence du Manege, la méthode lance une exception
	   ManegeAbsentException et en cas dabsence du Parc, elle lance une exception
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

	/* Permet de sauvegarder les informations de la banque de données dans trois fichiers
	   dont les noms sont passés en argument. Les formats des fichiers sont les même que
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

	/* Retourne l'auteur correspondant au nom pass� en argument. */
	
	public Auteur getAuteur(String nomAuteur)
	{
		Auteur temp = new Auteur(nomAuteur);
		if (mapA.containsKey(temp)) 
		{
			return (Auteur) ((TreeMap) mapA).tailMap(temp).firstKey();
		} else
			return null;
	}
	
	/* Retourne l'auteur correspondant au code pass� en argument. */
	
	public Auteur getAuteur(int codeAuteur)
	{
		Auteur temp = new Auteur(codeAuteur);
		if (mapA.containsKey(temp)) 
		{
			return (Auteur)((TreeMap) mapA).tailMap(temp).firstKey();
		} else
			return null;
	}

	/* Retourne le livre correspondant au titre pass� en argument. */
	
	public Livre getLivre(String nomLivre) {
		Livre temp = new Livre(nomLivre);
		if (mapL.containsKey(temp)) {
			return (Livre) ((TreeMap) mapL).tailMap(temp).firstKey();
		} else
			return null;
	}
	
	/* Retourne le livre correspondant au code pass� en argument. */
	
	public Livre getLivre(int codeLivre) {
		Livre temp = new Livre(codeLivre);
		if (mapL.containsKey(temp)) {
			return (Livre) ((TreeMap) mapL).tailMap(temp).firstKey();
		} else
			return null;
	}
	

	/* Retourne une collection contenant tous les parcs o� le man�ge, dont le nom est
	   passé en argument, est pr�sent. */
	
	/*public Collection getColLivresAut(String titre) {
		Livre livre = getTitre(titre);
		if (livre == null)
			return null;
		else
			//return manege.getParcs();
			return (Set) mapL.get(manege);
	}*/

	/* Retourne une collection contenant tous les livres de l'auteur dont le nom
	   est pass� en argument. */
	
	public Collection colLivresAut(String nom) {
		Auteur auteur = getAuteur(nom);
		if (auteur == null)
			return null;
		else
			//return parc.getManeges();
			return (Set) mapA.get(auteur);
	}

	/* Retourne une collection contenant tous les parcs situés dans létat (province)
	   passé en argument. 
	   
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

	/* Retourne une Map contenant tous les parcs, et leurs manèges, situés dans létat
	   (province) passé en argument. Sil y a lieu, les parcs situés dans létat mais
	   nayant aucun manège sont aussi inclus. 
	
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

		/* Affichage de tous les auteurs pr�sents dans la map ainsi que leur diff�rents livres  */
	
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

	

	/* M�thode permettant de lire un fichier texte et de retourner une
	   liste des �l�ments lus */
	
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


	/* M�thode permettant d'�crire un fichier texte à partir d'un Vector de Vector,
	   un Vector par ligne d�compos� en champs s�par�s par des tabulations ('\t')
	   dans un fichier dont le nom est transmis en param�tre. */
	
	static private void ecrireFichier(Vector data, String nomFichier) throws IOException
 {
		FileWriter fw = null;
		try {
			String nomFichier2="/Bibliographie/src/"+nomFichier;
			fw = new FileWriter(nomFichier2);
		}
		catch (java.io.FileNotFoundException e) {
			System.out.println("Incapable d'�crire sur le fichier " + nomFichier + " \n");
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