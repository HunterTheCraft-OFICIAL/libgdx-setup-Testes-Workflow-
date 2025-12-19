package com.badlogic.gdx.setup;

import com.badlogic.gdx.setup.DependencyBank;
import com.badlogic.gdx.setup.DependencyBank.ProjectType;
import com.badlogic.gdx.setup.DependencyBank.ProjectDependency;
import com.badlogic.gdx.setup.ProjectBuilder;
import com.badlogic.gdx.setup.Dependency;
import com.badlogic.gdx.setup.Language;

import java.io.IOException;
import java.util.*;

public class HeadlessSetup {
    public static void main(String[] args) {
        // Criar o banco de dependências
        DependencyBank bank = new DependencyBank();

        // Criar o builder passando o banco
        ProjectBuilder builder = new ProjectBuilder(bank);

        // Definir os módulos (CORE e DESKTOP)
        List<ProjectType> modules = Arrays.asList(ProjectType.CORE, ProjectType.DESKTOP);

        // Definir as dependências usando o enum ProjectDependency
        List<Dependency> dependencies = Arrays.asList(
            bank.getDependency(ProjectDependency.GDX),
            bank.getDependency(ProjectDependency.BOX2D)
        );

        // Construir o projeto
        builder.buildProject(modules, dependencies);

        try {
            builder.build(Language.JAVA);
            System.out.println("Projeto gerado sem interface gráfica!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}