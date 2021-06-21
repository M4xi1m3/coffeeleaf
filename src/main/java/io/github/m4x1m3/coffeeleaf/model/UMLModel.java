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
package io.github.m4x1m3.coffeeleaf.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.github.m4x1m3.coffeeleaf.annotations.GenUML;

/**
 * Represents a UML Model
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class UMLModel {
	private String name;

	private Set<UMLRelation> relations;
	private Map<Class<?>, UMLClass> classes;

	private UMLRootPackage rootpkg;

	public UMLModel(String name) {
		this.name = name;
		this.rootpkg = new UMLRootPackage();
		this.relations = new HashSet<UMLRelation>();
		this.classes = new HashMap<Class<?>, UMLClass>();
	}

	public void addClass(Class<? extends Object> clazz, GenUML gu) {
		String name = clazz.getCanonicalName();

		ArrayDeque<String> names = new ArrayDeque<String>();
		for (String s : name.split("\\.")) {
			names.add(s);
		}

		UMLPackage current = rootpkg;

		while (names.size() > 1) {
			current = current.findOrCreatePackage(names.pop());
		}

		UMLClass c = new UMLClass(clazz, current);

		for (Method m : clazz.getDeclaredMethods()) {
			// Dirty hack to avoid lambdas and other shit
			if ((m.getDeclaringClass().equals(clazz) && !m.getName().contains("$") && gu.methods())
					|| m.isAnnotationPresent(GenUML.class)) {
				UMLMethod meth = new UMLMethod(m, c);
				c.addMethod(meth);
			}
		}

		for (Constructor<?> t : clazz.getDeclaredConstructors()) {
			// Dirty hack to avoid lambdas and other shit
			if ((t.getDeclaringClass().equals(clazz) && !t.getName().contains("$") && gu.constructors())
					|| t.isAnnotationPresent(GenUML.class)) {
				UMLConstructor cons = new UMLConstructor(t, c);
				c.addConstructor(cons);
			}
		}

		for (Field f : clazz.getDeclaredFields()) {
			// Dirty hack to avoid lambdas and other shit
			if ((f.getDeclaringClass().equals(clazz) && !f.getName().contains("$") && gu.fields())
					|| f.isAnnotationPresent(GenUML.class)) {
				UMLField field = new UMLField(f, c);
				c.addField(field);
			}
		}

		current.addClass(c);
		this.classes.put(clazz, c);

		for (Class<?> inter : clazz.getInterfaces()) {
			if (!this.classes.containsKey(inter))
				this.addClass(inter, gu);
			this.addRelation(new UMLRelation(classes.get(clazz), classes.get(inter), UMLRelationType.IMPLEMENTS,
					UMLRelationDirection.UP));
		}

		if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
			if (!this.classes.containsKey(clazz.getSuperclass()))
				this.addClass(clazz.getSuperclass(), gu);
			this.addRelation(new UMLRelation(classes.get(clazz), classes.get(clazz.getSuperclass()),
					UMLRelationType.EXTENDS, UMLRelationDirection.UP));
		}

	}

	public String getName() {
		return name;
	}

	public UMLRootPackage getRootPackage() {
		return rootpkg;
	}

	public void addRelation(UMLRelation rel) {
		this.relations.add(rel);
	}

	public Set<UMLRelation> getRelations() {
		return new HashSet<UMLRelation>(this.relations);
	}

	public void debug() {
		System.out.println("[MDL] " + name);
		rootpkg.debug(0);
	}
}
