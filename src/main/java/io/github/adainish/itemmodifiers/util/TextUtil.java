package io.github.adainish.itemmodifiers.util;

import net.minecraft.util.text.*;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {
    private static final Pattern HEXPATTERN = Pattern.compile("\\{(#[a-fA-F0-9]{6})}");
    private static final String SPLITPATTERN = "((?=\\{#[a-fA-F0-9]{6}}))";

    public static ITextComponent parseHexCodes(String text, boolean removeItalics) {
        if(text == null)
            return null;
        StringTextComponent comp = new StringTextComponent("");

        String[] temp = text.split(SPLITPATTERN);
        Arrays.stream(temp).forEach(s -> {
            Matcher m = HEXPATTERN.matcher(s);
            if(m.find()) {
                Color color = Color.fromHex(m.group(1));
                s = m.replaceAll("");
                if(removeItalics)
                    comp.appendSibling(new StringTextComponent(s).setStyle(Style.EMPTY.setColor(color).setItalic(false)));
                else
                    comp.appendSibling(new StringTextComponent(s).setStyle(Style.EMPTY.setColor(color)));
            } else {
                comp.appendSibling(new StringTextComponent(s));
            }
        });

        return comp;
    }
    public static final Color BLUE = Color.fromHex("#00AFFC");

    public static final Color ORANGE = Color.fromHex("#FF6700");

    private static final IFormattableTextComponent PLUGIN_PREFIX = new StringTextComponent(Util.formattedString("&b[&eModifiers&b]")).setStyle(Style.EMPTY.setColor(BLUE));

    private static final IFormattableTextComponent MESSAGE_PREFIX = getPluginPrefix().appendSibling(new StringTextComponent(" &c» ").setStyle(Style.EMPTY.setColor(ORANGE)));

    /**
     * @return a copy of the coloured TextComponent
     */
    public static IFormattableTextComponent getPluginPrefix() {
        return PLUGIN_PREFIX.deepCopy();
    }

    /**
     * @return a copy of the coloured prefix
     */
    public static IFormattableTextComponent getMessagePrefix() {
        return MESSAGE_PREFIX.deepCopy();
    }
}
