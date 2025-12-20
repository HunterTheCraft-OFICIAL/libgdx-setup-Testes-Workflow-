package com.badlogic.gdx.setup;

import com.badlogic.gdx.setup.DependencyBank.ProjectDependency;
import com.badlogic.gdx.setup.DependencyBank.ProjectType;
import com.badlogic.gdx.setup.Executor.CharCallback;
import com.badlogic.gdx.setup.VariantsCatalog.Variant;
import java.io.File;
import java.util.*;

/**
 * Headless entry point for libGDX project generation via CLI/CI-CD.
 * Designed to work with the official gdx-setup.jar (v1.10.0 logic).
 */
public class HeadlessSetup {
    public static void main(String[] args) {
        try {
            Map<String, String> params = parseArgs(args);
            String outputDir = params.getOrDefault("dir", "MyGdxGame");
            
            // Clean output directory to avoid conflicts in GitHub Runner environments
            File out = new File(outputDir);
            if (out.exists()) deleteDir(out);

            DependencyBank bank = new DependencyBank();
            ProjectBuilder builder = new ProjectBuilder(bank);
            List<ProjectType> projects = new ArrayList<>();
            List<Dependency> dependencies = new ArrayList<>();

            // Selection via Catalog
            String variantKey = params.get("variant");
            Variant selected = VariantsCatalog.getVariant(variantKey);
            
            // Fallback to basic desktop if variant is not found or null
            if (selected == null) {
                selected = VariantsCatalog.getVariant("only-desktop-basic");
            }

            projects.addAll(selected.platforms);
            for (ProjectDependency pd : selected.extensions) {
                dependencies.add(bank.getDependency(pd));
            }

            builder.buildProject(projects, dependencies);
            builder.build(Language.JAVA);

            // Core generation call using legacy gdx-setup API
            new GdxSetup().build(builder, outputDir, 
                params.getOrDefault("name", "MyGdxGame"), 
                "com.mygdx.game", "MainClass", Language.JAVA,
                System.getenv("ANDROID_HOME"), new CharCallback() {
                    @Override public void character(char c) { System.out.print(c); }
                }, null);

            System.out.println("\n[SUCCESS] Project generated at: " + outputDir);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Recursively deletes a directory.
     */
    private static void deleteDir(File dir) {
        File[] files = dir.listFiles();
        if (files != null) for (File f : files) deleteDir(f);
        dir.delete();
    }

    /**
     * Parses CLI arguments in --key value format.
     */
    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--") && i + 1 < args.length) {
                params.put(args[i].replace("--", ""), args[i+1]);
                i++;
            }
        }
        return params;
    }
}
