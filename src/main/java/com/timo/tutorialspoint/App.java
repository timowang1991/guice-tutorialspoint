package com.timo.tutorialspoint;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.google.inject.name.Named;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@BindingAnnotation @Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
@interface WinWord {}

interface SpellChecker {
    public void checkSpelling();
}

class SpellCheckerImpl implements SpellChecker {
    public void checkSpelling() {
        System.out.println("Inside checkSpelling.");
    }
}

// binding module
class TextEditorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SpellChecker.class).annotatedWith(Names.named("OpenOffice")).to(WinWordSpellCheckerImpl.class);
        bind(String.class).annotatedWith(Names.named("JDBC")).toInstance("jdbc:mysql://localhost:5326/emp");
    }
}

class TextEditor {
    private SpellChecker spellChecker;
    private String dbUrl;

    @Inject
    public TextEditor(@Named("OpenOffice") SpellChecker spellChecker, @Named("JDBC") String dbUrl) {
        this.spellChecker = spellChecker;
        this.dbUrl = dbUrl;
    }

    public void makeSpellCheck() {
        spellChecker.checkSpelling();
    }

    public void makeConnection() {
        System.out.println(dbUrl);
    }
}

class WinWordSpellCheckerImpl extends SpellCheckerImpl {
    @Override
    public void checkSpelling() {
        System.out.println("Inside WinWordSpellCheckerImpl.checkSpelling.");
    }
}

/**
 * OpenOfficeWordSpellCheckerImpl
 */
class OpenOfficeWordSpellCheckerImpl extends SpellCheckerImpl {
    @Override
    public void checkSpelling() {
        System.out.println("Inside OpenOfficeWordSpellCheckerImpl.checkSpelling.");
    }
}

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Injector injector = Guice.createInjector(new TextEditorModule());
        TextEditor editor = injector.getInstance(TextEditor.class);
        editor.makeSpellCheck();
        editor.makeConnection();
    }
}
