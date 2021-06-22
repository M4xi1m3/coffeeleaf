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

import java.util.HashSet;
import java.util.Set;

import io.github.m4x1m3.coffeeleaf.model.cls.UMLClass;
import io.github.m4x1m3.coffeeleaf.model.pkg.UMLRootPackage;
import io.github.m4x1m3.coffeeleaf.model.rel.UMLRelation;

/**
 * Represents a UML Model
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class UMLModel {
	/**
	 * Name of the model
	 */
	private String name;

	/**
	 * Relations present in the model
	 */
	private Set<UMLRelation> relations;

	/**
	 * Classes present in the model
	 */
	private Set<UMLClass> classes;

	/**
	 * Root package of the model
	 */
	private UMLRootPackage rootpkg;

	/**
	 * Constructor
	 * 
	 * @param name Name of the model
	 */
	public UMLModel(String name) {
		this.name = name;
		this.rootpkg = new UMLRootPackage(this);
		this.relations = new HashSet<UMLRelation>();
		this.classes = new HashSet<UMLClass>();
	}

	/**
	 * Get the name of the model
	 * 
	 * @return Name of the model
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the root package of the model
	 * 
	 * @return The root package of the model
	 */
	public UMLRootPackage getRootPackage() {
		return rootpkg;
	}

	/**
	 * Add a relation in the model
	 * 
	 * @param rel New relation
	 */
	public void addRelation(UMLRelation rel) {
		this.relations.add(rel);
	}

	/**
	 * Add a new class in the model
	 * 
	 * @param clazz Class to add
	 */
	public void addClass(UMLClass clazz) {
		this.classes.add(clazz);
	}

	/**
	 * Get the relations in the model
	 * 
	 * @return Relations in the model
	 */
	public Set<UMLRelation> getRelations() {
		return this.relations;
	}

	/**
	 * Get the classes in the model
	 * 
	 * @return Map. Key is the full name of the class, value is the class.
	 */
	public Set<UMLClass> getClasses() {
		return classes;
	}

	/**
	 * Set the name of the model
	 * 
	 * @param name New name of the model
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = hash * 31 + name.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof UMLModel) {
			UMLModel o = (UMLModel) other;
			return o.name.equals(this.name);
		} else {
			return false;
		}
	}
}
