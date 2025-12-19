package com.badlogic.gdx.setup;

import com.badlogic.gdx.setup.DependencyBank.ProjectDependency;
import com.badlogic.gdx.setup.DependencyBank.ProjectType;
import com.badlogic.gdx.setup.Executor.CharCallback;
import com.badlogic.gdx.setup.VariantsCatalog.Variant;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * CLI Headless completa para automação de builds libGDX.
 * Focada em profissionalismo, nomenclatura técnica e robustez em CI/CD.
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

            // --- Limpeza Preventiva ---
            // Essencial para evitar falhas silenciosas no GitHub Actions se o diretório já existir
            File outDirFile = new File(outputDir);
            if (outDirFile.exists()) {
                System.out.println("[INFO] Limpando diretório existente: " + outputDir);
                deleteDirectory(outDirFile);
            }

            DependencyBank bank = new DependencyBank();
            ProjectBuilder builder = new ProjectBuilder(bank);

            List<ProjectType> projects = new ArrayList<>();
            List<Dependency> dependencies = new ArrayList<>();

            // --- Seleção de Variante Técnica ---
            String variantKey = params.get("variant");
            Variant selected = VariantsCatalog.getVariant(variantKey);

            if (selected != null) {
                System.out.println("[INFO] Aplicando Variante: " + selected.name);
            } else {
                System.out.println("[WARNING] Variante '" + variantKey + "' não encontrada.");
                System.out.println("[INFO] Opções disponíveis: " + VariantsCatalog.getVariantNames());
                
                selected = VariantsCatalog.getVariant("only-desktop-basic");
                System.out.println("[INFO] Utilizando fallback técnico: " + selected.name);
            }

            // Mapeamento de plataformas e extensões conforme o catálogo
            projects.addAll(selected.platforms);
            for (ProjectDependency pd : selected.extensions) {
                dependencies.add(bank.getDependency(pd));
            }

            // --- Configuração da Build ---
            builder.buildProject(projects, dependencies);
            builder.build(Language.JAVA); // Focado em Java para manter o padrão profissional

            // --- Execução do Setup Original ---
            new GdxSetup().build(builder, outputDir, appName, packageName, mainClass, Language.JAVA,
                    sdkLocation, new CharCallback() {
                        @Override
                        public void character(char c) {
                            System.out.print(c);
                        }
                    }, null);

            // --- Relatório Final ---
            System.out.println("\n-------------------------------------------------------");
            System.out.println("[SUCCESS] Projeto gerado com sucesso!");
            System.out.println("[INFO] Variante: " + selected.name);
            System.out.println("[INFO] Destino: " + outputDir);
            System.out.println("-------------------------------------------------------");

        } catch (Exception e) {
            System.err.println("\n[ERROR] Falha crítica durante a geração do projeto.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Remove diretórios recursivamente para garantir um ambiente limpo.
     */
    private static void deleteDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) deleteDirectory(f);
                else f.delete();
            }
        }
        dir.delete();
    }

    /**
     * Parser de argumentos robusto para CLI (--chave valor).
     */
    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--") && i + 1 < args.length) {
                String key = args[i].replace("--", "");
                String value = args[i + 1];
                params.put(key, value);
                i++; // Pula o próximo pois é o valor da chave atual
            }
        }
        return params;
    }
}