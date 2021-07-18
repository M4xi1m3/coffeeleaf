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
package io.github.m4x1m3.coffeeleaf.loader;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;

/**
 * Class used to load the ILoaders at runtime
 * 
 * @author Maxime "M4x1m3" FRIESS
 *
 */
public class Loaders {
	private static HashMap<String, Class<? extends ILoader>> loaders;

	/**
	 * Loads the ILoaders
	 */
	@SuppressWarnings("unchecked")
	public static void load() {
		loaders = new HashMap<String, Class<? extends ILoader>>();

		Reflections r = new Reflections("");
		Set<Class<?>> loaders = r.getTypesAnnotatedWith(Loader.class);
		for (Class<?> clazz : loaders) {
			if (ILoader.class.isAssignableFrom(clazz)) {
				Class<? extends ILoader> loader = (Class<? extends ILoader>) clazz;
				Loaders.loaders.put(loader.getAnnotation(Loader.class).value(), loader);
			} else {
				System.err.println("[WARNING] Class " + clazz.getCanonicalName()
						+ " has Loader annotation but doesn't implement ILoader!");
			}
		}
	}

	/**
	 * Get an ILoader from the loaded generators
	 * 
	 * @param name Name of the loader to get
	 * @return An instance of ILoader, or null if not found
	 */
	public static ILoader getLoader(String name) {
		Class<? extends ILoader> clazz = loaders.get(name);

		if (clazz == null)
			return null;

		ILoader instance = null;

		try {
			instance = clazz.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			System.err.println("[ERROR] Can't instanciate loader " + clazz.getCanonicalName());
			e.printStackTrace();
		}

		return instance;
	}
}
