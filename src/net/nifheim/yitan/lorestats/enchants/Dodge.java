package net.nifheim.yitan.lorestats.enchants;

import net.nifheim.yitan.lorestats.GearStats;
import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.PlayerStats;
import net.nifheim.yitan.lorestats.SetBonuses;
import net.nifheim.yitan.lorestats.durability.Durability;
import net.nifheim.yitan.lorestats.utils.InvSlot.GetSlots;
import net.nifheim.yitan.lorestats.utils.Util_Colours;
import net.nifheim.yitan.lorestats.utils.Util_EntityManager;
import net.nifheim.yitan.lorestats.utils.Util_Format;
import net.nifheim.yitan.lorestats.utils.Util_GetResponse;
import net.nifheim.yitan.lorestats.utils.Util_Random;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Dodge {

    Durability durability = new Durability();
    GearStats gearStats = new GearStats();
    GetSlots getSlots = new GetSlots();
    InternalCooldown internalCooldown = new InternalCooldown();
    SetBonuses setBonuses = new SetBonuses();
    Util_Colours util_Colours = new Util_Colours();
    Util_EntityManager util_EntityManager = new Util_EntityManager();
    Util_Format util_Format = new Util_Format();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_Random util_Random = new Util_Random();

    public boolean checkDodge(EntityDamageByEntityEvent e, Entity getAttacker, Entity getDefender, PlayerStats defenderStats) {
        if (dodgeChanceOnHit(getDefender, defenderStats)) {
            if (((getAttacker instanceof Player))
                    && (Main.getInstance().getConfig().getBoolean("combatMessages.outgoing.enemyDodgedAttack"))) {
                ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyDodgeSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
            }
            if (((getDefender instanceof Player))
                    && (Main.getInstance().getConfig().getBoolean("combatMessages.incoming.dodgeAttack"))) {
                ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DodgeSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
            }
            e.setDamage(0);
            e.setCancelled(true);
            return true;
        }
        return false;
    }

    public boolean dodgeChanceOnHit(Entity getDefender, PlayerStats defenderStats) {
        double dodgePercent = 0;
        if (getDefender instanceof Player && defenderStats != null) {
            dodgePercent = defenderStats.dodge;
        }

        if (dodgePercent > 1) {
            dodgePercent = 1;
        }

        if (dodgePercent >= Math.random()) {
            return true;
        }
        return false;
    }
}
