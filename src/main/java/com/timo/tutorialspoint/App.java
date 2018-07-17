package com.timo.tutorialspoint;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;
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
    private String dbUrl;
    private String user;
    private Integer timeout;

    public SpellCheckerImpl() {
    }

    public SpellCheckerImpl(String dbUrl, String user, Integer timeout) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.timeout = timeout;
    }

    public void checkSpelling() {
        System.out.println("Inside checkSpelling.");
        System.out.println(dbUrl);
        System.out.println(user);
        System.out.println(timeout);
    }
}

// binding module
class TextEditorModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    public SpellChecker provideSpellChecker() {
        String dbUrl = "jdbc:mysql://localhost:5326/emp";
        String user = "user";
        int timeout = 100;
        SpellChecker spellChecker = new SpellCheckerImpl(dbUrl, user, timeout);
        return spellChecker;
    }
}

class TextEditor {
    private SpellChecker spellChecker;

    @Inject
    public TextEditor(SpellChecker spellChecker) {
        this.spellChecker = spellChecker;
    }

    public void makeSpellCheck() {
        spellChecker.checkSpelling();
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
    }
}
