package de.andrena.eclipse.filesearch.core;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;

public class FileNode implements IAdaptable {

	private final IFile file;
	private final List<FileNode> users = new LinkedList<>();
	private boolean searchStarted = false;
	private FileNode parent;

	public FileNode(IFile file) {
		this.file = file;
	}

	public void addUser(FileNode user) {
		users.add(user);
		user.setParent(this);
	}

	private void setParent(FileNode parent) {
		this.parent = parent;
	}

	public List<FileNode> getUsers() {
		return users;
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {

		if (adapter.equals(IResource.class) || adapter.equals(IFile.class)) {
			return file;
		}

		return file.getAdapter(adapter);
	}

	public String getFilenameWithoutExtension() {
		String extension = file.getFileExtension();
		String name = file.getName();
		if (extension == null) {
			return name;
		}

		return name.substring(0, name.length() - extension.length() - 1);
	}

	public boolean searchStarted() {
		return searchStarted;
	}

	public void startSearch() {
		searchStarted = true;
	}

	public IFile getFile() {
		return file;
	}

	public FileNode getParent() {
		return parent;
	}
}
