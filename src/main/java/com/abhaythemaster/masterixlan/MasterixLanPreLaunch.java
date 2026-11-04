package com.abhaythemaster.masterixlan;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

/**
 * Runs before normal Fabric mod initializers so stock e4mc sees a healthy config
 * and a verified Android Netty-Quiche native staged for DroidBridge LibPatcher.
 */
public final class MasterixLanPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        MasterixGate.Verification verification = MasterixGate.verify();
        if (!verification.allowed) {
            MasterixLanMod.log("prelaunch compatibility skipped: " + verification.reason);
            return;
        }

        E4mcConfigRecovery.recoverMalformedConfig();
        boolean prepared = AndroidE4mcNativeBootstrap.ensureLoaded();
        MasterixLanMod.log("prelaunch Android e4mc compatibility nativePrepared=" + prepared);
    }
}
