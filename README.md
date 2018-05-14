# Smart XML Analyzer

1.General

    Application is written according to requirements described in document 
    "Smart XML Analyzer: Software Engineer - Complex algorithms"
    
    Task performed by using java 8, jsoup, junit, slf4j.
   
    Main algorithm: 
        - find origin element by provided element id;
        - calculate n-parent for origin element, based on similarity_level parameter;
        - find n-parent element in diff file by css_selector;
        - search similar elements using DFS algorithm. 
        Similarity is defined by tag and tag attributes;
        - return the most similar element that has maximum retained tag attributes.

2.Run Application

    Execute command: java -jar path/to/smart-xml-analyzer-0.0.1.jar <target_element_id> <input_origin_file_path> <input_other_sample_file_path> 
    
    For example: java -jar smart-xml-analyzer-full-0.0.1.jar make-everything-ok-button D:\smart-xml-analyzer\samples\sample-0-origin.html D:\smart-xml-analyzer\samples\sample-2-container-and-clone.html
    
    Using provided samples from tutorial you will see the following result:
    html[0] -> body[1] -> div[0] -> div[1] -> div[2] -> div[0] -> div[0] -> div[1] -> div[0] -> a[0]


3.Comparison Output
    
    For samples:
    - sample-0-origin.html
    html[0] -> body[1] -> div[0] -> div[1] -> div[2] -> div[0] -> div[0] -> div[1] -> a[0]
    
    - sample-1-evil-gemini.html
    html[0] -> body[1] -> div[0] -> div[1] -> div[2] -> div[0] -> div[0] -> div[1] -> a[1]
    
    - sample-2-container-and-clone.html
    html[0] -> body[1] -> div[0] -> div[1] -> div[2] -> div[0] -> div[0] -> div[1] -> div[0] -> a[0]
    
    - sample-3-the-escape.html
    html[0] -> body[1] -> div[0] -> div[1] -> div[2] -> div[0] -> div[0] -> div[2] -> a[0]

    - sample-4-the-mash.html
    html[0] -> body[1] -> div[0] -> div[1] -> div[2] -> div[0] -> div[0] -> div[2] -> a[0]