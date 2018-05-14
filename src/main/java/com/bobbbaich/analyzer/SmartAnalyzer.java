package com.bobbbaich.analyzer;

import org.jsoup.nodes.Element;

import java.io.File;
import java.util.Map;
import java.util.Optional;

public interface SmartAnalyzer {
    Optional<Element> findSimilar(String targetElementId, File origin, File modified);

    Optional<Map<Element, Integer>> findSimilars(Element originElement, File file);
}