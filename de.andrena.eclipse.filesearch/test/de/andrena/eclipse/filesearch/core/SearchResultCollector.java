package de.andrena.eclipse.filesearch.core;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;

class SearchResultCollector implements SearchResultFoundNotifier {

	private List<IFile> users = new LinkedList<>();

	@Override
	public void foundResult(FileNode user) {
		users.add(user.getFile());
	}

	List<IFile> getUsers() {
		return users;
	}
}