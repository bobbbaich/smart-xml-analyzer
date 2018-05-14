package com.bobbbaich.analyzer;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import java.io.File;
import java.util.*;

public class JsoupSmartAnalyser implements SmartAnalyzer {
    private static final int SIMILARITY_DEPTH = 3;

    private final ElementSelector elementSelector;

    public JsoupSmartAnalyser(ElementSelector elementSelector) {
        this.elementSelector = Objects.requireNonNull(elementSelector, "'elementSelector' cannot be null.");
    }

    @Override
    public Optional<Element> findSimilar(String targetElementId, File origin, File modified) {
        Element originElement = elementSelector.findElementById(origin, targetElementId)
                .orElseThrow(() -> new NoSuchElementException("Element with id [" + targetElementId + "] is not found in origin file."));

        Map<Element, Integer> similarElements = findSimilars(originElement, modified).orElse(new HashMap<>());

        return similarElements.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    @Override
    public Optional<Map<Element, Integer>> findSimilars(Element originElement, File file) {
        final String originTagName = originElement.tagName();
        final Attributes originAttributes = originElement.attributes();
        final Map<Element, Integer> similarElements = new HashMap<>();

        Element nParent = getNParent(originElement, SIMILARITY_DEPTH);
        Elements children = getChildren(nParent, file);

        children.traverse(new NodeVisitor() {
            @Override
            public void head(Node node, int depth) {
                if (!Objects.equals(originTagName, node.nodeName())) return;

                List<Attribute> nodeAttrs = new ArrayList<>(node.attributes().asList());
                nodeAttrs.retainAll(originAttributes.asList());

                if (!nodeAttrs.isEmpty()) similarElements.put((Element) node, nodeAttrs.size());
            }

            @Override
            public void tail(Node node, int depth) {
            }
        });

        return Optional.of(similarElements);
    }

    private Elements getChildren(Element element, File file) {
        Optional<Elements> children = elementSelector.findElementsByQuery(file, element.cssSelector());
        return children
                .map(Elements::first)
                .map(Element::children)
                .orElse(new Elements());
    }

    private Element getNParent(Element element, int n) {
        if (element == null || n < 0)
            throw new IllegalArgumentException("Param 'element' cannot be null or SIMILARITY_DEPTH < 0.");

        Element parent = element.parent();

        if (parent == null || n == 0) return element;
        return getNParent(parent, --n);
    }
}