import java.util.ArrayList;

public class Launcher {

	// pi : duree de la tache
	// di : date de fin de la tache pour pas etre en retard
	// wi : poids de la tache
	
	// retard poids de la tache * nombre unites de retard
	
	private static ArrayList<Tache> maListe;
	private static Noeud monArbre;
	
	public static void main(String[] args) {
		
		maListe = new ArrayList<Tache>();
		maListe.add(new Tache(1, 3, 16, 2));
		maListe.add(new Tache(2, 7, 15, 3));
		maListe.add(new Tache(3, 2, 9, 1));
		maListe.add(new Tache(4, 3, 4, 2));
		maListe.add(new Tache(5, 5, 10, 4));
	
		ArrayList<Tache> listeVide = new ArrayList<Tache>(); // pour ne pas utiliser null
		
		// Calcul du retard avec l'ordre de l'enonce (Q1)
		tri_croissant(maListe);
		Noeud exempleQ1 = new Noeud(null, maListe, listeVide);
		int retard = exempleQ1.getRetard().getValeur();
		
		System.out.println("Question 1 : Retard : " + retard);
		
		// Creation de l'arbre pour le branch & bound
		monArbre = new Noeud(null, listeVide, maListe);
		
		// Calcul de la borne inferieure
		Borne borneInf = monArbre.getBorneInferieure();
		System.out.println("Question 2 : Borne inferieure : "+borneInf.getValeur());
		System.out.println("Question 3 : Resolution instance avec le chemin  : "+borneInf.getChemin());
		
	}
	
	/**
	 * Retourne tab trie dans l'ordre croissant en fonction de d/w
	 * @param tab
	 */
	public static void tri_croissant(ArrayList<Tache> tab){
	    boolean tri_fini = false;
	    int taille = tab.size();
	    double temp1, temp2;
	    Tache temp;
	    
	    while(!tri_fini){
	        tri_fini = true;
	    
	        for(int i=0 ; i < taille-1 ; i++){
	            temp1 = tab.get(i).getD() / tab.get(i).getW();
	            temp2 = tab.get(i+1).getD() / tab.get(i+1).getW();
	        	if(temp1 > temp2){
	                temp = tab.get(i);
	                tab.set(i, tab.get(i+1));
	                tab.set(i+1, temp);
	                tri_fini = false;
	            }
	        }
	        taille--;
	    }
	}
}
