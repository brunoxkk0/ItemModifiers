package io.github.adainish.itemmodifiers.listeners;

import com.pixelmonmod.pixelmon.api.events.RareCandyEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ConsumptionListener {

    @SubscribeEvent
    public void onRareCandyUse(RareCandyEvent event) {
        if(event.getPlayer().getActiveItemStack().hasTag() && event.getPlayer().getActiveItemStack().getTag().getBoolean("itemmodifier"))
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void onPixelInteract(PlayerInteractEvent.EntityInteract event) {
        if(event.isCanceled())
            return;
        if (!event.isCanceled() && event.getTarget() instanceof PixelmonEntity) {
            if (event.getHand().equals(Hand.MAIN_HAND) && event.getItemStack().hasTag() && event.getItemStack().getTag().getBoolean("itemmodifier")) {
                event.setCanceled(true);
            }
        }
    }

}
