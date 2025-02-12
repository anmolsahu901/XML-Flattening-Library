package com.example.xmlFatteningPoc2.controller;


import com.example.xmlFatteningPoc2.lib.XMLProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class FunctionController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/process")
    public String processXMLMapping(
            @RequestParam("xmlInput") String xmlInput,
            @RequestParam("mappingInput") String mappingInput,
            Model model) {

        // Simulate processing of XML and mapping
//        String result = "Processed XML: " +
//                xmlInput.toUpperCase() + "\nProcessed Mapping: " + mappingInput.toUpperCase();

        XMLProcessor processor = new XMLProcessor();
        String result = null;

        try{
            result = processor.process(xmlInput.trim(),mappingInput.trim());
        }catch (Exception e){
            System.out.println("Error in flattening {}"+e.getMessage());
        }


        // Add data to the model to display on the result textarea
        model.addAttribute("resultOutput", result);
        model.addAttribute("xmlInput", xmlInput);
        model.addAttribute("mappingInput", mappingInput);

        return "index"; // The name of your HTML template
    }
}
