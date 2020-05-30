package darkkronicle.advancedchat.config;

import darkkronicle.advancedchat.filters.FilteredMessage;

public class ConfigFilter {
    private String trigger;
    private FilteredMessage.FilterResult triggerFilter;
    private boolean active;
    private boolean ignoreCase;
    private String name;

    public ConfigFilter(String trigger, FilteredMessage.FilterResult triggerFilter, boolean active, boolean ignoreCase, String name) {
        this.trigger = trigger;
        this.triggerFilter = triggerFilter;
        this.active = active;
        this.ignoreCase = ignoreCase;
        this.name = name;
    }

    public ConfigFilter() {
        trigger = "Nothing :D";
        triggerFilter = FilteredMessage.FilterResult.BLOCK;
        active = false;
        ignoreCase = false;
        name = "Default";
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public FilteredMessage.FilterResult getTriggerFilter() {
        return triggerFilter;
    }

    public void setTriggerFilter(FilteredMessage.FilterResult triggerFilter) {
        this.triggerFilter = triggerFilter;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}