package de.manu.fprework.models.database;

import de.manu.fprework.utils.javaef.Entity;

public class CharacterSkillBind extends Entity {

    public int id;
    public int charId;
    public int skillId;
    public String bind;

    public CharacterSkillBind(int id, int charId, int skillId, String bind) {
        this.id = id;
        this.charId = charId;
        this.skillId = skillId;
        this.bind = bind;
    }

    public CharacterSkillBind(int charId, int skillId, String bind) {
        this.charId = charId;
        this.skillId = skillId;
        this.bind = bind;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

}
