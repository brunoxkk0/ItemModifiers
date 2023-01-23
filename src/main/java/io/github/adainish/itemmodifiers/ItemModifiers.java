package io.github.adainish.itemmodifiers;

import io.github.adainish.itemmodifiers.commands.Command;
import io.github.adainish.itemmodifiers.config.*;
import io.github.adainish.itemmodifiers.listeners.ConsumptionListener;
import io.github.adainish.itemmodifiers.listeners.ModifierListener;
import io.github.adainish.itemmodifiers.obj.*;
import io.github.adainish.itemmodifiers.wrapper.PermissionWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("itemmodifiers")
public class ItemModifiers {

    public static ItemModifiers instance;
    public static ItemModifiers getInstance() {return instance;}
    public static final String MOD_NAME = "ItemModifiers";
    public static final String VERSION = "1.0.2";
    public static final String AUTHORS = "Winglet";
    public static final String YEAR = "2022";
    public static MinecraftServer server;
    private static File configDir;
    public static final Logger log = LogManager.getLogger(MOD_NAME);

    public static PermissionWrapper permissionWrapper;

    public ItemModifiers() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
    }

    public static File getConfigDir() {
        return configDir;
    }

    public static void setConfigDir(File configDir) {
        ItemModifiers.configDir = configDir;
    }

    public void initDirs() {
        setConfigDir((new File(FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()).toString())));
        getConfigDir().mkdir();
    }

    private void setup(final FMLCommonSetupEvent event) {
        log.info("Booting up %n by %authors %v %y"
                .replace("%n", MOD_NAME)
                .replace("%authors", AUTHORS)
                .replace("%v", VERSION)
                .replace("%y", YEAR)
        );
        initDirs();
    }

    @SubscribeEvent
    public void onCommandRegistry(RegisterCommandsEvent event) {
        log.info("Registering commands and permissions...");
        permissionWrapper = new PermissionWrapper();
        event.getDispatcher().register(Command.getCommand());
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        initConfig();
        log.info("Item Modifiers has finished loading");
    }

    @SubscribeEvent
    public void onServerStarted(FMLServerStartedEvent event) {
        server = ServerLifecycleHooks.getCurrentServer();
        MinecraftForge.EVENT_BUS.register(new ModifierListener());
        MinecraftForge.EVENT_BUS.register(new ConsumptionListener());
        initItems();
    }


    public void initItems() {
        Ability.loadItems();
        EVS.loadItems();
        IVS.loadItems();
        Level.loadItems();
        Nature.loadItems();
        Shiny.loadItems();
        Size.loadItems();
        Gender.loadItems();
        PokeBall.loadItems();
    }

    private void initConfig() {
        AbilityConfig.getConfig().setup();
        AbilityConfig.getConfig().load();

        EVSConfig.getConfig().setup();
        EVSConfig.getConfig().load();

        IVSConfig.getConfig().setup();
        IVSConfig.getConfig().load();

        LevelConfig.getConfig().setup();
        LevelConfig.getConfig().load();

        NatureConfig.getConfig().setup();
        NatureConfig.getConfig().load();

        ShinyConfig.getConfig().setup();
        ShinyConfig.getConfig().load();

        SizeConfig.getConfig().setup();
        SizeConfig.getConfig().load();

        PokeBallConfig.getConfig().setup();
        PokeBallConfig.getConfig().load();
        GenderConfig.getConfig().setup();
        GenderConfig.getConfig().load();
    }

    public void reload() {
        reloadConfigs();
        initItems();
    }

    public void reloadConfigs() {
        AbilityConfig.getConfig().load();
        EVSConfig.getConfig().load();
        IVSConfig.getConfig().load();
        LevelConfig.getConfig().load();
        NatureConfig.getConfig().load();
        ShinyConfig.getConfig().load();
        SizeConfig.getConfig().load();
        PokeBallConfig.getConfig().load();
        GenderConfig.getConfig().load();
    }
}
