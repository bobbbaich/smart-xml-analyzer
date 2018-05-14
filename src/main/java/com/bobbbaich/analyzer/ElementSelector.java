package com.bobbbaich.analyzer;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.Optional;

public interface ElementSelector {
    Optional<Element> findElementById(File htmlFile, String targetElementId);

    Optional<Elements> findElementsByQuery(File htmlFile, String cssQuery);
}