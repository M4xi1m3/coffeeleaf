/**
 * Copyright 2021 Maxime "M4x1m3" FRIESS
 *
 * This file is part of CoffeeLeaf.
 *
 * CoffeeLeaf is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CoffeeLeaf is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CoffeeLeaf.  If not, see <https://www.gnu.org/licenses/>.
 */
package io.github.m4x1m3.coffeeleaf.model;

/**
 * @author Maxime "M4x1m3" FRIESS
 *
 */
public class UMLRelation {
	private UMLRelationType type;
	private UMLClass from;
	private UMLClass to;
	private UMLRelationDirection direction;

	public UMLRelation(UMLClass from, UMLClass to, UMLRelationType type, UMLRelationDirection direction) {
		this.type = type;
		this.from = from;
		this.to = to;
		this.direction = direction;
	}

	public UMLRelationType getType() {
		return type;
	}

	public UMLClass getFrom() {
		return from;
	}

	public UMLClass getTo() {
		return to;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = hash * 31 + from.getName().hashCode();
		hash = hash * 31 + to.getName().hashCode();
		hash = hash * 31 + type.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof UMLRelation) {
			UMLRelation o = (UMLRelation) other;
			if (o == this) {
				return true;
			} else {
				return this.from.getName().equals(o.from.getName()) && this.to.getName().equals(o.to.getName()) && this.type.equals(o.type);
			}
		} else {
			return false;
		}
	}

	public UMLRelationDirection getDirection() {
		return direction;
	}
}
