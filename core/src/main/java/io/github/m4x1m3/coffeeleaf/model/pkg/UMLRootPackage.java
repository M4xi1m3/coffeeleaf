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
package io.github.m4x1m3.coffeeleaf.model.pkg;

import io.github.m4x1m3.coffeeleaf.model.UMLModel;

/**
 * This represents the default (aka. root) package of an UML Model
 * 
 * @author Maxime "M4x1m3" FRIESS
 * @see UMLModel
 */
public class UMLRootPackage extends UMLPackage {
	private final UMLModel model;

	/**
	 * Create a new root package
	 * 
	 * @param model Model of the root package
	 */
	public UMLRootPackage(UMLModel model) {
		super("");
		this.model = model;
	}

	/**
	 * Get the name
	 * 
	 * @return empty string, has no name.
	 */
	public final String getName() {
		return "";
	}

	/**
	 * Set the name of the root package
	 * 
	 * @param name New name
	 * @throws RuntimeException You can't set the name if the root package, it
	 *                          doesn't have one, you idiot.
	 */
	public final void setName(String name) {
		throw new RuntimeException("Can't set name of root package!");
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = hash * 31 + model.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof UMLRootPackage) {
			UMLRootPackage o = (UMLRootPackage) other;

			return model.equals(o.model);
		} else {
			return false;
		}
	}

	/**
	 * Get the model of the package
	 * 
	 * @return Model of the package
	 */
	public UMLModel getModel() {
		return model;
	}
}
