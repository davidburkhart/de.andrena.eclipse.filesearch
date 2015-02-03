package de.andrena.eclipse.filesearch.core;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.hamcrest.Matchers;
import org.junit.Test;

public class ElementsFromTest {

	@Test
	public void emptySelection() {
		ElementsFrom empty = new ElementsFrom(StructuredSelection.EMPTY);
		assertThat(empty.asObject(), is(empty()));
		assertThat(empty.as(String.class), is(empty()));
	}

	@Test
	public void asObjectReturnsAllObjects() {
		Object[] elements = new Object[] { 123L, "String" };
		ISelection selection = new StructuredSelection(elements);
		assertThat(new ElementsFrom(selection).asObject(), Matchers.<Object> contains(123L, "String"));
	}

	@Test
	public void asCastsRelevantObjects() {
		Object[] elements = new Object[] { 123L, "String", 456L };
		ISelection selection = new StructuredSelection(elements);
		assertThat(new ElementsFrom(selection).as(Long.class), contains(123L, 456L));
	}

}
