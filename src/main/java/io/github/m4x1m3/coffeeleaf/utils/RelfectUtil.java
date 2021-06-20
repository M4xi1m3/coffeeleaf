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

import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import io.github.m4x1m3.coffeeleaf.annotations.GenUML;

/**
 * Utility class for reflection
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class RelfectUtil {
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
}
