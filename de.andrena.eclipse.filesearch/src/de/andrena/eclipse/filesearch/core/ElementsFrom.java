package de.andrena.eclipse.filesearch.core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public class ElementsFrom {

	private ISelection selection;

	public ElementsFrom(ISelection selection) {
		this.selection = selection;
	}

	public List<?> asObject() {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			return structuredSelection.toList();
		}
		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> as(Class<T> target) {
		LinkedList<T> result = new LinkedList<T>();
		for (Object element : asObject()) {
			if (target.isAssignableFrom(element.getClass())) {
				result.add((T) element);
			}
		}
		return result;
	}
}
