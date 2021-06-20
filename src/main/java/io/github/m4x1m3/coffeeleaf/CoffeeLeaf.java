package io.github.m4x1m3.coffeeleaf;

import io.github.m4x1m3.coffeeleaf.model.UMLModel;
import io.github.m4x1m3.coffeeleaf.utils.RelfectUtil;

public class CoffeeLeaf {
	public static void main(String[] args) {
		UMLModel model = new UMLModel("test");
		
		for(Class<? extends Object> c : RelfectUtil.getGenUMLClasses()) {
			model.addClass(c);
		}
		
		model.debug();
	}
}
