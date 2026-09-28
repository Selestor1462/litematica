package fi.dy.masa.litematica.gui;

import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;

import fi.dy.masa.litematica.gui.GuiMainMenu.ButtonListenerChangeMenu;
import fi.dy.masa.litematica.gui.widgets.WidgetListLoadedSchematics;
import fi.dy.masa.litematica.gui.widgets.WidgetSchematicEntry;
import fi.dy.masa.litematica.schematic.LitematicaSchematic;
import fi.dy.masa.malilib.gui.GuiListBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.widgets.WidgetSearchBar;
import fi.dy.masa.malilib.util.StringUtils;
import fi.dy.masa.malilib.util.input.ScanCodes;

public class GuiSchematicLoadedList extends GuiListBase<LitematicaSchematic, WidgetSchematicEntry, WidgetListLoadedSchematics>
{
    public GuiSchematicLoadedList()
    {
        super(12, 30);

        this.title = StringUtils.translate("litematica.gui.title.manage_loaded_schematics");
    }

    @Override
    public boolean charTyped(CharacterEvent input)
    {
        WidgetListLoadedSchematics list = this.getListWidget();
        WidgetSearchBar searchBar = list != null ? list.getSearchBarWidget() : null;

        if (searchBar != null && searchBar.isSearchOpen() == false && input.isAllowedChatCharacter())
        {
            return list.onCharTyped(input);
        }

        return super.charTyped(input);
    }

    @Override
    public boolean onKeyTyped(KeyEvent input)
    {
        WidgetListLoadedSchematics list = this.getListWidget();
        WidgetSearchBar searchBar = list != null ? list.getSearchBarWidget() : null;
        boolean wasSearchOpen = searchBar != null && searchBar.isSearchOpen();
        boolean handled = super.onKeyTyped(input);

        if (wasSearchOpen && searchBar.isSearchOpen() == false && input.key() == ScanCodes.SCAN_ESCAPE)
        {
            searchBar.setSearchOpen(true);
            searchBar.setSearchOpen(false);
        }

        return handled;
    }

    @Override
    protected int getBrowserWidth()
    {
        return this.getScreenWidth() - 20;
    }

    @Override
    protected int getBrowserHeight()
    {
        return this.getScreenHeight() - 68;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        WidgetSearchBar searchBar = this.getListWidget().getSearchBarWidget();
        searchBar.setSearchOpen(true);
        searchBar.setSearchOpen(false);

        int x = 12;
        int y = this.getScreenHeight() - 26;
        int buttonWidth;
        String label;
        ButtonGeneric button;

        ButtonListenerChangeMenu.ButtonType type = ButtonListenerChangeMenu.ButtonType.LOAD_SCHEMATICS;
        label = StringUtils.translate(type.getLabelKey());
        buttonWidth = this.getStringWidth(label) + 30;
        button = new ButtonGeneric(x, y, buttonWidth, 20, label, type.getIcon());
        this.addButton(button, new ButtonListenerChangeMenu(type, this.getParent()));
        x += buttonWidth + 4;

        type = ButtonListenerChangeMenu.ButtonType.SCHEMATIC_PLACEMENTS;
        label = StringUtils.translate(type.getLabelKey());
        buttonWidth = this.getStringWidth(label) + 30;
        button = new ButtonGeneric(x, y, buttonWidth, 20, label, type.getIcon());
        this.addButton(button, new ButtonListenerChangeMenu(type, this.getParent()));

        type = ButtonListenerChangeMenu.ButtonType.MAIN_MENU;
        label = StringUtils.translate(type.getLabelKey());
        buttonWidth = this.getStringWidth(label) + 20;
        x = this.getScreenWidth() - buttonWidth - 10;
        button = new ButtonGeneric(x, y, buttonWidth, 20, label);
        this.addButton(button, new ButtonListenerChangeMenu(type, this.getParent()));
    }

    @Override
    protected WidgetListLoadedSchematics createListWidget(int listX, int listY)
    {
        return new WidgetListLoadedSchematics(listX, listY, this.getBrowserWidth(), this.getBrowserHeight(), null);
    }
}
