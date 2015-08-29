package com.toyknight.aeii.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.toyknight.aeii.ResourceManager;
import com.toyknight.aeii.entity.Unit;
import com.toyknight.aeii.manager.GameManager;
import com.toyknight.aeii.screen.GameScreen;
import com.toyknight.aeii.utils.UnitToolkit;

/**
 * Created by toyknight on 6/3/2015.
 */
public class AttackInformationRenderer {

    private final int ts;
    private final GameScreen screen;

    public AttackInformationRenderer(GameScreen screen) {
        this.screen = screen;
        this.ts = screen.getContext().getTileSize();
    }

    private GameManager getManager() {
        return screen.getGameManager();
    }

    public void render(Batch batch) {
        if (getManager().getState() == GameManager.STATE_ATTACK && !getManager().isAnimating()) {
            int cursor_x = screen.getCursorMapX();
            int cursor_y = screen.getCursorMapY();
            Unit attacker = getManager().getSelectedUnit();
            Unit defender = getManager().getGame().getMap().getUnit(cursor_x, cursor_y);
            if (defender != null &&
                    UnitToolkit.isWithinRange(attacker, defender.getX(), defender.getY()) &&
                    getManager().getGame().isEnemy(attacker, defender)) {
                drawInformation(batch, attacker, defender);
            }
        }
    }

    private void drawInformation(Batch batch, Unit attacker, Unit defender) {
        int aw = ts / 24 * 9;
        int ah = ts / 24 * 7; // arrow height
        int hw = ts / 24 * 13; //hud icon width
        int hh = ts / 24 * 16; //hud icon height
        int tfw = ts * 2; //text field width
        float lbh = FontRenderer.getTextFont().getCapHeight(); //label height
        int tfh = FontRenderer.getSCharHeight() + ts / 12; //text field height
        int lmargin = (screen.getViewportWidth() - hw * 4 - tfw * 4 - ts / 24 * 7) / 2; //margin left
        int infoh = tfh * 2 + ts / 24 * 3; //information panel height
        int cursor_sy = screen.getYOnScreen(screen.getCursorMapY());
        int infoy = cursor_sy > (screen.getViewportHeight() - ts) / 2 + ts ?
                ts + ts / 12 : ts + screen.getViewportHeight() - infoh - ts / 12; //information panel y

        //draw background
        batch.draw(ResourceManager.getBorderDarkColor(), 0, infoy - ts / 24, screen.getViewportWidth(), infoh);
        batch.draw(ResourceManager.getBorderLightColor(), 0, infoy, screen.getViewportWidth(), infoh);

        //draw icons
        batch.draw(ResourceManager.getBattleHudIcon(0), lmargin, infoy + (infoh - hh) / 2, hw, hh);
        batch.draw(ResourceManager.getBattleHudIcon(1), lmargin + hw + tfw + ts / 24 * 2, infoy + (infoh - hh) / 2, hw, hh);
        batch.draw(ResourceManager.getBattleHudIcon(2), lmargin + hw * 2 + tfw * 2 + ts / 24 * 4, infoy + (infoh - hh) / 2, hw, hh);
        batch.draw(ResourceManager.getBattleHudIcon(3), lmargin + hw * 3 + tfw * 3 + ts / 24 * 6, infoy + (infoh - hh) / 2, hw, hh);

        //draw text field
        batch.draw(ResourceManager.getPanelBackground(), lmargin + hw + ts / 24, infoy + ts / 24, tfw, tfh);
        batch.draw(ResourceManager.getPanelBackground(), lmargin + hw + ts / 24, infoy + tfh + ts / 24 * 2, tfw, tfh);
        batch.draw(ResourceManager.getPanelBackground(), lmargin + hw * 2 + tfw + ts / 24 * 3, infoy + ts / 24, tfw, tfh);
        batch.draw(ResourceManager.getPanelBackground(), lmargin + hw * 2 + tfw + ts / 24 * 3, infoy + tfh + ts / 24 * 2, tfw, tfh);
        batch.draw(ResourceManager.getPanelBackground(), lmargin + hw * 3 + tfw * 2 + ts / 24 * 5, infoy + ts / 24, tfw, tfh);
        batch.draw(ResourceManager.getPanelBackground(), lmargin + hw * 3 + tfw * 2 + ts / 24 * 5, infoy + tfh + ts / 24 * 2, tfw, tfh);
        batch.draw(ResourceManager.getPanelBackground(), lmargin + hw * 4 + tfw * 3 + ts / 24 * 7, infoy + ts / 24, tfw, tfh);
        batch.draw(ResourceManager.getPanelBackground(), lmargin + hw * 4 + tfw * 3 + ts / 24 * 7, infoy + tfh + ts / 24 * 2, tfw, tfh);

        batch.draw(ResourceManager.getTeamBackground(attacker.getTeam()), lmargin + hw + ts / 24 * 2, infoy + ts / 24 * 2, ts / 4, FontRenderer.getSCharHeight());
        batch.draw(ResourceManager.getTeamBackground(attacker.getTeam()), lmargin + hw * 2 + tfw + ts / 24 * 4, infoy + ts / 24 * 2, ts / 4, FontRenderer.getSCharHeight());
        batch.draw(ResourceManager.getTeamBackground(attacker.getTeam()), lmargin + hw * 3 + tfw * 2 + ts / 24 * 6, infoy + ts / 24 * 2, ts / 4, FontRenderer.getSCharHeight());
        batch.draw(ResourceManager.getTeamBackground(attacker.getTeam()), lmargin + hw * 4 + tfw * 3 + ts / 24 * 8, infoy + ts / 24 * 2, ts / 4, FontRenderer.getSCharHeight());

        batch.draw(ResourceManager.getTeamBackground(defender.getTeam()), lmargin + hw + ts / 24 * 2, infoy + tfh + ts / 24 * 3, ts / 4, FontRenderer.getSCharHeight());
        batch.draw(ResourceManager.getTeamBackground(defender.getTeam()), lmargin + hw * 2 + tfw + ts / 24 * 4, infoy + tfh + ts / 24 * 3, ts / 4, FontRenderer.getSCharHeight());
        batch.draw(ResourceManager.getTeamBackground(defender.getTeam()), lmargin + hw * 3 + tfw * 2 + ts / 24 * 6, infoy + tfh + ts / 24 * 3, ts / 4, FontRenderer.getSCharHeight());
        batch.draw(ResourceManager.getTeamBackground(defender.getTeam()), lmargin + hw * 4 + tfw * 3 + ts / 24 * 8, infoy + tfh + ts / 24 * 3, ts / 4, FontRenderer.getSCharHeight());

        //get tiles
        int attacker_tile = getManager().getGame().getMap().getTileIndex(attacker.getX(), attacker.getY());
        int defender_tile = getManager().getGame().getMap().getTileIndex(defender.getX(), defender.getY());

        //draw attack
        switch (attacker.getAttackType()) {
            case Unit.ATTACK_PHYSICAL:
                FontRenderer.setTextColor(ResourceManager.getPhysicalAttackColor());
                break;
            case Unit.ATTACK_MAGICAL:
                FontRenderer.setTextColor(ResourceManager.getMagicalAttackColor());
                break;
        }
        int attacker_atk = attacker.getAttack();
        int attacker_atk_bonus = UnitToolkit.getAttackBonus(attacker, defender, attacker_tile);
        String attacker_attack_str = Integer.toString(attacker_atk + attacker_atk_bonus);
        FontRenderer.drawText(batch, attacker_attack_str,
                lmargin + hw + ts / 24 * 3 + ts / 4,
                infoy + ts / 24 + (tfh - lbh) / 2 + lbh);
        if (attacker_atk_bonus > 0) {
            float attack_width = FontRenderer.getTextFont().getBounds(attacker_attack_str).width;
            batch.draw(ResourceManager.getArrowIcon(1),
                    lmargin + hw + ts / 24 * 4 + ts / 4 + attack_width,
                    infoy + ts / 24 + (tfh - ah) / 2,
                    aw, ah);
        }

        switch (defender.getAttackType()) {
            case Unit.ATTACK_PHYSICAL:
                FontRenderer.setTextColor(ResourceManager.getPhysicalAttackColor());
                break;
            case Unit.ATTACK_MAGICAL:
                FontRenderer.setTextColor(ResourceManager.getMagicalAttackColor());
                break;
        }
        int defender_atk = defender.getAttack();
        int defender_atk_bonus = UnitToolkit.getAttackBonus(defender, attacker, defender_tile);
        int modified_defender_atk = UnitToolkit.canCounter(defender, attacker) ? defender_atk + defender_atk_bonus : 0;
        String defender_attack_str = Integer.toString(modified_defender_atk);
        FontRenderer.drawText(batch, defender_attack_str,
                lmargin + hw + ts / 24 * 3 + ts / 4,
                infoy + tfh + ts / 24 * 2 + (tfh - lbh) / 2 + lbh);
        if (modified_defender_atk > 0 && defender_atk_bonus > 0) {
            float attack_width = FontRenderer.getTextFont().getBounds(defender_attack_str).width;
            batch.draw(ResourceManager.getArrowIcon(1),
                    lmargin + hw + ts / 24 * 4 + ts / 4 + attack_width,
                    infoy + tfh + ts / 24 * 2 + (tfh - ah) / 2,
                    aw, ah);
        }

        //paint defence
        FontRenderer.setTextColor(Color.WHITE);
        int attacker_defence_bonus = UnitToolkit.getDefenceBonus(attacker, attacker_tile);
        int attacker_p_defence = attacker.getPhysicalDefence();
        String attacker_p_defence_str = Integer.toString(attacker_p_defence + attacker_defence_bonus);
        FontRenderer.drawText(batch, attacker_p_defence_str,
                lmargin + hw * 2 + tfw + ts / 24 * 5 + ts / 4,
                infoy + ts / 24 + (tfh - lbh) / 2 + lbh);
        int attacker_m_defence = attacker.getMagicalDefence();
        String attacker_m_defence_str = Integer.toString(attacker_m_defence + attacker_defence_bonus);
        FontRenderer.drawText(batch, attacker_m_defence_str,
                lmargin + hw * 3 + tfw * 2 + ts / 24 * 7 + ts / 4,
                infoy + ts / 24 + (tfh - lbh) / 2 + lbh);
        if (attacker_defence_bonus > 0) {
            float p_defence_width = FontRenderer.getTextFont().getBounds(attacker_p_defence_str).width;
            batch.draw(ResourceManager.getArrowIcon(1),
                    lmargin + hw * 2 + tfw + ts / 24 * 6 + ts / 4 + p_defence_width,
                    infoy + ts / 24 + (tfh - ah) / 2,
                    aw, ah);
            float m_defence_width = FontRenderer.getTextFont().getBounds(attacker_m_defence_str).width;
            batch.draw(ResourceManager.getArrowIcon(1),
                    lmargin + hw * 3 + tfw * 2 + ts / 24 * 8 + ts / 4 + m_defence_width,
                    infoy + ts / 24 + (tfh - ah) / 2,
                    aw, ah);
        }

        int defender_defence_bonus = UnitToolkit.getDefenceBonus(defender, defender_tile);
        int defender_p_defence = defender.getPhysicalDefence();
        String defender_p_defence_str = Integer.toString(defender_p_defence + defender_defence_bonus);
        FontRenderer.drawText(batch, defender_p_defence_str,
                lmargin + hw * 2 + tfw + ts / 24 * 5 + ts / 4,
                infoy + tfh + ts / 24 * 2 + (tfh - lbh) / 2 + lbh);
        int defender_m_defence = defender.getMagicalDefence();
        String defender_m_defence_str = Integer.toString(defender_m_defence + defender_defence_bonus);
        FontRenderer.drawText(batch, defender_m_defence_str,
                lmargin + hw * 3 + tfw * 2 + ts / 24 * 7 + ts / 4,
                infoy + tfh + ts / 24 * 2 + (tfh - lbh) / 2 + lbh);
        if (defender_defence_bonus > 0) {
            float p_defence_width = FontRenderer.getTextFont().getBounds(defender_p_defence_str).width;
            batch.draw(ResourceManager.getArrowIcon(1),
                    lmargin + hw * 2 + tfw + ts / 24 * 6 + ts / 4 + p_defence_width,
                    infoy + tfh + ts / 24 * 2 + (tfh - ah) / 2,
                    aw, ah);
            float m_defence_width = FontRenderer.getTextFont().getBounds(defender_m_defence_str).width;
            batch.draw(ResourceManager.getArrowIcon(1),
                    lmargin + hw * 3 + tfw * 2 + ts / 24 * 8 + ts / 4 + m_defence_width,
                    infoy + tfh + ts / 24 * 2 + (tfh - ah) / 2,
                    aw, ah);
        }

        //draw level
        int attacker_level = attacker.getLevel();
        FontRenderer.drawText(batch, Integer.toString(attacker_level),
                lmargin + hw * 4 + tfw * 3 + ts / 24 * 9 + ts / 4,
                infoy + ts / 24 + (tfh - lbh) / 2 + lbh);
        int defender_level = defender.getLevel();
        FontRenderer.drawText(batch, Integer.toString(defender_level),
                lmargin + hw * 4 + tfw * 3 + ts / 24 * 9 + ts / 4,
                infoy + tfh + ts / 24 * 2 + (tfh - lbh) / 2 + lbh);
        batch.flush();
    }

}