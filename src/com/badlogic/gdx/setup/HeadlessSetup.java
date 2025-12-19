package com.badlogic.gdx.setup;

import com.badlogic.gdx.setup.DependencyBank.ProjectDependency;
import com.badlogic.gdx.setup.DependencyBank.ProjectType;
import com.badlogic.gdx.setup.Executor.CharCallback;
import com.badlogic.gdx.setup.VariantsCatalog.Variant;

import java.io.IOException;
import java.util.*;

/**
 * CLI Headless refinada para automação via GitHub Actions.
 * Inclui sugestões de consistência de fallback e logs informativos.
 */
public class HeadlessSetup {
    public static void main(String[] args) {
        try {
            Map<String, String> params = parseArgs(args);

            // --- Parâmetros Base ---
            String outputDir   = params.getOrDefault("dir", "libGdxProject");
            String appName     = params.getOrDefault("name", "MyGdxGame");
            String packageName = params.getOrDefault("package", "com.mygdx.game");
            String mainClass   = params.getOrDefault("mainClass", "MainClass");
            String sdkLocation = System.getenv("ANDROID_HOME") != null ? System.getenv("ANDROID_HOME") : "";

            DependencyBank bank = new DependencyBank();
            ProjectBuilder builder = new ProjectBuilder(bank);

            List<ProjectType> projects = new ArrayList<>();
            List<Dependency> dependencies = new ArrayList<>();

            // --- Seleção de Variante ---
            String variantKey = params.get("variant");
            Variant selected = VariantsCatalog.getVariant(variantKey);

            if (selected != null) {
                System.out.println("[INFO] Aplicando Variante Técnica: " + selected.name);
            } else {
                // Sugestão Copilot: Melhorar visibilidade do erro e fallback consistente
                System.out.println("[WARNING] Variante '" + variantKey + "' inválida ou não informada.");
                System.out.println("[INFO] Variantes disponíveis: " + VariantsCatalog.getVariantNames());
                
                selected = VariantsCatalog.getVariant("only-desktop-basic");
                System.out.println("[INFO] Utilizando fallback padrão: " + selected.name);
            }

            // Aplicar platforms e extensões da variante (seja a escolhida ou o fallback)
            projects.addAll(selected.platforms);
            for (ProjectDependency pd : selected.extensions) {
                dependencies.add(bank.getDependency(pd));
            }

            // --- Linguagem ---
            String language = params.getOrDefault("language", "java").toLowerCase();
            Language languageEnum = Language.JAVA;
            for (Language l : Language.values()) {
                if (l.name.equalsIgnoreCase(language)) {
                    languageEnum = l;
                }
            }

            // --- Construção do Modelo ---
            builder.buildProject(projects, dependencies);
            builder.build(languageEnum);

            // --- Geração Física ---
            new GdxSetup().build(builder, outputDir, appName, packageName, mainClass, languageEnum,
                    sdkLocation, new CharCallback() {
                        @Override
                        public void character(char c) {
                            System.out.print(c);
                        }
                    }, null);

            // Sugestão Copilot: Saída mais amigável com resumo do que foi feito
            System.out.println("\n-------------------------------------------------------");
            System.out.println("[SUCCESS] Projeto '" + appName + "' gerado com sucesso!");
            System.out.println("[INFO] Variante utilizada: " + selected.name);
            System.out.println("[INFO] Localização: " + outputDir);
            System.out.println("-------------------------------------------------------");

        } catch (IOException e) {
            System.err.println("[ERROR] Falha crítica na geração do projeto.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--") && i + 1 < args.length) {
                String key = args[i].replace("--", "");
                String value = args[i + 1];
                params.put(key, value);
                i++;
            }
        }
        return params;
    }
}
