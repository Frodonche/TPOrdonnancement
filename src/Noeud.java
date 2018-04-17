import java.util.ArrayList;

public class Noeud {
	private Noeud pere;
	private ArrayList<Noeud> lesFils;
	private ArrayList<Tache> lesTachesRangees;
	private ArrayList<Tache> lesTachesARanger;
	
	public Noeud(Noeud pere, ArrayList<Tache> rangees, ArrayList<Tache> aRanger) {
		this.pere = pere;
		this.lesTachesRangees = rangees;
		this.lesTachesARanger = aRanger;
		this.lesFils = new ArrayList<Noeud>();
		
		genererFils();
		
	}
	
	private void genererFils() {
		ArrayList<Tache> tempRangees;
		ArrayList<Tache> tempARanger;
		
		// Pour chaque tache a ranger
		for(Tache t : lesTachesARanger) {
			tempRangees = new ArrayList<Tache>();
					
			// On recupere les taches rangees
			for(Tache v : lesTachesRangees) {
				tempRangees.add(v);
			}
			// On ajoute la classe que l'on veut ranger
			tempRangees.add(t);
			
			// On reprend les taches a ranger
			tempARanger = new ArrayList<Tache>(lesTachesARanger);
			// Et on leur enleve la tache que l'on vient de ranger
			tempARanger.remove(t);
			
			// Puis on stocke les taches pour creer les fils plus tard
			lesFils.add(new Noeud(this, tempRangees, tempARanger));
		}
	}
	
	public String getStringLesTachesRangees() {
		String result = "[ ";
		for(Tache t : lesTachesRangees) {
			result+=""+t.getNum()+" ";
		}
		result+="]";
		return result;
	}
	
	/**
	 * Recupere le retard dans la configuration actuelle des taches
	 * @return
	 */
	public Borne getRetard() {
		int sommeP = 0;
		int retard = 0;
		Tache current;
		for(int i = 0; i < lesTachesRangees.size(); i++) {
			current = lesTachesRangees.get(i);
			sommeP += current.getP();
			if(sommeP > current.getD())
				retard += (sommeP - current.getD()) * current.getW();
		}
		return new Borne(retard, this.getStringLesTachesRangees());
	}
	
	
	/**
	 * Calcule le retard minimum pour ce noeud
	 * Si il a des fils, retourne le retard minimum entre les fils
	 * Sinon, retourne son propre retard
	 * @return
	 */
	public Borne getBorneInferieure() {
		Borne result = new Borne((int)Float.POSITIVE_INFINITY, "rate");
		Borne tempBI;
		// si pas de fils
		if(lesFils.size() < 1) {
			result = getRetard();
		}else { // si des fils, on calcule le retard minimum entre les fils
			for(Noeud n : lesFils) {
				tempBI = n.getBorneInferieure();
				if(tempBI.getValeur() < result.getValeur()) {
					result = tempBI;
				}
			}
		}
		return result;
	}
	
	
}
