package com.badlogic.gdx.setup;

import java.io.File;
import java.util.*;

public class HeadlessSetup {

    public static void main(String[] args) {
        Map<String, String> params = parseArgs(args);

        String variantKey = params.getOrDefault("variant", "only-desktop-basic");
        String outputDir = params.getOrDefault("dir", "out-default");
        String appName = params.getOrDefault("name", "MyGdxGame");

        VariantsCatalog.Variant selected = VariantsCatalog.getVariant(variantKey);

        if (selected == null) {
            System.out.println("[ERRO] Variante '" + variantKey + "' não encontrada no catálogo.");
            return;
        }

        // Limpeza de segurança para não misturar arquivos de builds anteriores
        deleteDir(new File(outputDir));

        try {
            DependencyBank bank = new DependencyBank();
            ProjectBuilder builder = new ProjectBuilder(bank);

            // Configuração baseada na variante escolhida
            builder.requestedPlatforms.addAll(selected.platforms);
            builder.requestedExtensions.addAll(selected.extensions);

            // Gera o projeto (fixando pacote e classe para esta versão de estabilização)
            new GdxSetup().build(builder, outputDir, appName, "com.mygdx.game", "MainClass", Language.JAVA, null);

            System.out.println("[SUCCESS] Gerado: " + outputDir + " (" + selected.name + ")");

        } catch (Exception e) {
            System.out.println("[FAIL] Erro ao gerar projeto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--") && i + 1 < args.length) {
                params.put(args[i].substring(2), args[i + 1]);
            }
        }
        return params;
    }

    private static void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) deleteDir(f);
        }
        file.delete();
    }
}
