package de.andrena.eclipse.filesearch.ui;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import de.andrena.eclipse.filesearch.core.ElementsFrom;

public class WorkbenchContainerSelectionDialog extends CheckedTreeSelectionDialog {

	public WorkbenchContainerSelectionDialog(Shell shell) {
		super(shell, new WorkbenchLabelProvider(), new WorkbenchContainerContentProvider());
		setContainerMode(true);
		setTitle("Tree Selection");
		setMessage("Select the elements from the tree:");
		setInput(ResourcesPlugin.getWorkspace().getRoot());
	}

	public List<IContainer> openAndGetResult() {
		if (open() == Window.OK) {
			return toIContainerList(getResult());
		}

		return Collections.emptyList();
	}

	private List<IContainer> toIContainerList(Object[] objects) {
		ISelection selection = new StructuredSelection(objects);
		return new ElementsFrom(selection).as(IContainer.class);
	}

	@Override
	protected void computeResult() {
		List<Object> all = new LinkedList<Object>(Arrays.asList(getTreeViewer().getCheckedElements()));
		List<Object> grayed = Arrays.asList(getTreeViewer().getGrayedElements());
		all.removeAll(grayed);
		setResult(all);
	}
}
