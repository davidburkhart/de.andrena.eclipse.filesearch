package de.andrena.eclipse.filesearch.core;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.search.ui.text.FileTextSearchScope;

public class SearchConfiguration {

	private static final String[] FILENAME_PATTERNS = new String[] { "*" };

	private List<IContainer> containers;

	public SearchConfiguration() {
		containers = Collections.singletonList(ResourcesPlugin.getWorkspace().getRoot());
	}

	public FileTextSearchScope createScope() {
		IResource[] roots = containers.toArray(new IResource[containers.size()]);
		return FileTextSearchScope.newSearchScope(roots, FILENAME_PATTERNS, false);
	}

	public void setContainers(List<IContainer> containers) {
		this.containers = containers;
	}
}
