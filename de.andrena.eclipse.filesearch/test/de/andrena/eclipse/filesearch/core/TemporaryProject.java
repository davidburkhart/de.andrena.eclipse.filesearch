package de.andrena.eclipse.filesearch.core;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.rules.ExternalResource;

public class TemporaryProject extends ExternalResource {

	private final String projectName;
	private IProject project;

	public TemporaryProject(String projectName) {
		this.projectName = projectName;
	}

	@Override
	protected void before() throws Throwable {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		project.create(noProgress());
		project.open(noProgress());
	}

	@Override
	protected void after() {
		try {
			project.delete(true, noProgress());
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	public IFolder createFolder(String folderName) throws CoreException {
		IFolder folder = project.getFolder(folderName);
		folder.create(false, true, noProgress());
		return folder;
	}

	public IFile createFile(String fileName, String content) throws CoreException {
		ByteArrayInputStream source = new ByteArrayInputStream(content.getBytes());
		IFile file = project.getFile(fileName);
		file.create(source, false, noProgress());
		return file;
	}

	public IProject getProject() {
		return project;
	}

	private IProgressMonitor noProgress() {
		return new NullProgressMonitor();
	}

}
