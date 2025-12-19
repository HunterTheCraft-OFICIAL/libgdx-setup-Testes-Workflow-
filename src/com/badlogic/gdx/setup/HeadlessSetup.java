package com.badlogic.gdx.setup;

import com.badlogic.gdx.setup.DependencyBank;
import com.badlogic.gdx.setup.DependencyBank.ProjectType;
import com.badlogic.gdx.setup.DependencyBank.ProjectDependency;
import com.badlogic.gdx.setup.ProjectBuilder;
import com.badlogic.gdx.setup.Dependency;
import com.badlogic.gdx.setup.Language;
import com.badlogic.gdx.setup.Executor.CharCallback;

import java.io.IOException;
import java.util.*;

public class HeadlessSetup {
    public static void main(String[] args) {
        try {
            // Parâmetros fixos (podem ser passados via args depois)
            String outputDir = "MyGdxGame";
            String appName = "MyGdxGame";
            String packageName = "com.mygdx.game";
            String mainClass = "MainClass";
            String sdkLocation = System.getenv("ANDROID_HOME") != null ? System.getenv("ANDROID_HOME") : "";

            // Banco de dependências
            DependencyBank bank = new DependencyBank();
            ProjectBuilder builder = new ProjectBuilder(bank);

            // Módulos padrão
            List<ProjectType> projects = new ArrayList<>();
            projects.add(ProjectType.CORE);
            projects.add(ProjectType.DESKTOP);
            projects.add(ProjectType.ANDROID);
            projects.add(ProjectType.IOS);
            projects.add(ProjectType.HTML);

            // Dependências padrão
            List<Dependency> dependencies = new ArrayList<>();
            dependencies.add(bank.getDependency(ProjectDependency.GDX));
            dependencies.add(bank.getDependency(ProjectDependency.BOX2D));

            // Linguagem padrão
            Language languageEnum = Language.JAVA;

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
}