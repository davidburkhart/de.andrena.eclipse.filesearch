package de.andrena.eclipse.filesearch.ui;

import static org.eclipse.core.runtime.IStatus.ERROR;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;

import de.andrena.eclipse.filesearch.Activator;
import de.andrena.eclipse.filesearch.core.ElementsFrom;
import de.andrena.eclipse.filesearch.core.FileNode;
import de.andrena.eclipse.filesearch.core.SearchConfiguration;
import de.andrena.eclipse.filesearch.core.SearchFilenameAndAddResultToUsers;
import de.andrena.eclipse.filesearch.core.SearchResultFoundNotifier;

public class SearchFilenameRecursiveResultView extends ViewPart {

	static final String VIEW_ID = SearchFilenameRecursiveResultView.class.getName();

	private TreeViewer treeViewer;
	private SearchConfiguration configuration = new SearchConfiguration();

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());

		createTreeViewer(parent);
		hookOpenListener();
		hookActions();
	}

	private void createTreeViewer(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.VIRTUAL);
		treeViewer.setLabelProvider(new WorkbenchLabelProvider());
		treeViewer.setContentProvider(new SearchFilenameRecursiveResultContentProvider());
		hookSearchOnExpandListener();
	}

	private void hookActions() {
		IToolBarManager toolBar = getViewSite().getActionBars().getToolBarManager();
		toolBar.add(new EditSearchScopeConfiguration(configuration));
	}

	private void hookSearchOnExpandListener() {
		treeViewer.addTreeListener(new ITreeViewerListener() {
			@Override
			public void treeExpanded(TreeExpansionEvent event) {
				FileNode element = (FileNode) event.getElement();
				if (!element.searchStarted()) {
					runSearchOn(element);
				}
			}

			@Override
			public void treeCollapsed(TreeExpansionEvent event) {
			}
		});
	}

	private void hookOpenListener() {
		treeViewer.addOpenListener(new IOpenListener() {
			@Override
			public void open(OpenEvent event) {
				ISelection selection = event.getSelection();
				for (Object object : new ElementsFrom(selection).asObject()) {
					openEditor(object);
				}
			}
		});
	}

	private void openEditor(Object object) {
		try {
			IDE.openEditor(getSite().getPage(), ((FileNode) object).getFile());
		} catch (PartInitException e) {
			Status status = new Status(ERROR, Activator.getPluginId(), "Error opening editor", e);
			ErrorDialog.openError(getSite().getShell(), "Error opening editor",
					"Error opening editor: " + e.getMessage(), status);
		}
	}

	private void runSearchOn(FileNode element) {
		SearchResultFoundNotifier notifier = new SearchResultFoundNotifier() {
			@Override
			public void foundResult(FileNode user) {
				refreshParentAndReveal(user);
			}
		};
		new SearchFilenameAndAddResultToUsers(configuration, element, notifier).schedule();
	}

	private void refreshParentAndReveal(FileNode user) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				treeViewer.refresh(user.getParent());
				treeViewer.reveal(user);
			}
		});
	}

	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();
	}

	public void setInput(IFile file) {
		treeViewer.setInput(file);
	}
}
