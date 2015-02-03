package de.andrena.eclipse.filesearch.ui;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import de.andrena.eclipse.filesearch.core.ElementsFrom;

public class SearchFilenameRecursive extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		List<?> elements = new ElementsFrom(selection).asObject();
		if (elements.size() == 1) {
			Object element = elements.get(0);
			IFile file = (IFile) Platform.getAdapterManager().getAdapter(element, IFile.class);
			if (file != null) {
				runSearchOn(file);
			}
		}
		return null;
	}

	private void runSearchOn(IFile file) throws ExecutionException {
		try {
			showSearchFilenameRecursiveResultView().setInput(file);
		} catch (PartInitException e) {
			throw new ExecutionException("View " + SearchFilenameRecursiveResultView.VIEW_ID + " won't open.", e);
		}
	}

	private SearchFilenameRecursiveResultView showSearchFilenameRecursiveResultView() throws PartInitException {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		return (SearchFilenameRecursiveResultView) page.showView(SearchFilenameRecursiveResultView.VIEW_ID);
	}
}
