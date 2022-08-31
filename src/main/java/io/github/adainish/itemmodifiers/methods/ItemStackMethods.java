package io.github.adainish.itemmodifiers.methods;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import net.minecraft.item.ItemStack;

public class ItemStackMethods {
    public static ItemStack getPokemonPhoto(Pokemon pokemon){
        return SpriteItemHelper.getPhoto(pokemon);
    }

}
