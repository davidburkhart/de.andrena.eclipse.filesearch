package de.andrena.eclipse.filesearch.core;

import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.junit.Rule;
import org.junit.Test;

public class SearchConfigurationPluginTest {

	@Rule
	public TemporaryProject temporaryProject = new TemporaryProject("junit-test-project");

	@Test
	public void initialzedWithWorkspaceRoot() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		SearchConfiguration searchConfiguration = new SearchConfiguration();
		assertThat(searchConfiguration.createScope().getRoots(), arrayContaining(workspace.getRoot()));
	}

	@Test
	public void returnsOnlyRealRootDirectoriesWithoutChildren() throws CoreException {
		IFolder folder1 = temporaryProject.createFolder("folder1");
		IFolder folder2 = temporaryProject.createFolder("folder2");

		SearchConfiguration searchConfiguration = new SearchConfiguration();
		searchConfiguration.setContainers(Arrays.asList(temporaryProject.getProject(), folder1, folder2));
		assertThat(searchConfiguration.createScope().getRoots(), arrayContaining(temporaryProject.getProject()));
	}
}
