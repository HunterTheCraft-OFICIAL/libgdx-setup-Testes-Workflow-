package com.badlogic.gdx.setup;

import java.util.*;
import com.badlogic.gdx.setup.DependencyBank.ProjectType;
import com.badlogic.gdx.setup.DependencyBank.ProjectDependency;

/**
 * Catálogo Técnico de Variantes para Automação Headless/CI.
 * Nomenclatura baseada estritamente em Plataformas e Dependências.
 */
public class VariantsCatalog {

    public static class Variant {
        public final String name;
        public final List<ProjectType> platforms;
        public final List<ProjectDependency> extensions;

        public Variant(String name, List<ProjectType> platforms, List<ProjectDependency> extensions) {
            this.name = name;
            this.platforms = platforms;
            this.extensions = extensions;
        }
    }

    public static final Map<String, Variant> VARIANTS = new LinkedHashMap<>();

    static {
        // --- 1. PLATAFORMAS INDIVIDUAIS (BASIC) ---
        reg("only-desktop-basic", Arrays.asList(ProjectType.DESKTOP), Arrays.asList(ProjectDependency.GDX));
        reg("only-android-basic", Arrays.asList(ProjectType.ANDROID), Arrays.asList(ProjectDependency.GDX));
        reg("only-ios-basic",     Arrays.asList(ProjectType.IOS),     Arrays.asList(ProjectDependency.GDX));
        reg("only-html-basic",    Arrays.asList(ProjectType.HTML),    Arrays.asList(ProjectDependency.GDX));

        // --- 2. COMBINAÇÕES DE PLATAFORMA (CROSS-PLATFORM) ---
        reg("mobile-basic",       Arrays.asList(ProjectType.ANDROID, ProjectType.IOS), Arrays.asList(ProjectDependency.GDX));
        reg("desktop-html-basic", Arrays.asList(ProjectType.DESKTOP, ProjectType.HTML), Arrays.asList(ProjectDependency.GDX));
        reg("desktop-mobile-basic", Arrays.asList(ProjectType.DESKTOP, ProjectType.ANDROID, ProjectType.IOS), Arrays.asList(ProjectDependency.GDX));
        reg("all-platforms-basic", Arrays.asList(ProjectType.values()), Arrays.asList(ProjectDependency.GDX));

        // --- 3. COMBINAÇÕES TÉCNICAS DE EXTENSÕES ---
        
        // Physics Stack (2D)
        reg("desktop-box2d-freetype", 
            Arrays.asList(ProjectType.DESKTOP), 
            Arrays.asList(ProjectDependency.GDX, ProjectDependency.BOX2D, ProjectDependency.FREETYPE));

        reg("mobile-box2d-lights-freetype", 
            Arrays.asList(ProjectType.ANDROID, ProjectType.IOS), 
            Arrays.asList(ProjectDependency.GDX, ProjectDependency.BOX2D, ProjectDependency.BOX2D_LIGHTS, ProjectDependency.FREETYPE));

        // Physics Stack (3D)
        reg("desktop-bullet-controllers", 
            Arrays.asList(ProjectType.DESKTOP), 
            Arrays.asList(ProjectDependency.GDX, ProjectDependency.BULLET, ProjectDependency.CONTROLLERS, ProjectDependency.TOOLS));

        // Architecture Stack (ECS/AI)
        reg("desktop-ashley-ai", 
            Arrays.asList(ProjectType.DESKTOP), 
            Arrays.asList(ProjectDependency.GDX, ProjectDependency.ASHLEY, ProjectDependency.AI));

        reg("all-platforms-ashley-ai-freetype", 
            Arrays.asList(ProjectType.values()), 
            Arrays.asList(ProjectDependency.GDX, ProjectDependency.ASHLEY, ProjectDependency.AI, ProjectDependency.FREETYPE));

        // --- 4. CONFIGURAÇÕES COMPLETAS (STRESS TEST) ---
        
        reg("desktop-all-extensions", 
            Arrays.asList(ProjectType.DESKTOP), 
            Arrays.asList(ProjectDependency.values()));

        reg("all-all", 
            Arrays.asList(ProjectType.values()), 
            Arrays.asList(ProjectDependency.values()));
    }

    private static void reg(String name, List<ProjectType> p, List<ProjectDependency> d) {
        // Garante que CORE e GDX sempre estejam presentes para evitar erros de build
        List<ProjectType> platforms = new ArrayList<>(p);
        if (!platforms.contains(ProjectType.CORE)) {
            platforms.add(0, ProjectType.CORE);
        }

        List<ProjectDependency> extensions = new ArrayList<>(d);
        if (!extensions.contains(ProjectDependency.GDX)) {
            extensions.add(0, ProjectDependency.GDX);
        }

        VARIANTS.put(name, new Variant(name, platforms, extensions));
    }

    public static Variant getVariant(String name) {
        return VARIANTS.get(name);
    }
    
    public static Set<String> getVariantNames() {
        return VARIANTS.keySet();
    }
}
