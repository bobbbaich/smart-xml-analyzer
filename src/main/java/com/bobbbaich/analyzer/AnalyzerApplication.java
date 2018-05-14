package com.bobbbaich.analyzer;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnalyzerApplication {

    public static void main(String[] args) {
        ElementSelector elementSelector = new JsoupElementSelector();
        SmartAnalyzer analyzer = new JsoupSmartAnalyser(elementSelector);

        String targetElementId = args[0];
        File origin = new File(args[1]);
        File modified = new File(args[2]);

        Optional<Element> similar = analyzer.findSimilar(targetElementId, origin, modified);

        String elementPath = similar
                .map(AnalyzerApplication::getElementPath)
                .orElse("Nothing found!");

        System.out.println(elementPath);
    }

    private static String getElementPath(Element element) {
        Elements parents = element.parents();
        Collections.reverse(parents);
        parents.add(element);
        return parents.stream()
                .map(e -> e.nodeName() + "[" + e.elementSiblingIndex() + "]")
                .collect(Collectors.joining(" -> "));
    }
}