package de.andrena.eclipse.filesearch.core;

import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.search.core.text.TextSearchEngine;
import org.eclipse.search.core.text.TextSearchMatchAccess;
import org.eclipse.search.core.text.TextSearchRequestor;
import org.eclipse.search.ui.text.FileTextSearchScope;

public class SearchFilenameAndAddResultToUsers extends Job {

	private static final int MAX_RESULTS_PER_FILE = 50;

	private final SearchConfiguration configuration;
	private final FileNode fileNode;
	private final SearchResultFoundNotifier notifier;

	private IProgressMonitor monitor;

	public SearchFilenameAndAddResultToUsers(SearchConfiguration configuration, FileNode fileNode,
			SearchResultFoundNotifier notifier) {
		super("Searching...");
		this.configuration = configuration;
		this.fileNode = fileNode;
		this.notifier = notifier;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		this.monitor = monitor;
		fileNode.startSearch();
		return TextSearchEngine.create().search(createScope(), createRequestor(), createPattern(), monitor);
	}

	private FileTextSearchScope createScope() {
		return configuration.createScope();
	}

	private Pattern createPattern() {
		return TextSearchEngine.createPattern(fileNode.getFilenameWithoutExtension(), false, false);
	}

	private boolean handleMatch(TextSearchMatchAccess matchAccess) {
		if (fileNode.getUsers().size() >= MAX_RESULTS_PER_FILE) {
			monitor.setCanceled(true);
			return false;
		}

		IFile file = matchAccess.getFile();
		FileNode user = new FileNode(file);
		fileNode.addUser(user);
		notifier.foundResult(user);
		return false;
	}

	private TextSearchRequestor createRequestor() {
		return new TextSearchRequestor() {
			@Override
			public boolean acceptPatternMatch(TextSearchMatchAccess matchAccess) throws CoreException {
				return handleMatch(matchAccess);
			}
		};
	}
}