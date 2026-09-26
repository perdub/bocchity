import re, json, yaml
from pathlib import Path

root = Path(__file__).parent
workflow = yaml.safe_load((root/".github/workflows/build.yml").read_text(encoding="utf-8"))
assert "build" in workflow["jobs"]
steps = workflow["jobs"]["build"]["steps"]
assert any(s.get("run", "").strip() == "gradle clean build --no-daemon" for s in steps)
assert any(s.get("uses", "").startswith("actions/upload-artifact@") for s in steps)
assert any(s.get("uses", "").startswith("actions/setup-java@") for s in steps)

# Balance Groovy delimiters outside strings/comments as a coarse syntax sanity check.
s = (root/"build.gradle").read_text(encoding="utf-8")
# Strip comments and quoted strings conservatively.
clean=[]; i=0; state=None; quote=None
while i < len(s):
    c=s[i]; n=s[i+1] if i+1 < len(s) else ''
    if state == 'line':
        if c == '\n': state=None; clean.append(c)
        i += 1; continue
    if state == 'block':
        if c == '*' and n == '/': state=None; i += 2
        else:
            if c == '\n': clean.append(c)
            i += 1
        continue
    if state == 'string':
        if c == '\\': i += 2; continue
        if c == quote: state=None
        if c == '\n': clean.append(c)
        i += 1; continue
    if c == '/' and n == '/': state='line'; i += 2; continue
    if c == '/' and n == '*': state='block'; i += 2; continue
    if c in "'\"": state='string'; quote=c; i += 1; continue
    clean.append(c); i += 1
stack=[]; pairs={')':'(',']':'[','}':'{'}
for c in clean:
    if c in '([{': stack.append(c)
    elif c in ')]}':
        assert stack and stack[-1] == pairs[c], f"Delimiter mismatch at {c}"
        stack.pop()
assert not stack, f"Unclosed delimiters: {stack}"
print("build.gradle coarse syntax check: PASS")
print("workflow structure check: PASS")
