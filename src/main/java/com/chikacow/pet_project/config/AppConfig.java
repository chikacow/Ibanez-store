package com.chikacow.pet_project.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Map;

@Configuration
public class AppConfig {

    public static final String FEATURE_FILE = "src/main/resources/static/images";

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RedirectAttributes redirectAttributes() {
        return new RedirectAttributes() {
            @Override
            public RedirectAttributes addAttribute(String attributeName, Object attributeValue) {
                return null;
            }

            @Override
            public RedirectAttributes addAttribute(Object attributeValue) {
                return null;
            }

            @Override
            public RedirectAttributes addAllAttributes(Collection<?> attributeValues) {
                return null;
            }

            @Override
            public RedirectAttributes mergeAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public RedirectAttributes addFlashAttribute(String attributeName, Object attributeValue) {
                return null;
            }

            @Override
            public RedirectAttributes addFlashAttribute(Object attributeValue) {
                return null;
            }

            @Override
            public Map<String, ?> getFlashAttributes() {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public boolean containsAttribute(String attributeName) {
                return false;
            }

            @Override
            public Object getAttribute(String attributeName) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        };
    }
}
