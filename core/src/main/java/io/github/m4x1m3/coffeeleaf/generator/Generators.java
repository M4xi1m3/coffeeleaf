/**
 * Copyright 2021 Maxime "M4x1m3" FRIESS
 * 
 * This file is part of CoffeeLeaf.
 *
 * CoffeeLeaf is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CoffeeLeaf is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with CoffeeLeaf.  If not, see <https://www.gnu.org/licenses/>.
 */
package io.github.m4x1m3.coffeeleaf.generator;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;

/**
 * Class used to load the IGenerators at runtime
 * 
 * @author Maxime "M4x1m3" FRIESS
 *
 */
public class Generators {
	private static HashMap<String, Class<? extends IGenerator>> generators;

	/**
	 * Load the IGenerators
	 */
	@SuppressWarnings("unchecked")
	public static void load() {
		generators = new HashMap<String, Class<? extends IGenerator>>();

		Reflections r = new Reflections("");
		Set<Class<?>> generators = r.getTypesAnnotatedWith(Generator.class);
		for (Class<?> clazz : generators) {
			if (IGenerator.class.isAssignableFrom(clazz)) {
				Class<? extends IGenerator> loader = (Class<? extends IGenerator>) clazz;
				Generators.generators.put(loader.getAnnotation(Generator.class).value(), loader);
			} else {
				System.err.println("[WARNING] Class " + clazz.getCanonicalName()
						+ " has Generator annotation but doesn't implement IGenerator!");
			}
		}
	}

	/**
	 * Get an IGenerator from the loaded generators
	 * 
	 * @param name Name of the generator to get
	 * @return An instance of IGenerator, or null if not found
	 */
	public static IGenerator getGenerator(String name) {
		Class<? extends IGenerator> clazz = generators.get(name);

		if (clazz == null)
			return null;

		IGenerator instance = null;

		try {
			instance = clazz.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			System.err.println("[ERROR] Can't instanciate generator " + clazz.getCanonicalName());
			e.printStackTrace();
		}

		return instance;
	}
}
