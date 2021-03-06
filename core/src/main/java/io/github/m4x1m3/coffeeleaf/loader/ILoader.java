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

import java.util.Set;

import io.github.m4x1m3.coffeeleaf.model.UMLModel;
import net.md_5.bungee.config.Configuration;

/**
 * Interface implemented by UML models loaders
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public interface ILoader {
	/**
	 * Loads the models
	 * 
	 * @return The loaded models
	 */
	public Set<UMLModel> load();
	
	/**
	 * Load loader's configuration
	 * @param conf
	 */
	public void config(Configuration conf);
}
