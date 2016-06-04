/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui.performance.basic;

import java.util.Map;

import br.ufrn.ase.r.GraphicPlot;
import br.ufrn.ase.service.performance.basic.HighestAverageService;
import br.ufrn.ase.util.MapUtil;
import br.ufrn.ase.util.SwingUtil;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ConsoleHighestAverage {

	/** QTD to plot in the graphic */
	public final static int QTD = 10;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Starting "+Thread.currentThread().getStackTrace()[1].getClassName()+" ... ");
		
		String systemVersion = SwingUtil.readSystemVersion();
		boolean executeMining = SwingUtil.readTypeExecution();
		
		long start = System.currentTimeMillis();
		
		Map<String, Double> mapRange_3_21 = new ConsoleHighestAverage().getScenariosHighestAverage(systemVersion, executeMining);
		
		
		GraphicPlot plot = new GraphicPlot();
		
		plot.drawColumnChart(mapRange_3_21, "Average", "Scenario", "Times");
		plot.drawBoxPlotChart(mapRange_3_21);

		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");


	}
	
	public Map<String, Double> getScenariosHighestAverage(String systemVersion, boolean executeMining){
		 
		HighestAverageService service = new HighestAverageService();
		
		Map<String, Double> mapRange_3_21 = service.findAvaregeScenarios(systemVersion, executeMining, false);

		mapRange_3_21 = MapUtil.cutOff(mapRange_3_21, QTD);
		
		return mapRange_3_21;
	}

}
