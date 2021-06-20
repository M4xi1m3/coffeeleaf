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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import io.github.m4x1m3.coffeeleaf.annotations.GenUML;
import io.github.m4x1m3.coffeeleaf.model.UMLAccessLevel;
import io.github.m4x1m3.coffeeleaf.model.UMLClassType;

/**
 * Utility class for reflection
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class ReflectUtil {
	/**
	 * Get all classes annotated for UML generation
	 * 
	 * @param bases List of packages names
	 * @return Set of classes
	 */
	public static Set<Class<?>> getUMLClasses() {
		HashSet<Class<?>> out = new HashSet<Class<?>>();

		Reflections r = new Reflections("", new SubTypesScanner(false), new TypeAnnotationsScanner());
		out.addAll(r.getTypesAnnotatedWith(GenUML.class));

		return out;
	}

	public static UMLAccessLevel getAccessLevel(Class<? extends Object> c) {
		if ((c.getModifiers() & Modifier.PRIVATE) != 0)
			return UMLAccessLevel.PRIVATE;
		else if ((c.getModifiers() & Modifier.PUBLIC) != 0)
			return UMLAccessLevel.PUBLIC;
		else if ((c.getModifiers() & Modifier.PROTECTED) != 0)
			return UMLAccessLevel.PROTECTED;
		else
			return UMLAccessLevel.PACKAGE;
	}

	public static UMLAccessLevel getAccessLevel(Method c) {
		if ((c.getModifiers() & Modifier.PRIVATE) != 0)
			return UMLAccessLevel.PRIVATE;
		else if ((c.getModifiers() & Modifier.PUBLIC) != 0)
			return UMLAccessLevel.PUBLIC;
		else if ((c.getModifiers() & Modifier.PROTECTED) != 0)
			return UMLAccessLevel.PROTECTED;
		else
			return UMLAccessLevel.PACKAGE;
	}
	
	public static UMLClassType getClassType(Class<? extends Object> c) {
		if (c.isEnum())
			return UMLClassType.ENUM;
		else if (c.isAnnotation())
			return UMLClassType.ANNOTATION;
		else if ((c.getModifiers() & Modifier.INTERFACE) != 0)
			return UMLClassType.INTERFACE;
		else if ((c.getModifiers() & Modifier.ABSTRACT) != 0)
			return UMLClassType.ABSTRACT;
		else
			return UMLClassType.CLASS;
	}

}
