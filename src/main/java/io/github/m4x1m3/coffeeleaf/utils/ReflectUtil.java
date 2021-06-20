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

import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

/**
 * Utility class for reflection
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class ReflectUtil {
	/**
	 * Get all classes in bases packages
	 * 
	 * @param bases List of packages names
	 * @return Set of classes
	 */
	public static Set<Class<?>> getClassesInPackages(String[] bases) {
		HashSet<Class<?>> out = new HashSet<Class<?>>();

		for (String base : bases) {
			Reflections r = new Reflections(base, new SubTypesScanner(false));
			out.addAll(r.getSubTypesOf(Object.class));

		}

		return out;
	}
}
