package com.badlogic.gdx.setup;
//package com.badlogic.gdx.setup;
//package headless;

import com.badlogic.gdx.setup.DependencyBank;
import com.badlogic.gdx.setup.ProjectBuilder;
import com.badlogic.gdx.setup.ProjectType;
import com.badlogic.gdx.setup.ProjectDependency;
import com.badlogic.gdx.setup.Dependency;

import java.util.*;

public class HeadlessSetup {
    public static void main(String[] args) {
        DependencyBank bank = new DependencyBank();
        ProjectBuilder builder = new ProjectBuilder();

        List<ProjectType> modules = Arrays.asList(ProjectType.CORE, ProjectType.DESKTOP);
        List<Dependency> dependencies = Arrays.asList(
            bank.getDependency(ProjectDependency.GDX),
            bank.getDependency(ProjectDependency.BOX2D)
        );

        builder.buildProject(modules, dependencies);
        System.out.println("Projeto gerado sem interface gráfica!");
    }
}