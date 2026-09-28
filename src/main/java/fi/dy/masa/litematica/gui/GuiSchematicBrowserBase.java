package fi.dy.masa.litematica.gui;

import java.nio.file.Path;
import javax.annotation.Nullable;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;

import fi.dy.masa.malilib.gui.GuiListBase;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetDirectoryEntry;
import fi.dy.masa.malilib.gui.widgets.WidgetFileBrowserBase.DirectoryEntry;
import fi.dy.masa.malilib.gui.widgets.WidgetSearchBar;
import fi.dy.masa.malilib.util.input.ScanCodes;
import fi.dy.masa.litematica.gui.widgets.WidgetSchematicBrowser;

public abstract class GuiSchematicBrowserBase extends GuiListBase<DirectoryEntry, WidgetDirectoryEntry, WidgetSchematicBrowser>
{
    public GuiSchematicBrowserBase(int browserX, int browserY)
    {
        super(browserX, browserY);
    }

    @Override
    public void initGui()
    {
        super.initGui();

        WidgetSchematicBrowser browser = this.getListWidget();

        if (browser != null)
        {
            WidgetSearchBar searchBar = browser.getSearchBarWidget();

            if (searchBar != null)
            {
                searchBar.setSearchOpen(true);
                searchBar.setSearchOpen(false);
            }
        }
    }

    @Override
    public boolean charTyped(CharacterEvent input)
    {
        WidgetSchematicBrowser browser = this.getListWidget();
        WidgetSearchBar searchBar = browser != null ? browser.getSearchBarWidget() : null;

        if (searchBar != null && searchBar.isSearchOpen() == false && input.isAllowedChatCharacter())
        {
            return browser.onCharTyped(input);
        }

        return super.charTyped(input);
    }

    @Override
    public boolean onKeyTyped(KeyEvent input)
    {
        WidgetSchematicBrowser browser = this.getListWidget();
        WidgetSearchBar searchBar = browser != null ? browser.getSearchBarWidget() : null;
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
    protected WidgetSchematicBrowser createListWidget(int listX, int listY)
    {
        // The width and height will be set to the actual values in initGui()
        return new WidgetSchematicBrowser(listX, listY, 100, 100, this, this.getSelectionListener());
    }

    /**
     * This is the string the DataManager uses for saving/loading/storing the last used directory
     * for each browser GUI type/context.
     * @return ()
     */
    public abstract String getBrowserContext();

    public abstract Path getDefaultDirectory();

    @Override
    @Nullable
    protected ISelectionListener<DirectoryEntry> getSelectionListener()
    {
        return null;
    }

    @Override
    protected int getBrowserWidth()
    {
        return this.getScreenWidth() - 20;
    }

    @Override
    protected int getBrowserHeight()
    {
        return this.getScreenHeight() - 70;
    }

    public int getMaxInfoHeight()
    {
        return this.getBrowserHeight();
    }
}
