package GameControl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ABPActionManagerReader extends AIOJSONHelper {

    public ABPActionManagerReader(String filename) {
        super (filename);
    }

    public AResourceManager<ABPAction> initializeActionManager(AResourceManager<ABPType> t) throws IOException, ParseException {
        JSONObject jo = getJSON();
        AResourceManager<ABPAction> m = new AResourceManager<>();

        JSONArray items = (JSONArray) jo.get("Actions");
        Iterator ii = items.iterator();

        while (ii.hasNext())
        {
            // Get the action's specific data object
            JSONObject oi = (JSONObject) ii.next();
            // Get the basic data about the Action
            int id = getInt(oi, "ID");
            String name = getString(oi, "Name");
            int typeID = getInt(oi, "TypeID");
            ABPType type = t.getItemByID(typeID);
            // Get the Action Effects for the object
            ArrayList<ABPActionEffect> effects = new ArrayList<>();
            Iterator aei = ((JSONArray) oi.get("ActionEffects")).iterator();
            while (aei.hasNext()) {
                JSONObject ae = (JSONObject) aei.next();
                ABPActionEffect eff;
                // Create the Action Effect based off the "Type" parameter
                String effectType = getString(ae, "EffectType");
                switch (effectType) {
                    default:
                        throw new RuntimeException("Invalid effect type \"" + effectType + "\"");
                    case "DAMAGE":
                        int damage = getInt(ae, "Damage");
                        eff = new ABPActionAttack(damage);
                        break;
                    case "STAT":
                        double modifier = getDouble(ae, "Modifier");
                        boolean doesEffectCaster = (getInt(ae, "DoesEffectCaster") == 1);
                        AStats.STATS toEffect;
                        String toEffectString = getString(ae, "Stat");
                        switch (toEffectString) {
                            default:
                                throw new RuntimeException("Invalid stat target \"" + toEffectString + "\"");
                            case "HP":
                                toEffect = AStats.STATS.HP;
                                break;
                            case "AS":
                                toEffect = AStats.STATS.AS;
                                break;
                            case "AD":
                                toEffect = AStats.STATS.AD;
                                break;
                            case "AP":
                                toEffect = AStats.STATS.AP;
                                break;
                            case "APP":
                                toEffect = AStats.STATS.APP;
                                break;
                            case "SP":
                                toEffect = AStats.STATS.SP;
                                break;
                            case "ED":
                                toEffect = AStats.STATS.ED;
                                break;
                        }
                        eff = new ABPActionStatEffect(doesEffectCaster, toEffect, modifier);
                        break;
                }
                effects.add(eff);
            }
            // Construct the object
            ABPAction action = new ABPAction(id, type, name, effects.toArray(ABPActionEffect[]::new));
            m.addItem(action);
        }

        if (!m.verifyIDNumbers()) {
            throw new IOException("Multiple Actions exist with the same ID");
        }

        return m;
    }

}
