package de.andrena.eclipse.filesearch.ui;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

final class WorkbenchContainerContentProvider extends BaseWorkbenchContentProvider {
	@Override
	public Object[] getChildren(Object element) {
		Object[] children = super.getChildren(element);
		List<Object> result = new LinkedList<Object>();
		for (Object object : children) {
			if (!(object instanceof IFile)) {
				result.add(object);
			}
		}
		return result.toArray();
	}
}