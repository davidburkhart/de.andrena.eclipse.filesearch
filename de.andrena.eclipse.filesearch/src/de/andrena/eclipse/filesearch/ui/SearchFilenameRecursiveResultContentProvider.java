package de.andrena.eclipse.filesearch.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.andrena.eclipse.filesearch.core.FileNode;

final class SearchFilenameRecursiveResultContentProvider implements ITreeContentProvider {

	private FileNode root;

	@Override
	public Object[] getElements(Object inputElement) {
		return new Object[] { root };
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
		if (element == root) {
			return root.getFile();
		}
		if (element instanceof FileNode) {
			FileNode fileNode = (FileNode) element;
			return fileNode.getParent();
		}
		return null;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		root = new FileNode((IFile) newInput);
	}

	@Override
	public void dispose() {
	}
}