package com.bobbbaich.analyzer;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ElementSelectorTest {
    private File htmlWithID = new File(getClass().getResource("/fixtures/sample-0-origin.html").getFile());

    private ElementSelector elementSelector = new JsoupElementSelector();

    @Test
    public void findElementById() {
        String targetElementId = "make-everything-ok-button";

        Optional<Element> buttonOpt = elementSelector.findElementById(htmlWithID, targetElementId);

        assertTrue(buttonOpt.isPresent());
        assertEquals(targetElementId, buttonOpt.get().id());
    }

    @Test
    public void findElementsByQuery() {
        String cssQuery = "a[id=\"make-everything-ok-button\"]";

        Optional<Elements> buttonOpt = elementSelector.findElementsByQuery(htmlWithID, cssQuery);

        assertTrue(buttonOpt.isPresent());
        assertEquals(1, buttonOpt.get().size());
    }
}