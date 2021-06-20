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
package io.github.m4x1m3.coffeeleaf;

import io.github.m4x1m3.coffeeleaf.model.UMLModel;
import io.github.m4x1m3.coffeeleaf.utils.RelfectUtil;

/**
 * Main class
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class CoffeeLeaf {
	public static void main(String[] args) {
		UMLModel model = new UMLModel("test");

		for (Class<? extends Object> c : RelfectUtil.getGenUMLClasses()) {
			model.addClass(c);
		}

		model.debug();
	}
}
