/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.dao.mongodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.RegistroEntradaDAO;
import br.ufrn.ase.dao.UserScenariosDAO;
import br.ufrn.ase.domain.LogOperacao;
import br.ufrn.ase.domain.RegistroEntrada;

/**
 * This class should have mining method to get information from mongodb
 * database.
 * 
 * @author jadson - jadsonjs@gmail.com
 */
public class UserScenariosMongoDAO implements UserScenariosDAO {

	/**
	 * This method gets from the mongodb a map of all users and the time that
	 * each user spends to access all pages (scenarios) in a specific version of
	 * a system.
	 * 
	 * @param system_version
	 *            The system and its version
	 * @param is_user_enabled
	 *            If true the map key will be like user_id+scenario. If false it
	 *            will be only scenario.
	 * @return A map like <user_id+scenario, {timeScenario1, timeScenario2,
	 *         timeScenario3, ..., timeScenarioN}> if is_user_enabled is true,
	 *         or <scenario, {timeScenario1, timeScenario2, timeScenario3, ...,
	 *         timeScenarioN}> if is_user_enabled is false.
	 */
	public Map<String, List<Double>> findUserScenario(String system_version, boolean is_user_enabled) {
		Map<String, List<Double>> retorno = new HashMap<String, List<Double>>();

		List<RegistroEntrada> registros = DAOFactory.getDAO(RegistroEntradaDAO.class)
				.findAllBySystemVersion(system_version, "idUsuario", "logOperacao.action", "logOperacao.tempo");

		for (RegistroEntrada registroEntrada : registros) {
			for (LogOperacao log : registroEntrada.getLogOperacao()) {

				String key = (is_user_enabled ? registroEntrada.getIdUsuario() : "") + log.getAction();

				List<Double> tempos = retorno.get(key);

				if (tempos == null) {
					tempos = new ArrayList<Double>();
					retorno.put(key, tempos);
				}

				tempos.add((double) log.getTempo());
			}
		}

		return retorno;
	}

	/**
	 * This method gets from the mongodb a map of all users and the time that
	 * each user spends to access all pages (scenarios) in a specific version of
	 * a system.
	 * 
	 * @param system_version
	 *            The system and its version
	 * @return A map like <user_id+scenario, {timeScenario1, timeScenario2,
	 *         timeScenario3, ..., timeScenarioN}>
	 */
	public Map<String, List<Double>> findUserScenario(String system_version) {
		return findUserScenario(system_version, true);
	}

}
