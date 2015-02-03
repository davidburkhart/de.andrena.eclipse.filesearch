package de.andrena.eclipse.filesearch.core;

import static org.eclipse.core.runtime.IStatus.OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Rule;
import org.junit.Test;

public class SearchFilenameAndAddResultToUsersPluginTest {

	@Rule
	public TemporaryProject temporaryProject = new TemporaryProject("junit-test-project");

	private SearchConfiguration configuration = new SearchConfiguration();
	private SearchResultCollector collector = new SearchResultCollector();

	@Test
	public void findsFilenameWithoutExtension() throws CoreException {
		temporaryProject.createFolder("test1");
		temporaryProject.createFolder("test2");
		IFile gruen = temporaryProject.createFile("test1/gruen.txt", "Inhalt: nix");
		IFile blau = temporaryProject.createFile("test2/blau.txt", "Inhalt: gruen");

		FileNode fileNode = new FileNode(gruen);
		IStatus status = runSearch(fileNode);
		assertThat(status.getCode(), is(OK));

		assertThat(collector.getUsers(), contains(blau));

		assertThat(fileNode.getUsers(), hasSize(1));
		assertThat(fileNode.getUsers().get(0).getFile(), is(blau));
	}

	private IStatus runSearch(FileNode fileNode) {
		return new SearchFilenameAndAddResultToUsers(configuration, fileNode, collector) //
				.run(new NullProgressMonitor());
	}

}
