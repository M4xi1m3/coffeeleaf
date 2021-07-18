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
package io.github.m4x1m3.coffeeleaf.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

import io.github.m4x1m3.coffeeleaf.generator.Generators;
import io.github.m4x1m3.coffeeleaf.generator.IGenerator;
import io.github.m4x1m3.coffeeleaf.loader.ILoader;
import io.github.m4x1m3.coffeeleaf.loader.Loaders;
import io.github.m4x1m3.coffeeleaf.model.UMLModel;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/**
 * Main class
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class CoffeeLeaf {

	public static void main(String[] args) {
		String name = "coffeeleaf.yml";

		if (args.length == 1) {
			name = args[0];
		}

		FileInputStream fis = null;
		try {
			File configf = new File(name);
			fis = new FileInputStream(configf);
		} catch (IOException e) {
			System.err.println("Unable to load config file " + name);
			e.printStackTrace();
			System.exit(1);
		}

		Configuration conf = ConfigurationProvider.getProvider(YamlConfiguration.class).load(fis);
		
		Loaders.load();
		Generators.load();
		
		ILoader loader = Loaders.getLoader(conf.getString("loader.name"));
		loader.config(conf.getSection("loader.config"));
		
		Set<UMLModel> models = loader.load();
		
		IGenerator generator = Generators.getGenerator(conf.getString("generator.name"));
		generator.config(conf.getSection("generator.config"));
		
		models.forEach(m -> generator.generate(m));
	}
}
