package model.dial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe para representar um plano de discagem
 * Um plano de discagem tem diferentes rotas
 * 
 * Exemplo:
 * [LOCAL]                                     <---- Tag
	exten => *35,1,Answer()                    <
	exten => *35,n,MusicOnHold(default,90)     <--- Rota *35
	exten => *35,n,Hangup()                    <
	
	exten => *50,1,Macro(dizramal)             <
	exten => *50,n,Hangup()                    <--- Rota *50
 * 
 * @author yvens
 *
 */
public class DialPlan implements Comparable<DialPlan>{

	private String tag;
	private List<DialRoute> routeList;
	
	public DialPlan(String tag) {
		super();
		this.tag = tag;
		routeList = new ArrayList<DialRoute>();
	}
	
	/**
	 * Método para adição de rotas na lista de rota no plano de discagem
	 * Logo depois da inserção a lista é ordenada
	 * 
	 * @param command
	 */
	public void addRoute(DialRoute route){
		routeList.add(route);
		Collections.sort(routeList);
	}
	
	public void removeRoute(DialRoute route){
		routeList.remove(route);
		Collections.sort(routeList);
	}
	
	public boolean containsRoute(DialRoute route){
		for(DialRoute dialRoute : routeList){
			if(dialRoute.equals(route)){
				return true;
			}
		}
		return false;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public List<DialRoute> getRouteList() {
		return routeList;
	}

	public void setRouteList(List<DialRoute> routeList) {
		this.routeList = routeList;
	}

	public String[] toDialPlan(){
		List<String> dialPlanList = new ArrayList<String>();
		
		for(DialRoute route : routeList){
			String[] dialRoute = route.toDialRoute();
			
			for (int i = 0; i < dialRoute.length; i++) {
				dialPlanList.add(dialRoute[i]);
			}
		}
		
		String[] dialPlan = new String[dialPlanList.size()+1];
		dialPlan[0] = "["+tag+"]";
		
		for (int i = 1; i < dialPlan.length; i++) {
			//dialPlanList tem 1 elemento a menos que o vetor dialPlan
			dialPlan[i] = dialPlanList.get(i-1);
		}
		
		return dialPlan;
	}
	
	@Override
	/**
	 * Método para comparação, compara a tag dos dois dialplans
	 */
	public int compareTo(DialPlan o) {
		return o.getTag().compareTo(this.getTag());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		return result;
	}

	@Override
	/**
	 * Compara os valores da tag
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DialPlan other = (DialPlan) obj;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}
	
}
