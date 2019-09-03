package com.rowdy.common_methods.utils;

import android.content.Context;
import android.graphics.Typeface;

/*
 * Created by devayat on 18/05/18.
 */

public class Typefaces {
    public enum Style {
        BOLD, BOLD_ITALIC, EXTRA_BOLD, EXTRA_BOLD_ITALIC, ITALIC, LIGHT, LIGHT_ITALIC, REGULAR, SEMI_BOLD, SEMI_BOLD_ITALIC
    }

    private static Typefaces instance;
    private static final String TYPEFACE_BOLD_FILE_PATH = "fonts/OpenSans-Bold.ttf";
    private static final String TYPEFACE_BOLD_ITALIC_FILE_PATH = "fonts/OpenSans-BoldItalic.ttf";
    private static final String TYPEFACE_EXTRA_BOLD_PATH = "fonts/OpenSans-ExtraBold.ttf";
    private static final String TYPEFACE_EXTRA_BOLD_ITALIC_PATH = "fonts/OpenSans-ExtraBoldItalic.ttf";
    private static final String TYPEFACE_ITALIC_FILE_PATH = "fonts/OpenSans-Italic.ttf";
    private static final String TYPEFACE_LIGHT_FILE_PATH = "fonts/OpenSans-Light.ttf";
    private static final String TYPEFACE_LIGHT_ITALIC_FILE_PATH = "fonts/OpenSans-LightItalic.ttf";
    private static final String TYPEFACE_REGULAR_FILE_PATH = "fonts/OpenSans-Regular.ttf";
    private static final String TYPEFACE_SEMI_BOLD_FILE_PATH = "fonts/OpenSans-Semibold.ttf";
    private static final String TYPEFACE_SEMI_BOLD_ITALIC_FILE_PATH = "fonts/OpenSans-SemiboldItalic.ttf";

    private Typeface bold;
    private Typeface boldItalic;
    private Typeface extraBold;
    private Typeface extraBoldItalic;
    private Typeface italic;
    private Typeface light;
    private Typeface lightItalic;
    private Typeface regular;
    private Typeface semibold;
    private Typeface semiboldItalic;

    private Typefaces(Context context) {
        bold = Typeface.createFromAsset(context.getAssets(), TYPEFACE_BOLD_FILE_PATH);
        boldItalic = Typeface.createFromAsset(context.getAssets(), TYPEFACE_BOLD_ITALIC_FILE_PATH);
        extraBold = Typeface.createFromAsset(context.getAssets(), TYPEFACE_EXTRA_BOLD_PATH);
        extraBoldItalic = Typeface.createFromAsset(context.getAssets(), TYPEFACE_EXTRA_BOLD_ITALIC_PATH);
        italic = Typeface.createFromAsset(context.getAssets(), TYPEFACE_ITALIC_FILE_PATH);
        light = Typeface.createFromAsset(context.getAssets(), TYPEFACE_LIGHT_FILE_PATH);
        lightItalic = Typeface.createFromAsset(context.getAssets(), TYPEFACE_LIGHT_ITALIC_FILE_PATH);
        regular = Typeface.createFromAsset(context.getAssets(), TYPEFACE_REGULAR_FILE_PATH);
        semibold = Typeface.createFromAsset(context.getAssets(), TYPEFACE_SEMI_BOLD_FILE_PATH);
        semiboldItalic = Typeface.createFromAsset(context.getAssets(), TYPEFACE_SEMI_BOLD_ITALIC_FILE_PATH);
    }

    public static Typefaces getInstance(Context context) {
        if (instance == null) {
            instance = new Typefaces(context);
        }
        return instance;
    }

    public Typeface getTypeface(Style style) {
        switch (style) {
            case BOLD:
                return bold;
            case BOLD_ITALIC:
                return boldItalic;
            case EXTRA_BOLD:
                return extraBold;
            case EXTRA_BOLD_ITALIC:
                return extraBoldItalic;
            case ITALIC:
                return italic;
            case LIGHT:
                return light;
            case LIGHT_ITALIC:
                return lightItalic;
            case REGULAR:
                return regular;
            case SEMI_BOLD:
                return semibold;
            case SEMI_BOLD_ITALIC:
                return semiboldItalic;
            default:
                return regular;
        }
    }
}
