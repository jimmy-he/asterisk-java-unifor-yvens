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
		
		String[] dialPlan = new String[dialPlanList.size()];
		for (int i = 0; i < dialPlan.length; i++) {
			dialPlan[i] = dialPlanList.get(i);
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
}
