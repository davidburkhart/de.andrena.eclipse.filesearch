package de.andrena.eclipse.filesearch.ui;

import static java.util.Arrays.asList;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

import de.andrena.eclipse.filesearch.core.SearchConfiguration;

final class EditSearchScopeConfiguration extends Action {

	private final SearchConfiguration configuration;

	EditSearchScopeConfiguration(SearchConfiguration configuration) {
		super("Search Scope");
		this.configuration = configuration;
	}

	@Override
	public void runWithEvent(Event event) {
		Shell shell = event.display.getActiveShell();

		WorkbenchContainerSelectionDialog dialog = new WorkbenchContainerSelectionDialog(shell);
		dialog.setInitialElementSelections(asList(configuration.createScope().getRoots()));
		List<IContainer> selectedContainers = dialog.openAndGetResult();
		if (!selectedContainers.isEmpty()) {
			configuration.setContainers(selectedContainers);
			setToolTipText(configuration.createScope().getDescription());
		}
	}
}