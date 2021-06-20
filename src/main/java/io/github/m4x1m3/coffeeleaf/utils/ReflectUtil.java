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
package io.github.m4x1m3.coffeeleaf.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import io.github.m4x1m3.coffeeleaf.DefaultConfig;
import io.github.m4x1m3.coffeeleaf.UMLConfig;
import io.github.m4x1m3.coffeeleaf.annotations.GenUML;
import io.github.m4x1m3.coffeeleaf.annotations.UMLConf;

/**
 * Utility class for reflection
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class ReflectUtil {
	private static Reflections reflections = new Reflections("", new SubTypesScanner(false),
			new TypeAnnotationsScanner());

	/**
	 * Get all the class with the GenUML annotation
	 * 
	 * @return A Set of all the classes with the GenUML annotation
	 * 
	 * @see GenUML
	 */
	public static Set<Class<?>> getGenUMLClasses() {
		return reflections.getTypesAnnotatedWith(GenUML.class);
	}

	public static Class<? extends UMLConfig> getConfigClass() {
		int nb = 0;
		Class<? extends UMLConfig> cfg = DefaultConfig.class;

		for (Class<? extends Object> c : reflections.getTypesAnnotatedWith(UMLConf.class)) {
			cfg = c.getAnnotation(UMLConf.class).config();
			nb++;
		}

		if (nb == 0) {
			System.err.println("Warning: No config set, using default!");
		} else if (nb > 1) {
			System.err.println("Warning: config set more than once!");
		}
		
		return cfg;
	}
	
	public static UMLConfig getConfig() {
		Class<? extends UMLConfig> cfgClass = getConfigClass();
		try {
			return cfgClass.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			System.err.println("Warning: Exception while instancing config, using default!");
			e.printStackTrace();
		}
		return new DefaultConfig();
	}
}
