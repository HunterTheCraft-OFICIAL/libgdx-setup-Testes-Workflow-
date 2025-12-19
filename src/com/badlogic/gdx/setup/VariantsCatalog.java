package com.badlogic.gdx.setup;

import com.badlogic.gdx.setup.DependencyBank.ProjectDependency;
import com.badlogic.gdx.setup.DependencyBank.ProjectType;
import java.util.*;

public class VariantsCatalog {

    public static class Variant {
        public String name;
        public List<ProjectType> platforms;
        public List<ProjectDependency> extensions;

        public Variant(String name, List<ProjectType> platforms, List<ProjectDependency> extensions) {
            this.name = name;
            this.platforms = platforms;
            this.extensions = extensions;
        }
    }

    private static final Map<String, Variant> catalog = new HashMap<>();

    static {
        // --- 1. Desktop Basic ---
        catalog.put("only-desktop-basic", new Variant(
            "Somente Desktop (Básico)",
            Arrays.asList(ProjectType.CORE, ProjectType.DESKTOP),
            Arrays.asList(ProjectDependency.GDX)
        ));

        // --- 2. Android Basic ---
        catalog.put("only-android-basic", new Variant(
            "Somente Android (Básico)",
            Arrays.asList(ProjectType.CORE, ProjectType.ANDROID),
            Arrays.asList(ProjectDependency.GDX)
        ));

        // --- 3. Mobile Basic ---
        catalog.put("mobile-basic", new Variant(
            "Mobile (Android + iOS)",
            Arrays.asList(ProjectType.CORE, ProjectType.ANDROID, ProjectType.IOS),
            Arrays.asList(ProjectDependency.GDX)
        ));

        // --- 4. Desktop + Box2D + FreeType ---
        catalog.put("desktop-box2d-freetype", new Variant(
            "Desktop Avançado (Física + Fontes)",
            Arrays.asList(ProjectType.CORE, ProjectType.DESKTOP),
            Arrays.asList(ProjectDependency.GDX, ProjectDependency.BOX2D, ProjectDependency.FREETYPE)
        ));

        // --- 5. Desktop + Ashley + AI ---
        catalog.put("desktop-ashley-ai", new Variant(
            "Desktop ECS (Ashley + AI)",
            Arrays.asList(ProjectType.CORE, ProjectType.DESKTOP),
            Arrays.asList(ProjectDependency.GDX, ProjectDependency.ASHLEY, ProjectDependency.AI)
        ));

        // --- 6. Stress Test (All Platforms + All Extensions) ---
        catalog.put("all-all", new Variant(
            "Tudo em Tudo (Stress Test)",
            Arrays.asList(ProjectType.CORE, ProjectType.DESKTOP, ProjectType.ANDROID, ProjectType.IOS, ProjectType.HTML),
            Arrays.asList(ProjectDependency.GDX, ProjectDependency.BOX2D, ProjectDependency.FREETYPE, 
                          ProjectDependency.ASHLEY, ProjectDependency.AI, ProjectDependency.CONTROLLERS)
        ));
    }

    public static Variant getVariant(String key) {
        return catalog.get(key);
    }

    public static Set<String> getVariantNames() {
        return catalog.keySet();
    }
}