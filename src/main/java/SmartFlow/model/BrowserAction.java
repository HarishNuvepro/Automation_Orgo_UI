package SmartFlow.model;

/**
 * Represents a single browser action captured during a recording session.
 * Populated from the JavaScript recorder and serialised to/from JSON.
 */
public class BrowserAction {

    public String action;     // navigate | click | fill | check | uncheck | verify
    public String url;        // for navigate actions
    public String id;         // element id attribute (most stable selector)
    public String selector;   // CSS selector fallback
    public String text;       // visible text of the element
    public String label;      // associated <label> text or placeholder
    public String value;      // filled value (password shown as ****)
    public String inputType;  // text | password | email | checkbox | radio | select
    public String tagName;    // button | input | a | div | select | textarea
    public long   timestamp;  // epoch ms — used for ordering and dedup

    // ── Factory helpers ────────────────────────────────────────────────────────

    public static BrowserAction navigate(String url) {
        BrowserAction a = new BrowserAction();
        a.action    = "navigate";
        a.url       = url;
        a.timestamp = System.currentTimeMillis();
        return a;
    }

    public static BrowserAction verify(String visibleText) {
        BrowserAction a = new BrowserAction();
        a.action    = "verify";
        a.text      = visibleText;
        a.timestamp = System.currentTimeMillis();
        return a;
    }

    @Override
    public String toString() {
        if ("navigate".equals(action)) return "navigate → " + url;
        if ("fill".equals(action))     return "fill [" + (label != null ? label : id) + "] = " + value;
        return action + " [" + (id != null ? "#" + id : selector) + "] \"" + text + "\"";
    }
}
