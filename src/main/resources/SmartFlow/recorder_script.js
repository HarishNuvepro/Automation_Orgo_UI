// SmartFlow Browser Action Recorder — injected by ActionRecorder.java
// Re-injected on every page navigation so no actions are lost.

(function () {
    if (window.__sfRecorderActive) return; // already injected on this page
    window.__sfRecorderActive = true;
    window.__sfActions = window.__sfActions || [];

    // ── Helpers ────────────────────────────────────────────────────────────────

    function getStableSelector(el) {
        if (!el) return 'unknown';
        if (el.id) return '#' + el.id;
        var name = el.getAttribute('name');
        if (name) return el.tagName.toLowerCase() + '[name="' + name + '"]';
        var testid = el.getAttribute('data-testid');
        if (testid) return '[data-testid="' + testid + '"]';
        var role = el.getAttribute('role');
        if (role && el.tagName) return el.tagName.toLowerCase() + '[role="' + role + '"]';
        return el.tagName ? el.tagName.toLowerCase() : 'element';
    }

    function getLabel(el) {
        if (!el) return null;
        if (el.id) {
            var lbl = document.querySelector('label[for="' + el.id + '"]');
            if (lbl) return lbl.innerText.trim();
        }
        if (el.placeholder) return el.placeholder;
        if (el.getAttribute('aria-label')) return el.getAttribute('aria-label');
        if (el.getAttribute('title')) return el.getAttribute('title');
        return null;
    }

    function cleanText(el) {
        if (!el) return null;
        var t = (el.innerText || el.value || el.textContent || '').trim();
        return t ? t.replace(/\s+/g, ' ').substring(0, 100) : null;
    }

    function walkUp(el, maxDepth) {
        var depth = 0;
        while (el && depth < maxDepth) {
            if (el.id || (el.innerText && el.innerText.trim())) return el;
            el = el.parentElement;
            depth++;
        }
        return el;
    }

    // ── Click listener ─────────────────────────────────────────────────────────

    document.addEventListener('click', function (e) {
        var el = walkUp(e.target, 5);
        if (!el || el === document.body || el === document.documentElement) return;

        var action = {
            action:    'click',
            id:        el.id || null,
            text:      cleanText(el),
            tagName:   el.tagName ? el.tagName.toLowerCase() : null,
            selector:  getStableSelector(el),
            timestamp: Date.now()
        };

        // attach label for form-adjacent buttons
        var label = getLabel(el);
        if (label) action.label = label;

        window.__sfActions.push(action);
    }, true);

    // ── Change (fill) listener ─────────────────────────────────────────────────

    document.addEventListener('change', function (e) {
        var el = e.target;
        if (!el || !['INPUT', 'TEXTAREA', 'SELECT'].includes(el.tagName)) return;

        window.__sfActions.push({
            action:    'fill',
            id:        el.id || null,
            label:     getLabel(el),
            value:     el.type === 'password' ? '****' : (el.value || ''),
            inputType: el.type || null,
            tagName:   el.tagName.toLowerCase(),
            selector:  getStableSelector(el),
            timestamp: Date.now()
        });
    }, true);

    // ── Select / checkbox / radio ──────────────────────────────────────────────

    document.addEventListener('input', function (e) {
        var el = e.target;
        if (!el) return;
        if (el.type === 'checkbox' || el.type === 'radio') {
            window.__sfActions.push({
                action:    el.checked ? 'check' : 'uncheck',
                id:        el.id || null,
                label:     getLabel(el),
                selector:  getStableSelector(el),
                timestamp: Date.now()
            });
        }
    }, true);

})();
