package com.badlogic.gdx.setup;

import com.badlogic.gdx.setup.DependencyBank;
import com.badlogic.gdx.setup.DependencyBank.ProjectDependency;
import com.badlogic.gdx.setup.DependencyBank.ProjectType;
import com.badlogic.gdx.setup.ProjectBuilder;
import com.badlogic.gdx.setup.Dependency;
import com.badlogic.gdx.setup.Language;
import com.badlogic.gdx.setup.Executor.CharCallback;

import java.io.IOException;
import java.util.*;

public class HeadlessSetup {
    public static void main(String[] args) {
        try {
            Map<String, String> params = parseArgs(args);

            // Defaults
            String outputDir = params.getOrDefault("dir", "MyGdxGame");
            String appName   = params.getOrDefault("name", "MyGdxGame");
            String packageName = params.getOrDefault("package", "com.mygdx.game");
            String mainClass = params.getOrDefault("mainClass", "MainClass");
            String sdkLocation = System.getenv("ANDROID_HOME") != null ? System.getenv("ANDROID_HOME") : "";

            DependencyBank bank = new DependencyBank();
            ProjectBuilder builder = new ProjectBuilder(bank);

            List<ProjectType> projects = new ArrayList<>();
            List<Dependency> dependencies = new ArrayList<>();

            // Escolher modo
            String mode = params.getOrDefault("mode", "basic").toLowerCase();
            switch (mode) {
                case "basic":
                    projects.add(ProjectType.CORE);
                    projects.add(ProjectType.DESKTOP);
                    dependencies.add(bank.getDependency(ProjectDependency.GDX));
                    break;

                case "all":
                    projects.addAll(Arrays.asList(ProjectType.values()));
                    for (ProjectDependency pd : ProjectDependency.values()) {
                        dependencies.add(bank.getDependency(pd));
                    }
                    break;

                case "custom":
                default:
                    // começa vazio, será preenchido pelas opções
                    break;
            }

            // Customizações opcionais
            if (params.containsKey("addModules")) {
                for (String m : params.get("addModules").split(";")) {
                    try { projects.add(ProjectType.valueOf(m.toUpperCase())); } catch (Exception ignored) {}
                }
            }
            if (params.containsKey("excludeModules")) {
                projects.removeIf(p -> Arrays.asList(params.get("excludeModules").split(";"))
                        .contains(p.name().toLowerCase()));
            }
            if (params.containsKey("addExtensions")) {
                for (String e : params.get("addExtensions").split(";")) {
                    try { dependencies.add(bank.getDependency(ProjectDependency.valueOf(e.toUpperCase()))); } catch (Exception ignored) {}
                }
            }
            if (params.containsKey("excludeExtensions")) {
                dependencies.removeIf(d -> Arrays.asList(params.get("excludeExtensions").split(";"))
                        .contains(d.name.toLowerCase()));
            }

            // Garantir pelo menos uma plataforma
            if (projects.isEmpty()) {
                System.out.println("Nenhuma plataforma escolhida. Adicionando CORE por padrão.");
                projects.add(ProjectType.CORE);
            }

            // Garantir biblioteca principal GDX
            boolean hasGdx = dependencies.stream()
                    .anyMatch(d -> d.name.equalsIgnoreCase("gdx"));
            if (!hasGdx) {
                System.out.println("Biblioteca principal GDX não encontrada. Adicionando por padrão.");
                dependencies.add(bank.getDependency(ProjectDependency.GDX));
            }

            // Linguagem
            String language = params.getOrDefault("language", "java");
            Language languageEnum = Language.JAVA;
            for (Language l : Language.values()) {
                if (l.name.equalsIgnoreCase(language)) {
                    languageEnum = l;
                }
            }

            // Construir projeto
            builder.buildProject(projects, dependencies);
            builder.build(languageEnum);

            // Criar arquivos e pastas
            new GdxSetup().build(builder, outputDir, appName, packageName, mainClass, languageEnum,
                    sdkLocation, new CharCallback() {
                        @Override
                        public void character(char c) {
                            System.out.print(c);
                        }
                    }, null);

            System.out.println("Projeto gerado sem interface gráfica em: " + outputDir);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Função para parsear argumentos tipo --dir MyGame --name MyGame
    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length - 1; i += 2) {
            String key = args[i].replace("--", "");
            String value = args[i + 1];
            params.put(key, value);
        }
        return params;
    }
}