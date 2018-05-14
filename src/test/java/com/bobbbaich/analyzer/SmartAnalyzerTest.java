package com.bobbbaich.analyzer;

import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.File;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class SmartAnalyzerTest {
    private String targetElementId = "make-everything-ok-button";
    private File originHtml = new File(getClass().getResource("/fixtures/sample-0-origin.html").getFile());

    private ElementSelector elementSelector = new JsoupElementSelector();
    private SmartAnalyzer smartAnalyzer = new JsoupSmartAnalyser(elementSelector);

    @Test
    public void findEvilGemini() {
        File evilGeminiHtml = new File(getClass().getResource("/fixtures/sample-1-evil-gemini.html").getFile());
        Optional<Element> origin = elementSelector.findElementById(evilGeminiHtml, "evil-gemini-button");

        Optional<Element> similar = smartAnalyzer.findSimilar(targetElementId, originHtml, evilGeminiHtml);

        assertEquals(origin.map(Element::text), similar.map(Element::text));
    }

    @Test
    public void findContainerAndClone() {
        File containerAndCloneHtml = new File(getClass().getResource("/fixtures/sample-2-container-and-clone.html").getFile());
        Optional<Element> origin = elementSelector.findElementById(containerAndCloneHtml, "container-and-clone-button");

        Optional<Element> similar = smartAnalyzer.findSimilar(targetElementId, originHtml, containerAndCloneHtml);

        assertEquals(origin.map(Element::text), similar.map(Element::text));
    }

    @Test
    public void findTheEscape() {
        File theEscapeHtml = new File(getClass().getResource("/fixtures/sample-3-the-escape.html").getFile());
        Optional<Element> origin = elementSelector.findElementById(theEscapeHtml, "the-escape-button");

        Optional<Element> similar = smartAnalyzer.findSimilar(targetElementId, originHtml, theEscapeHtml);

        assertEquals(origin.map(Element::text), similar.map(Element::text));
    }


    @Test
    public void findTheMash() {
        File theMashHtml = new File(getClass().getResource("/fixtures/sample-4-the-mash.html").getFile());
        Optional<Element> origin = elementSelector.findElementById(theMashHtml, "the-mash-button");

        Optional<Element> similar = smartAnalyzer.findSimilar(targetElementId, originHtml, theMashHtml);

        assertEquals(origin.map(Element::text), similar.map(Element::text));
    }

    @Test
    public void findSimilarsElements() {
        File theEscapeHtml = new File(getClass().getResource("/fixtures/sample-3-the-escape.html").getFile());
        Optional<Element> origin = elementSelector.findElementById(originHtml, targetElementId);

        Optional<Map<Element, Integer>> similars = smartAnalyzer.findSimilars(origin.get(), theEscapeHtml);

        assertEquals(Optional.of(2), similars.map(Map::entrySet).map(Set::size));
    }

    @Test(expected = NoSuchElementException.class)
    public void throwsNoSuchElementException() {
        String fakeId = "fake-element-id";
        File theMashHtml = new File(getClass().getResource("/fixtures/sample-4-the-mash.html").getFile());

        smartAnalyzer.findSimilar(fakeId, originHtml, theMashHtml);
    }
}