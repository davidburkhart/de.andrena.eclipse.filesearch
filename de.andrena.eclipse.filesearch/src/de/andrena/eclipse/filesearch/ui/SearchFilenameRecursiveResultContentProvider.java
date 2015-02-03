package de.andrena.eclipse.filesearch.ui;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.andrena.eclipse.filesearch.core.FileNode;
import de.andrena.eclipse.filesearch.core.SearchFilenameRecursiveRoot;

final class SearchFilenameRecursiveResultContentProvider implements ITreeContentProvider {

	private SearchFilenameRecursiveRoot root;

	@Override
	public Object[] getElements(Object inputElement) {
		return new Object[] { root.getRootNode() };
	}

	@Override
	public boolean hasChildren(Object element) {
		return true;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof FileNode) {
			FileNode fileNode = (FileNode) parentElement;
			return fileNode.getUsers().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		if (element == root.getRootNode()) {
			return root;
		}
		if (element instanceof FileNode) {
			FileNode fileNode = (FileNode) element;
			return fileNode.getParent();
		}
		return null;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		root = (SearchFilenameRecursiveRoot) newInput;
	}

	@Override
	public void dispose() {
	}
}